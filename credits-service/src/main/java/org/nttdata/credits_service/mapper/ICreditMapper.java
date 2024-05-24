package org.nttdata.credits_service.mapper;

import org.mapstruct.*;
import org.nttdata.credits_service.domain.dto.CreditDto;
import org.nttdata.credits_service.domain.entity.Credit;
import org.nttdata.credits_service.domain.type.CreditState;

/**
 * Mapea de una entidad a un DTO
 *
 * @author Erick David Carpio Hachiri
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICreditMapper {

    /**
     * Convierte un DTO de crédito a una entidad de crédito.
     *
     * @param creditDto El DTO de crédito.
     * @return La entidad de crédito resultante.
     */
    @Mapping(target = "creditState", source = "creditState", qualifiedByName = "stringToEnum")
    Credit toEntity(CreditDto creditDto);

    /**
     * Convierte una entidad de crédito a un DTO de crédito.
     *
     * @param credit La entidad de crédito.
     * @return El DTO de crédito resultante.
     */
    @Mapping(target = "creditState", source = "creditState", qualifiedByName = "enumToString")
    CreditDto toDto(Credit credit);

    /**
     * Actualiza parcialmente una entidad de crédito con los datos de un DTO de crédito.
     *
     * @param creditDto El DTO de crédito con los datos actualizados.
     * @param credit    La entidad de crédito a actualizar.
     * @return La entidad de crédito actualizada.
     */
    @Mapping(target = "creditState", source = "creditState", qualifiedByName = "stringToEnum")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Credit partialUpdate(CreditDto creditDto, @MappingTarget Credit credit);

    /**
     * Convierte un enum CreditState a su representación en cadena.
     *
     * @param creditState el enum CreditState que se va a convertir
     * @return la representación en cadena del CreditState
     */
    @Named(value = "enumToString")
    default String map(CreditState creditState) {
        if (creditState == null)
            return null;
        return creditState.name();
    }

    /**
     * Convierte una representación en cadena de CreditState al enum correspondiente.
     *
     * @param name la representación en cadena del CreditState
     * @return el enum CreditState correspondiente
     */
    @Named(value = "stringToEnum")
    default CreditState map(String name) {
        if (name == null)
            return null;
        return CreditState.valueOf(name);
    }

}
