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
                    v-model="email"
                    :rules="emailRules"
                    :counter="10"
                    label="Email"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="password"
                    :rules="passwordRules"
                    label="Password"
                    required
                  ></v-text-field>
                  <v-text-field
                    v-model="password"
                    :rules="passwordRules"
                    label="Name"
                    required
                  ></v-text-field>
                </v-form>

              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary">Register</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
  export default {
    data: () => ({
      valid: false,
      email: '',
      emailRules: [
        v => !!v || 'Email is required',
        v => v.length <= 10 || 'Email must be less than 10 characters'
      ],
      password: '',
      passwordRules: [
        v => !!v || 'Password is needed',
        v => /.+@.+/.test(v) || 'Invalid password'
      ]
    })
  }
</script>
