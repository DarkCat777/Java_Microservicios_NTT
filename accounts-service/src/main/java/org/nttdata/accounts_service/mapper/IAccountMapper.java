package org.nttdata.accounts_service.mapper;

import org.mapstruct.*;
import org.nttdata.accounts_service.domain.dto.AccountDto;
import org.nttdata.accounts_service.domain.entity.Account;
import org.nttdata.accounts_service.domain.entity.AccountAuthorizedSignatory;
import org.nttdata.accounts_service.domain.entity.AccountHolder;
import org.nttdata.accounts_service.domain.entity.AccountType;

import java.util.Set;
import java.util.stream.Collectors;

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
    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "holders", ignore = true)
    @Mapping(target = "authorizedSignatories", ignore = true)
    Account toEntity(AccountDto accountDto);

    /**
     * Convierte una entidad de cuenta a un DTO de cuenta.
     *
     * @param account La entidad de cuenta.
     * @return El DTO de cuenta resultante.
     */
    @Mapping(source = "accountType", target = "accountType", qualifiedByName = "accountTypeMapper")
    @Mapping(source = "holders", target = "holderIds", qualifiedByName = "holdersMapper")
    @Mapping(source = "authorizedSignatories", target = "authorizedSignatoryIds", qualifiedByName = "authorizedSignatoriesMapper")
    AccountDto toDto(Account account);

    @Named("accountTypeMapper")
    default String accountTypeToDto(AccountType accountType) {
        if (accountType != null) {
            return accountType.getName().name();
        } else {
            return null;
        }
    }

    @Named("holdersMapper")
    default Set<Long> accountHoldersToDto(Set<AccountHolder> accountHolders) {
        return accountHolders.stream().map(accountHolder -> accountHolder.getId().getHolderId()).collect(Collectors.toSet());
    }

    @Named("authorizedSignatoriesMapper")
    default Set<Long> authorizedSignatoriesToDto(Set<AccountAuthorizedSignatory> accountHolders) {
        return accountHolders.stream().map(accountHolder -> accountHolder.getId().getAuthorizedSignatoryId()).collect(Collectors.toSet());
    }

    /**
     * Actualiza parcialmente una entidad de cuenta con los datos de un DTO de cuenta.
     *
     * @param accountDto El DTO de cuenta con los datos actualizados.
     * @param account    La entidad de cuenta a actualizar.
     * @return La entidad de cuenta actualizada.
     */

    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "holders", ignore = true)
    @Mapping(target = "authorizedSignatories", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDto accountDto, @MappingTarget Account account);
}
