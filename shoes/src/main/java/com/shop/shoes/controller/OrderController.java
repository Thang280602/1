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
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
                                          @RequestParam("totalOrderPrice") Double totalOrderPrice
    ) {
        UserDTO userDTO = this.userService.findByUserName(userName);
        User user = userUtils.mapUserDtoToUser(userDTO);
        Cart cart = this.cartService.findCartByUser(user);
        List<CartItem> cartItems = this.cartItemService.findCartItemByCart(cart);


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

    @GetMapping("/order/status")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam("status") Integer status, @RequestParam("userName") String userName) {
        UserDTO userDTO = userService.findByUserName(userName);
        User user = userUtils.mapUserDtoToUser(userDTO);
        List<Order> orders = orderService.finOrdersByStatus(status, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/findOrderDetailByOrder/{orderId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailByOrder(@PathVariable("orderId") Long id) {
        Order order = orderService.findById(id);
        List<OrderDetail> orderDetails = orderDetailService.getByOrderId(order);
        return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
    }

    @PutMapping("/order/cancel/{orderId}")
    public ResponseEntity<Order> updateOrderByStatus(@PathVariable("orderId") Long id) {
        Order order = orderService.updateStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
    @GetMapping("/order/getById/{orderId}")
    public ResponseEntity<Order> geOrderById(@PathVariable("orderId") Long id){
        Order order = orderService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAll(){
        List<Order> orders = this.orderService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
    @PutMapping("/order/updateStatus/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long id,@RequestParam("status") Integer status){
        Order order = orderService.update(id,status);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

}
