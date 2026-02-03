package org.revature.ecommproject.controller;


import org.revature.ecommproject.dto.ProductRequestDTO;
import org.revature.ecommproject.model.Products;
import org.revature.ecommproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
//CORS -> Cross Origin Resource Sharing
//@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private ProductService service;

    @Autowired
    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Products> addProducts(@RequestBody ProductRequestDTO p){
        Products products = new Products();
        products.setColor(p.getColor());
        products.setPrice(p.getPrice());
        products.setName(p.getName());
        return new ResponseEntity<>(service.addProduct(products),HttpStatus.CREATED);
    }

    @GetMapping("/{id}") // products/1
    public ResponseEntity<Products> getProductsById(@PathVariable Long id){
        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);

    }

    @PutMapping // products?id=1
    public ResponseEntity<Products> updateProducts(@RequestParam Long id, @RequestBody Products product, @RequestHeader("token") String token){
                if(service.getProductById(id) != null){
                    return new ResponseEntity<>(service.updateProduct(product), HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return new ResponseEntity<>("Product " + id + " successfully Deleted",HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<String> sendError(){
        return new ResponseEntity<>("Nullpointer ex", HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
