package com.springcommerce.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springcommerce.utils.CartStatus;

import javax.persistence.*;
import java.util.Set;

import static com.springcommerce.utils.Time.getPresentTime;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;
    private double total;
    private String createdAt;
    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private Set<CartItem> cartItems;

    public Cart() {
        this.createdAt = getPresentTime();
        this.status = CartStatus.ORDERING;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}

