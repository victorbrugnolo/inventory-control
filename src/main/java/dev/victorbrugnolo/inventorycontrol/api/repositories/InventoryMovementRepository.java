package dev.victorbrugnolo.inventorycontrol.api.repositories;

import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, UUID> {

  @Query("SELECT sum(im.handledSupply) from inventory_movement im where im.type = ?1 and product = ?2")
  Integer sumHandledSupplyByTypeAndProduct(MoveTypeEnum type, Product product);

  List<InventoryMovement> findByTypeAndProduct(MoveTypeEnum type, Product product);

  List<InventoryMovement> findByProduct(Product product);
}
