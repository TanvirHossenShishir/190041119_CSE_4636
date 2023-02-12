package com.example.shoppingsite;

import java.util.ArrayList;
import java.util.List;

/**
 *  This class represents a shopping cart. It stores a list of products,
 *  and provides methods to add, remove, and modify products in the cart.
 **/
public class Cart {
    private List<Product> products;
    public Cart() {
        products = new ArrayList<Product>();
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                products.remove(product);
                break;
            }
        }
    }
    public List<Product> getProducts() {
        return products;
    }

}
