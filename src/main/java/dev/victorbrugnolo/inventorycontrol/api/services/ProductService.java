package dev.victorbrugnolo.inventorycontrol.api.services;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductByTypeResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductProfitResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductRequest;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Product save(ProductRequest toSave);

  Product getById(String id);

  Page<Product> getAll(Pageable pageable);

  Product update(String id, ProductRequest update);

  void delete(String id);

  List<ProductByTypeResponse> getByType(ProductTypeEnum type);

  ProductProfitResponse getProfit(String id);
}
