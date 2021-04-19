package dev.victorbrugnolo.inventorycontrol.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTests {

  @Autowired
  private ProductRepository productRepository;

  private static final String CODE = "4A";

  @BeforeEach
  public void setUp() {
    productRepository.deleteAll();
    productRepository
        .save(Product.builder().code("1A").description("Geladeira").type(ProductTypeEnum.APPLIANCE)
            .supplierAmount(BigDecimal.valueOf(3499.99)).supply(1000).build());
    productRepository
        .save(Product.builder().code("2A").description("Celular Asus")
            .type(ProductTypeEnum.ELECTRONIC)
            .supplierAmount(BigDecimal.valueOf(1999.99)).supply(5000).build());
    productRepository
        .save(Product.builder().code("3A").description("Escrivaninha")
            .type(ProductTypeEnum.PIECE_OF_FURNITURE).supplierAmount(BigDecimal.valueOf(199.90))
            .supply(10000).build());
  }

  @Test
  void mustGetAll() {
    List<Product> saved = productRepository.findAll();
    assertEquals(3, saved.size());
  }

  @Test
  void mustGetById() {
    Product saved = saveNewProduct();
    Product found = productRepository.findById(saved.getId()).orElse(Product.builder().build());
    assertEquals(saved.getCode(), found.getCode());
  }

  @Test
  void mustSave() {
    assertNotNull(saveNewProduct().getId());
  }

  @Test
  void mustUpdate() {
    Product saved = saveNewProduct();
    saved.setSupply(1);
    productRepository.save(saved);
    assertEquals(1, saved.getSupply());
  }

  @Test
  void mustDelete() {
    Product saved = saveNewProduct();
    productRepository.delete(saved);
    Optional<Product> found = productRepository.findById(saved.getId());
    assertFalse(found.isPresent());
  }

  @Test
  void mustGetByCode() {
    Product saved = saveNewProduct();
    Product found = productRepository.findByCode(CODE).orElse(Product.builder().build());
    assertEquals(saved.getId().toString(), found.getId().toString());
  }

  @Test
  void mustGetAllByType() {
    saveNewProduct();
    List<Product> found = productRepository.findAllByType(ProductTypeEnum.ELECTRONIC);
    assertEquals(2, found.size());
  }

  private Product saveNewProduct() {
    return productRepository
        .save(Product.builder().code(CODE).description("Monitor")
            .type(ProductTypeEnum.ELECTRONIC).supplierAmount(BigDecimal.valueOf(1299.90))
            .supply(7500).build());
  }
}
