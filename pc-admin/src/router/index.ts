import { createRouter, createWebHistory } from 'vue-router';
import { getAdminToken } from '../stores/auth';
import AdminLayout from '../layouts/AdminLayout.vue';
import ContentOpsView from '../views/ContentOpsView.vue';
import DashboardView from '../views/DashboardView.vue';
import LoginView from '../views/LoginView.vue';
import UserDetailView from '../views/UserDetailView.vue';
import UsersView from '../views/UsersView.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { guestOnly: true }
    },
    {
      path: '/',
      component: AdminLayout,
      meta: { requiresAuth: true },
      children: [
        {
          path: '',
          name: 'dashboard',
          component: DashboardView
        },
        {
          path: 'users',
          name: 'users',
          component: UsersView
        },
        {
          path: 'users/:userId',
          name: 'user-detail',
          component: UserDetailView
        },
        {
          path: 'content',
          name: 'content',
          component: ContentOpsView
        }
      ]
    }
  ]
});

router.beforeEach((to) => {
  const token = getAdminToken();
  if (to.meta.requiresAuth && !token) {
    return {
      name: 'login',
      query: {
        redirect: to.fullPath
      }
    };
  }

  if (to.meta.guestOnly && token) {
    return { name: 'dashboard' };
  }

  return true;
});

export default router;
