package com.springcommerce.Api;

import com.springcommerce.Model.Brand;
import com.springcommerce.Model.Category;
import com.springcommerce.Model.Product;
import com.springcommerce.Repository.BrandRepository;
import com.springcommerce.Repository.CategoryRepository;
import com.springcommerce.Repository.ProductRepository;
import com.springcommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestParam("name") String name,
                                @RequestParam("color") String color,
                                @RequestParam("price") Double price,
                                @RequestParam("category") Long categoryId,
                                @RequestParam("brand") Long brandId){
        Product product = new Product();
        product.setName(name);
        product.setColor(color);
        product.setPrice(price);
        product.setBrand(brandRepository.findById(categoryId).get());
        product.setCategory(categoryRepository.findById(brandId).get());

        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                @RequestParam("name") String name,
                                                 @RequestParam(value = "color") String color,
                                                 @RequestParam("price") Double price,
                                                 @RequestParam("category") Long categoryId,
                                                 @RequestParam("brand") Long brandId){
        Optional<Product> updateProduct = productRepository.findById(id);
        if(updateProduct.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = updateProduct.get();
        product.setName(name);
        product.setColor(color);
        product.setPrice(price);
        product.setCategory(categoryRepository.findById(categoryId).get());
        product.setBrand(brandRepository.findById(brandId).get());

        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id){
        Product product = productRepository.findById(id).get();
       if(productRepository.findById(id).isEmpty()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
        product.setBrand(null);
        product.setCategory(null);
        productRepository.deleteById(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> listCategories(){
        List<Category> categories = categoryRepository.findAll();

        return new ResponseEntity<>(categories, HttpStatus.ACCEPTED);
    }
    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestParam("name") String name){
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            return new ResponseEntity<>("Can not find category", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }
    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> listBrands(){
        List<Brand> brands = brandRepository.findAll();

        return new ResponseEntity<>(brands, HttpStatus.ACCEPTED);
    }
    @PostMapping("/brands")
    public ResponseEntity<Brand> createBrand(@RequestParam("name") String name){
        Brand brand = new Brand();
        brand.setName(name);

        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id){
        Optional<Brand> brand = brandRepository.findById(id);
        if(brand.isEmpty()){
            return new ResponseEntity<>("Cannot find brand", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }
}
