<script setup>
import Footer from '@/components/Footer.vue';
import Header from '@/components/Header.vue';
const urlParams = new URLSearchParams(window.location.search);
const vnpOrderInfo = urlParams.get('vnp_OrderInfo');

let orderId = null;
if (vnpOrderInfo) {
    const parts = vnpOrderInfo.split(':');
    if (parts.length > 1) {
        orderId = parts[1].trim();
    }
}
</script>

<template>
    <main>
        <Header></Header>
        <section class="contact_us section-padding" style="height: 700px;margin-top: 10%;">
            <div class="container">
                <div class="row">
                    <div class="" style="display: flex; justify-content: center;">
                        <div class="" style="">
                            <div class=""
                                style="display: flex; justify-content: center;font-weight: bold; font-size: 40px">Shop
                                CarForYou car sales shop
                            </div>
                            <div class="" style="margin-top: 5px; text-align: center">
                            </div>
                            <div class="" style="margin-top: 5px; text-align: center">
                                <p>
                                    SDT: 0794070559
                                </p>
                            </div>
                            <div class=""
                                style="margin-top: 10px;margin-left: 10px; display: flex; justify-content: center">
                                <p th:text="${'Mã hóa đơn: '+ order.id}"></p>
                                <p style="margin-left: 30px" th:text="'Ngày tạo: ' + ${ order.getCreateAt()}"></p>
                                <p style="margin-left: 60px" th:text="'Status: ' + ${checkstatus}"></p>
                            </div>
                        </div>
                    </div>
                    <div class=""
                        style="padding: 20px 10px;border-top-style: dashed; border-width: 1px; border-color: rgb(0, 0, 0);">
                        <div class="">
                            <div class="" style="margin-left: 5px; ">
                                <p th:text="${'Khách hàng: ' +  order.getUser().fullName}"></p>
                                <p th:text="${'Số điện thoại: '+order.getUser().getTelephone()}"></p>
                                <p th:text="${'Địa chỉ: '+  order.getAddressShip() }"></p>
                            </div>
                        </div>
                    </div>
                    <div
                        style="padding: 20px 10px;border-top-style: dashed; border-width: 1px; border-color: rgb(0, 0, 0);">
                        <div class="" style="margin-right: 20px;">
                            <p style="font-size: 18px; font-weight: 800; margin-bottom: 30px">Biên Lai
                            </p>
                            <table class="table " style="width: 100%">
                                <thead>
                                    <td class="th"><strong>Stt</strong></td>
                                    <td class="th"><strong>Tên sản phẩm</strong></td>
                                    <td class="th"><strong>Số lượng</strong></td>
                                    <td class="th"><strong>Giá</strong></td>
                                    <td class="th"><strong>Tổng</strong></td>
                                </thead>
                                <tbody>
                                    <th:block th:each="item,index : ${card.cardItems}">
                                        <tr>
                                            <td class="td" th:text="${index.index  + 1}">BS-200</td>
                                            <td class="td" th:text="${item.getProduct().getProductName()}">BS-200</td>
                                            <td class="td" th:text="${item.getQuantity()}">1</td>
                                            <td class="td" th:text="${item.getProduct().getPrice()} +' $'"></td>
                                            <td class="td"
                                                th:text="${item.getQuantity() * item.getProduct().getPrice()} + ' $'    ">
                                            </td>
                                        </tr>
                                    </th:block>
                                </tbody>
                            </table>
                            <table>
                                <tbody>
                                    <tr>
                                        <td class="thick-line "><strong>Tổng Tiền hàng:</strong></td>
                                        <td class="thick-line text-end" float="right" th:text="${total} + ' $'"></td>
                                    </tr>
                                    <tr>
                                        <td class="thick-line"><strong>Phí Ship:</strong></td>
                                        <td class="thick-line  text-end" float="right"><span>Free Ship</span></td>
                                    </tr>
                                    <tr>
                                        <td class="thick-line "><strong>Tổng thanh toán:</strong></td>
                                        <td class="no-border  text-end" float="right" th:text="${total}">
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div
                        style="padding: 20px 10px;border-top-style: dashed; border-width: 1px; border-color: rgb(0, 0, 0);">
                        <div
                            style="font-size: 18px;margin-top: 20px; font-weight: 700; text-align: center; width: 100%">
                            Thank you for visiting and purchasing products from the store :
                            <a th:href="@{/}"><span style="color: orangered">Return shop</span> </a>
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
