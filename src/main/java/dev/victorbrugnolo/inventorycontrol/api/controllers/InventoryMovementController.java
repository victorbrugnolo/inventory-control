package dev.victorbrugnolo.inventorycontrol.api.controllers;

import dev.victorbrugnolo.inventorycontrol.api.dtos.MovementDTO;
import dev.victorbrugnolo.inventorycontrol.api.services.InventoryService;
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
public class InventoryMovementController {

  private final InventoryService inventoryService;

  @PostMapping("/{id}")
  public ResponseEntity<Void> makeMovement(@PathVariable("id") final String id,
      @RequestBody @Valid final MovementDTO movement) {
    inventoryService.makeMovement(id, movement);
    return ResponseEntity.ok().build();
  }
}
