package com.shop.shoes.controller;

import com.shop.shoes.dto.UserDTO;
import com.shop.shoes.model.Cart;
import com.shop.shoes.model.CartItem;
import com.shop.shoes.model.Order;
import com.shop.shoes.model.OrderDetail;
import com.shop.shoes.model.User;
import com.shop.shoes.service.CartItemService;
import com.shop.shoes.service.CartService;
import com.shop.shoes.service.OrderDetailService;
import com.shop.shoes.service.OrderService;
import com.shop.shoes.service.UserService;
import com.shop.shoes.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    public CartService cartService;
    @Autowired
    public UserService userService;
    @Autowired
    public UserUtils userUtils;
    @Autowired
    public OrderService orderService;
    @Autowired
    public CartItemService cartItemService;
    @Autowired
    public OrderDetailService orderDetailService;

    @PostMapping("/order/checkout")
    public ResponseEntity<Order> checkout(@RequestParam("userName") String userName,
                                          @RequestParam("addressShip") String addressShip,
                                          @RequestParam("totalOrderPrice") Double totalOrderPrice) {
        UserDTO userDTO = this.userService.findByUserName(userName);
        User user = userUtils.mapUserDtoToUser(userDTO);
        Cart cart = this.cartService.findCartByUser(user);
        List<CartItem> cartItems = this.cartItemService.findCartItemByCart(cart);

        // Khởi tạo một đối tượng Order mới
        Order order = new Order();
        order.setUser(user);
        order.setCreateAt(new Date());
        order.setStatus(0);
        order.setAddressShip(addressShip);
        order.setTotalPrice(totalOrderPrice);
        order.setPhone(user.getPhoneNumber());
        this.orderService.create(order);

        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setPrice(cartItem.getProductDetail().getProduct().getPrice());
            orderDetail.setProductDetail(cartItem.getProductDetail());
            orderDetail.setQuantity(cartItem.getQuantity());
            this.orderDetailService.add(orderDetail);
        }
        this.cartItemService.deleteCartItemByCartId(cart.getId());
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
