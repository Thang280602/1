<template>
    <div class="tableCategory">
        <table class="styled-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Customer Name</th>
                    <th>Status</th>
                    <th>Day Create</th>
                    <th>Phone</th>
                    <th>Address Ship</th>
                    <th>Total Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="order in orders" :key="order.id">
                    <td>{{ order.id }}</td>
                    <td class="text-center">{{ order.user.username }}</td>
                    <td class="text-center">{{ getStatusText(order.status) }}</td>
                    <td class="text-center">{{ formatDate(order.createAt) }}</td>
                    <td class="text-center">{{ order.phone }}</td>
                    <td class="text-center">{{ order.addressShip }}</td>
                    <td class="text-center">{{ order.totalPrice }}</td>
                    <td>
                        <div class="actionPro1">
                            <div class="buttonEditPro1">
                                <a :href="`/detail-order/${order.id}`"><span style="color: white;">Detail</span></a>
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import axios from 'axios';
import { onMounted, ref } from 'vue';

export default {
    setup() {
        const orders = ref([]);

        const getOrders = async () => {
            try {
                const response = await axios.get('http://localhost:8080/order');
                orders.value = response.data;
            } catch (error) {
                console.error('Error:', error);
            }
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

        const getStatusText = (status) => {
            switch (status) {
                case 0:
                    return 'Chờ xác nhận';
                case 1:
                    return 'Chờ lấy hàng';
                case 2:
                    return 'Đang giao hàng';
                case 3:
                    return 'Giao hàng thành công';
                case 4:
                    return 'Đã hủy';
                default:
                    return 'Không xác định';
            }
        };

        onMounted(() => {
            getOrders();
        });

        return {
            orders,
            formatDate,
            getStatusText
        };
    }
}
</script>


<style>
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
