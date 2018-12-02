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
	      },
	      instructor:true,
	      loggedin:false,

	      uid: 1,
	      token: 99999,
	    }
	},

	methods: {
		isLoggedin(){
			return true;
		},
		isStudent(){

		},
		getUserToken(){
			return this.token;
		},
		isInstructor(){
			return this.instructor;
		},
		getUserID(){
			return this.uid;
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