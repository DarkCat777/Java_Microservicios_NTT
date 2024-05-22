package org.nttdata.credits_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.credits_service.domain.type.CreditState;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Producto bancario de tipo crédito
 *
 * @author Erick David Carpio Hachiri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Credit {
    /**
     * Identificador único
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Id del cliente propietario
     */
    private Long ownerId;
    /**
     * Cantidad a pagar de crédito
     */
    private Double balance;
    /**
     * Saldo pendiente a pagar
     */
    private Double outstandingBalance;
    /**
     * Porcentaje de interés
     * - Cuando es CRÉDITO - Aplicado sobre el monto total
     */
    private Double interestRate;
    /**
     * Fecha de creación e inicio del crédito
     */
    @CreatedDate
    private Date startDate;
    /**
     * Fecha de vencimiento del crédito
     */
    private Date dueDate;
    /**
     * Estado del producto crediticio
     * - PAID -> Pagado
     * - PENDING_TO_PAY -> Pendiente a pago
     */
    @Enumerated(EnumType.STRING)
    private CreditState creditState;
}
