<template>
    <header>
        <div class="header">
            <div class="logo">
                <a href="/"><img class="logo" src="/src/assets/img/logo.png" alt=""></a>
            </div>
            <div class="search">
                <form action=""></form>
                <div class="input">
                    <input class="form-control input-search" type="text" placeholder="Search">
                </div>
                <div class="inputsearch">
                    <a><i class="fa-brands fa-searchengin icon-search"></i></a>
                </div>
            </div>
            <div class="iconlove">
                <div class="bag">
                    <a href="/cartHover"><img class="bag" src="/src/assets/img/bag.png"></a>
                </div>
                <div class="love">
                    <a><img class="love" src="/src/assets/img/heart.png"></a>
                </div>
            </div>
            <div class="signIn" v-if="!isLoggedIn">
                <button type="submit"><a href="/register" class="none" style="text-decoration: none;">Sign In</a></button>
            </div>
            <div class="logIn" v-if="!isLoggedIn">
                <button type="submit"><a href="/login" class="none" style="color: white; text-decoration: none;">Log In</a></button>
            </div>
            <div class="userLogin" v-if="isLoggedIn" >
                <div class="nameUser" style="margin-top: 10px;" >
                    <span>{{ isLoggedIn ? userName : 'UserName Description' }}</span><br>
                  
                </div>
                <div class="avatar">
                    <img class="avatarIcon" src="/src/assets/img/avatar.png" style="width:100%;height: 50%;margin-top: 80%;margin-left: -130%;">
                </div>
                <div class="signOut" v-if="isLoggedIn" style="margin-top: -5px;">
                    <button @click="signOut" type="button">Sign Out</button>
                </div>
            </div>
        </div>
    </header>
</template>

<script>
export default {
    data() {
        return {
            isLoggedIn: false,
            userName: 'UserName',
        };
    },
    created() {
        this.checkLoginStatus();
    },
    methods: {
        checkLoginStatus() {
            const token = this.getTokenFromLocalStorage();
            if (token) {
                const decodedToken = this.decodeToken(token);
                console.log('decodedToken', decodedToken);
                if (decodedToken && decodedToken.sub) {
                    this.isLoggedIn = true;
                    this.userName = decodedToken.sub; 
                }
            } else {
                this.isLoggedIn = false;
            }
        },
        getTokenFromLocalStorage() {
            return localStorage.getItem('token'); 
        },
        decodeToken(token) {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            return JSON.parse(jsonPayload);
        },
        signOut() {
            localStorage.removeItem('token'); 
            this.isLoggedIn = false;
            this.userName = 'UserName';
            console.log('User has signed out');
            this.$router.push('/');
        }
    }
};
</script>

<style scoped>
@import url('/src/assets/css/header.css');
.signOut button {
    background-color: #f44336; 
    color: white; 
    border: none;
    padding: 10px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    margin-left: 50px;
    cursor: pointer;
    border-radius: 12px;
}

.signOut button:hover {
    background-color: #d32f2f; 
}
</style>