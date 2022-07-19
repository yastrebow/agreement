package ru.yastrebov.agreement.mapstruct;

import org.mapstruct.Mapper;
import ru.yastrebov.agreement.model.ProcessedRequest;
import ru.yastrebov.agreementStatisticsLib.model.AgreementStatistics;

@Mapper(componentModel = "spring")
public interface AgreementMapper {

    AgreementStatistics requestToAgreement(ProcessedRequest processedRequest);

    ProcessedRequest agreementToRequest(AgreementStatistics agreementsStatistics);


}
