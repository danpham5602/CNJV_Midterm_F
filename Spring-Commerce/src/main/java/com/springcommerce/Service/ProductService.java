package com.springcommerce.Service;

import com.springcommerce.Model.Product;
import com.springcommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProduct(){
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
    public List<Product> getProductByKeyword(String keyword) {
        return productRepository.searchProductByKeyword(keyword);
    }
    public void saveProduct(Product product){
        productRepository.save(product);
    }
}
