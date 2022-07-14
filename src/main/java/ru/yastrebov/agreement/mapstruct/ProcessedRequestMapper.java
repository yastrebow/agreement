package ru.yastrebov.agreement.mapstruct;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import ru.yastrebov.agreement.model.ProcessedRequest;
import ru.yastrebov.agreement.model.ProcessedRequestDTO;
@Mapper(componentModel = "spring")
public interface ProcessedRequestMapper {
    ProcessedRequestDTO entityToDto(ProcessedRequest processedRequest);
    ProcessedRequest dtoToEntity(ProcessedRequestDTO processedRequestDTO);
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    void updateFromDtoToEntity(ProcessedRequestDTO processedRequestDTO, @MappingTarget ProcessedRequest processedRequest);

}
