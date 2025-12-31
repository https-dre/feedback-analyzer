package br.httpsdre.feedback_analyzer.models;

import br.httpsdre.feedback_analyzer.types.Category;
import br.httpsdre.feedback_analyzer.types.Sentiment;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "feedback_message")
public class Feedback {
  @Id
  private UUID id;
  @Column(name = "original_body", columnDefinition = "TEXT")
  private String originalBody;
  @Enumerated(EnumType.STRING)
  private Category category;
  @Enumerated(EnumType.STRING)
  private Sentiment sentiment;
  @Column(columnDefinition = "TEXT")
  private String summary;
  @Column(name = "is_analyzed")
  private boolean isAnalyzed;
  @ManyToOne
  @JoinColumn(name = "batch_id")
  private Batch batch;

  public Feedback(String content) {
    this.id = UuidCreator.getTimeOrderedEpoch();
    this.originalBody = content;
    this.category = Category.UNKNOWN;
    this.sentiment = Sentiment.UNDEFINED;
    this.isAnalyzed = false;
  }

  public Feedback(String content, Batch parentBatch) {
    this.id = UuidCreator.getTimeOrderedEpoch();
    this.originalBody = content;
    this.category = Category.UNKNOWN;
    this.sentiment = Sentiment.UNDEFINED;
    this.isAnalyzed = false;
    this.batch = parentBatch;
  }
}
