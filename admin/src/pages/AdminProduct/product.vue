<template>
    <div class="head" style="display: flex;justify-content: space-around;align-items: center;">
        <div class="buttonAdd">
            <a href="/add-product"><span>Add</span></a>
        </div>
        <FileUpload :type="$route.meta.type" />
    </div>
    <div class="tableCategory">
        <VTable>
            <thead>
                <tr>
                    <th class="text-uppercase">ID</th>
                    <th>Product Name</th>
                    <th>Product Status</th>
                    <th>Product Address</th>
                    <th>Description</th>
                    <th>CategoryName</th>
                    <th>Price</th>
                    <th>Image</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="product in products" :key="product.id">
                    <td>{{ product.id }}</td>
                    <td class="text-center">{{ product.productName }}</td>
                    <td class="text-center">{{ product.productStatus ? 'Active' : 'Inactive' }}</td>
                    <td class="text-center">{{ product.productAddress }}</td>
                    <td class="text-center">{{ product.description }}</td>
                    <td class="text-center">{{ product?.category?.categoryName }}</td>
                    <td class="text-center">{{ product.price }}</td>
                    <td class="text-center">
                        <img :src="getFullImageUrl(product.image)" alt="Product Image" width="100" height="100" />
                    </td>
                    <td>
                        <div class="actionPro">
                            <div class="buttonEditPro">
                                <a :href="`/edit-product/${product.id}`"><span style="color: white;">Edit</span></a>
                            </div>
                            <div class="buttonDeletePro">
                                <div class="buttonDelete">
                                    <button @click="deleteProduct(product.id)"><span
                                            style="color: white;">Delete</span></button>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </VTable>
        <div class="pagination">
            <button @click="prevPage" :disabled="currentPage === 1" style="border-radius: 8px;">Previous</button>
            <span style="margin-top: 10px;">Page {{ currentPage }} of {{ totalPages }}</span>
            <button @click="nextPage" :disabled="currentPage === totalPages" style="border-radius: 8px;">Next</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import FileUpload from '../../layouts/components/FileUpload.vue';

export default {
    components: { FileUpload },
    data() {
        return {
            products: [],
            currentPage: 1,
            pageSize: 6,
            totalPages: 0
        };
    },
    mounted() {
        this.getProducts();
    },
    methods: {
        getFullImageUrl(imagePath) {
            return `http://localhost:8080/uploads/${imagePath}`;
        },
        async getProducts() {
            try {
                const response = await axios.get(`http://localhost:8080/product`, {
                    params: {
                        page: this.currentPage - 1,
                        size: this.pageSize
                    }
                });
                if (response.data && Array.isArray(response.data.content)) {
                    this.products = response.data.content;
                    this.totalPages = response.data.totalPages;
                } else {
                    console.error('Data is not an array:', response.data);
                }
            } catch (error) {
                console.error('Error:', error);
            }
        },
        async deleteProduct(id) {
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
                    const response = await axios.delete(`http://localhost:8080/product/delete/${id}`);
                    console.log('Product deleted:', response.data);
                    Swal.fire("Deleted!", "Your file has been deleted.", "success");
                    this.getProducts();
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    Swal.fire("Cancelled", "Your imaginary file is safe :)", "error");
                }
            } catch (error) {
                console.error('Error deleting product:', error);
            }
        },
        nextPage() {
            if (this.currentPage < this.totalPages) {
                this.currentPage++;
                this.getProducts(); // Gọi lại phương thức getProducts() để tải dữ liệu mới
            }
        },
        prevPage() {
            if (this.currentPage > 1) {
                this.currentPage--;
                this.getProducts(); // Gọi lại phương thức getProducts() để tải dữ liệu mới
            }
        }
    }
};
</script>

<style>
@import url('../../../src/assets/category/category.css');

/* Your existing styles */
.pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

.pagination button {
    margin: 0 5px;
    padding: 10px 20px;
    border: none;
    background-color: #007bff;
    color: white;
    cursor: pointer;
}

.pagination button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}
</style>
