<template>
</template>
<script>
export default {
	data() {
		return {
			regData: {
				username: "",
				password: "",
				gender: "",
				role: "",
				birth: "",
				points: "",
				address: "",
				email: "",
				colorBlind: true
			},
			loginData: {
				userName: "",
				password: ""
			},
			instructor: true,
			loggedin: false,
			colorBlind: true,

			uid: 1,
			token: 99999,
		}
	},

	methods: {
		isLoggedin() {
			return this.loggedin;
		},
		isStudent() {

		},
		getUserToken() {
			return this.token;
		},
		isInstructor() {
			return this.instructor;
		},
		getUserID() {
			return this.uid;
		},
		setColorBlindMode() {
			
		},
		getColorBlindMode() {
			return this.colorBlind;
		},
		getAddress() {

		},
		tryLogin() {

		},
		tryRegister() {

		}

	}
}

</script>
