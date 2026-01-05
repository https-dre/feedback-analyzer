package br.httpsdre.feedback_analyzer.infra.webhook;

public record WebhookPayload(
        String eventType,
        String batchId,
        String status,
        Long totalCompleted,
        Long totalFailed
) {
}
