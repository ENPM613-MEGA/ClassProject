import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Register from '@/components/Register'
import Login from '@/components/Login'
import AfterLogin from '@/components/AfterLogin'
import Tutorial from '@/components/Tutorial'
import Syllabus from '@/components/Syllabus'
import Assignments from '@/components/Assignments'
import Grades from '@/components/Grades'
import Modules from '@/components/Modules'
import Classes from '@/components/Classes'


import Vuetify from 'vuetify'
import axios from 'axios'

Vue.use(axios)
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
        path: "/register",
        name: "register",
        component: Register
      },

      {
        path: "/login",
        name: "login",
        component: Login
      },
      {
        path: "/AfterLogin",
        name: "AfterLogin",
        component: AfterLogin
      },
      {
        path: "/Tutorial",
        name: "Tutorial",
        component: Tutorial
      },
      {
        path: "/Syllabus",
        name: "Syllabus",
        component: Syllabus
      },
      {
        path: "/Grades",
        name: "Grades",
        component: Grades
      },
      {
        path: "/Modules",
        name: "Modules",
        component: Modules
      },
      {
        path: "/Assignments",
        name: "Assignments",
        component: Assignments
      },
      {
        path: "/Classes",
        name: "Classes",
        component: Classes
      }
          ]
})
