<script setup>
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
import { useRouter } from 'vue-router';
const router = useRouter();

const form = ref({
  username: '',
  password: '',
  remember: false,
});

const isPasswordVisible = ref(false);

async function login() {
  try {
    debugger
    const response = await axios.post('http://localhost:8080/auth/generateToken', {
      username: form.value.username,
      authenticationCode: form.value.password,
    });

    const token = response.data.token;

    console.log('role', decodeToken.role)
    const decodedToken = decodeToken(token);
    console.log(decodedToken.sub);
    if (decodedToken.role === 'ROLE_ADMIN') {
      localStorage.setItem('token', token);
      Swal.fire({
        position: "top-end",
        icon: "success",
        title: "Successful login",
        showConfirmButton: false,
        timer: 1500
      });
      router.push('/dashboard');
    } else {
      Swal.fire({
        icon: "error",
        title: "Error...",
        text: "Yon do not have permission"
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
</script>`

<template>
  <div class="auth-wrapper d-flex align-center justify-center pa-4">
    <VCard class="auth-card pa-4 pt-7" max-width="448">
      <VCardItem class="justify-center">
        <template #prepend>
          <div class="d-flex">
            <div class="d-flex text-primary" v-html="logo" />
          </div>
        </template>

        <VCardTitle class="text-2xl font-weight-bold">
          sneat
        </VCardTitle>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="text-h5 mb-1">
          Welcome to sneat! 👋🏻
        </h5>
        <p class="mb-0">
          Please sign-in to your account and start the adventure
        </p>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="$router.push('/')">
          <VRow>
            <!-- email -->
            <VCol cols="12">
              <VTextField v-model="form.username" autofocus placeholder="thang123456" label="username" type="text"
                :rules="[v => !!v || 'Username is required']" />
            </VCol>

            <!-- password -->
            <VCol cols="12">
              <VTextField v-model="form.password" label="Password" placeholder="············"
                :type="isPasswordVisible ? 'text' : 'password'"
                :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                @click:append-inner="isPasswordVisible = !isPasswordVisible"
                :rules="[v => !!v || 'Password is required']" />

              <!-- remember me checkbox -->
              <div class="d-flex align-center justify-space-between flex-wrap mt-1 mb-4">
                <VCheckbox v-model="form.remember" label="Remember me" />

                <RouterLink class="text-primary ms-2 mb-1" to="javascript:void(0)">
                  Forgot Password?
                </RouterLink>
              </div>

              <!-- login button -->
              <VBtn block type="submit" @click="login">
                Login
              </VBtn>
            </VCol>

            <!-- create account -->
            <VCol cols="12" class="text-center text-base">
              <span>New on our platform?</span>
              <RouterLink class="text-primary ms-2" to="/register">
                Create an account
              </RouterLink>
            </VCol>

            <VCol cols="12" class="d-flex align-center">
              <VDivider />
              <span class="mx-4">or</span>
              <VDivider />
            </VCol>

            <!-- auth providers -->
            <VCol cols="12" class="text-center">
              <AuthProvider />
            </VCol>
          </VRow>
        </VForm>
      </VCardText>
    </VCard>
  </div>
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>
