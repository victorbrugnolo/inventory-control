package dev.victorbrugnolo.inventorycontrol.api.repositories;

import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

  Optional<Product> findByCode(String code);

  List<Product> findAllByType(ProductTypeEnum type);

}
