package org.nttdata.accounts_service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Definición de la entidad cuentas como producto bancario
 *
 * @author Erick David Carpio Hachiri
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {
    /**
     * Identificador único para la cuenta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El ID del propietario asociado con esta cuenta.
     */
    private Long ownerId;

    /**
     * El saldo de la cuenta.
     */
    private Double balance;

    /**
     * Tipo de cuenta y propiedades
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AccountType accountType;

    /**
     * La fecha y hora en que se creó la cuenta.
     */
    @CreatedDate
    private LocalDate createdDate;

    /**
     * La fecha y hora en que se modificó por última vez la cuenta.
     */
    @LastModifiedDate
    private LocalDate lastModifiedDate;

    /**
     * El conjunto de titulares asociados con esta cuenta.
     */
    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private Set<AccountHolder> holders = new LinkedHashSet<>();

    /**
     * El conjunto de firmantes autorizados asociados con esta cuenta.
     */
    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private Set<AccountAuthorizedSignatory> authorizedSignatories = new LinkedHashSet<>();
}
