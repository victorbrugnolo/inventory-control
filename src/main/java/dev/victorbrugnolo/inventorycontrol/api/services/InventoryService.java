package dev.victorbrugnolo.inventorycontrol.api.services;

import dev.victorbrugnolo.inventorycontrol.api.dtos.MovementRequest;
import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;

public interface InventoryService {

  InventoryMovement makeMovement(String id, MovementRequest movement);

}
