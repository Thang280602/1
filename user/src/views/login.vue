<script setup>
import { ref } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import { useRouter } from 'vue-router';

const form = ref({
    username: '',
    password: '',
    remember: false,
});

const isPasswordVisible = ref(false);
const router = useRouter();

async function login() {
    try {
        const response = await axios.post('http://localhost:8080/auth/generateToken', {
            username: form.value.username,
            authenticationCode: form.value.password,
        });

        const token = response.data.token;

        const decodedToken = decodeToken(token);
        console.log(decodedToken);
        if (decodedToken.sub === 'admin','user') {
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
</script>

<template>
    <main>
        <div class="login">
            <div class="form">
                <div class="text1">
                    <h1>
                        WELCOME TO ZONE
                    </h1><br>

                </div>
                <div class="text2">
                    <h6>Welcome back! Please enter your details.</h6>
                </div>
                <div class="icon">
                    <img src="../assets/img/logo1.png" alt="">
                </div>
                <form>
                    <div class="formLogin">
                        <div class="emailform">
                            <label>Email</label><br>
                            <input type="text" name="" id="" placeholder="Enter your username" v-model="form.username" :rules="[v => !!v || 'Username is required']">
                        </div>
                        <div class="passform">
                            <label for="">Password</label><br>
                            <input type="password" name="" id="" placeholder="Enter your password" v-model="form.password" :rules="[v => !!v || 'Password is required']">
                        </div>
                        <div class="check">
                            <div class="checkbox">
                                <input type="checkbox" name="" id="">
                                <span>Remember me</span>
                            </div>
                            <div class="forgot">
                                <a href="">Forgot password</a>
                            </div>

                        </div>
                        <button class="buttonSubmit" type="submit" @click.prevent="login"><span class="Signin">Sign in</span> </button>

                    </div>
                </form>
                <div class="registerfromlogin">
                    <span>Don’t have an account?</span><br>
                    <a href="">Sign up to free</a>
                </div>
            </div>
            <div class="avt">
                <img src="../assets/img/imglogin.png" alt="">
            </div>
        </div>
    </main>
</template>
