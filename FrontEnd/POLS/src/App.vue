<template>

  <v-app>
    <router-view></router-view>
    <v-toolbar color="accent" app>
        <v-icon left>supervisor_account</v-icon>
        <v-toolbar-title>{{title}}</v-toolbar-title>


      <v-spacer></v-spacer>
      <v-toolbar-items app>
      <v-btn flat v-for="menu in menus"
        :Key="menu.name"
        router
        :to="menu.link">
        {{menu.name}}
      </v-btn>

      </v-toolbar-items>
    </v-toolbar>
  </v-app>
</template>


<script>
  export default {
    data () {
      return {
        title: 'POLS',
        menus:[
          { name: 'Login', link: '/login' },
          { name: 'Register', link: '/register'},
          { name: 'home', link: '/home' }


        ]

      }
    }

  }

</script>


<style lang="css">
</style>
