package dev.victorbrugnolo.inventorycontrol.api.dtos;

import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  @NotNull
  @NotEmpty
  private String code;

  @NotNull
  @NotEmpty
  private String description;

  @NotNull
  private ProductTypeEnum type;

  @NotNull
  private BigDecimal supplierAmount;

  @NotNull
  private Integer supply;

  public static Product toEntity(ProductRequest productRequest) {
    return Product.builder().code(productRequest.code).description(productRequest.description)
        .supplierAmount(productRequest.supplierAmount).supply(productRequest.supply).type(
            productRequest.type)
        .build();
  }
}
