package org.nttdata.accounts_service.mapper;

import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.domain.entity.Account;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants;

/**
 * Convierte una entidad a un DTO
 *
 * @author Erick David Carpio Hachiri
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IAccountMapper {

    /**
     * Convierte un DTO de cuenta a una entidad de cuenta.
     *
     * @param accountDto El DTO de cuenta.
     * @return La entidad de cuenta resultante.
     */
    Account toEntity(AccountDto accountDto);

    /**
     * Convierte una entidad de cuenta a un DTO de cuenta.
     *
     * @param account La entidad de cuenta.
     * @return El DTO de cuenta resultante.
     */
    AccountDto toDto(Account account);

    /**
     * Actualiza parcialmente una entidad de cuenta con los datos de un DTO de cuenta.
     *
     * @param accountDto El DTO de cuenta con los datos actualizados.
     * @param account La entidad de cuenta a actualizar.
     * @return La entidad de cuenta actualizada.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDto accountDto, @MappingTarget Account account);
}
