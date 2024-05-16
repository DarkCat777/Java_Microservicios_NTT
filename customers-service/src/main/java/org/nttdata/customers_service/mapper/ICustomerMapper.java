package org.nttdata.customers_service.mapper;

import org.mapstruct.*;
import org.nttdata.customers_service.domain.entity.Customer;
import org.nttdata.customers_service.domain.dto.CustomerDto;

/**
 * Utilitario para transforma un {@link Customer} a {@link CustomerDto} y viceversa
 *
 * @author Erick David Carpio Hachiri
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICustomerMapper {
    /**
     * Mapea el DTO a una Entidad
     *
     * @param clientDto es el DTO del cual se obtiene los datos
     * @return Es la entidad que se obtiene del DTO
     */
    Customer toEntity(CustomerDto clientDto);

    /**
     * Mapea de una entidad a un DTO
     *
     * @param client es la entidad del cual se obtiene los datos
     * @return Es el DTO que se obtiene al procesar la entidad
     */
    CustomerDto toDto(Customer client);

    /**
     * Mapea una Entidad cuando se requiere solo una actualización de datos de la misma
     * @param clientDto Es el DTO que actualizara datos en la entidad
     * @param client Es la entidad en contexto que se actualizara
     * @return Es la entidad actualizada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerDto clientDto, @MappingTarget Customer client);
}