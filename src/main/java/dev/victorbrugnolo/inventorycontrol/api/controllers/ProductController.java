package dev.victorbrugnolo.inventorycontrol.api.controllers;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductByTypeResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductProfitResponse;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductRequest;
import dev.victorbrugnolo.inventorycontrol.api.dtos.ProductResponse;
import dev.victorbrugnolo.inventorycontrol.api.entities.Product;
import dev.victorbrugnolo.inventorycontrol.api.enums.ProductTypeEnum;
import dev.victorbrugnolo.inventorycontrol.api.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Product", tags = {"Product"})
public class ProductController {

  private final ProductService productService;

  @PostMapping
  @ApiOperation(value = "Permite ao usuário cadastrar um novo produto")
  @ApiResponse(code = 400, message = "Product with code 'code' already exists")
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
  @ApiOperation(value = "Permite ao usuário obter um produto por id")
  @ApiResponse(code = 404, message = "Product not found")
  public ResponseEntity<ProductResponse> getById(@PathVariable("id") final String id) {
    return ResponseEntity.ok(productService.getById(id));
  }

  @GetMapping
  @ApiOperation(value = "Permite ao usuário obter todos os produtos")
  public ResponseEntity<Page<ProductResponse>> getAll(final Pageable pageable) {
    return ResponseEntity.ok(productService.getAll(pageable));
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Permite ao usuário atualizar um produto")
  @ApiResponse(code = 404, message = "Product not found")
  public ResponseEntity<ProductResponse> update(@PathVariable("id") final String id,
      @RequestBody @Valid final ProductRequest toUpdate) {
    return ResponseEntity.ok(productService.update(id, toUpdate));
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Permite ao usuário excluir um produto")
  @ApiResponses
      ({@ApiResponse(code = 404, message = "Product not found"),
          @ApiResponse(code = 422, message = "This product have movements")})
  public ResponseEntity<Product> delete(@PathVariable("id") final String id) {
    productService.delete(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/output/{type}")
  @ApiOperation(value = "Permite ao usuário obter todos os produtos com quantidade de saída filtrados por tipo")
  public ResponseEntity<List<ProductByTypeResponse>> getByType(
      @PathVariable("type") final ProductTypeEnum type) {
    List<ProductByTypeResponse> products = productService.getByType(type);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}/profit")
  @ApiOperation(value = "Permite ao usuário consultar o lucro e a quantidade de saída de um produto")
  @ApiResponse(code = 404, message = "Product not found")
  public ResponseEntity<ProductProfitResponse> getProfit(@PathVariable("id") final String id) {
    ProductProfitResponse profit = productService.getProfit(id);
    return ResponseEntity.ok(profit);
  }
}
