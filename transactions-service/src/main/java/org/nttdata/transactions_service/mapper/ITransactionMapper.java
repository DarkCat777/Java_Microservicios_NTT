package org.nttdata.transactions_service.mapper;

import org.mapstruct.*;
import org.nttdata.transactions_service.domain.entity.Transaction;
import org.nttdata.transactions_service.domain.dto.TransactionDto;
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
    Transaction toEntity(TransactionDto transactionDto);

    /**
     * Mapea de una entidad a un DTO
     *
     * @param transaction es la entidad del cual se obtiene los datos
     * @return Es el DTO que se obtiene al procesar la entidad
     */
    TransactionDto toDto(Transaction transaction);

    /**
     * Mapea una Entidad cuando se requiere solo una actualización de datos de la misma
     * @param transactionDto Es el DTO que actualizara datos en la entidad
     * @param transaction Es la entidad en contexto que se actualizara
     * @return Es la entidad actualizada
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction partialUpdate(TransactionDto transactionDto, @MappingTarget Transaction transaction);
}