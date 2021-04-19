package dev.victorbrugnolo.inventorycontrol.api.repositories;

import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, UUID> {

}
