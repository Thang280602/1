<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const editorData = ref('<p>Please enter description here</p>');
const discount = ref('');
const image = ref('');
const quantity = ref('');
const price = ref('');
const productId = ref(null);
const colorId = ref(null);
const sizeId = ref(null);
const productDetailStatus = ref('1');
const router = useRouter();
const products = ref([]);
const colors = ref([]);
const sizes = ref([]);
const imageName = ref('');
const file = ref(null);
const selectedProduct = ref('');
const selectedSize = ref('');
const selectedColor = ref('');
const handleProductChange = () => {
    productId.value = selectedProduct.value.id;
};
const handleColorChange = () => {
    colorId.value = selectedColor.value.id;
};
const handleSizeChange = () => {
    sizeId.value = selectedSize.value.id;
};
const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    file.value = selectedFile;
    imageName.value = selectedFile.name;
    image.value = selectedFile;
};
const handleSubmit = async () => {
    try {

        const formData = new FormData();
        formData.append('fileImage', file.value);
        console.log('image', file);
        formData.append('discount', discount.value);
        formData.append('price', price.value);
        formData.append('status', parseInt(productDetailStatus.value));
        formData.append('quantity', quantity.value);
        formData.append('productID', productId.value);
        formData.append('colorID', colorId.value);
        formData.append('sizeID', sizeId.value);
        console.log(formData);
        // Chuyển dữ liệu sản phẩm sang JSON và thêm vào FormData
        // formData.append('productDetailDTO', JSON.stringify(productData));
        console.log(formData);
        const response = await axios.post('http://localhost:8080/productDetail/add', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        console.log('formData', formData);
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


const getProducts = async () => {
    try {
        const response = await axios.get('http://localhost:8080/product');
        products.value = response.data;
    } catch (error) {
        console.error('Error:', error);
    }
};

onMounted(() => {
    getProducts();
});
const getColor = async () => {
    try {
        const response = await axios.get('http://localhost:8080/color');
        colors.value = response.data;
        console.log(colors.value);
    } catch (error) {
        console.error('Error:', error);
    }
};

onMounted(() => {
    getColor();
});
const getSize = async () => {
    try {
        const response = await axios.get('http://localhost:8080/size');
        sizes.value = response.data;
        console.log(sizes.value);
    } catch (error) {
        console.error('Error:', error);
    }
};

onMounted(() => {
    getSize();
});

const resetFields = () => {
    discount.value = '';
    price.value = '';
    quantity.value = '';

};
</script>

<template>
    <VForm @submit.prevent="handleSubmit" enctype="multipart/form-data">
        <VRow>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 First Name -->
                    <VCol cols="12" md="3">
                        <label for="discount">Discount</label>
                    </VCol>

                    <VCol cols="12" md="6">
                        <VTextField id="discount" v-model="discount" placeholder="5%" persistent-placeholder />
                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <VCol cols="12" md="3">
                        <label for="image">Image</label>
                    </VCol>
                    <VCol cols="12" md="6">
                        <input type="file" @change="handleFileChange" name="file" accept="image/*" />
                    </VCol>

                </VRow>
            </VCol>
            <!-- <VCol cols="12">
                <VRow no-gutters>
                    <VCol cols="12" md="3">
                        <label for="image">Image</label>
                    </VCol>
                    <VCol cols="12" md="6">
                        <input type="file" @change="handleFileChange" name="files" accept="image/*" multiple />
                    </VCol>
                </VRow>
            </VCol> -->
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 Email -->
                    <VCol cols="12" md="3">
                        <label for="status">Status</label>
                    </VCol>
                    <VCol cols="12" md="9">

                        <input v-model="productDetailStatus" type="radio" value="1" name="productDetailStatus" />
                        <label for="ds" style="margin-right: 6%;">Active</label>
                        <input v-model="productDetailStatus" type="radio" value="0" name="productDetailStatus" />
                        <label for="ds">Hidden</label>
                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 First Name -->
                    <VCol cols="12" md="3">
                        <label for="price">Price</label>
                    </VCol>

                    <VCol cols="12" md="6">
                        <VTextField id="price" v-model="price" placeholder="100$,200$..." persistent-placeholder />
                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 First Name -->
                    <VCol cols="12" md="3">
                        <label for="quantity">quantity</label>
                    </VCol>

                    <VCol cols="12" md="6">
                        <VTextField id="quantity" v-model="quantity" placeholder="100,200" persistent-placeholder />
                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- Label for select -->
                    <VCol cols="12" md="3">
                        <label for="productName">Product Name</label>
                    </VCol>

                    <!-- Select element -->
                    <VCol cols="12" md="9">
                        <div class="sel">
                            <select v-model="selectedProduct" @change="handleProductChange" class="select-dropdown">
                                <option disabled value="">Choose a product</option>
                                <option v-for="product in products" :value="product" class="select-option">{{
                                    product.productName }}
                                </option>
                            </select>
                        </div>

                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- Label for select -->
                    <VCol cols="12" md="3">
                        <label for="colorName">Size</label>
                    </VCol>

                    <!-- Select element -->
                    <VCol cols="12" md="9">
                        <div class="sel">
                            <select v-model="selectedSize" @change="handleSizeChange" class="select-dropdown">
                                <option disabled value="">Choose a size</option>
                                <option v-for="size in sizes" :value="size" class="select-option">{{
                                    size.sizeName }}
                                </option>
                            </select>
                        </div>

                    </VCol>
                </VRow>
            </VCol>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- Label for select -->
                    <VCol cols="12" md="3">
                        <label for="colorName">Color</label>
                    </VCol>

                    <!-- Select element -->
                    <VCol cols="12" md="9">
                        <div class="sel">
                            <select v-model="selectedColor" @change="handleColorChange" class="select-dropdown">
                                <option disabled value="">Choose a color</option>
                                <option v-for="color in colors" :value="color" class="select-option">{{
                                    color.colorName }}
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
                <VBtn color="secondary" variant="tonal" type="submit" @click="resetFields">
                    Reset
                </VBtn>
            </VCol>

        </VRow>
    </VForm>
</template>
<style>
@import url('../../../src/assets/category/category.css');
</style>
