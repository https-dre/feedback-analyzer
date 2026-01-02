package br.httpsdre.feedback_analyzer.models;

import br.httpsdre.feedback_analyzer.types.AnalysisStatus;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "analysis_batch")
@Entity
public class Batch {
  @Id
  private UUID id;
  @Column(name = "tenant_id")
  private String tenantId;
  @Enumerated(EnumType.STRING)
  private AnalysisStatus status;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "completed_at")
  private LocalDateTime completedAt;
  @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
  private List<Feedback> feedbacks;

  public Batch(String tenantId, List<Feedback> feedbacks) {
    this.id = UuidCreator.getTimeOrderedEpoch();
    this.tenantId = tenantId;
    this.status = AnalysisStatus.PENDING;
    this.createdAt = LocalDateTime.now();
    this.feedbacks = feedbacks;
  }
}
