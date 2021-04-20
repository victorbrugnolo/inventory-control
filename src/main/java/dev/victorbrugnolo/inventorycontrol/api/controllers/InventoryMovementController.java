package dev.victorbrugnolo.inventorycontrol.api.controllers;

import dev.victorbrugnolo.inventorycontrol.api.dtos.MovementRequest;
import dev.victorbrugnolo.inventorycontrol.api.services.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/inventory-movements")
@Api(value = "Movement", tags = {"Movement"})
public class InventoryMovementController {

  private final InventoryService inventoryService;

  @PostMapping("/{id}")
  @ApiOperation(value = "Permite ao usuário realizar a entrada ou saída de um produto")
  @ApiResponses
      ({@ApiResponse(code = 404, message = "Product not found"),
          @ApiResponse(code = 422, message = "Insufficient product")})
  public ResponseEntity<Void> makeMovement(@PathVariable("id") final String id,
      @RequestBody @Valid final MovementRequest movement) {
    inventoryService.makeMovement(id, movement);
    return ResponseEntity.ok().build();
  }
}
