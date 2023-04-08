package com.springcommerce.Service;

import com.springcommerce.Model.Cart;
import com.springcommerce.Repository.CartRepository;
import com.springcommerce.utils.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }
    public List<Cart> findALl(){
        return cartRepository.findAll();
    }
    public Cart findCartById(Long id){
        return cartRepository.findById(id).get();
    }
    public Cart findCartIsOrdering(List<Cart> cartList){
        cartList = cartRepository.findAll();
        if(cartList.isEmpty()){
            return new Cart();
        }
        for(Cart cart: cartList){
            if(cart.getStatus().equals(CartStatus.ORDERING)){
                return cart;
            }
        }
        return new Cart();
    }
    public List<Cart> findCartIsOrdered(){
        List<Cart> cartList = cartRepository.findAll();
        if(cartList.isEmpty()){
            return cartList;
        }
        cartList.removeIf(cart -> cart.getStatus().equals(CartStatus.ORDERING));
        return cartList;
    }
}
