package dev.victorbrugnolo.inventorycontrol.api.controllers;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductDTO;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.services.ProductService;
import java.net.URI;
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
  public ResponseEntity<Void> save(@RequestBody final ProductDTO toSave) {
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
      @RequestBody final ProductDTO toUpdate) {
    Product updated = productService.update(id, toUpdate);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Product> delete(@PathVariable("id") final String id) {
    productService.delete(id);
    return ResponseEntity.ok().build();
  }
}
