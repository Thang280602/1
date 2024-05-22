<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

// Khởi tạo các biến và hàm cần thiết
const route = useRoute();
const router = useRouter();
const productId = ref(route.params.id);
const discount = ref('');
const image = ref('');
const quantity = ref('');
const colorId = ref(null);
const sizeId = ref(null);
const productDetailStatus = ref('1');
const products = ref([]);
const colors = ref([]);
const sizes = ref([]);
const imageName = ref('');
const file = ref(null);
const selectedProduct = ref('');
const selectedSize = ref('');
const selectedColor = ref('');
const imagePreviewUrl = ref('');

// Lấy dữ liệu của sản phẩm cần sửa từ server
const getProductDetail = async () => {
    try {

        console.log(productId.value);
        const response = await axios.get(`http://localhost:8080/productDetail/${productId.value}`);
        const productDetail = response.data;

        // Gán dữ liệu vào các biến
        discount.value = productDetail.discount;
        quantity.value = productDetail.quantity;
        colorId.value = productDetail.colorID;
        sizeId.value = productDetail.sizeID;
        productDetailStatus.value = productDetail.status;
        console.log(productDetailStatus);
    } catch (error) {
        console.error('Error fetching product detail:', error);
    }
};

// Lấy dữ liệu sản phẩm và các danh sách màu sắc, kích thước từ server
const getData = async () => {
    try {

        // Lấy danh sách sản phẩm
        const productsResponse = await axios.get('http://localhost:8080/product');
        products.value = productsResponse.data;

        // Lấy danh sách màu sắc
        const colorsResponse = await axios.get('http://localhost:8080/color');
        colors.value = colorsResponse.data;

        // Lấy danh sách kích thước
        const sizesResponse = await axios.get('http://localhost:8080/size');
        sizes.value = sizesResponse.data;

        // Gọi hàm để lấy dữ liệu của sản phẩm cần sửa
        await getProductDetail();
    } catch (error) {
        console.error('Error:', error);
    }
};

// Hàm sửa sản phẩm
const handleSubmit = async () => {
    try {

        const formData = new FormData();
        formData.append('fileImage', file.value);
        formData.append('discount', discount.value);
        formData.append('status', productDetailStatus.value);
        formData.append('quantity', quantity.value);
        formData.append('productID', selectedProduct.value.id);
        formData.append('colorID', colorId.value);
        formData.append('sizeID', sizeId.value);

        // Gửi yêu cầu sửa sản phẩm lên server
        const response = await axios.put(`http://localhost:8080/productDetail/update/${productId.value}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        // Hiển thị thông báo thành công và chuyển về trang sản phẩm
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Product updated successfully!',
            confirmButtonText: 'OK'
        });
        router.push('/productItem');
    } catch (error) {
        console.error('Error editing product:', error);
    }
};

// Hàm xử lý sự kiện khi người dùng thay đổi sản phẩm
const handleProductChange = () => {
    getProductDetail();
};

// Hàm xử lý sự kiện khi người dùng thay đổi màu sắc
const handleColorChange = () => {
    colorId.value = selectedColor.value.id;
};

// Hàm xử lý sự kiện khi người dùng thay đổi kích thước
const handleSizeChange = () => {
    sizeId.value = selectedSize.value.id;
};

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
// Hàm xử lý sự kiện khi người dùng chọn file ảnh
const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    file.value = selectedFile;
    imageName.value = selectedFile.name;
    image.value = selectedFile;
    const reader = new FileReader();
    reader.onload = () => {
        imagePreviewUrl.value = reader.result;
    };
    reader.readAsDataURL(selectedFile);
};

// Khởi tạo dữ liệu khi component được tạo ra
onMounted(() => {
    getData();
});

// Hàm reset các trường dữ liệu
const resetFields = () => {
    discount.value = '';
    quantity.value = '';
    colorId.value = '';
    sizeId.value = '';
    productDetailStatus.value = '1';
};
</script>

<template>
    <VForm @submit.prevent="handleSubmit" enctype="multipart/form-data">
        <VRow>
            <input type="hidden" v-model="productId">
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
                    <!-- Hiển thị hình ảnh đã chọn -->
                    <VCol cols="12" v-if="imagePreviewUrl" style="margin-left: 25%;margin-top: 1%;">
                        <img :src="imagePreviewUrl" alt="Selected Image" style="max-width: 100%; max-height: 150px;" />
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
            <VRow>
                <VCol offset-md="3" cols="12" md="9" class="d-flex gap-4">
                    <VBtn type="submit">
                        Submit
                    </VBtn>
                    <VBtn color="secondary" variant="tonal" @click="resetFields">
                        Reset
                    </VBtn>
                </VCol>
            </VRow>

        </VRow>
    </VForm>
</template>
<style>
@import url('../../../src/assets/category/category.css');
</style>
