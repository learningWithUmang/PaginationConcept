package dev.umang.productservice09april.controllers;

import dev.umang.productservice09april.commons.AuthenticationCommons;
import dev.umang.productservice09april.dtos.RequestBodyProductDto;
import dev.umang.productservice09april.dtos.Role;
import dev.umang.productservice09april.dtos.UserDTO;
import dev.umang.productservice09april.models.Product;
import dev.umang.productservice09april.services.FakeStoreProductService;
import dev.umang.productservice09april.services.ProductService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    /*
    ProductService productService
     */
    /*
    POST /products/
    Request body
    {
        "id" :
        "title:
        "descr":
        "category":
        "price"
     }
     */
    ProductService productService;
    AuthenticationCommons authenticationCommons;
    public ProductController(@Qualifier("selfproductservice") ProductService productService,
                             AuthenticationCommons authenticationCommons){
        this.productService = productService;
        this.authenticationCommons = authenticationCommons;
    }
    /*
    Qualifier is used to identify the depedency to be injected here
     */
    @PostMapping("/products")
    public Product createProduct(@RequestBody RequestBodyProductDto request){
        return productService.createProduct(request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCategory(),
                request.getImage());
    }

    // /product/1 - get details of a particular product
    /*
    id:
    title:
    price:
     */
    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long id){
        /*
        1 - > directly make a call fakestore api
        2 - > productService.getProductDetails()
         */
        return productService.getSingleProduct(id);
    }

    @GetMapping("/products/{token}")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable("token") @NonNull String token){
        //validate the token first
        UserDTO userDTO = authenticationCommons.validateToken(token);

        if(userDTO == null){
            //the token is valid, it's a forbidden request
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        boolean isAdmin = false;

        for(Role role : userDTO.getRoles()){
            if(role.getName().equals("Admin")){
                isAdmin = true;
            }
        }

        if(!isAdmin){
            //authg failed, not allowed
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        //only admins are allowed to go to this /products/
        //handle authg part here?



        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
    }

    @GetMapping("/paginationProducts/{pageNo}/{pageSize}")
    public Page<Product> getProductsByPage(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
        return productService.getPaginatedProducts(pageNo, pageSize, null);
    }

    @GetMapping("/paginationProductsByPrice/{pageNo}/{pageSize}")
    public Page<Product> getProductsByPrice(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
        return productService.getPaginatedProducts(pageNo, pageSize, "price");
    }


    public void updateProduct(){

    }

    @GetMapping("/generate")
    public boolean generateProducts(){
        return productService.generateProducts();
    }
}



/*
Every api call at the end of the day is a method call inside a controller
 */
/*
page no also starts from 0
20 products, 7 is page size
1 to 7 - 0th page
8 to 14 - 1st page
15 to 20 - 2nd page
nothing - 3rd page
nohting

 */