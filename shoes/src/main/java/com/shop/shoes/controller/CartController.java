package com.shop.shoes.controller;

import com.shop.shoes.dto.UserDTO;
import com.shop.shoes.model.Cart;
import com.shop.shoes.model.CartItem;
import com.shop.shoes.model.ProductDetail;
import com.shop.shoes.model.User;
import com.shop.shoes.service.CartItemService;
import com.shop.shoes.service.CartService;
import com.shop.shoes.service.Impl.UserServiceImpl;
import com.shop.shoes.service.ProductDetailService;
import com.shop.shoes.service.ProductService;
import com.shop.shoes.util.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;

    @PostMapping("/cart/add")
    public ResponseEntity<Void> addCart(@RequestParam("productId") Long productId, @RequestParam("colorName") String colorName,
                                        @RequestParam("sizeName") String sizeName,
                                        @RequestParam("userName") String userName,
                                        @RequestParam("amout") int amount) {
        UserDTO userDTO = userService.findByUserName(userName);
        User user = userUtils.mapUserDtoToUser(userDTO);
        if (this.cartService.checkCartByUser(user.getId()) == 0) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartService.createCart(cart);
        }
        ProductDetail productDetail = productDetailService.findProductDetailByColorNameAndSizeName(productId, colorName, sizeName);
        Cart cartFindByUser = cartService.findCartByUser(user);
        CartItem cartItem = new CartItem();
        cartItem.setCart(cartFindByUser);
        CartItem checkCartItem = this.cartItemService.checkCardItem(cartFindByUser.getId(), productDetail.getId());
        if (checkCartItem != null) {
            checkCartItem.setQuantity(checkCartItem.getQuantity() + amount);
            this.cartItemService.create(checkCartItem);
        } else {
            cartItem.setProductDetail(productDetail);
            cartItem.setQuantity(amount);
            cartItem.setCart(cartFindByUser);
            this.cartItemService.create(cartItem);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Lấy danhh sách cartItem",
            description = "Trả về danh sách cartItem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách cartItem")})
    @GetMapping("/cart")
    public ResponseEntity<List<CartItem>> getAll(@RequestParam("userName") String userName) {
        UserDTO userDTO = this.userService.findByUserName(userName);
        User user = userUtils.mapUserDtoToUser(userDTO);
        Cart cart = this.cartService.findCartByUser(user);
        List<CartItem> cartItems = this.cartItemService.findCartItemByCart(cart);
        return ResponseEntity.status(HttpStatus.OK).body(cartItems);
    }

    @DeleteMapping("/cart/delete/{id}")
    public ResponseEntity<CartItem> deleteCartItemById(@PathVariable Long id) {
        cartItemService.deleteCartItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/cart/updateCartItem")
    public ResponseEntity<CartItem> updateCartItem(@RequestParam("id") Long id,@RequestParam("quantity") int quantity){
        CartItem cartItem= cartItemService.findById(id);
        cartItem.setQuantity(quantity);
        cartItemService.update(cartItem,id);
        return ResponseEntity.status(HttpStatus.OK).body(cartItem);
    }

}
