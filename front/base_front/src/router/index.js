import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/common/login'
import register from '@/components/common/register'
import home from '@/components/common/home'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/HelloWorld',  
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },
    {
      path: '/home',
      name: 'home',
      component: home
    }
  ]
})
