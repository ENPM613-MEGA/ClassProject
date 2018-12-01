<template>
</template>

<script>
export default {
	data() {
	    return {
	      regData: {
	      	username:"",
	      	password:"",
	      	gender:"",
	      	role:"",
	      	birth:"",
	      	points:"",
	      	address:"",
	      	email:"",
	      	colorBlind:true
	      },
	      loginData:{
	      	userName:"",
	      	password:""
	      }
	      instructor:false
	      loggedin:false

	      uid: 0,
	      token: 0,
	    }
	},

	methods: {
		isLoggedin(){

		},
		isStudent(){

		},
		getUserToken(){

		},
		isInstructor(){

		},
		getUserID(){

		},
		setColorBlindMode(){

		},
		getColorBlindMode(){

		},
		getAddress(){

		},
		tryLogin(){

		},
		tryRegister(){

		}

	}
}




</script>