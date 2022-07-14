package ru.yastrebov.agreement.service;

import ru.yastrebov.agreement.model.ProcessedRequest;
import ru.yastrebov.agreement.model.ProcessedRequestDTO;

public interface AgreementService {

    ProcessedRequestDTO getRequestById(Long id);

    public void makeAgreement(ProcessedRequest requestForAgreement);
}
