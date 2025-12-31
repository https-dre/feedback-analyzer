package br.httpsdre.feedback_analyzer.batch;

import br.httpsdre.feedback_analyzer.batch.listeners.BatchCompletionListener;
import br.httpsdre.feedback_analyzer.batch.processors.FeedbackAnalysisProcessor;
import br.httpsdre.feedback_analyzer.models.Feedback;
import br.httpsdre.feedback_analyzer.repositories.FeedbackRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Configuration
public class BatchConfig {

  @Bean
  @StepScope
  public RepositoryItemReader<Feedback> feedbackReader(
          FeedbackRepository repository,
          @Value("#{jobParameters['batchId']}") String batchIdStr
  ) {
    UUID batchId = UUID.fromString(batchIdStr);
    return new RepositoryItemReaderBuilder<Feedback>()
            .name("feedbackItemReader")
            .repository(repository)
            .methodName("findByBatchId")
            .arguments(Collections.singletonList(batchId))
            .pageSize(10)
            .sorts(Map.of("id", Sort.Direction.ASC))
            .saveState(false)
            .build();
  }

  @Bean
  public RepositoryItemWriter<Feedback>
    feedbackWriter(FeedbackRepository repository) {
    return new RepositoryItemWriterBuilder<Feedback>()
            .repository(repository)
            .methodName("save")
            .build();
  }

  @Bean
  public Step analysisStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           RepositoryItemReader<Feedback> reader,
                           FeedbackAnalysisProcessor processor,
                           RepositoryItemWriter<Feedback> writer) {
    return new StepBuilder("analysisStep", jobRepository)
            .<Feedback, Feedback>chunk(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
  }

  @Bean
  public Job feedbackAnalysisJob(Step analysisStep,
                                 JobRepository jobRepository,
                                 BatchCompletionListener listener) {
    return new JobBuilder("feedbackAnalysisJob", jobRepository)
            .start(analysisStep)
            .listener(listener)
            .incrementer(new RunIdIncrementer())
            .build();
  }
}
