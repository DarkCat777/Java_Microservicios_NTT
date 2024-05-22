package org.nttdata.credits_service.mapper;

import org.mapstruct.*;
import org.nttdata.credits_service.domain.dto.CreditCardDto;
import org.nttdata.credits_service.domain.entity.CreditCard;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICreditCardMapper {
    CreditCard toEntity(CreditCardDto creditCardDto);

    CreditCardDto toDto(CreditCard creditCard);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CreditCard partialUpdate(CreditCardDto creditCardDto, @MappingTarget CreditCard creditCard);
}