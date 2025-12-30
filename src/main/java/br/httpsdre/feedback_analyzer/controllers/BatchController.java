package br.httpsdre.feedback_analyzer.controllers;

import br.httpsdre.feedback_analyzer.core.CreateBatchUseCase;
import br.httpsdre.feedback_analyzer.dtos.CreateBatchDTO;
import br.httpsdre.feedback_analyzer.dtos.CreateBatchResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/batches")
public class BatchController {
  @Autowired
  private CreateBatchUseCase service;

  @PostMapping
  public ResponseEntity<CreateBatchResponse> createBatch(@Valid @RequestBody CreateBatchDTO dto) {
    var batch_id = this.service.execute(dto.tenantId(), dto.feedbacks());
    return ResponseEntity.accepted()
            .body(new CreateBatchResponse(batch_id.toString(), "Processing batch"));
  }
}
