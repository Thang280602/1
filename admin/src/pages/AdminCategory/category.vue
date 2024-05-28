<template>

    <div class="head" style="display: flex;justify-content: space-around;align-items: center;">
        <div class="buttonAdd">
            <a href="/add-category"><span>Add</span></a>
        </div>
        <FileUpload :type="$route.meta.type" />

    </div>


    <div class="tableCategory">
        <VTable>
            <thead>
                <tr>
                    <th class="text-uppercase">
                        ID
                    </th>
                    <th>
                        Category Name
                    </th>
                    <th>
                        Status
                    </th>
                    <th>
                        Action
                    </th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="category in categories" :key="category.id">
                    <td>
                        {{ category.id }}
                    </td>
                    <td class="text-center">
                        {{ category.categoryName }}
                    </td>
                    <td class="text-center">
                        {{ category.categoryStatus ? 'Active' : 'Inactive' }}
                    </td>
                    <td>
                        <div class="action">
                            <div class="buttonEdit">
                                <a :href="`/edit-category/${category.id}`"><span style="color: white;">Edit</span></a>
                            </div>
                            <div class="buttonDelete">
                                <button @click="deleteCategory(category.id)"><span
                                        style="color: white;">Delete</span></button>
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
import FileUpload from '../../layouts/components/FileUpload.vue';
export default {
    components: { FileUpload },
    data() {
        return {
            categories: []
        }
    },
    mounted() {
        this.getCategories();
    },
    methods: {
        async getCategories() {
            try {
                const response = await axios.get('http://localhost:8080/category');
                this.categories = response.data;
                console.log(this.categories);
            } catch (error) {
                console.error('Error:', error);
            }
        },
        async deleteCategory(id) {
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
                    const response = await axios.delete(`http://localhost:8080/category/delete/${id}`);
                    console.log('Category deleted:', response.data);
                    Swal.fire("Deleted!", "Your file has been deleted.", "success");
                    // Sau khi xóa, cập nhật danh sách categories bằng cách gọi lại phương thức getCategories
                    this.getCategories();
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    Swal.fire("Cancelled", "Your imaginary file is safe :)", "error");
                }
            } catch (error) {
                console.error('Error deleting category:', error);
            }
        }
    },
}
</script>
