package br.httpsdre.feedback_analyzer.repositories;

import br.httpsdre.feedback_analyzer.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BatchRepository extends JpaRepository<Batch, UUID> {
}
