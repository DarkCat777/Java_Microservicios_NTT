package org.nttdata.customers_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.customers_service.domain.entity.Customer;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Customer}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
    private String id;
    @NotBlank
    private String surnames;
    @NotBlank
    private String lastnames;
    @NotBlank
    private String customerType;
}