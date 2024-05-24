package org.nttdata.customers_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nttdata.customers_service.domain.entity.Customer;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Customer}
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date lastModifiedDate;
}