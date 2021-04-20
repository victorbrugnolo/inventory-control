package dev.victorbrugnolo.inventorycontrol.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class InventoryMovementRepositoryTest {

  @Autowired
  private InventoryMovementRepository inventoryMovementRepository;

  @Autowired
  private ProductRepository productRepository;

  @BeforeEach
  public void setUp() {
    inventoryMovementRepository.deleteAll();
    productRepository.deleteAll();

    Product product = getProduct("2A");

    inventoryMovementRepository.save(
        InventoryMovement.builder().type(MoveTypeEnum.OUTPUT).handledSupply(200)
            .product(product).saleAmount(BigDecimal.valueOf(2500))
            .saleDate(LocalDateTime.now()).build());
    inventoryMovementRepository.save(
        InventoryMovement.builder().type(MoveTypeEnum.OUTPUT).handledSupply(100)
            .product(product).saleAmount(BigDecimal.valueOf(2300))
            .saleDate(LocalDateTime.now()).build());
  }

  @Test
  void mustGetAll() {
    List<InventoryMovement> saved = inventoryMovementRepository.findAll();
    assertEquals(2, saved.size());
  }

  @Test
  void mustGetById() {
    InventoryMovement saved = saveNewMovement();
    InventoryMovement found = inventoryMovementRepository.findById(saved.getId())
        .orElse(InventoryMovement.builder().build());
    assertEquals(saved.getHandledSupply(), found.getHandledSupply());
  }

  @Test
  void mustSave() {
    assertNotNull(saveNewMovement().getId());
  }

  @Test
  void mustUpdate() {
    InventoryMovement saved = saveNewMovement();
    saved.setHandledSupply(5);
    inventoryMovementRepository.save(saved);
    assertEquals(5, saved.getHandledSupply());
  }

  @Test
  void mustDelete() {
    InventoryMovement saved = saveNewMovement();
    inventoryMovementRepository.delete(saved);
    Optional<InventoryMovement> found = inventoryMovementRepository.findById(saved.getId());
    assertFalse(found.isPresent());
  }

  @Test
  void mustGetSumHandledSupplyByTypeAndProduct() {
    Product product = getProduct("4A");
    saveNewMovement(product);
    saveNewMovement(product);

    Integer handledSupply = inventoryMovementRepository
        .sumHandledSupplyByTypeAndProduct(MoveTypeEnum.OUTPUT, product);

    assertEquals(100, handledSupply);
  }

  @Test
  void mustGetByTypeAndProduct() {
    Product product = getProduct("4A");
    saveNewMovement(product);
    saveNewMovement(product);

    List<InventoryMovement> movements = inventoryMovementRepository
        .findByTypeAndProduct(MoveTypeEnum.OUTPUT, product);

    assertEquals(2, movements.size());
  }


  private InventoryMovement saveNewMovement() {
    Product product = getProduct("3A");

    return inventoryMovementRepository
        .save(InventoryMovement.builder().type(MoveTypeEnum.OUTPUT).handledSupply(50)
            .product(product).saleAmount(BigDecimal.valueOf(2400))
            .saleDate(LocalDateTime.now()).build());
  }

  private InventoryMovement saveNewMovement(final Product product) {
    return inventoryMovementRepository
        .save(InventoryMovement.builder().type(MoveTypeEnum.OUTPUT).handledSupply(50)
            .product(product).saleAmount(BigDecimal.valueOf(2400))
            .saleDate(LocalDateTime.now()).build());
  }

  private Product getProduct(final String code) {
    return productRepository.save(
        Product.builder().code(code).description("Celular Asus").type(ProductTypeEnum.ELECTRONIC)
            .supplierAmount(BigDecimal.valueOf(2000)).supply(5000).build());
  }
}
