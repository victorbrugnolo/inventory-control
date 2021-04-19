package dev.victorbrugnolo.inventorycontrol.api.dtos;

import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementRequest {

  private MoveTypeEnum type;

  private Integer supply;

  private BigDecimal amount;
}
