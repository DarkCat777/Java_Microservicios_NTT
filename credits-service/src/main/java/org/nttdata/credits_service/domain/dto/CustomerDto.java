package org.nttdata.credits_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link org.nttdata.customers_service.domain.entity.Customer}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
    private Long id;
    @NotBlank
    private String surnames;
    @NotBlank
    private String lastnames;
    @NotBlank
    private String customerType;
}