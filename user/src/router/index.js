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
import Order from '@/views/Order.vue'
import History from '@/views/History.vue'
import Bill from '@/views/Bill.vue'
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
      path:'/detail/:id',
      name:'detail',
      component:Detail
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
      path:'/order',
      name:'order',
      component: Order
    },
    {
      path:'/history',
      name:'history',
      component: History
    },
    {
      path:'/bill',
      name:'bill',
      component: Bill
    },
 
    
  ]
})

export default router
