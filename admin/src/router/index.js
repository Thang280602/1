import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login',
    },
    {
      path: '/login',
      component: () => import('../pages/login.vue'),
    },
   
    {
      path: '/',
      component: () => import('../layouts/default.vue'),
      children: [
        {
          path: 'dashboard',
          component: () => import('../pages/dashboard.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'category',
          component: () => import('../pages/AdminCategory/category.vue'),
          meta: { requiresAuth: true }, 
        },
        {
          path: 'add-category',
          component: () => import('../pages/AdminCategory/categoryAdd.vue'),
          meta: { requiresAuth: true }, 
        },
        {
          path: 'edit-category/:id',
          component: () => import('../pages/AdminCategory/categoryEdit.vue'),
          meta: { requiresAuth: true }, 
        },
        {
          path: 'product',
          component: () => import('../pages/AdminProduct/product.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'order',
          component: () => import('../pages/Order/order.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'detail-order/:id',
          component: () => import('../pages/Order/orderDetail.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'add-product',
          component: () => import('../pages/AdminProduct/productAdd.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: '/edit-product/:id',
          component: () => import('../pages/AdminProduct/productEdit.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'productItem',
          component: () => import('../pages/AdminProductItem/productItem.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'add-productItem',
          component: () => import('../pages/AdminProductItem/productItemAdd.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'edit-productItem/:id',
          component: () => import('../pages/AdminProductItem/productItemEdit.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'account-settings',
          component: () => import('../pages/account-settings.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'typography',
          component: () => import('../pages/typography.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'icons',
          component: () => import('../pages/icons.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'cards',
          component: () => import('../pages/cards.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'tables',
          component: () => import('../pages/tables.vue'),
          meta: { requiresAuth: true },
        },
        {
          path: 'form-layouts',
          component: () => import('../pages/form-layouts.vue'),
          meta: { requiresAuth: true },
        },
        
      ],
    },
    {
      path: '/',
      component: () => import('../layouts/blank.vue'),
      children: [
        {
          path: 'register',
          component: () => import('../pages/register.vue'),
        },
      ],
    },
    // Thêm các routes khác ở đây
  ],
});

// Navigation Guards
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!localStorage.getItem('token')) {
      next('/login');
    } else {
      next(); 
    }
  } else {
    next(); 
  }
});

export default router;
