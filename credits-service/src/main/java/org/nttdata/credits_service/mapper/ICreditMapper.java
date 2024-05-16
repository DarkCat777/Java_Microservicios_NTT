package org.nttdata.credits_service.mapper;

import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.domain.entity.Credit;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;

/**
 * Mapea de una entidad a un DTO
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICreditMapper {

    /**
     * Convierte un DTO de crédito a una entidad de crédito.
     *
     * @param creditDto El DTO de crédito.
     * @return La entidad de crédito resultante.
     */
    Credit toEntity(CreditDto creditDto);

    /**
     * Convierte una entidad de crédito a un DTO de crédito.
     *
     * @param credit La entidad de crédito.
     * @return El DTO de crédito resultante.
     */
    CreditDto toDto(Credit credit);

    /**
     * Actualiza parcialmente una entidad de crédito con los datos de un DTO de crédito.
     *
     * @param creditDto El DTO de crédito con los datos actualizados.
     * @param credit La entidad de crédito a actualizar.
     * @return La entidad de crédito actualizada.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Credit partialUpdate(CreditDto creditDto, @MappingTarget Credit credit);
}
