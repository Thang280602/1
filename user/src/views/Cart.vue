<script setup>
import Header from '../components/Header.vue';
import Footer from '@/components/Footer.vue';
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const userName = ref('');
const cartItems = ref([]);
const router = useRouter();
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
const calculateTotal2 = (price, discount) => {
    return Math.round(price * (1 + discount / 100));

};
const calculateTotal1 = (price, discount, quantity) => {
    return price * (1 - discount / 100) * quantity;

};
const isLoggedIn = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        return false;
    }
    const decodedToken = decodeToken(token);
    userName.value = decodedToken.sub;
    console.log(userName.value);
    return !!token;
};
const getCart = async () => {
    if (!isLoggedIn()) {

        router.push('/login');
        return;
    }
    try {

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
const deleteProduct = async (id, index) => {
    try {

        const result = await Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel!",
            reverseButtons: true
        });

        if (result.isConfirmed) {
            const response = await axios.delete(`http://localhost:8080/cart/delete/${id}`);
            console.log('CartItem deleted:', response.data);
            Swal.fire("Deleted!", "Your file has been deleted.", "success");
            cartItems.value.splice(index, 1);

        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire("Cancelled", "Your imaginary file is safe :)", "error");
        }
    } catch (error) {
        console.error('Error deleting product:', error);
    }
}
const updateCartItem = async (cartItem, index) => {
    try {
        const response = await axios.put('http://localhost:8080/cart/updateCartItem', null, {
            params: {
                id: cartItem.id,
                quantity: cartItem.quantity
            }
        });
        
        if (response.status === 200) {
            Swal.fire("Update!", "Your file has been update.", "success");
        } else {
            console.error('Failed to update cart item:', response.statusText);
        }
    } catch (error) {
        console.error('Error updating cart item:', error);
    }
}
const goToOrderPage = () => {
    router.push('/order');
        };
const calculateCartTotal = () => {
    console.log(cartItems.value);
      return cartItems.value.reduce((total, cartItem) => {
        const itemTotal = calculateTotal(cartItem?.productDetail?.product?.price, cartItem?.productDetail?.discount) * cartItem?.quantity;
        return total + itemTotal;
      }, 0);
    };
const getImageUrl = (relativePath) => {
    return `http://localhost:8080/uploads/${relativePath}`;
};
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
        <div class="cart">
            <div class="textcart">
                <div class="text5">
                    <span>Shopping Cart</span>
                </div>
                <div class="text6">
                    <span>Items will be shipped to you as soon as the seller has specified. Possible delays due to
                        holidays.</span>
                </div>
            </div>
            <div class="chia">
                <div class="listcart">
                    <div class="cartItem"  v-for="(cartItem, index) in cartItems" :key="cartItem.id">
                        <input type="hidden" v-model="cartItem.id">
                        <div class="imgcart">
                            <div class="heartcart">
                                <img src="../assets/img/heart.png" alt="">
                            </div>
                            <div class="imganh">
                                <img :src="getImageUrl(cartItem?.productDetail?.image)" alt="">
                            </div>
                        </div>
                        <div class="cartaction" style="margin-left:20px">
                            <div class="productname">
                                <div class="tenproduct" style="margin-top:10px;">
                                    <span class="textname">
                                        {{ cartItem?.productDetail?.product?.productName }}
                                    </span><br>
                                    <span class="textdescripstion">
                                        Size: {{ cartItem?.productDetail?.size?.sizeName }}
                                    </span>
                                </div>
                                <div class="deletecart">
                                    <button @click="deleteProduct(cartItem.id, index)"><img
                                            src="../assets/img/deletecart.png" alt=""></button>
                                </div>
                            </div>
                            <div class="action">
                                <div class="priceproduct">
                                    <span class="textprice">
                                        Price: {{
                                            calculateTotal(cartItem?.productDetail?.product?.price, cartItem?.productDetail?.discount)
                                        }}
                                    </span>
                                </div>
                                <div class="addcart">
                                    <input type="text" placeholder="1"
                                        style="border: 2px solid black; border-radius: 8px;"
                                        v-model="cartItem.quantity">
                                    <button @click="updateCartItem(cartItem, index)"><img
                                            src="../assets/img/iconcart.png"></button>
                                </div>
                            </div>
                            <div class="total" style="margin-top: 0%;">
                                Total : {{
                                    calculateTotal1(cartItem?.productDetail?.product?.price, cartItem?.productDetail?.discount,cartItem?.quantity)}}
                                VND
                            </div>
                        </div>
                    </div>

                </div>
                <div class="caculrator">
                    <div class="textOrder">
                        Order Summary
                    </div>
                    <div class="discoutcode">
                        <div class="textdis">
                            <span>Discount code:</span>
                        </div>
                        <div class="code">
                            <input type="text" >
                            <button type="submit">Apply</button>
                        </div>
                    </div>
                    <div class="discripstion">
                        <div class="tieudethanhtoan">
                            <span>Order value :</span><br>
                            <span>VAT :</span><br>
                        </div>
                        <div class="thanhtoan">
                            <span>{{ calculateCartTotal()}} VND</span><br>
                            <span>10%</span><br>
                           
                        </div> 
                    </div>
                    <div class="total">
                        <div class="textTotal">
                            <span>TOTAL :</span>
                        </div>
                        <div class="gtTotal">
                            <span>{{ calculateTotal2(calculateCartTotal(),10) }} VND</span><br>
                        </div>
                    </div>
                    <div class="button-total">
                        <button type="submit" @click="goToOrderPage"><span>Buy now</span></button>
                    </div>

                </div>
            </div>
        </div>
        <a href="/">
            <div class="backtoindex">

                <div class="iconback">
                    <img src="../assets/img/back.png" alt="">
                </div>
                <div class="textback">
                    <span>
                        Continue Shopping
                    </span>
                </div>

            </div>
        </a>
        <div class="last">
            <div class="container">
                <div class="textlast">
                    Similar Items You Might Also Like
                </div>
                <div class="row mx-auto my-auto justify-content-center">
                    <div id="recipeCarousel" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner" role="listbox">
                            <div class="carousel-item active">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 1</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 2</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 3</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 4</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 5</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 6</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 7</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <div class="col-md-2">
                                    <div class="SP">
                                        <div class="imgIconSP">
                                            <div class="imgSP">
                                                <img src="../assets/img/sp.png" alt="">
                                            </div>
                                            <div class="iconsp">
                                                <div class="iconheartsp">
                                                    <img src="../assets/img/heart.png" alt="">
                                                </div>
                                                <div class="iconcartsp">
                                                    <img src="../assets/img/iconcart.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="informationsp">
                                            <div class="namesp">
                                                <span>Adidas Balance 8</span>
                                            </div>
                                            <div class="pricesp">
                                                <span>$ 200.00</span>
                                            </div>
                                            <div class="discripsp">
                                                <span>5 types of shoos available</span>
                                            </div>
                                            <div class="starsp">
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="onestar">
                                                    <img src="../assets/img/onestar.png" alt="">
                                                </div>
                                                <div class="halfstar">
                                                    <img src="../assets/img/halfstar.png" alt="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <a class="carousel-control-prev bg-transparent w-aut lefticon" href="#recipeCarousel" role="button"
                        data-bs-slide="prev">
                        <span class="carousel-control-prev-icon iconnext" aria-hidden="true"></span>
                    </a>
                    <a class="carousel-control-next bg-transparent w-aut righticon" href="#recipeCarousel" role="button"
                        data-bs-slide="next">
                        <span class="carousel-control-next-icon iconnext " aria-hidden="true"></span>
                    </a>
                </div>
            </div>

        </div>
        <Footer></Footer>
    </main>
</template>
<style>
@import url('/src/assets/css/cart.css');
</style>