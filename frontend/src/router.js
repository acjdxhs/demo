import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from './view/Login'

Vue.use(VueRouter)

export default new VueRouter ({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    }
  ]
})
