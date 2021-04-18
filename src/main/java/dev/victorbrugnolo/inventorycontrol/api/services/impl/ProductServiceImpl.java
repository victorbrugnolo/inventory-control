package dev.victorbrugnolo.inventorycontrol.api.services.impl;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductDTO;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.BadRequestException;
import dev.victorbrugnolo.inventorycontrol.api.exceptions.NotFoundException;
import dev.victorbrugnolo.inventorycontrol.api.repositories.ProductRepository;
import dev.victorbrugnolo.inventorycontrol.api.services.ProductService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

  private static final String PRODUCT_ALREADY_EXISTS = "Product with code %s already exists";
  private static final String PRODUCT_NOT_FOUND = "Product not found";

  private final ProductRepository productRepository;

  @Override
  public Product save(final ProductDTO toSave) {
    productRepository.findByCode(toSave.getCode())
        .ifPresent(p -> {
          throw new BadRequestException(String.format(PRODUCT_ALREADY_EXISTS, toSave.getCode()));
        });

    return productRepository.save(ProductDTO.toEntity(toSave));
  }

  @Override
  public Product getById(final String id) {
    return productRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));
  }

  @Override
  public Page<Product> getAll(final Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @Override
  public Product update(final String id, final ProductDTO update) {
    Product found = getById(id);
    found.update(update);
    return productRepository.save(found);
  }

  @Override
  public void delete(final String id) {
    productRepository.delete(getById(id));
  }
}
