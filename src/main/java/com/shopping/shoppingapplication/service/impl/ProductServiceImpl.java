package com.shopping.shoppingapplication.service.impl;

import com.shopping.shoppingapplication.domain.dto.ProductDTO;
import com.shopping.shoppingapplication.domain.entity.Product;
import com.shopping.shoppingapplication.domain.entity.ProductCategory;
import com.shopping.shoppingapplication.domain.filter.FilterProduct;
import com.shopping.shoppingapplication.exception.category.ProductCategoryNotFoundException;
import com.shopping.shoppingapplication.exception.product.ProductIncorrectFieldException;
import com.shopping.shoppingapplication.exception.product.ProductNotFoundException;
import com.shopping.shoppingapplication.repository.ProductCategoryRepository;
import com.shopping.shoppingapplication.repository.ProductRepository;
import com.shopping.shoppingapplication.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductServiceImpl(ModelMapper modelMapper,
                              ProductRepository productRepository,
                              ProductCategoryRepository productCategoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Long create(ProductDTO productDTO) throws ProductIncorrectFieldException {
        try {
            Product product = modelMapper.map(productDTO, Product.class);

            product = productRepository.save(product);

            return product.getId();
        }catch (DataIntegrityViolationException e) {
            throw new ProductIncorrectFieldException();
        }
    }

    public ProductDTO get(Long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return modelMapper.map(productOptional.get(), ProductDTO.class);
    }

    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();

        return products.parallelStream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO update(Long id, ProductDTO productDTO) throws ProductNotFoundException, ProductCategoryNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Product product = productOptional.get();

        if(productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }

        if(productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }

        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }

        if(productDTO.getProductCategoryId() != null) {
            Optional<ProductCategory> categoryOptional = productCategoryRepository.findById(productDTO.getProductCategoryId());

            if(categoryOptional.isEmpty()) {
                throw new ProductCategoryNotFoundException();
            }

            ProductCategory productCategory = categoryOptional.get();

            product.setProductCategory(productCategory);
        }

        product = productRepository.save(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO> filter(FilterProduct filterProduct) {
        return productRepository.filterProducts(filterProduct)
                .parallelStream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
