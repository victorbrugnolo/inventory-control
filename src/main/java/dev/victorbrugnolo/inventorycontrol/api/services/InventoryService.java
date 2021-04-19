package dev.victorbrugnolo.inventorycontrol.api.services;

import dev.victorbrugnolo.inventorycontrol.api.dtos.MovementDTO;
import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;

public interface InventoryService {

  InventoryMovement makeMovement(String id, MovementDTO movement);

}
