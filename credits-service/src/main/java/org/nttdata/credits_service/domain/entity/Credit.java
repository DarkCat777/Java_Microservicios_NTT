package org.nttdata.credits_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.credits_service.domain.type.CreditState;
import org.nttdata.credits_service.domain.type.CreditType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Producto bancario de tipo crédito
 *
 * @author Erick David Carpio Hachiri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("credits")
public class Credit {
    /**
     * Identificador único
     */
    @Id
    private String id;
    /**
     * Tipo de crédito
     * - CRÉDITO
     * - TARJETA DE CRÉDITO
     */
    private CreditType creditType;
    /**
     * Id del cliente propietario
     */
    private String ownerId;
    /**
     * Total disponible de créditos
     */
    private Double balance;
    /**
     * Saldo pendiente a pagar
     */
    private Double outstandingBalance;
    /**
     * Porcentaje de interés
     * - Cuando es CRÉDITO - Aplicado sobre el monto total
     * - Cuando es TARJETA DE CRÉDITO - Aplicado sobre el saldo pendiente
     */
    private Double interestRate;
    /**
     * Fecha de creación e inicio del crédito
     */
    @CreatedDate
    private OffsetDateTime startDate;
    /**
     * Fecha de vencimiento del crédito
     * - Cuando es CRÉDITO - No actualizable
     * - Cuando es TARJETA DE CRÉDITO - Actualizable
     */
    private OffsetDateTime dueDate;
    /**
     * Identificadores de los pagos realizados
     */
    private Set<String> paymentIds;
    /**
     * Estado del producto crediticio
     * - Cerrado - TARJETA DE CRÉDITO
     * - Cancelado - CRÉDITO
     * - Activo - CRÉDITO & TARJETA DE CRÉDITO
     */
    private CreditState creditState;
}
