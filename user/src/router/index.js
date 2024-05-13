import { createRouter, createWebHistory } from 'vue-router'
import TestVue from '../views/TestVue.vue'
import Header from '../components/Header.vue'
import Index from '../views/index.vue'
import Footer from '../components/Footer.vue'
import Login from '../views/login.vue'
import Register from '@/views/Register.vue'
import Cart from '@/views/Cart.vue'
import FaveriteProduct from '../views/FaveriteProduct.vue'
import CartHover from '../views/CartHover.vue'
import Detail from '@/views/Detail.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Index
    },
    {
      path:'/header',
      name:'header',
      component:Header
    },
    {
      path:'/footer',
      name:'footer',
      component:Footer
    },
    {
      path:'/login',
      name:'login',
      component:Login
    },
    {
      path:'/register',
      name:'register',
      component:Register
    },
    {
      path:'/cart',
      name:'cart',
      component: Cart
    },
    {
      path:'/faveriteproduct',
      name:'faveriteproduct',
      component: FaveriteProduct
    },
    {
      path:'/carthover',
      name:'carthover',
      component: CartHover
    },
    {
      path:'/detail',
      name:'detail',
      component: Detail
    },
    
  ]
})

export default router
