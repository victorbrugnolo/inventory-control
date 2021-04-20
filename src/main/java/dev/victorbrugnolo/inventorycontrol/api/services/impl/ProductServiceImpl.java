package dev.victorbrugnolo.inventorycontrol.api.services.impl;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductByTypeResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductProfitResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductRequest;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductResponse;
import dev.victorbrugnolo.inventorycontrol.api.entities.InventoryMovement;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.MoveTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.BadRequestException;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.NotFoundException;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.UnprocessableEntityException;
import dev.victorbrugnolo.inventorycontrol.api.repositories.InventoryMovementRepository;
import dev.victorbrugnolo.inventorycontrol.api.repositories.ProductRepository;
import dev.victorbrugnolo.inventorycontrol.api.services.ProductService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

  private static final String PRODUCT_ALREADY_EXISTS = "Product with code %s already exists";
  private static final String PRODUCT_NOT_FOUND = "Product not found";
  private static final String PRODUCT_HAVE_MOVEMENTS = "This product have movements";

  private final ProductRepository productRepository;
  private final InventoryMovementRepository inventoryMovementRepository;

  @Override
  public Product save(final ProductRequest toSave) {
    productRepository.findByCode(toSave.getCode())
        .ifPresent(p -> {
          throw new BadRequestException(String.format(PRODUCT_ALREADY_EXISTS, toSave.getCode()));
        });

    return productRepository.save(ProductRequest.toEntity(toSave));
  }

  @Override
  public ProductResponse getById(final String id) {
    Product found = findById(id);
    return ProductResponse.toResponse(found);
  }

  @Override
  public Page<ProductResponse> getAll(final Pageable pageable) {
    return productRepository.findAll(pageable).map(ProductResponse::toResponse);
  }

  @Override
  public ProductResponse update(final String id, final ProductRequest update) {
    Product found = findById(id);
    found.update(update);
    return ProductResponse.toResponse(productRepository.save(found));
  }

  @Override
  public void delete(final String id) {
    Product toDelete = findById(id);
    List<InventoryMovement> movements = inventoryMovementRepository.findByProduct(toDelete);

    if (!movements.isEmpty()) {
      throw new UnprocessableEntityException(PRODUCT_HAVE_MOVEMENTS);
    }

    productRepository.delete(findById(id));
  }

  @Override
  public List<ProductByTypeResponse> getByType(final ProductTypeEnum type) {
    List<Product> products = productRepository.findAllByType(type);
    return products.stream().map(product -> {
      ProductByTypeResponse productByTypeResponse = ProductByTypeResponse
          .toProductByTypeResponse(product);
      Integer outputQuantity = getProductOutputQuantity(product);
      productByTypeResponse.setOutputQuantity(Objects.isNull(outputQuantity) ? 0 : outputQuantity);
      return productByTypeResponse;
    }).collect(Collectors.toList());
  }

  @Override
  public ProductProfitResponse getProfit(String id) {
    Product product = findById(id);

    AtomicReference<BigDecimal> profit = new AtomicReference<>();
    AtomicReference<Integer> handledSupply = new AtomicReference<>();
    profit.set(BigDecimal.ZERO);
    handledSupply.set(0);

    List<InventoryMovement> movements = inventoryMovementRepository
        .findByTypeAndProduct(MoveTypeEnum.OUTPUT, product);

    movements.forEach(inventoryMovement -> {
      BigDecimal saleProfit = inventoryMovement.getSaleAmount()
          .subtract(product.getSupplierAmount());
      BigDecimal handledSupplyBigDecimal = BigDecimal.valueOf(inventoryMovement.getHandledSupply());
      profit.set(profit.get()
          .add(saleProfit.multiply(handledSupplyBigDecimal)));

      handledSupply.set(handledSupply.get() + inventoryMovement.getHandledSupply());
    });

    return ProductProfitResponse.builder().outputQuantity(handledSupply.get())
        .profit(profit.get()).build();
  }

  private Integer getProductOutputQuantity(final Product product) {
    return inventoryMovementRepository
        .sumHandledSupplyByTypeAndProduct(MoveTypeEnum.OUTPUT, product);
  }

  private Product findById(final String id) {
    return productRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));
  }
}
