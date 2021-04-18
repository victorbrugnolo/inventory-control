package dev.victorbrugnolo.inventorycontrol.api.entities;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductDTO;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class Product extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String code;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductTypeEnum type;

  @Column(name = "supplier_amount", nullable = false)
  private BigDecimal supplierAmount;

  @Column(nullable = false)
  private Integer supply;

  public Product update(final ProductDTO toUpdate) {
    this.code = toUpdate.getCode();
    this.description = toUpdate.getDescription();
    this.type = toUpdate.getType();
    this.supplierAmount = toUpdate.getSupplierAmount();
    this.supply = toUpdate.getSupply();

    return this;
  }
}
