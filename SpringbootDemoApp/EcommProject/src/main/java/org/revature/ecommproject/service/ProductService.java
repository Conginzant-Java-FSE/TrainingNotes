package org.revature.ecommproject.service;

import org.revature.ecommproject.model.Products;
import org.revature.ecommproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo){
        this.repo = repo;
    }

    public Products addProduct(Products product){
       return repo.save(product);
    }

    public Products updateProduct(Products product){
        return repo.save(product);
    }

    public List<Products> getAllProducts(){
        return repo.findAll();
    }

    public Products getProductById(Long id){
        return repo.findById(id).get();
    }

    public void deleteProduct(Long id){
         repo.deleteById(id);
    }

}
