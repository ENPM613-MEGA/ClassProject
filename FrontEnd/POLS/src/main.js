
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuetify from 'vuetify'
import Axios from 'axios'
import {store} from './Store'
import Vuex from 'vuex';

Vue.use(Vuetify)


import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: { App }
})
