package com.shopping.shoppingapplication.service.impl;

import com.shopping.shoppingapplication.domain.dto.ProductCategoryDTO;
import com.shopping.shoppingapplication.domain.entity.ProductCategory;
import com.shopping.shoppingapplication.exception.category.ProductCategoryIncorrectFieldException;
import com.shopping.shoppingapplication.exception.category.ProductCategoryNotFoundException;
import com.shopping.shoppingapplication.repository.ProductCategoryRepository;
import com.shopping.shoppingapplication.service.ProductCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ModelMapper modelMapper;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ModelMapper modelMapper,
                                      ProductCategoryRepository productCategoryRepository) {
        this.modelMapper = modelMapper;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Long create(ProductCategoryDTO productCategoryDTO) {
        try {
            ProductCategory productCategory = modelMapper.map(productCategoryDTO, ProductCategory.class);

            productCategory = productCategoryRepository.save(productCategory);

            return productCategory.getId();
        } catch (DataIntegrityViolationException e) {
            throw new ProductCategoryIncorrectFieldException();
        }
    }

    public ProductCategoryDTO get(Long id) throws ProductCategoryNotFoundException {

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);

        if(productCategory.isPresent()) {
            return modelMapper.map(productCategory.get(), ProductCategoryDTO.class);
        }

        throw new ProductCategoryNotFoundException();
    }

    public List<ProductCategoryDTO> getAll() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();

        return productCategories.parallelStream()
                .map(productCategory -> modelMapper.map(productCategory, ProductCategoryDTO.class))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        productCategoryRepository.deleteById(id);
    }

    public ProductCategoryDTO update(Long id, ProductCategoryDTO productCategoryDTO) throws ProductCategoryNotFoundException {
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(id);

        if(productCategoryOptional.isEmpty()) {
            throw new ProductCategoryNotFoundException();
        }

        ProductCategory productCategory = productCategoryOptional.get();

        if(productCategoryDTO.getName() != null) {
            productCategory.setName(productCategoryDTO.getName());
        }

        if(productCategory.getDescription() != null) {
            productCategory.setDescription(productCategoryDTO.getDescription());
        }

        productCategory = productCategoryRepository.save(productCategory);

        return modelMapper.map(productCategory, ProductCategoryDTO.class);
    }
}
