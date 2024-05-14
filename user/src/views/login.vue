<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import { useRouter } from 'vue-router';

const form = ref({
    username: '',
    password: '',
    remember: false,
});

const errors = ref({
    username: '',
    password: ''
});

const user = ref(null);
const isLoggedIn = ref(false);

const router = useRouter();

function validateForm() {
    errors.value.username = form.value.username ? '' : 'Username is required';
    errors.value.password = form.value.password ? '' : 'Password is required';

    return !errors.value.username && !errors.value.password;
}

async function login() {
    if (!validateForm()) {
        Swal.fire({
            icon: "error",
            title: "Error...",
            text: "Please fill in all required fields"
        });
        return;
    }

    try {
        debugger
        const response = await axios.post('http://localhost:8080/auth/generateToken', {
            username: form.value.username,
            authenticationCode: form.value.password,
        });

        const token = response.data.token;

        const decodedToken = decodeToken(token);

        if (decodedToken.role === 'ROLE_USER' || decodedToken.role === 'ROLE_ADMIN') {
            localStorage.setItem('token', token);

            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Successful login",
                showConfirmButton: false,
                timer: 1500
            });
            router.push('/');
        } else {
            Swal.fire({
                icon: "error",
                title: "Error...",
                text: "You do not have permission"
            });
        }
    } catch (error) {
        Swal.fire({
            icon: "question",
            title: "Error...",
            text: "Your account is logged in incorrectly"
        });
    }
}

// Hàm giải mã token
function decodeToken(token) {
    const decodedToken = JSON.parse(atob(token.split('.')[1]));
    return decodedToken;
}

onMounted(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
        user.value = JSON.parse(storedUser);
        isLoggedIn.value = true;
    }
});
</script>

<template>
    <main>
        <div class="login">
            <div class="form">
                <div class="text1">
                    <h1>WELCOME TO ZONE</h1><br>
                </div>
                <div class="text2">
                    <h6>Welcome back! Please enter your details.</h6>
                </div>
                <div class="icon">
                    <img src="../assets/img/logo1.png" alt="">
                </div>
                <form @submit.prevent="login">
                    <div class="formLogin">
                        <div class="emailform">
                            <label>UserName</label><br>
                            <input type="text" placeholder="Enter your username" v-model="form.username"><br>
                            <span v-if="errors.username" class="error" style="color: red;">{{ errors.username }}</span>
                        </div>
                        <div class="passform">
                            <label for="">Password</label><br>
                            <input type="password" placeholder="Enter your password" v-model="form.password"><br>
                            <span v-if="errors.password" class="error" style="color: red;">{{ errors.password }}</span>
                        </div>
                        <div class="check">
                            <div class="checkbox">
                                <input type="checkbox" v-model="form.remember">
                                <span>Remember me</span>
                            </div>
                            <div class="forgot">
                                <a href="">Forgot password</a>
                            </div>
                        </div>
                        <button class="buttonSubmit" type="submit"><span class="Signin">Sign in</span></button>
                    </div>
                </form>
                <div class="registerfromlogin">
                    <span>Don’t have an account?</span><br>
                    <a href="/register">Sign up to free</a>
                </div>
            </div>
            <div class="avt">
                <img src="../assets/img/imglogin.png" alt="">
            </div>
        </div>
    </main>
</template>


