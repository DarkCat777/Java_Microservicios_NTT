package org.nttdata.accounts_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.accounts_service.domain.type.AccountTypeEnum;

import javax.persistence.*;

/**
 * Definición de la entidad tipo de cuenta con sus propiedades
 *
 * @author Erick David Carpio Hachiri
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {
    /**
     * Identificador único para el tipo de cuenta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * El tipo de la cuenta.
     */
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum name;

    /**
     * La comisión de mantenimiento.
     */
    private Double maintenanceFee;

    /**
     * El límite de movimientos mensuales permitidos para la cuenta.
     */
    private Integer monthlyMovementLimit;
}
