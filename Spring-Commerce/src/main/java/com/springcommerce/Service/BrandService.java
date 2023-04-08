package com.springcommerce.Service;

import com.springcommerce.Model.Brand;
import com.springcommerce.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> brandList(){
        return brandRepository.findAll();
    }
    public void saveBrand(Brand brand){
        brandRepository.save(brand);
    }
}
