package dev.victorbrugnolo.inventorycontrol.api.entities;

import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "inventory_movement")
public class InventoryMovement extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
  private Product product;

  @Enumerated(EnumType.STRING)
  @Column(name = "move_type", nullable = false)
  private MoveTypeEnum type;

  @Column(name = "sale_amount", nullable = false)
  private BigDecimal saleAmount;

  @Column(name = "sale_date", nullable = false)
  private LocalDateTime saleDate;

  @Column(name = "handled_supply", nullable = false)
  private Integer handledSupply;
}
