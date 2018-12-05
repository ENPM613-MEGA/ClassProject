<template>
  <v-app>
    <router-view></router-view>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Register </v-toolbar-title>
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
                    :rules="passwordRules"
                    :counter="10"
                    label="User Name"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="passwd"
                    :rules="passwordRules"
                    label="Password"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="gender"
                    :rules="passwordRules"
                    label="Gender"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="role"
                    :rules="passwordRules"
                    label="Role"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="birth"
                    :rules="passwordRules"
                    label="Date of Birth"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="points"
                    :rules="passwordRules"
                    :counter="10"
                    label="Points"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="address"
                    :rules="passwordRules"
                    :counter="10"
                    label="Address"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="email"
                    :rules="passwordRules"
                    :counter="10"
                    label="Email"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="colorBlind"
                    :rules="passwordRules"
                    :counter="10"
                    label="Color Blind"
                    required
                  ></v-text-field>
                </v-form>

              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click='Registeruser'>Register</v-btn>
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
  import {mapGetters} from 'vuex'
  export default
  {
    data: () => ({
      valid: false,
      username:'',
      passwd:'',
      gender:'',
      role:'',
      birth:'',
      points:'',
      address:'',
      email: '',
      colorBlind: '',
      emailRules: [
        v => !!v || 'Email is required',
        v => v.length <= 10 || 'Email must be less than 10 characters'
      ],
      passwordRules: [
        v => !!v || 'Password is needed',
        v => /.+@.+/.test(v) || 'Invalid password'
      ]
    }),
    methods:
    {
      Registeruser()
      {
        let newuser =
        {
          "username":this.username,
          "passwd":this.passwd,
          "gender":this.gender,
          "role":this.role,
          "birth":this.birth,
          "points":this.points,
          "address":this.address,
          "email":this.email,
          "colorBlind":this.colorBlind
        }
      axios.post('http://localhost:8080/v1/account/register',
      newuser,
      { headers: {'Content-Type': 'application/json' }})
      .then (response => {
       if (response.data.status == "success"){this.$router.push('AfterLogin')}
       else	if (response.data.status == "failure")
       {
          this.username = '',
          this.password ='',
          
          this.$router.push('login')
        }
                }
             )
            }
          }
        }
</script>
