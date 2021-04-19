package dev.victorbrugnolo.inventorycontrol.api.dtos;

import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductByTypeResponse {

  private String code;

  private String description;

  private ProductTypeEnum type;

  private BigDecimal supplierAmount;

  private Integer supply;

  private Integer outputQuantity;

  public static ProductByTypeResponse toProductByTypeResponse(Product product) {
    return ProductByTypeResponse.builder().code(product.getCode())
        .description(product.getDescription())
        .supplierAmount(product.getSupplierAmount()).supply(product.getSupply()).type(
            product.getType())
        .build();
  }

}
