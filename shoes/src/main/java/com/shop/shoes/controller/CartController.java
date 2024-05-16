//package com.shop.shoes.controller;
//
//import com.shop.shoes.model.Cart;
//import com.shop.shoes.model.ProductDetail;
//import com.shop.shoes.repository.CartItemRepository;
//import com.shop.shoes.repository.CartRepository;
//import com.shop.shoes.repository.ProductRepository;
//import com.shop.shoes.security.CustomUserDetails;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.endpoint.SecurityContext;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class CartController {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @PostMapping("/add-to-cart")
//    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartRequest request) {
//        String userId = request.getUserId();
//
//        // Kiểm tra xem người dùng đã có giỏ hàng (cart) hay chưa
//        Cart cart = cartRepository.findByUserId(userId);
//
//        if (cart != null) {
//            Product product = productRepository.findById(request.getProductId()).orElse(null);
//            if (product != null) {
//                // Tạo mới hoặc cập nhật cartItem
//                CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
//                if (cartItem != null) {
//                    cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
//                } else {
//                    cartItem = new CartItem(cart, product, request.getQuantity());
//                }
//                cartItemRepository.save(cartItem);
//            }
//        } else {
//            cart = new Cart(userId);
//            cartRepository.save(cart);
//            // Lấy thông tin sản phẩm từ request hoặc từ cơ sở dữ liệu
//            Product product = productRepository.findById(request.getProductId()).orElse(null);
//            if (product != null) {
//                CartItem cartItem = new CartItem(cart, product, request.getQuantity());
//                cartItemRepository.save(cartItem);
//            }
//        }
//
//        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
//    }
//}
//
