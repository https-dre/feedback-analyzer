package br.httpsdre.feedback_analyzer.controllers;

import br.httpsdre.feedback_analyzer.application.BatchService;
import br.httpsdre.feedback_analyzer.dtos.BatchDTO;
import br.httpsdre.feedback_analyzer.dtos.BatchSummaryResponse;
import br.httpsdre.feedback_analyzer.dtos.CreateBatchDTO;
import br.httpsdre.feedback_analyzer.dtos.CreateBatchResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/batches")
public class BatchController {
  @Autowired
  private BatchService service;

  @PostMapping
  public ResponseEntity<CreateBatchResponse> createBatch(@RequestBody @Valid CreateBatchDTO dto) {
    var batch_id = this.service.createBatch(dto.tenantId(), dto.feedbacks());
    return ResponseEntity.accepted()
            .body(new CreateBatchResponse(batch_id.toString(), "Processing batch"));
  }

  @GetMapping("/tenant/{tenantId}")
  public ResponseEntity<List<BatchSummaryResponse>>
    getBatchesByTenantId(@PathVariable String tenantId) {
    return ResponseEntity.ok(this.service.getBatchesByTenantId(tenantId));
  }

  @GetMapping("/{batch_id}")
  public ResponseEntity<BatchDTO> getBatchById(@PathVariable String batch_id) {
    return ResponseEntity.ok(this.service.getBatchById(batch_id));
  }
}
