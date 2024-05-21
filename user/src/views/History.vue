<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
const orders = ref([]);
const orderDetails = ref({});
const route = useRoute();
const router = useRouter();
const userName = ref('');
let token = '';
const selectedStatus = ref(0);

const filterOrders = async (status) => {
    try {
        token = localStorage.getItem('token');
        const decodedToken = decodeToken(token);
        userName.value = decodedToken.sub;
        selectedStatus.value = status;
        const response = await axios.get(`http://localhost:8080/order/status`, {
            params: {
                status: status,
                userName: userName.value
            },
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        orders.value = response.data;

        // Fetch order details for each order
        for (const order of orders.value) {
            await filterOrderDetail(order.id);
        }
    } catch (error) {
        console.error('Error fetching orders:', error);
    }
};

const filterOrderDetail = async (orderId) => {
    try {
        const response = await axios.get(`http://localhost:8080/order/findOrderDetailByOrder/${orderId}`);
        orderDetails.value[orderId] = response.data;
    } catch (error) {
        console.error('Error fetching order details:', error);
    }
};
const cancelOrder = async (orderId) => {
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
            await axios.put(`http://localhost:8080/order/cancel/${orderId}`, null, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            Swal.fire("Deleted!", "Your order has been cancelled.", "success");
            filterOrders(selectedStatus.value);
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            Swal.fire("Cancelled", "Your order is safe :)", "error");
            filterOrders(selectedStatus.value);
        }
    } catch (error) {
        console.error('Error canceling order:', error);
    }
};

const decodeToken = (token) => {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
};

watch(() => route.query.status, (newValue) => {
    filterOrders(newValue || 0);
});

const getImageUrl = (relativePath) => {
    return `http://localhost:8080/uploads/${relativePath}`;
};

const changeStatus = (status) => {
    router.push({ query: { status: status } });
    filterOrders(status);
};

const calculateTotal = (price, discount) => {
    return price * (1 - discount / 100);
};

onMounted(() => {
    filterOrders(route.query.status || 0);
});
</script>

<template>
    <main>
        <Header></Header>
        <section class="checkout-area pb-70 abc">
            <div>
                <h1 style="display: flex; align-items: center; justify-content: center;">Lịch Sử Mua Hàng</h1>
                <div class="menu" style="display: flex; justify-content: space-between; margin: 50px;">
                    <button :class="{ active: selectedStatus.value === 0 }" @click="changeStatus(0)"
                        style="color: blueviolet;">Chờ xác nhận</button>
                    <button :class="{ active: selectedStatus.value === 1 }" @click="changeStatus(1)"
                        style="color: blueviolet;">Chờ lấy hàng</button>
                    <button :class="{ active: selectedStatus.value === 2 }" @click="changeStatus(2)"
                        style="color: blueviolet;">Đang giao hàng</button>
                    <button :class="{ active: selectedStatus.value === 3 }" @click="changeStatus(3)"
                        style="color: blueviolet;">Giao hàng thành công</button>
                    <button :class="{ active: selectedStatus.value === 4 }" @click="changeStatus(4)"
                        style="color: blueviolet;">Đã hủy</button>
                </div>
                <div v-if="orders.length" style="margin-left: 15%;">
                    <div class="order" v-for="order in orders" :key="order.id">
                        <div class="cartItem1" v-for="cartItem in orderDetails[order.id]" :key="cartItem.id">
                            <input type="hidden" v-model="cartItem.id">
                            <div class="imganh1">
                                <img :src="getImageUrl(cartItem?.productDetail?.image)" alt="">
                            </div>
                            <div class="cartaction1" style="margin-left:20px">
                                <div class="productname">
                                    <div class="tenproduct" style="margin-top:10px;">
                                        <span class="textname">
                                            {{ cartItem?.productDetail?.product?.productName }} x {{ cartItem.quantity
                                            }}
                                        </span><br>
                                        <span class="textdescription">
                                            Size: {{ cartItem?.productDetail?.size?.sizeName }}
                                        </span><br>
                                        <span>Price : <span><del>{{ cartItem.productDetail.product.price }}
                                                    VND</del></span></span><br>
                                        <span>Price after discount: <span>{{
                                            calculateTotal(cartItem?.productDetail?.product?.price,
                                                cartItem?.productDetail?.discount) }} VND</span></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="display: flex; justify-content: space-between; align-items: center;">
                            <span style="margin-left:18%; margin-bottom: 1%;">Total price: {{ order.totalPrice }}
                                VND</span>
                            <button v-if="order.status !== 4" @click="cancelOrder(order.id)"
                                style="background-color: orangered; margin-right: 20%; margin-bottom: 1%; border-radius: 8px; width: 15%; height: 30px;">
                                <span>Cancel Order</span>
                            </button>
                        </div>
                    </div>
                </div>
                <div v-else style="margin-left: 42%;">
                    <p>Không có đơn hàng nào.</p>
                </div>
            </div>
        </section>
        <Footer></Footer>
    </main>
</template>

<style scoped>
.menu button {
    text-decoration: none;
    border-bottom: 2px solid transparent;
    /* Mặc định ẩn gạch chân */
}

.menu button.active {
    border-bottom-color: blueviolet;
    /* Hiển thị gạch chân khi nút được chọn */
}

.order {
    height: 100%;
    width: 80%;
    margin-bottom: 50px;
    background-color: aqua;
}

.cartItem1 {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    width: 100%;
    height: 150px;
    background-color: antiquewhite;
}

.imgcart1 {
    display: flex;
    align-items: center;
    position: relative;
}

.imganh1 img {
    width: 100%;
    height: 150px;
}

.abc {
    margin-bottom: 20%;
}

.cartaction1 {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: -2%;
}
</style>
