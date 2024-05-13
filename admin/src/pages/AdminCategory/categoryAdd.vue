<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import { ref } from 'vue';

const categoryName = ref('');
const categoryStatus = ref('1');
const router = useRouter();
const category = {
    categoryName: categoryName.value,
    categoryStatus: categoryStatus.value // Chuyển đổi sang số nguyên nếu cần
};
const handleSubmit = async () => {
    try {
        const category = {
            categoryName: categoryName.value,
            categoryStatus: parseInt(categoryStatus.value)
        };

        const response = await axios.post('http://localhost:8080/category/add', category);
        console.log('Category added:', response.data);
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Category added successfully!',
            confirmButtonText: 'OK'
        });

        router.push('/category');
        resetFields();
    } catch (error) {
        console.error('Error adding category:', error);
    }
};

const resetFields = () => {
    categoryName.value = '';
    categoryStatus.value = '1';
};
</script>

<template>
    <VForm @submit.prevent="handleSubmit">
        <VRow>
            <VCol cols="12">
                <VRow no-gutters>
                    <!-- 👉 First Name -->
                    <VCol cols="12" md="3">
                        <label for="categoryName">Category Name</label>
                    </VCol>

                    <VCol cols="12" md="3">
                        <VTextField id="categoryName" v-model="categoryName" placeholder="Quần,Giày,Áo,...."
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

                        <input v-model="categoryStatus" type="radio" value="1" name="categoryStatus" />
                        <label for="ds" style="margin-right: 6%;">Active</label>
                        <input v-model="categoryStatus" type="radio" value="0" name="categoryStatus" />
                        <label for="ds">Hidden</label>
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
