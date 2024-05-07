package dev.umang.productservice09april.services;

import dev.umang.productservice09april.dtos.FakeStoreProductdto;
import dev.umang.productservice09april.models.Category;
import dev.umang.productservice09april.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("fakestore")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreProductdto fakeStoreProductResponse = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductdto.class);
        /*
        make a call to external fakestore
         */
        return fakeStoreProductResponse.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product createProduct(String title,
                                 String description,
                                 double price,
                                 String category,
                                 String image) {
        FakeStoreProductdto fakeStoreProductdto = new FakeStoreProductdto();
        fakeStoreProductdto.setTitle(title);
        fakeStoreProductdto.setDescription(description);
        fakeStoreProductdto.setPrice(price);
        fakeStoreProductdto.setCategory(category);
        fakeStoreProductdto.setImage(image);
        FakeStoreProductdto fakeStoreProductdto1 = restTemplate.postForObject("https://fakestoreapi.com/products",
                fakeStoreProductdto,
                FakeStoreProductdto.class);

        return fakeStoreProductdto1.toProduct();
    }

    @Override
    public boolean generateProducts() {
        return false;
    }

    @Override
    public Page<Product> getPaginatedProducts(Integer pageNo, Integer pageSize, String sort) {
        return null;
    }
}

/*
/products/1

Rest Template(like a utility) - help you allow sending http requests to external apis and get response
 Inversion of Control(IoC)
 */