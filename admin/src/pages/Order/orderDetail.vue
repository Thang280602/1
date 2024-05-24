<template>
    <div style="text-align: center;">
        <h1>Order Detail</h1>
    </div>
    <div v-if="order">
        <div class="1" style="display: flex; margin-top: 5%;">
            <div class="nameCustomer" style="margin-left: 10%;"> Name Customer:
                {{ order.user.username }}
            </div>
            <div class="phoneCustomer" style="margin-left: 40%;"> Phone Customer:
                {{ order.phone }}
            </div>
        </div>
        <div class="1" style="display: flex; ">
            <div class="nameCustomer" style="margin-left: 10%;">Day Create:
                {{ formatDate(order.createAt) }}
            </div>
            <div class="phoneCustomer" style="margin-left: 35%;"> AddressShip:
                {{ order.addressShip }}
            </div>
        </div>
        <div class="action" style="display: flex;">
            <div class="status-update" style="margin-top: 20px; margin-left: 10%;">
                <label for="orderStatus">Update Order Status:</label>
                <select id="orderStatus" v-model="selectedStatus" @change="updateOrderStatus"
                    style="border: 1px solid black; border-radius: 8px;" class="select-dropdown">
                    <option value="" selected disabled>Choose status</option>
                    <option value="0">Chờ xác nhận</option>
                    <option value="1">Chờ lấy hàng</option>
                    <option value="2">Đang giao hàng</option>
                    <option value="3">Giao hàng thành công</option>
                    <option value="4">Đã hủy</option>
                </select>
            </div>
            <div class="exportPDF">
                <button @click="exportPDF"><span>ExportPDF</span></button>
            </div>
        </div>

    </div>
    <div class="tableCategory">
        <table class="styled-table">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Image</th>
                    <th>Color Name</th>
                    <th>Size Name</th>
                    <th>quantity</th>
                    <th>Price</th>
                    <th>Discount</th>
                    <th>PriceAfterDiscont</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="orderDetail in orderDetails" :key="orderDetail.id">
                    <td class="text-center">{{ orderDetail.productDetail.product.productName }}</td>
                    <td class="text-center"> <img :src="getFullImageUrl(orderDetail.productDetail.product.image)"
                            alt="Product Image" width="100" height="100" /></td>
                    <td class="text-center">{{ orderDetail.productDetail.color.colorName }}</td>
                    <td class="text-center">{{ orderDetail.productDetail.size.sizeName }}</td>
                    <td class="text-center">{{ orderDetail.quantity }}</td>
                    <td class="text-center">{{ orderDetail.productDetail.product.price }} VND</td>
                    <td class="text-center">{{ orderDetail.productDetail.discount }} %</td>
                    <td class="text-center">{{
                        calculateTotal(orderDetail.productDetail.product.price, orderDetail.productDetail.discount) }}
                        VND
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="12" style="margin-left: 10%;">
        <span v-if="order">
            Total: {{ order.totalPrice }} (VAT included)
        </span>
    </div>
</template>



<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const selectedStatus = ref(null);
const route = useRoute();
const router = useRouter();
const order = ref(null);
const orderDetails = ref([]);
const exportPDFA = ref(null);
const orderId = ref(route.params.id);
console.log(orderId.value);

const getOrders = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/order/getById/${orderId.value}`);
        order.value = response.data;
        console.log(response.data);
    } catch (error) {
        console.error('Error:', error);
    }
};
const getOrrderDetail = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/order/findOrderDetailByOrder/${orderId.value}`);
        orderDetails.value = response.data;
        console.log(response.data);
    } catch (error) {
        console.error('Error:', error);
    }
};
const exportPDF = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/order/export/pdf/${orderId.value}`, {
            responseType: 'blob'
        });

        const blob = new Blob([response.data], { type: 'application/pdf' });
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);
        link.href = url;
        link.setAttribute('download', `order_${orderId.value}.pdf`);
        document.body.appendChild(link);
        link.click();
        URL.revokeObjectURL(url);
        document.body.removeChild(link);

        console.log('PDF downloaded successfully');
    } catch (error) {
        console.error('Error exporting PDF:', error);
    }
};
const getFullImageUrl = (imagePath) => {
    return `http://localhost:8080/uploads/${imagePath}`;
};
const formatDate = (dateString) => {
    if (!dateString) return '';

    const date = new Date(dateString);

    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();

    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
};
const calculateTotal = (price, discount) => {
    return price * (1 - discount / 100);

};
const updateOrderStatus = async () => {
    debugger
    try {
        if (!selectedStatus.value) {
            console.error('Please select a status');
            return;
        }

        const response = await axios.put(`http://localhost:8080/order/updateStatus/${orderId.value}`, null, {
            params: {
                status: selectedStatus.value
            }
        });
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Update status order successfully!',
            confirmButtonText: 'OK'
        });
        router.push('/order');
        console.log('Order status updated successfully:', response.data);
    } catch (error) {
        console.error('Error updating order status:', error);
    }
};


onMounted(() => {
    getOrders();
    getOrrderDetail();
});
</script>

<style lang="scss">
.exportPDF {
    margin-top: 10px;
    margin-left: 31%;
}

.exportPDF button {
    background-color: #00d1d1;
    /* Lighter Aqua */
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    font-weight: bold;
    border-radius: 5px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: background-color 0.3s, transform 0.3s;
}

.exportPDF button:hover {
    background-color: #00b3b3;
    /* Darker Aqua */
    transform: scale(1.05);
}

.exportPDF button:active {
    background-color: #009999;
    /* Even Darker Aqua */
    transform: scale(1);
}

.exportPDF span {
    font-family: 'Arial', sans-serif;
}

.tableCategory {
    margin: 20px;
}

.styled-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 18px;
    font-family: 'Arial', sans-serif;
}

.styled-table thead tr {
    background-color: #009879;
    color: #ffffff;
    text-align: left;
}

.styled-table th,
.styled-table td {
    padding: 12px 15px;
    border: 1px solid #dddddd;
}

.styled-table tbody tr {
    border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr:nth-of-type(even) {
    background-color: #f3f3f3;
}

.styled-table tbody tr:last-of-type {
    border-bottom: 2px solid #009879;
}

.styled-table tbody tr:hover {
    background-color: #f1f1f1;
}

.buttonEditPro1 {
    background-color: #4CAF50;
    /* Green */
    border: none;
    color: white;
    padding: 10px 24px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 4px;
}

.buttonEditPro1 a {
    color: white;
    text-decoration: none;
}
</style>
