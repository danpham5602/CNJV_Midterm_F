package com.springcommerce.Controller;

import com.springcommerce.Model.Cart;
import com.springcommerce.Model.CartItem;
import com.springcommerce.Model.Product;
import com.springcommerce.Service.CartItemService;
import com.springcommerce.Service.CartService;
import com.springcommerce.Service.ProductService;
import com.springcommerce.utils.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @GetMapping("/")
    public String getHome(Model model){
        List<Product> products = productService.listProduct();
        model.addAttribute("products", products);
        return "user/index";
    }
    @GetMapping("/search")
    public String getSearch(Model model,
                            @RequestParam(name = "keyword") String keyword){
        List<Product> products = productService.getProductByKeyword(keyword);
        model.addAttribute("products",products);
        model.addAttribute("keyword",keyword);
        return "user/index";
    }
    @GetMapping("cart")
    public String getCart(Model model){
        Cart cart = cartService.findCartIsOrdering(cartService.findALl());
        model.addAttribute("cart", cart);
        return "user/cart";
    }
    @PostMapping("/cart/{id}")
    public String postCart(@PathVariable Long id,
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

        return "redirect:/cart";
    }
    @PostMapping("/cart/delete/{id}")
    public String deleteCartItem(@PathVariable Long id){
        CartItem cartItem = cartItemService.findCardItemById(id).get();
        Cart cart = cartService.findCartIsOrdering(cartService.findALl());
        cart.setTotal(cart.getTotal() - cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setCart(null);
        cartItem.setProduct(null);
        cartItemService.saveCartItem(cartItem);
        cartItemService.deleteById(id);
        return "redirect:/cart";
    }
    @GetMapping("cart/checkout/{id}")
    public String postCheckout(@PathVariable Long id){
        Cart cart = cartService.findCartById(id);
        cart.setStatus(CartStatus.ORDERED);
        cartService.saveCart(cart);
        return "redirect:/history";
    }
    @GetMapping("history")
    public String getHistory(Model model){
        List<Cart> carts = cartService.findCartIsOrdered();
        model.addAttribute("carts", carts);
        return "user/history";
    }
}
