<template>
	<v-app>
		<router-view></router-view>
		<v-toolbar color="teal" dark app>
			<v-icon left>home</v-icon>
			<v-toolbar-title>{{title}}</v-toolbar-title>
			<v-spacer></v-spacer>
			<div v-if="logged"></div>
			<div v-else>
			<v-toolbar-items app>
				<v-btn flat v-for="menu in menus" :Key="menu.name" router :to="menu.link">
					{{menu.name}}
				</v-btn>
			</v-toolbar-items>
			</div>
		</v-toolbar>
	</v-app>
</template>
<script>
import AccountServices from '@/components/AccountServices'
import DocumentService from '@/components/DocumentService'
export default {
	components:{
		AccountServices,
		DocumentService
	},
	methods:{
		updateClassInFocus(cid){
			this.classInFocus = cid;
		}
	},
	data() {
		return {
			title: 'POLS',
			menus: [
				{ name: 'Login', link: '/login' },
				{ name: 'Register', link: '/register' },

			],
			logged:false

		}
	},
	onMount(){
		logged:this.$store.state.loginedIn;
	}, 
	computed(){
		logged:this.$store.state.loginedIn;
	}



}

</script>
<style lang="css">
</style>
