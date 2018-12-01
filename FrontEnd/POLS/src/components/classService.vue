<template>
	<AccountServices ref="accounts"></AccountServices>
</template>

<script>


import AccountServices from '@/components/AccountServices'


export default {
	components:{
		
		AccountServices
	},

	data() {
		return {
			classID:0,
			classData:"",
			classMemberList:"",
			token:0,
		}
	},

	methods: {
		addClasses(){

		},
		getClasses(){
			

		},
		getClassDocumentList(){

		},
		addClassDocument(cid,aid){

		},
		removeClassDocument(cid,aid){

		},
		getClassAssignmentList(cid){

		},
		addClassAssignment(cid,aid){

		},
		getClassAssignment(cid,aid){

		},
		getClassMembers(cid){

		},
		addClassMembers(cid,uid){

		},
		removeClassMembers(cid,uid){

		},

	},
	Ready(){
		var uid = this.$refs.accounts.getUserID()
		var token = this.$refs.accounts.getUserToken()
		this.getClasses()
		
	}
}




</script>