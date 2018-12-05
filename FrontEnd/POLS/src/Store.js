import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex);

 export const store = new Vuex.Store(
   {
     state:
     {
       userProfile:
       {
         id:'',
         username:'',
         passwd:'',
         gender:'',
         role:'',
         birth:'',
         address:'',
         email:'',
         colorBlind:''
       },
       token: ''

     },
     getters:
     {

     },
     mutations:
     {

       Register(state,registered)
       {
         state.userProfile.username = registered.username
         state.userProfile.passwd = registered.passwd
         state.userProfile.gender = registered.gender
         state.userProfile.role = registered.role
         state.userProfile.birth = registered.birth
         state.userProfile.address = registered.address
         state.userProfile.email = registered.email
         state.userProfile.colorBlind = registered.colorBlind
       }
     },

    actions:
     {
       Register(context,newuser)
       {

       axios.post('http://localhost:8080/v1/account/register',
       {
         username:newuser.username,
         passwd:newuser.passwd,
         gender:newuser.gender,
         role:newuser.Role
        })
       .then(
         (response)  =>  {
         let registered = {
              username:response.data.userProfile.username,
              passwd:response.data.userProfile.passwd,
              gender:response.data.userProfile.gender,
              role:response.data.userProfile.role,
              birth:response.data.userProfile.birth,
              address:response.data.userProfile.address,
              email:response.data.userProfile.email,
              colorBlind:response.data.userProfile.colorBlind
           }
          },
           console.log(registered.username),
           context.commit('Register',newregistereduser)
         )
       }
     }
 }
)
