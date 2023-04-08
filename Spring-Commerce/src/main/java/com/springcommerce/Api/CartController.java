package com.springcommerce.Api;

import com.springcommerce.Model.Cart;
import com.springcommerce.Model.CartItem;
import com.springcommerce.Service.CartItemService;
import com.springcommerce.Service.CartService;
import com.springcommerce.Service.ProductService;
import com.springcommerce.utils.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;
    @Autowired
    CartItemService cartItemService;
    @GetMapping()
    public ResponseEntity<Cart> getCart(){
        Cart cart = cartService.findCartIsOrdering(cartService.findALl());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long id,
                                                 @RequestParam(name = "quantity") int quantity){
        Cart cart = cartService.findCartIsOrdering(cartService.findALl());
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(productService.getProductById(id).get());
        cartItem.setQuantity(quantity);
        cartItemService.saveCartItem(cartItem);
        double total = 0;
        if(cart.getCartItems() != null){
            for(CartItem item : cart.getCartItems()){
                total += item.getProduct().getPrice() * item.getQuantity();
            }
        }else{
            total = cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        cart.setTotal(total);
        cartService.saveCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id){
        CartItem cartItem = cartItemService.findCardItemById(id).get();
        Cart cart = cartService.findCartIsOrdering(cartService.findALl());
        cart.setTotal(cart.getTotal() - cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setCart(null);
        cartItem.setProduct(null);
        cartItemService.saveCartItem(cartItem);
        cartItemService.deleteById(id);

        return new ResponseEntity<>(cart, HttpStatus.NO_CONTENT);
    }
    @GetMapping("/checkout/{id}")
    public ResponseEntity<Cart> checkoutCart(@PathVariable Long id){
        Cart cart = cartService.findCartById(id);
        cart.setStatus(CartStatus.ORDERED);
        cartService.saveCart(cart);

        return new ResponseEntity<>(cart, HttpStatus.ACCEPTED);
    }
    @GetMapping("/history")
    public ResponseEntity<List<Cart>> history(){
        List<Cart> cartList = cartService.findCartIsOrdered();
        return new ResponseEntity<>(cartList, HttpStatus.ACCEPTED);
    }
}
