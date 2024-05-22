<script setup>
import Header from '../components/Header.vue';
import Footer from '@/components/Footer.vue';
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import axios from 'axios';
import router from '@/router';

const userName = ref('');
const cartItems = ref([]);
const totalOrderPrice = ref(0);
const addressShip = ref('');
const paymentMethod = ref('online');
const user = ref({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: ''
});

let token = ''; // Sử dụng biến cục bộ thay vì ref
const decodeToken = (token) => {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
};
const calculateTotal = (price, discount) => {
    return price * (1 - discount / 100);

};
const calculateCartTotal = () => {
    return cartItems.value.reduce((total, cartItem) => {
        const itemTotal = calculateTotal(cartItem.productDetail.product.price, cartItem.productDetail.discount) * cartItem.quantity;
        return total + itemTotal;
    }, 0);
};
const calculateTotal2 = (price, discount) => {
    return Math.round(price * (1 + discount / 100));
};

const calculateTotal1 = (price, discount, quantity) => {
    return price * (1 - discount / 100) * quantity;

};
const getCart = async () => {
    try {
        const response = await axios.get('http://localhost:8080/cart', {
            params: {
                userName: userName.value
            },
            headers: {
                Authorization: `Bearer ${token}` // Gửi token trong tiêu đề Authorization
            }
        });
        cartItems.value = response.data;
        updateTotalOrderPrice();
        console.log(cartItems.value);
    } catch (error) {
        console.error('Error adding to cart:', error);
        Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Đã xảy ra lỗi khi hiển thị giỏ hàng !',
            confirmButtonText: 'OK'
        });
    }
};
const saveOrder = async () => {
    
    try {

        const response = await axios.post('http://localhost:8080/order/checkout', null, {
            params: {
                userName: userName.value,
                totalOrderPrice: totalOrderPrice.value,
                addressShip: addressShip.value,

            },
            headers: {
                Authorization: `Bearer ${token}` // Gửi token trong tiêu đề Authorization
            }
        });
        if (paymentMethod.value === 'online') {
            const orderId = response.data.id;
            console.log(response.data);
            console.log(paymentMethod.value);
            console.log(orderId);
            const paymentResponse = await axios.get(`http://localhost:8080/order/pay/${orderId}`);
            window.location.href = paymentResponse.data;
            
        } else {
            alert('Đơn hàng của bạn đã được lưu thành công!');
            router.push('/history');
        }
        cartItems.value = response.data;
        console.log(paymentMethod.value);
    } catch (error) {
        console.error('Error adding to order:', error);
        Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Đã xảy ra lỗi  !',
            confirmButtonText: 'OK'
        });
    }
};
const getUser = async () => {
    token = localStorage.getItem('token');
    const decodedToken = decodeToken(token);
    userName.value = decodedToken.sub;
    const response = await axios.get('http://localhost:8080/users/getByUserName', {
        params: {
            userName: userName.value
        },
        headers: {
            Authorization: `Bearer ${token}` // Gửi token trong tiêu đề Authorization
        }
    });
    user.value = {
        firstName: response.data.firstName,
        lastName: response.data.lastName,
        email: response.data.email,
        phoneNumber: response.data.phoneNumber
    };
};

const updateTotalOrderPrice = () => {
    totalOrderPrice.value = calculateTotal2(calculateCartTotal(), 10);
    console.log(totalOrderPrice.value);
};

onMounted(getUser);
onMounted(getCart);
onMounted(() => {
    let items = document.querySelectorAll('.carousel .carousel-item')

    items.forEach((el) => {
        const minPerSlide = 6
        let next = el.nextElementSibling
        for (var i = 1; i < minPerSlide; i++) {
            if (!next) {
                // wrap carousel by using first child
                next = items[0]
            }
            let cloneChild = next.cloneNode(true)
            el.appendChild(cloneChild.children[0])
            next = next.nextElementSibling
        }
    })
});
</script>

<template>
    <main>
        <Header></Header>
        <section class="checkout-area pb-70" style="height: 820px;">
            <div class="container container-small">
                <form @submit.prevent="saveOrder">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="checkbox-form">
                                <h3>Billing Details</h3>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="checkout-form-list">
                                            <label>First Name <span class="required">*</span></label>
                                            <input type="text" placeholder="" v-model="user.firstName">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="checkout-form-list">
                                            <label>Last Name <span class="required">*</span></label>
                                            <input type="text" placeholder="" v-model="user.lastName">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="checkout-form-list">
                                            <label>Email Address <span class="required">*</span></label>
                                            <input type="email" placeholder="" v-model="user.email">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="checkout-form-list">
                                            <label>Phone <span class="required">*</span></label>
                                            <input type="text" placeholder="" v-model="user.phoneNumber">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="checkout-form-list">
                                            <label>Address Ship<span class="required">*</span></label>
                                            <input type="text" placeholder="Street address" v-model="addressShip">
                                        </div>
                                    </div>



                                </div>

                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="your-order mb-30 ">
                                <h3>Your order</h3>
                                <div class="your-order-table table-responsive">
                                    <table>
                                        <thead>
                                            <tr>
                                                <th class="product-name">Product</th>
                                                <th class="product-total">Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr class="cart_item" v-for="(cartItem, index) in cartItems"
                                                :key="cartItem.id">
                                                <td class="product-name">
                                                    {{ cartItem.productDetail.product.productName }}<strong
                                                        class="product-quantity"> × {{ cartItem.quantity }}</strong>
                                                </td>
                                                <td class="product-total">
                                                    <span class="amount">{{
                                                        calculateTotal1(cartItem.productDetail.product.price,
                                                            cartItem.productDetail.discount, cartItem.quantity) }} VND</span>
                                                </td>
                                            </tr>

                                        </tbody>
                                        <tfoot>
                                            <tr class="cart-subtotal">
                                                <th>Cart Subtotal</th>
                                                <td><span class="amount">{{ calculateCartTotal() }} VND</span></td>
                                            </tr>
                                            <tr class="shipping">
                                                <th>VAT</th>
                                                <td><span class="amount">10%</span></td>
                                            </tr>
                                            <tr class="order-total">
                                                <th>Order Total</th>
                                                <td><strong><span class="amount">{{
                                                    calculateTotal2(calculateCartTotal(), 10) }}
                                                            VND</span></strong>
                                                </td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <div class="paymentMethod" style="display: flex;justify-content: space-around;">
                                    <input type="radio" name="TT" v-model="paymentMethod" value="online"><span style="margin-left: -8%;">Thanh toán Online</span>
                                    <br>
                                    <input type="radio" name="TT" v-model="paymentMethod" value="offline"><span style="margin-left: -8%;">Thanh toán Offline</span><br>
                                </div>
                                <div class="payment-method ">
                                    <div class="order-button-payment mt-20" style="display: flex;align-items: center;
                                    justify-content: center;" >
                                        <button type="submit" class="fill-btn">Payment</button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </section>
        <Footer></Footer>
    </main>
</template>
<style>
@import url('/src/assets/css/order.css');
</style>