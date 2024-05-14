<script setup>
import '/src/assets/css/register.css';
import axios from 'axios';
import { ref } from 'vue';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import { useRouter } from 'vue-router';
const router = useRouter();
const username = ref('');
const email = ref('');
const password = ref('');
const firstname = ref('');
const lastname = ref('');
const userId = ref(null);
const phonenumber = ref('');
const form = {
    privacyPolicies: false
}
const isPasswordVisible = ref(false)
async function register() {
    try {
        
        const responsedata = {
            id: userId.value,
            firstName: firstname.value,
            lastName: lastname.value,
            phoneNumber: phonenumber.value,
            username: username.value,
            authenticationCode: password.value,
            email: email.value
        }
        const request = await axios.post('http://localhost:8080/users/add', responsedata)
        Swal.fire({
            position: "top-end",
            icon: "success",
            title: "Successful register",
            showConfirmButton: false,
            timer: 1500
        });
        router.push('/login');
    } catch (error) {
        Swal.fire({
            icon: "question",
            title: "Error...",
            text: "Account registration failed"
        });
    }
}
</script>
<template>
    <main>
        <div class="login">
            <div class="form">
                <div class="text1">
                    <h1>
                        REGISTRATION TO THE ZONE
                    </h1><br>

                </div>
                <div class="text2">
                    <h6>Welcome to our family</h6>
                </div>
                <div class="icon">
                    <img src="../assets/img/logo1.png" alt="">
                </div>
                <form action="" method="">
                    <div class="formLogin">
                        <div class="emailform">
                            <label>User name</label><br>
                            <input type="text" name="" id="" placeholder="Enter your username" v-model="username">
                        </div>
                        <div class="mailform" style="margin-top: 20px;">
                            <label>Email</label><br>
                            <input type="email" name="" id="" placeholder="Enter your email" v-model="email">
                        </div>
                        <div class="passform">
                            <label for="">Password</label><br>
                            <input type="password" name="" id="" placeholder="Enter your password" v-model="password">
                        </div>

                        <button class="buttonSubmit" tyoe="submit" name=""><span class="Signin"
                                @click.prevent="register">Register</span> </button>

                    </div>
                </form>
                <div class="registerfromlogin">
                    <span>Do you already have an account?</span><br>
                    <a href="">Sign In</a>
                </div>
            </div>
            <div class="avt">
                <img src="../assets/img/imglogin.png" alt="">
            </div>
        </div>
    </main>
</template>
