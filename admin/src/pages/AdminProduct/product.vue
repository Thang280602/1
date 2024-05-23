<template>
    <div class="buttonAdd">
        <a href="/add-product"><span>Add</span></a>
    </div>
    <div class="tableCategory">
        <VTable>
            <thead>
                <tr>
                    <th class="text-uppercase">
                        ID
                    </th>
                    <th>
                        Product Name
                    </th>
                    <th>
                        Product Status
                    </th>
                    <th>
                        Product Address
                    </th>
                    <th>
                        Description
                    </th>
                    <th>
                        CategoryName
                    </th>
                    <th>
                        Price
                    </th>
                    <th>
                        Image
                    </th>
                    <th>
                        Action
                    </th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="product in products" :key="product.id">
                    <td>
                        {{ product.id }}
                    </td>
                    <td class="text-center">
                        {{ product.productName }}
                    </td>
                    <td class="text-center">
                        {{ product.productStatus ? 'Active' : 'Inactive' }}
                    </td>
                    <td class="text-center">
                        {{ product.productAddress }}
                    </td>
                    <td class="text-center">
                        {{ product.description }}
                    </td>
                    <td class="text-center">
                        {{ product?.category?.categoryName }}
                    </td>
                    <td class="text-center">
                        {{ product.price }}
                    </td>
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
    </div>
</template>
<style>
@import url('../../../src/assets/category/category.css');
</style>
<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
export default {
    data() {
        return {
            products: []
        }
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

                const response = await axios.get('http://localhost:8080/product');
                this.products = response.data;
                console.log(this.products);
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
        }

    },

}
</script>
