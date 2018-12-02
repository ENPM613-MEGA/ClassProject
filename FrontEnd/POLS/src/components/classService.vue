<template>
	<div>
		<AccountServices ref="accounts"></AccountServices>
		<v-toolbar-title>Classes</v-toolbar-title>
		<div v-html="classData"></div>
		<v-toolbar-title>Documents</v-toolbar-title>
		<div v-html="classDocumentList"></div>
		<v-toolbar-title>Class Assignments</v-toolbar-title>
		<div v-html="classAssignmentList"></div>
		<v-toolbar-title>Class Members</v-toolbar-title>
		<template v-for="item in classMemberList">
			
		</template>
		<div v-html="classMemberList"></div>
	</div>
</template>

<script>


import AccountServices from '@/components/AccountServices'
import axios from 'axios'

export default {
	components:{
		axios,
		AccountServices
	},

	data() {
		return {
			classID:1,
			classData:[],
			classMemberList:[],
			classDocumentList:[],
			classAssignmentList:[],
			classAssignmentListOk:false,
			token:0,
		}
	},

	methods: {
		addClasses(){

		},
		getClasses(uid,token){
			if (uid>0 && token>0 ) {
				var reqString = 'http://localhost:8080/v1/class/get-class-list/'+ uid + "&" + token
				
				console.log(reqString)
				axios
				.get(reqString)
				.then(response => (this.classData = response.data))
				.catch(error => (this.classData = null))
			}
		},
		getClassDocumentList(){
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			
			var reqString = 'http://localhost:8080/v1/class/get-class-files/'+this.classID +"&"+ uid + "&" + token
			
			console.log(reqString)
			axios
			.get(reqString)
			.then(response => (this.classDocumentList = response.data))
			.catch(error => (this.classDocumentList = null))
		},
		addClassDocument(cid,aid){

		},
		removeClassDocument(cid,aid){

		},
		getClassAssignmentList(cid){
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/class/get-class-assignments-list/'+uid +"&"+ this.classID + "&" + token
			
			console.log(reqString)
			axios
			.get(reqString)
			.then(response => (this.classData = response.data))
			.catch(error => (this.classData = null))
		},
		getClassAssignment(cid,aid){

		},
		getClassMembers(cid){
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/class/get-class-students/'+uid +"&"+ this.classID + "&" + token
			var returnString = ""
			console.log(reqString)
			axios
			.get(reqString)
			.then(response => (this.classMemberList = response.data))
			.catch(error => (this.classMemberList = null))

			console.log(String(this.classMemberList))
			if (this.classMemberList === "success") {
				classAssignmentListOk = true
				console.log("classMember ok")
			}else{
				console.log("classMember bad")
			}

		},

	},
	mounted(){
		var uid = this.$refs.accounts.getUserID()
		var token = this.$refs.accounts.getUserToken()
		this.getClasses(uid,token)
		this.getClassDocumentList()
		this.getClassAssignmentList(1)
		this.getClassMembers(1)
	}
}




</script>