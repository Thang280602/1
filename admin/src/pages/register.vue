<script setup>
import AuthProvider from '@/views/pages/authentication/AuthProvider.vue';
import logo from '@images/logo.svg?raw';
import axios from 'axios';
import Swal from 'sweetalert2';
import 'sweetalert2/src/sweetalert2.scss';
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
    console.log("request", request);
    console.log("reponsedata", responsedata);
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
          Adventure starts here 🚀
        </h5>
        <p class="mb-0">
          Make your app management easy and fun!
        </p>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="$router.push('/')">
          <VRow>
            <!-- Username -->
            <VCol cols="12">
              <VTextField v-model="username" autofocus label="Username" placeholder="Johndoe" />
            </VCol>
            <!-- Username -->
            <VCol cols="12">
              <VTextField v-model="firstname" autofocus label="Firstname" placeholder="Lion" />
            </VCol>
            <VCol cols="12">
              <VTextField v-model="lastname" autofocus label="Lastname" placeholder="Messi" />
            </VCol>
            <VCol cols="12">
              <VTextField v-model="phonenumber" autofocus label="PhoneNumber" placeholder="0123456789" />
            </VCol>
            <!-- email -->
            <VCol cols="12">
              <VTextField v-model="email" label="Email" placeholder="johndoe@email.com" type="email" />
            </VCol>

            <!-- password -->
            <VCol cols="12">
              <VTextField v-model="password" label="Password" placeholder="············"
                :type="isPasswordVisible ? 'text' : 'password'"
                :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                @click:append-inner="isPasswordVisible = !isPasswordVisible" />
              <div class="d-flex align-center mt-1 mb-4">
                <VCheckbox id="privacy-policy" v-model="form.privacyPolicies" inline />
                <VLabel for="privacy-policy" style="opacity: 1;">
                  <span class="me-1">I agree to</span>
                  <a href="javascript:void(0)" class="text-primary">privacy policy & terms</a>
                </VLabel>
              </div>

              <VBtn block type="submit" @click="register">
                Sign up
              </VBtn>
            </VCol>

            <!-- login instead -->
            <VCol cols="12" class="text-center text-base">
              <span>Already have an account?</span>
              <RouterLink class="text-primary ms-2" to="/login">
                Sign in instead
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
