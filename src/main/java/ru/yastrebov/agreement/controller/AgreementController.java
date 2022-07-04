package ru.yastrebov.agreement.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yastrebov.agreement.model.ProcessedRequestDTO;
import ru.yastrebov.agreement.service.AgreementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generate-agreement")
public class AgreementController {

    private final AgreementService agreementService;

    @Operation(
            summary = "Get processed request from DB 'processed_requests",
            description = "Get processed request from DB \"processed_requests\" for agreement creating")
    @GetMapping("{id}")
    public ResponseEntity<ProcessedRequestDTO> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(agreementService.getRequestById(id));
    }
}
