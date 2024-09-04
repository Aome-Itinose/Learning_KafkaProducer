package org.aome.learningkafka.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_description")
    private String productDescription;
}
