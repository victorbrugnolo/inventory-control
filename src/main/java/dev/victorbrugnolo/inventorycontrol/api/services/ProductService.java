package dev.victorbrugnolo.inventorycontrol.api.services;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductDTO;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Product save(ProductDTO toSave);

  Product getById(String id);

  Page<Product> getAll(Pageable pageable);

  Product update(String id, ProductDTO update);

  void delete(String id);
}
