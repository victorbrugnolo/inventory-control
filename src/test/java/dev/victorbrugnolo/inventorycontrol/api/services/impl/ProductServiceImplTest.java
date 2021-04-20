package dev.victorbrugnolo.inventorycontrol.api.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductByTypeResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductProfitResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductRequest;
import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.BadRequestException;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.NotFoundException;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.UnprocessableEntityException;
import dev.victorbrugnolo.inventorycontrol.api.repositories.InventoryMovementRepository;
import dev.victorbrugnolo.inventorycontrol.api.repositories.ProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private InventoryMovementRepository inventoryMovementRepository;

  private final static String STRING_UUID = "123e4567-e89b-12d3-a456-426614174000";

  @Test
  void mustGetBadRequestExceptionWhenCodeAlreadyExists() {
    ProductRequest request = ProductRequest.builder().code("code").build();
    when(productRepository.findByCode(anyString()))
        .thenReturn(Optional.of(Product.builder().build()));
    assertThrows(BadRequestException.class, () -> productService.save(request));
  }

  @Test
  void mustBeSave() {
    ProductRequest request = ProductRequest.builder().code("code").build();
    when(productRepository.findByCode(anyString())).thenReturn(Optional.empty());
    assertDoesNotThrow(() -> productService.save(request));
  }

  @Test
  void mustGetNotFoundExceptionWhenGetById() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class,
        () -> productService.getById(STRING_UUID));
  }

  @Test
  void mustGetById() {
    when(productRepository.findById(any(UUID.class)))
        .thenReturn(Optional.of(getProduct()));
    assertEquals(UUID.fromString(STRING_UUID).toString(),
        productService.getById(STRING_UUID).getId());
  }

  @Test
  void mustGetAll() {
    List<Product> productList = Arrays.asList(getProduct(), getProduct());
    Page<Product> productPage = new PageImpl<>(productList);

    when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);
    assertEquals(2, productService.getAll(Pageable.unpaged()).getTotalElements());
  }

  @Test
  void mustGetNotFoundExceptionWhenUpdate() {
    ProductRequest request = ProductRequest.builder().code("code").build();
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class,
        () -> productService.update(STRING_UUID, request));
  }

  @Test
  void mustUpdate() {
    ProductRequest request = ProductRequest.builder().code("code").build();
    when(productRepository.findById(any(UUID.class)))
        .thenReturn(Optional.of(getProduct()));
    when(productRepository.save(any(Product.class)))
        .thenReturn(getProduct());

    assertDoesNotThrow(() -> productService.update(STRING_UUID, request));
  }

  @Test
  void mustGetNotFoundExceptionWhenDelete() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class,
        () -> productService.delete(STRING_UUID));
  }

  @Test
  void mustGetUnprocessableEntityExceptionWhenDelete() {
    when(productRepository.findById(any(UUID.class)))
        .thenReturn(Optional.of(Product.builder().build()));
    when(inventoryMovementRepository.findByProduct(any(Product.class))).thenReturn(
        Collections.singletonList(getInventoryMovement()));

    assertThrows(UnprocessableEntityException.class, () -> productService.delete(STRING_UUID));
  }

  @Test
  void mustDelete() {
    when(productRepository.findById(any(UUID.class)))
        .thenReturn(Optional.of(Product.builder().build()));
    when(inventoryMovementRepository.findByProduct(any(Product.class))).thenReturn(new ArrayList<>());

    assertDoesNotThrow(() -> productService.delete(STRING_UUID));
  }

  @Test
  void mustGetByType() {
    List<Product> productList = Arrays.asList(Product.builder().build(), Product.builder().build());

    when(productRepository.findAllByType(any(ProductTypeEnum.class))).thenReturn(productList);
    when(inventoryMovementRepository
        .sumHandledSupplyByTypeAndProduct(any(MoveTypeEnum.class), any(Product.class)))
        .thenReturn(300);

    List<ProductByTypeResponse> response = productService.getByType(ProductTypeEnum.ELECTRONIC);
    assertEquals(2, response.size());
    assertEquals(300, response.get(0).getOutputQuantity());
  }

  @Test
  void mustGetNotFoundExceptionWhenGetProfit() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> productService.getProfit(STRING_UUID));
  }

  @Test
  void mustGetProfit() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(getProduct()));
    when(inventoryMovementRepository
        .findByTypeAndProduct(any(MoveTypeEnum.class), any(Product.class)))
        .thenReturn(Arrays.asList(getInventoryMovement(), getInventoryMovement()));

    ProductProfitResponse response = productService.getProfit(STRING_UUID);
    assertEquals(100, response.getOutputQuantity());
    assertEquals(BigDecimal.valueOf(20000), response.getProfit());
  }

  private Product getProduct() {
    return Product.builder().id(UUID.fromString(STRING_UUID)).code("1A").description("Monitor")
        .type(ProductTypeEnum.ELECTRONIC).supplierAmount(BigDecimal.valueOf(1300))
        .supply(7500).build();
  }

  private InventoryMovement getInventoryMovement() {
    return InventoryMovement.builder().type(MoveTypeEnum.OUTPUT).handledSupply(50)
        .product(getProduct()).saleAmount(BigDecimal.valueOf(1500))
        .saleDate(LocalDateTime.now()).build();
  }
}
