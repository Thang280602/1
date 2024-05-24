<script setup>
import Footer from '@/components/Footer.vue';
import Header from '@/components/Header.vue';
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import axios from 'axios';
import { useRoute } from 'vue-router';

const route = useRoute();
const urlParams = new URLSearchParams(window.location.search);
const vnpOrderInfo = urlParams.get('vnp_OrderInfo');
const order = ref(null);
let orderId = null;
const orderDetail= ref([]);
if (vnpOrderInfo) {
    const parts = vnpOrderInfo.split(':');
    if (parts.length > 1) {
        orderId = parts[1].trim();
    }
}
console.log(orderId);

const formatDate = (dateString) => {
    if (!dateString) return '';
    return dateString.split('T')[0]; // Lấy phần ngày trước ký tự 'T'
};

const getCart = async () => {
    if (orderId) {
        try {
            const response = await axios.get(`http://localhost:8080/order/getById/${orderId}`);
            order.value = response.data;
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching order:', error);
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Đã xảy ra lỗi khi hiển thị giỏ hàng!',
                confirmButtonText: 'OK'
            });
        }
    }
};
const getOrrderDetail = async () => {
    if (orderId) {
        try {
            const response = await axios.get(`http://localhost:8080/order/findOrderDetailByOrder/${orderId}`);
            orderDetail.value = response.data;
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching order:', error);
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Đã xảy ra lỗi khi hiển thị giỏ hàng!',
                confirmButtonText: 'OK'
            });
        }
    }
};
onMounted(getCart);
onMounted(getOrrderDetail);
</script>

<template>
    <main>
        <Header></Header>
        <section class="contact_us section-padding" style="height: 900px; margin-top: 10%;">
            <div class="container">
                <div class="row">
                    <div style="display: flex; justify-content: center;">
                        <div>
                            <div style="display: flex; justify-content: center; font-weight: bold; font-size: 40px">
                                Sports Shoe Shop DHT
                            </div>
                            <div style="margin-top: 5px; text-align: center">
                            </div>
                            <div style="margin-top: 5px; text-align: center">
                                <p>SDT: 0794070559</p>
                            </div>
                            <div style="margin-top: 10px; display: flex; justify-content: center">
                                <p>Mã hóa đơn: {{ order?.id }}</p>
                                <p style="margin-left: 30px">Ngày tạo: {{ formatDate(order?.createAt) }}</p>
                                <p style="margin-left: 60px">Status: Chờ xác nhận</p>
                            </div>
                        </div>
                    </div>
                    <div style="padding: 20px 10px; border-top-style: dashed; border-width: 1px; border-color: rgb(0, 0, 0);">
                        <div style="margin-left: 5px;">
                            <p>Khách hàng: {{ order?.user?.username }}</p>
                            <p>Số điện thoại: {{ order?.phone }}</p>
                            <p>Địa chỉ: {{ order?.addressShip }}</p>
                        </div>
                    </div>
                    <div style="padding: 20px 10px; border-top-style: dashed; border-width: 1px; border-color: rgb(0, 0, 0);">
                        <div style="margin-right: 20px;">
                            <p style="font-size: 18px; font-weight: 800; margin-bottom: 30px">Biên Lai</p>
                            <table class="table" style="width: 100%">
                                <thead>
                                    <tr>
                                        <td class="th"><strong>Stt</strong></td>
                                        <td class="th"><strong>Tên sản phẩm</strong></td>
                                        <td class="th"><strong>Số lượng</strong></td>
                                        <td class="th"><strong>Màu</strong></td>
                                        <td class="th"><strong>Size</strong></td>
                                        <td class="th"><strong>Giá</strong></td>
                                        <td class="th"><strong>Tổng</strong></td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(item, index) in orderDetail" :key="index">
                                        <td class="td">{{ index + 1 }}</td>
                                        <td class="td">{{ item.productDetail.product.productName }}</td>
                                        <td class="td">{{ item.quantity }}</td>
                                        <td class="td">{{ item.productDetail.color.colorName }}</td>
                                        <td class="td">{{ item.productDetail.size.sizeName }}</td>
                                        <td class="td">{{ item.productDetail.product.price }} VND</td>
                                        <td class="td">{{ item.productDetail.product.price - item.productDetail.product.price * item.productDetail.discount /100 }} VND</td>
                                    </tr>
                                </tbody>
                            </table>
                            <table>
                                <tbody>
                                    <tr>
                                        <td class="thick-line"><strong>Tổng Tiền hàng: </strong></td>
                                        <td class="thick-line text-end" float="right">{{ order?.totalPrice }} VND   (Đã tính cả VAT)</td>
                                    </tr>
                                    <tr>
                                        <td class="thick-line"><strong>Phí Ship: </strong></td>
                                        <td class="thick-line text-end" float="right"><span>Free Ship</span></td>
                                    </tr>
                                    <tr>
                                        <td class="thick-line"><strong>Tổng thanh toán: </strong></td>
                                        <td class="no-border text-end" float="right"> {{ order?.totalPrice }} VND</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div style="padding: 20px 10px; border-top-style: dashed; border-width: 1px; border-color: rgb(0, 0, 0);">
                        <div style="font-size: 18px; margin-top: 20px; font-weight: 700; text-align: center; width: 100%">
                            Thank you for visiting and purchasing products from the store:
                            <a href="/history" style="text-decoration: none;"><span style="color: orangered" >Theo dõi đơn hàng</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <Footer></Footer>
    </main>
</template>

<style scoped>

</style>
