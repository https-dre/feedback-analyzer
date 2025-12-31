package br.httpsdre.feedback_analyzer.adapters;

import br.httpsdre.feedback_analyzer.dtos.AnalysisResult;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface FeedbackAnalysisGateway {
  @SystemMessage("""
          Você é um especialista em análise de feedback de clientes para um produto SaaS.
          
          Sua tarefa é analisar o feedback recebido e extrair três informações:
          1. Categoria (FINANCIAL, TECHNICAL_SUPPORT, SALES, COMPLAINT, OTHER).
          2. Sentimento (POSITIVE, NEUTRAL, NEGATIVE).
          3. Um resumo curto (máximo 100 caracteres) em português.
          
          Responda estritamente com o objeto JSON solicitado.
          """)
  AnalysisResult analyze(@UserMessage String feedbackBody);
}
