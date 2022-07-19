package ru.yastrebov.agreement.service;

import ru.yastrebov.agreement.model.ProcessedRequestDTO;

public interface AgreementService {

    ProcessedRequestDTO getRequestById(Long id);
}
