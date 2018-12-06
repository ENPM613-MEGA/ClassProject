<template>
	<v-app>
		<router-view></router-view>
		<v-toolbar color="teal" dark app>
			<v-icon left>home @click='Loginuser'</v-icon>
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
import Router from 'vue-router'
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
			items: [
				{
					src: 'https://media1.pregopillow.org/wp-content/uploads/2018/08/14-Weeks.jpg'
				},
				{
					src: 'https://cdn.motherandbaby.co.uk/web/1/root/bump22_w555.jpg'
				},
				{
					src: 'http://www.ehendrick.org/Uploads/Public/Images/PregnancyBirth/Pregnancy-Birth_rop_image-500x470.jpg'
				},
				{
					src: 'http://www.actionbehavior.com/wp-content/uploads/2017/07/family-toddler-hapy-happy-160688.jpg'
				}
			],
			title: 'POLS',
			menus: [
				{ name: 'Login', link: '/login' },
				{ name: 'Register', link: '/register' },

			],
			logged:false

		}
	},
	mounted(){

	}, 




}

</script>
<style lang="css">
</style>
