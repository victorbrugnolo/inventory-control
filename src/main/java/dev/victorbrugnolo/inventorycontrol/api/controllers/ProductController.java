package dev.victorbrugnolo.inventorycontrol.api.controllers;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductByTypeResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductProfitResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductRequest;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.services.ProductService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<Void> save(@RequestBody @Valid final ProductRequest toSave) {
    Product saved = productService.save(toSave);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(saved.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable("id") final String id) {
    Product found = productService.getById(id);
    return ResponseEntity.ok(found);
  }

  @GetMapping
  public ResponseEntity<Page<Product>> getAll(final Pageable pageable) {
    Page<Product> found = productService.getAll(pageable);
    return ResponseEntity.ok(found);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable("id") final String id,
      @RequestBody @Valid final ProductRequest toUpdate) {
    Product updated = productService.update(id, toUpdate);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Product> delete(@PathVariable("id") final String id) {
    productService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/output/{type}")
  public ResponseEntity<List<ProductByTypeResponse>> getByType(
      @PathVariable("type") final ProductTypeEnum type) {
    List<ProductByTypeResponse> products = productService.getByType(type);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}/profit")
  public ResponseEntity<ProductProfitResponse> getProfit(@PathVariable("id") final String id) {
    ProductProfitResponse profit = productService.getProfit(id);
    return ResponseEntity.ok(profit);
  }
}
