package dev.umang.productservice09april.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseModel{
    private String title;
    private String description;
    private double price;
    private String imageURL;
    @ManyToOne(cascade = CascadeType.PERSIST) //m:1
    private Category category;
    //The above is going to add
    //a foreign key
    public Product(String title, double price){
        this.title = title;
        this.price = price;
    }
}


/*
Product with Category
1 1
m  1

m : 1
we have to use id of 1 side on m side

Adding a product to your app
If already a category will be there for that prod,
same category id will be used
In other case, you'll create a category and use that for the prod
 */
