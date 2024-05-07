package dev.umang.productservice09april.services;

import dev.umang.productservice09april.models.Category;
import dev.umang.productservice09april.models.Product;
import dev.umang.productservice09april.repositories.CategoryRepository;
import dev.umang.productservice09april.repositories.ProductRepository;
import dev.umang.productservice09april.repositories.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service("selfproductservice")
public class SelfProductService implements ProductService{
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product createProduct(String title,
                                 String description,
                                 double price,
                                 String category,
                                 String image) {
        Product p = new Product();
        p.setTitle(title);
        p.setDescription(description);
        p.setPrice(price);
        p.setImageURL(image);
        /*
        I need to confirm if already the category exists
         */
        Category categoryFromDatabase = categoryRepository.findByTitle(category);

        if(categoryFromDatabase == null){
            Category category1 = new Category();
            category1.setTitle(category);
            //categoryRepository.save(category1); //persist as cascade type
            categoryFromDatabase = category1;
        }

        p.setCategory(categoryFromDatabase); //persist as cascade type

        Product savedProduct = productRepository.save(p);
        return savedProduct;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        return productRepository.findByIdIs(productId);
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /*

     */
    public List<Product> getProductsByCategory(String category_name){
        List<ProductProjection> prd = productRepository.findAllProductsByCategoryNameAndProductPrice("abc", 90);
        //prd.get(0).getTitle();
        return productRepository.findAllByCategory_Title(category_name);
    }

    private static String generateRandomTitle() {
        String[] titles = {"Product A", "Product B", "Product C", "Product D", "Product E"}; // Add more titles as needed
        Random random = new Random();
        return titles[random.nextInt(titles.length)];
    }

    private static double generateRandomPrice() {
        // Generate a random price between 10 and 100
        return 10 + (Math.random() * 90);
    }

    @Override
    public boolean generateProducts() {
        List<Product> prod = new ArrayList<>();
        for(int i = 1 ; i <= 20 ; i++){
            Product p = new Product(generateRandomTitle(), generateRandomPrice());
            prod.add(p);
            //productRepository.save(p);
        }

        productRepository.saveAll(prod);
        return false;
    }

    @Override
    public Page<Product> getPaginatedProducts(Integer pageNo, Integer pageSize, String sort) {
        Pageable pageable = null;
        if(sort != null){
           pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, sort );
        }else{
            pageable = PageRequest.of(pageNo, pageSize);
        }
        return productRepository.findAll(pageable);
    }
}

/*
100 products
page size = 10
how many pages - 10
1st page - 1 to 10
2nd page - 11 to 20
nth page - ??

20 products
page size = 4
how many pages = 5
 */