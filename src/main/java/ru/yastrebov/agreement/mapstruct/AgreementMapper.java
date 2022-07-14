package ru.yastrebov.agreement.mapstruct;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import ru.yastrebov.agreement.model.ProcessedRequest;
import ru.yastrebov.agreementStatisticsLib.model.AgreementStatistics;

@Mapper(componentModel = "spring")
public interface AgreementMapper {

    AgreementStatistics requestToAgreement(ProcessedRequest processedRequest);

    ProcessedRequest agreementToRequest(AgreementStatistics agreementsStatistics);

    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateFromAgreementToStatistics(AgreementStatistics agreementsStatistics, @MappingTarget ProcessedRequest processedRequest);

}
