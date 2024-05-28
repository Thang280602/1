<template>
    <div class="file-upload">
        <label for="fileInput" class="upload-button">Import CSV</label>
        <input type="file" id="fileInput" style="display: none;" @change="handleFileUpload" />
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
export default {
    props: {
        type: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            selectedFile: null
        };
    },
    methods: {
        handleFileUpload(event) {
            this.selectedFile = event.target.files[0];
            this.uploadFile();
        },
        async uploadFile() {
            if (this.selectedFile) {
                const formData = new FormData();
                formData.append('file', this.selectedFile);
                try {
                    const response = await axios.post(`http://localhost:8080/api/csv/import/${this.type}`, formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    });
                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: 'Import successfully!',
                        confirmButtonText: 'OK'
                    });
                    console.log('File uploaded successfully', response.data);
                } catch (error) {
                    console.error('Error uploading file', error);
                }
            } else {
                alert('Please select a file');
            }
        }
    }
};
</script>

<style scoped>
.file-upload {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border-radius: 8px;
    background-color: aqua;
    width: 100px;
    /* Set a specific width */
    height: 35px;
    /* Set a specific height */
    border: 1px solid #ccc;
    /* Optional: Add border for visualization */
    padding: 10px;
    /* Optional: Add padding */
}

.upload-button {
    width: 100px;
    height: 40px;
    margin-top: 10px;
    /* Add some spacing */
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}
</style>
