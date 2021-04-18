package dev.victorbrugnolo.inventorycontrol.api.services.impl;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductDTO;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.repositories.ProductRepository;
import dev.victorbrugnolo.inventorycontrol.api.services.ProductService;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Product save(final ProductDTO toSave) {
    return productRepository.save(ProductDTO.toEntity(toSave));
  }

  @Override
  public Product getById(final String id) {
    Optional<Product> found = productRepository.findById(UUID.fromString(id));
    return found.orElse(null);
  }

  @Override
  public Page<Product> getAll(final Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @Override
  public Product update(String id, ProductDTO update) {
    Optional<Product> found = productRepository.findById(UUID.fromString(id));
    found.get().update(update);
    return productRepository.save(found.get());
  }

  @Override
  public void delete(String id) {
    Optional<Product> found = productRepository.findById(UUID.fromString(id));
    productRepository.delete(found.get());
  }
}
