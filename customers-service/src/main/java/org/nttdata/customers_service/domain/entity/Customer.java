package org.nttdata.customers_service.domain.entity;

import lombok.*;
import org.nttdata.customers_service.domain.type.CustomerType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Definición de la entidad cliente
 * PK: id de tipo Long y generado de forma automatica
 *
 * @author Erick David Carpio Hachiri
 * @see CustomerType
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    
    /**
     * Identificador único generado automáticamente por la base de datos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Apellidos del cliente
     */
    private String surnames;

    /**
     * Nombres del cliente
     */
    private String lastnames;

    /**
     * Tipo de cliente (presumiblemente una enumeración)
     */
    private CustomerType customerType;

    /**
     * Fecha y hora de creación de la entidad, establecida automáticamente
     */
    @CreatedDate
    private LocalDate createdDate;

    /**
     * Fecha y hora de la última modificación de la entidad, establecida automáticamente
     */
    @LastModifiedDate
    private LocalDate lastModifiedDate;
}
