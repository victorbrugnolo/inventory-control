package dev.victorbrugnolo.inventorycontrol.api.services.impl;

import dev.victorbrugnolo.inventorycontrol.api.dtos.MovementDTO;
import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.NotFoundException;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.UnprocessableEntityException;
import dev.victorbrugnolo.inventorycontrol.api.repositories.InventoryMovementRepository;
import dev.victorbrugnolo.inventorycontrol.api.repositories.ProductRepository;
import dev.victorbrugnolo.inventorycontrol.api.services.InventoryService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

  private static final String PRODUCT_NOT_FOUND = "Product not found";
  private static final String PRODUCT_INSUFFICIENT = "Insufficient product";

  private final ProductRepository productRepository;
  private final InventoryMovementRepository inventoryMovementRepository;

  @Override
  public InventoryMovement makeMovement(final String id, final MovementDTO movement) {
    Product product = productRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));

    InventoryMovement toSave = InventoryMovement.builder().product(product)
        .handledSupply(movement.getSupply())
        .saleAmount(movement.getAmount()).saleDate(LocalDateTime.now()).type(movement.getType())
        .build();

    int newSupply =
        MoveTypeEnum.OUTPUT.equals(movement.getType()) ? product.getSupply() - movement.getSupply()
            : product.getSupply() + movement.getSupply();

    if (newSupply < 0) {
      throw new UnprocessableEntityException(PRODUCT_INSUFFICIENT);
    }

    product.setSupply(newSupply);

    productRepository.save(product);
    return inventoryMovementRepository.save(toSave);
  }
}
