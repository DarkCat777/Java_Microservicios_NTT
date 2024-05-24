package org.nttdata.credits_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
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
     * Total disponible de créditos
     */
    private Double balance;
    /**
     * Saldo pendiente a pagar
     * -> Con intereses calculados al fin de mes
     */
    private Double outstandingBalance;
    /**
     * Porcentaje de interés
     * - Cuando es TARJETA DE CRÉDITO - Aplicado sobre el saldo pendiente de forma mensual
     */
    private Double interestRate;
    /**
     * Fecha de creación del crédito
     */
    @CreatedDate
    private Date createDate;

    /**
     * Fecha de última modificación del crédito
     */
    @LastModifiedDate
    private Date lastModifiedDate;
}
