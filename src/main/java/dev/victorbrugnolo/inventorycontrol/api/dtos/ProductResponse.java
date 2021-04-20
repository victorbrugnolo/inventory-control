package dev.victorbrugnolo.inventorycontrol.api.dtos;

import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

  private String id;

  private String code;

  private String description;

  private ProductTypeEnum type;

  private BigDecimal supplierAmount;

  private Integer supply;

  public static ProductResponse toResponse(final Product product) {
    return ProductResponse.builder().id(product.getId().toString()).code(product.getCode())
        .description(product.getDescription()).supplierAmount(product.getSupplierAmount())
        .supply(product.getSupply()).type(product.getType())
        .build();
  }
}
