package ru.yastrebov.agreement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yastrebov.agreement.model.ProcessedRequest;
import ru.yastrebov.agreement.model.ProcessedRequestDTO;

@Repository
public interface ProcessedRequestRepository extends JpaRepository <ProcessedRequest, Long> {
}
