package dev.victorbrugnolo.inventorycontrol.api.repositories;

import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
