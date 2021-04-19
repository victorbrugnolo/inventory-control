package dev.victorbrugnolo.inventorycontrol.api.dtos;

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
public class ProductProfitResponse {

  private Integer outputQuantity;

  private BigDecimal profit;

}
