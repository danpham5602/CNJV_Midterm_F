package com.springcommerce.Service;

import com.springcommerce.Model.Category;
import com.springcommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> categoryList(){
        return categoryRepository.findAll();
    }
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }
}
