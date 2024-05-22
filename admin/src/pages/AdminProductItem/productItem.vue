<template>
    <div class="buttonAdd">
        <a href="/add-productItem"><span>Add</span></a>
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
                        Color
                    </th>
                    <th>
                        Size
                    </th>
                    <th>
                        Quantity
                    </th>
                    <th>
                        Discount
                    </th>
                    <th>
                        Image
                    </th>
                    <th>
                        Status
                    </th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="product in productsDetail" :key="product.id">
                    <td>
                        {{ product.id }}
                    </td>
                    <td class="text-center">
                        {{ product.product.productName }}
                    </td>
                    <td class="text-center">
                        {{ product.color.colorName }}
                    </td>
                    <td class="text-center">
                        {{ product.size.sizeName }}
                    </td>
                    <td class="text-center">
                        {{ product.quantity }}
                    </td>
                    <td class="text-center">
                        {{ product.discount }}
                    </td>
                    <td class="text-center">
                        <img :src="getFullImageUrl(product.image)" alt="Product Image" width="100" height="100" />
                    </td>
                    <td class="text-center">
                        {{ product.productStatus ? 'Active' : 'Inactive' }}
                    </td>
                    <td>
                        <div class="actionPro">
                            <div class="buttonEditPro">
                                <a :href="`/edit-productItem/${product.id}`"><span style="color: white;">Edit</span></a>
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
            productsDetail: []
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

                const response = await axios.get('http://localhost:8080/productDetail');
                this.productsDetail = response.data;
                console.log(this.productsDetail);
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
                    const response = await axios.delete(`http://localhost:8080/productDetail/delete/${id}`);
                    console.log('Product detail deleted:', response.data);
                    Swal.fire("Deleted!", "Your file has been deleted.", "success");
                    this.getProducts();
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    Swal.fire("Cancelled", "Your imaginary file is safe :)", "error");
                }
            } catch (error) {
                console.error('Error deleting product detail:', error);
            }
        }

    },

}
</script>
