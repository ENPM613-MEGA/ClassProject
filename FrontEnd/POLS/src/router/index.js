import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Register from '@/components/Register'
import Login from '@/components/Login'

import Vuetify from 'vuetify'



Vue.use(Vuetify)
Vue.use(Router)

export default new Router({
    routes: [

      {
        path: "/home",
        name: "home",
        component: Home
      },
      {
        path: "/login",
        name: "login",
        component: Login
      },
      {
        path: "/register",
        name: "register",
        component: Register
      }


          ]
})
