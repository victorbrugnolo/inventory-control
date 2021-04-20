package dev.victorbrugnolo.inventorycontrol.api.services.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.victorbrugnolo.inventorycontrol.api.dtos.MovementRequest;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.NotFoundException;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.UnprocessableEntityException;
import dev.victorbrugnolo.inventorycontrol.api.repositories.InventoryMovementRepository;
import dev.victorbrugnolo.inventorycontrol.api.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

  @InjectMocks
  private InventoryServiceImpl inventoryService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private InventoryMovementRepository inventoryMovementRepository;

  private final static String STRING_UUID = "123e4567-e89b-12d3-a456-426614174000";

  @Test
  void mustGetNotFoundExceptionWhenMakeMovement() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    MovementRequest request = getMovementRequest(7500, MoveTypeEnum.OUTPUT);
    assertThrows(NotFoundException.class,
        () -> inventoryService.makeMovement(STRING_UUID, request));
  }

  @Test
  void mustGetUnprocessableEntityExceptionWhenMakeMovement() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(getProduct()));
    MovementRequest request = getMovementRequest(7501, MoveTypeEnum.OUTPUT);

    assertThrows(UnprocessableEntityException.class,
        () -> inventoryService.makeMovement(STRING_UUID, request));
  }

  @Test
  void mustMakeOutputMovement() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(getProduct()));
    assertDoesNotThrow(() -> inventoryService
        .makeMovement(STRING_UUID, getMovementRequest(7499, MoveTypeEnum.OUTPUT)));
  }

  @Test
  void mustMakeInputMovement() {
    when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(getProduct()));
    assertDoesNotThrow(() -> inventoryService
        .makeMovement(STRING_UUID, getMovementRequest(1000, MoveTypeEnum.INPUT)));
  }

  private Product getProduct() {
    return Product.builder().id(UUID.fromString(STRING_UUID)).code("1A").description("Monitor")
        .type(ProductTypeEnum.ELECTRONIC).supplierAmount(BigDecimal.valueOf(1300))
        .supply(7500).build();
  }

  private MovementRequest getMovementRequest(final Integer supply, final MoveTypeEnum moveType) {
    return MovementRequest.builder().supply(supply).type(moveType)
        .amount(BigDecimal.valueOf(1500)).build();
  }
}
