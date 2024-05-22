<script setup>

import index from '../views/index.vue'
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
const cartItems = ref([]);
const userName = ref('');
const decodeToken = (token) => {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
};
const isLoggedIn = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        return false;
    }
    const decodedToken = decodeToken(token);
    userName.value = decodedToken.sub;
    return !!token;
};
const getCart = async () => {
    if (!isLoggedIn()) {

        router.push('/login');
        return;
    }
    try {
        console.log(userName.value);
        const response = await axios.get('http://localhost:8080/cart', {
            params: {
                userName: userName.value
            }
        });
        cartItems.value = response.data;
        console.log(response.data);
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
const calculateCartTotal = () => {
    console.log(cartItems.value);
      return cartItems.value.reduce((total, cartItem) => {
        const itemTotal = calculateTotal(cartItem?.productDetail?.product?.price, cartItem?.productDetail?.discount) * cartItem?.quantity;
        return total + itemTotal;
      }, 0);
    };
    const calculateTotal = (price, discount) => {
    return price * (1 - discount / 100);

};

const calculateTotal2 = (price, discount) => {
    return Math.round(price * (1 + discount / 100));

};
onMounted(getCart);
</script>
<template>
    <main>
        <index class="index"></index>
        <div class="layout">
        </div>
        <div class="table3">
            <div class="item1" v-for="cartItem in cartItems">
                <div class="item-cartsp">
                    <div class="item-cartname">
                       {{ cartItem.productDetail.product.productName }}
                    </div><br>
                    <div class="item-ship">Shipper today
                    </div>
                </div>
                <div class="deleteandprice">
                    <div class="deleteitem">
                        <img src="../assets/img/deletecart.png">
                    </div>
                    <div class="priceproduct1">
                        <span>{{ cartItem.productDetail.product.price - cartItem.productDetail.product.price*cartItem.productDetail.discount/100 }} VND
                        </span>
                    </div>
                </div>
            </div>
                <div class="textcacu">
                    <div class="textnhan">
                        <span>
                            Order Value:
                        </span><br>
                        <span>
                            VAT:
                        </span><br>
                        <span>
                           Total befor discount:
                        </span><br>
                    </div>
                    <div class="numbercacu">
                        <span>
                            {{ calculateCartTotal()}} VND
                        </span><br>
                        <span>
                            10%
                        </span><br>
                        <span class="dis" >
                            {{ calculateTotal2(calculateCartTotal(),10) }} VND
                        </span><br>
                    </div>
                </div>
                <button class="buttonall1">
                    <div class="contentbut1">
                        <div class="textbut1">
                           <a href="/cart"><span>Buy Now</span></a> 
                        </div>
                        <div class="iconbut1">
                            <img src="../assets/img/arrow.png">
                        </div>
                    </div>      
                </button>
        </div>
        <div class="table4">
        </div>
    </main>
</template>
<style>
@import url('/src/assets/css/carthover.css');
</style>