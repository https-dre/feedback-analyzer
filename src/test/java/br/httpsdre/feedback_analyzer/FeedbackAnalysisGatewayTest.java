package br.httpsdre.feedback_analyzer;

import br.httpsdre.feedback_analyzer.adapters.FeedbackAnalysisGateway;
import br.httpsdre.feedback_analyzer.dtos.AnalysisResult;
import br.httpsdre.feedback_analyzer.types.Category;
import br.httpsdre.feedback_analyzer.types.Sentiment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = FeedbackAnalyzerApplication.class)
@ExtendWith(OutputCaptureExtension.class)
public class FeedbackAnalysisGatewayTest {
  private static final Logger log = LoggerFactory.getLogger(FeedbackAnalysisGatewayTest.class);

  @Autowired
  private FeedbackAnalysisGateway analysisGateway;

  @Test
  @DisplayName("Deve analisar um feedback negativo financeiro corretamente")
  void negativeFeedback() {
    String feedbackTexto = "Estou muito irritado, veio uma cobrança duplicada no meu cartão de crédito este mês!";

    log.info("Enviando para o Gemini: {}", feedbackTexto);
    AnalysisResult resultado = this.analysisGateway.analyze(feedbackTexto);

    log.info("Resposta do Gemini: {}", resultado);

    assertThat(resultado).isNotNull();
    assertThat(resultado.category()).isEqualTo(Category.FINANCIAL);
    assertThat(resultado.sentiment()).isEqualTo(Sentiment.NEGATIVE);
    assertThat(resultado.summary()).isNotBlank();
    assertThat(resultado.summary().length()).isLessThanOrEqualTo(100);
  }

  @Test
  @DisplayName("Deve analisar um feedback positivo técnico corretamente")
  void shouldAnalyzePositiveTechnicalFeedback() {
    String feedbackTexto = "A nova atualização deixou o sistema muito rápido, parabéns aos desenvolvedores!";
    AnalysisResult resultado = this.analysisGateway.analyze(feedbackTexto);

    log.info("Resposta do Gemini: {}", resultado);

    assertThat(resultado.sentiment()).isEqualTo(Sentiment.POSITIVE);
    assertThat(resultado.category()).isIn(Category.TECHNICAL_SUPPORT, Category.OTHER);
  }

  @Test
  @DisplayName("Deve analisar feedback e verificar se o LOG foi escrito")
  void shouldAnalyzeAndLog(CapturedOutput output) {

    String feedbackTexto = "O sistema está lento demais.";
    this.analysisGateway.analyze(feedbackTexto);

    assertThat(output.getOut()).contains("Enviando para o Gemini");
    assertThat(output.getOut()).contains("O sistema está lento demais");
    assertThat(output.getOut()).contains("Resposta do Gemini");
  }
}
