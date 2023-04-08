package com.springcommerce.Controller;

import com.springcommerce.Model.Brand;
import com.springcommerce.Model.Category;
import com.springcommerce.Model.Product;
import com.springcommerce.Service.BrandService;
import com.springcommerce.Service.CategoryService;
import com.springcommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @GetMapping("")
    public String getIndex(Model model){
        List<Product> products = productService.listProduct();
        model.addAttribute("products", products);
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categories", categories);
        List<Brand> brands = brandService.brandList();
        model.addAttribute("brands", brands);
        return "admin/index";
    }
    @PostMapping("product")
    public String postProduct(@ModelAttribute("product") Product product){
        productService.saveProduct(product);
        return "redirect:/admin";
    }
    @GetMapping("category")
    public String getCategory(Model model){
        List<Category> categories = categoryService.categoryList();
        model.addAttribute("categories", categories);
        return "admin/category";
    }
    @PostMapping("category")
    public String postCategory(@ModelAttribute("category") Category category){
        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }
    @GetMapping("brand")
    public String getBranch(Model model){
        List<Brand> brands = brandService.brandList();
        model.addAttribute("brands", brands);
        return "admin/branch";
    }
    @PostMapping("brand")
    public String postBranch(@ModelAttribute("category") Brand branch){
        brandService.saveBrand(branch);
        return "redirect:/admin/brand";
    }
}
