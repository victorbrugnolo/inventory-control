package dev.victorbrugnolo.inventorycontrol.api.dtos;

import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementRequest {

  @NotNull
  private MoveTypeEnum type;

  @NotNull
  private Integer supply;

  @NotNull
  private BigDecimal amount;
}
