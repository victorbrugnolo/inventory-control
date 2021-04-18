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
public class ProductDTO {

  private String code;

  private String description;

  private ProductTypeEnum type;

  private BigDecimal supplierAmount;

  private Integer supply;

  public static Product toEntity(ProductDTO productDTO) {
    return Product.builder().code(productDTO.code).description(productDTO.description)
        .supplierAmount(productDTO.supplierAmount).supply(productDTO.supply).type(productDTO.type)
        .build();
  }
}
