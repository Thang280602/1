<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const categoryName = ref('');
const categoryStatus = ref('1');
const route = useRoute();
const categoryId = ref(route.params.id);
const router = useRouter();

const loadCategoryInfo = async () => {
    try {
        debugger
        const response = await axios.get(`http://localhost:8080/category/get/${categoryId.value}`);
        const categoryInfo = response.data;
        categoryName.value = categoryInfo.categoryName;
        categoryStatus.value = categoryInfo.categoryStatus ? '1' : '0';
    } catch (error) {
        console.error('Error loading category information:', error);
    }
};

onMounted(loadCategoryInfo);

const handleSubmit = async () => {
    try {
        const categoryData = {
            id: categoryId.value,
            categoryName: categoryName.value,
            categoryStatus: categoryStatus.value === '1' ? true : false
        };

        const response = await axios.put(`http://localhost:8080/category/update/${categoryId.value}`, categoryData);
        console.log('Category updated:', response.data);

        Swal.fire({
            title: "Do you want to save the changes?",
            showCancelButton: true,
            confirmButtonText: "Save",
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire("Saved!", "", "success");
            }
        });
        router.push('/category');
        resetFields();
    } catch (error) {
        console.error('Error updating category:', error);
    }
};

const resetFields = () => {
    categoryName.value = '';
    categoryStatus.value = '1';
};
</script>

<template>
    <VForm @submit.prevent="handleSubmit">
        <input type="hidden" v-model="categoryId">
        <VRow>
            <VCol cols="12">
                <VRow no-gutters>
                    <VCol cols="12" md="3">
                        <label for="categoryName">Category Name</label>
                    </VCol>

                    <VCol cols="12" md="3">
                        <VTextField id="categoryName" v-model="categoryName" placeholder="Category Name"
                            persistent-placeholder />
                    </VCol>
                </VRow>
            </VCol>

            <VCol cols="12">
                <VRow no-gutters>
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
                <VBtn type="submit">Save</VBtn>
                <VBtn color="secondary" variant="tonal" type="button" @click="resetFields">Reset</VBtn>
            </VCol>
        </VRow>
    </VForm>
</template>
