<script setup>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import axios from 'axios';
import Swal from 'sweetalert2';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const editorData = ref('<p>Please enter description here</p>');
const productName = ref('');
const productAddress = ref('');
const categoryId = ref(null);
const productStatus = ref('1');
const router = useRouter();
const categories = ref([]);
const selectedCategory = ref('');
const handleCategoryChange = () => {
    categoryId.value = selectedCategory.value.id;
};
const handleSubmit = async () => {
    try {
        const cleanedDescription = editorData.value.replace(/(<([^>]+)>)/gi, "");
        const productData = {
            productName: productName.value,
            productAddress: productAddress.value,
            description: cleanedDescription,
            categoryID: categoryId.value,
            productStatus: parseInt(productStatus.value)
        };
        const response = await axios.post('http://localhost:8080/product/add', productData);
        console.log('Product added:', response.data);
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Product added successfully!',
            confirmButtonText: 'OK'
        });
        router.push('/product');
        resetFields();
    } catch (error) {
        console.error('Error adding product:', error);
    }
};
const getCategories = async () => {
    try {
        const response = await axios.get('http://localhost:8080/category');
        categories.value = response.data;
        console.log(categories.value);
    } catch (error) {
        console.error('Error:', error);
    }
};

onMounted(() => {
    getCategories();
});


const resetFields = () => {
    productName.value = '';
    editorData.value = '';
    productAddress.value = '';
    categoryId.value = '';
    productStatus.value = '1';
};
</script>

<template>
    <VForm @submit.prevent="handleSubmit">
        <VRow>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 First Name -->
                    <VCol cols="12" md="3">
                        <label for="productName">Product Name</label>
                    </VCol>

                    <VCol cols="12" md="6">
                        <VTextField id="productName" v-model="productName" placeholder="Quần Bò,Giày da,Áo kaki,...."
                            persistent-placeholder />
                    </VCol>
                </VRow>
            </VCol>

            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 Email -->
                    <VCol cols="12" md="3">
                        <label for="status">Status</label>
                    </VCol>
                    <VCol cols="12" md="9">

                        <input v-model="productStatus" type="radio" value="1" name="productStatus" />
                        <label for="ds" style="margin-right: 6%;">Active</label>
                        <input v-model="productStatus" type="radio" value="0" name="productStatus" />
                        <label for="ds">Hidden</label>
                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 First Name -->
                    <VCol cols="12" md="3">
                        <label for="productAddress">Product Address</label>
                    </VCol>

                    <VCol cols="12" md="6">
                        <VTextField id="productAddress" v-model="productAddress"
                            placeholder="Nam Định , Hà Nội, Quảng Ninh...." persistent-placeholder />
                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 First Name -->
                    <VCol cols="12" md="3">
                        <label for="productDescription">Description</label>
                    </VCol>

                    <VCol cols="12" md="6">
                        <ckeditor :editor="ClassicEditor" v-model="editorData" :config="editorConfig">
                        </ckeditor>
                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- Label for select -->
                    <VCol cols="12" md="3">
                        <label for="categoryName">Category Name</label>
                    </VCol>

                    <!-- Select element -->
                    <VCol cols="12" md="9">
                        <div class="sel">
                            <select v-model="selectedCategory" @change="handleCategoryChange" class="select-dropdown">
                                <option disabled value="">Choose a category</option>
                                <option v-for="category in categories" :value="category" class="select-option">{{
                                    category.categoryName }}
                                </option>
                            </select>
                        </div>

                    </VCol>
                </VRow>
            </VCol>
            <VCol offset-md="3" cols="12" md="9" class="d-flex gap-4">
                <VBtn type="submit">
                    Submit
                </VBtn>
                <VBtn color="secondary" variant="tonal" type="button" @click="resetFields">
                    Reset
                </VBtn>
            </VCol>

        </VRow>
    </VForm>
</template>
<style>
@import url('../../../src/assets/category/category.css');
</style>
