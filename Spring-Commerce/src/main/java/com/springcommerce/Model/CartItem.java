package com.springcommerce.Model;
import javax.persistence.*;

import static com.springcommerce.utils.Time.getPresentTime;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "id")
    private Product product;
    private int quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID", referencedColumnName = "id")
    private Cart cart;
    private String createdAt;

    public CartItem(){
        this.createdAt = getPresentTime();
        this.quantity = 0;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
