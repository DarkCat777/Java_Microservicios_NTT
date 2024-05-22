package org.nttdata.transactions_service.mapper;

import org.mapstruct.*;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
import org.nttdata.transactions_service.domain.entity.Transaction;
import org.nttdata.transactions_service.domain.type.BankProductType;
import org.nttdata.transactions_service.domain.type.TransactionType;

/**
 * Utilitario para transforma un {@link Transaction} a {@link TransactionDto} y viceversa
 *
 * @author Erick David Carpio Hachiri
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ITransactionMapper {
    /**
     * Mapea el DTO a una Entidad
     *
     * @param transactionDto es el DTO del cual se obtiene los datos
     * @return Es la entidad que se obtiene del DTO
     */
    @Mapping(target = "transactionType", source = "transactionType", qualifiedByName = "stringToTransactionType")
    @Mapping(target = "bankProductType", source = "bankProductType", qualifiedByName = "stringToBankProductType")
    Transaction toEntity(TransactionDto transactionDto);

    /**
     * Mapea de una entidad a un DTO
     *
     * @param transaction es la entidad del cual se obtiene los datos
     * @return Es el DTO que se obtiene al procesar la entidad
     */
    @Mapping(target = "transactionType", source = "transactionType", qualifiedByName = "transactionTypeToString")
    @Mapping(target = "bankProductType", source = "bankProductType", qualifiedByName = "bankProductTypeToString")
    TransactionDto toDto(Transaction transaction);

    /**
     * Mapea una Entidad cuando se requiere solo una actualización de datos de la misma
     * @param transactionDto Es el DTO que actualizara datos en la entidad
     * @param transaction Es la entidad en contexto que se actualizara
     * @return Es la entidad actualizada
     */
    @Mapping(target = "transactionType", source = "transactionType", qualifiedByName = "stringToTransactionType")
    @Mapping(target = "bankProductType", source = "bankProductType", qualifiedByName = "stringToBankProductType")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction partialUpdate(TransactionDto transactionDto, @MappingTarget Transaction transaction);
    /**
     * Convierte un enum TransactionType a su representación en cadena.
     *
     * @param transactionType el enum TransactionType que se va a convertir.
     * @return la representación en cadena del TransactionType.
     */
    @Named("transactionTypeToString")
    default String transactionTypeToString(TransactionType transactionType) {
        return transactionType.name();
    }

    /**
     * Convierte un enum BankProductType a su representación en cadena.
     *
     * @param bankProductType el enum BankProductType que se va a convertir.
     * @return la representación en cadena del BankProductType.
     */
    @Named("bankProductTypeToString")
    default String bankProductTypeToString(BankProductType bankProductType) {
        return bankProductType.name();
    }

    /**
     * Convierte una cadena a su correspondiente enum TransactionType.
     *
     * @param name la representación en cadena del TransactionType.
     * @return el enum TransactionType correspondiente.
     */
    @Named("stringToTransactionType")
    default TransactionType stringToTransactionType(String name) {
        return TransactionType.valueOf(name);
    }

    /**
     * Convierte una cadena a su correspondiente enum BankProductType.
     *
     * @param name la representación en cadena del BankProductType.
     * @return el enum BankProductType correspondiente.
     */
    @Named("stringToBankProductType")
    default BankProductType stringToBankProductType(String name) {
        return BankProductType.valueOf(name);
    }

    /**
     * Convierte cualquier enum a su representación en cadena.
     *
     * @param enumValue el valor del enum que se va a convertir.
     * @param <E> el tipo del enum.
     * @return la representación en cadena del enum.
     */
    default <E extends Enum<E>> String enumToString(E enumValue) {
        return enumValue.name();
    }

    /**
     * Convierte una cadena a su correspondiente enum de cualquier tipo.
     *
     * @param value la representación en cadena del enum.
     * @param enumType la clase del enum que se va a convertir.
     * @param <E> el tipo del enum.
     * @return el enum correspondiente.
     */
    default <E extends Enum<E>> E stringToEnum(String value, Class<E> enumType) {
        return Enum.valueOf(enumType, value);
    }
}