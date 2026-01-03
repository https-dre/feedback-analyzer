package br.httpsdre.feedback_analyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
            .info(new Info()
                    .title("Feedback Analyzer")
                    .version("1.0")
                    .description("Documentação de API para processamento em lote de feedbacks com LLMs")
            );
  }
}
