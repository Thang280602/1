<template>
    <main>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="text-content">View Category</h1>
                    <table class="table ">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">categoryName</th>
                                <th scope="col">status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="category in categories" :key="category.id">
                                <th scope="row">{{ category.id }}</th>
                                <td>{{ category.categoryName }}</td>
                                <td>{{ category.status }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </main>
</template>

<script>
export default {
    name: 'TestVue',
    data() {
        return {
            categories: []
        }
    },
    created() {
        this.getAll();
    },
    methods: {
        getAll() {
            fetch('http://localhost:8086/category')
                .then(res => res.json())
                .then(data => {
                    this.categories = data;
                    console.log(data);
                })
                .catch(error => {
                    console.error('Error fetching categories:', error);
                });
        },
        deleteCategory(id) {
            fetch(`http://localhost:8086/category/delete/${id}`, { method: 'DELETE' })
                .then(response => {
                    console.log(response);
                    this.getAll(); // Load categories again after deletion
                })
                .catch(error => {
                    console.error('Error deleting category:', error);
                });
        }
    }
}
</script>