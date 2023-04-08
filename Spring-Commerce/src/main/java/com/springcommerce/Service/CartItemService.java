package com.springcommerce.Service;

import com.springcommerce.Model.CartItem;
import com.springcommerce.Repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public void saveCartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }
    public Optional<CartItem> findCardItemById(Long id){
        return cartItemRepository.findById(id);
    }
    public void deleteById(Long id){
        cartItemRepository.deleteById(id);
    }
}
