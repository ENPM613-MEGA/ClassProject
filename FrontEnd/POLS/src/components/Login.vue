<template>
  <v-app>
    <router-view></router-view>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary" el>
                <v-toolbar-title>Login </v-toolbar-title>
                  <v-spacer></v-spacer>
                    <v-tooltip bottom>
                      <v-btn>
                        slot="activator"
                        :href="source"
                        icon
                        large
                        target="_blank"
                        <v-icon large>code</v-icon>
                      </v-btn>
                      <span>Source</span>
                    </v-tooltip>
                <v-tooltip right>
                  <v-btn slot="activator" icon large href="https://codepen.io/johnjleider/pen/wyYVVj" target="_blank">
                    <v-icon large>mdi-codepen</v-icon>
                  </v-btn>
                  <span>Codepen</span>
                </v-tooltip>
              </v-toolbar>
              <v-card-text>
              <v-form v-model="valid">
                  <v-text-field
                    v-model="username"
                    :counter="10"
                    label="User Name"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="password"
                    label="Password"
                    required
                  ></v-text-field>
                </v-form>

              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click='Loginuser'>Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import axios from 'axios'
import {mapGetters} from 'vuex';

  export default {
    data () {
      return {
      username: '',
      password:'',
      }
    },
    computed: mapGetters ({
     id: 'id',
     address: 'address',
     xxx:'homes'
   }),
   methods:
     {
       Loginuser()
       {
         axios.get('http://localhost:8080/v1/account/login/'+this.username+"&"+this.password)
         .then (response => {
 					if (response.data.status == "success"){this.$router.push('AfterLogin')}
            else	if (response.data.status == "failure"){
              this.username = '',
              this.password ='',
              this.$router.push('login')}
          }
        )

      }
    }
}
</script>
<style lang="css">
</style>
