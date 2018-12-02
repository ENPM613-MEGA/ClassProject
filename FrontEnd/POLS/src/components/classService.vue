<template>
	<div>
		<AccountServices ref="accounts"></AccountServices>
		<v-toolbar-title>Classes</v-toolbar-title>
		<div v-html="classesList"></div>
		<v-toolbar-title>Documents</v-toolbar-title>
		<div v-html="classDocumentList"></div>
		<v-toolbar-title>Class Assignments</v-toolbar-title>
		<div v-html="classAssignmentList"></div>
		<v-toolbar-title>Class Members</v-toolbar-title>
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
			classesList:[],
			classMemberList:[],
			classDocumentList:[],
			classAssignmentList:[],
			classAssignmentListOk:false,
			classSelected : false,
			token:0,
			isInst:false
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
				.then(response => {
					if(response.data.status == "success"){
					
						for (var item in response.data.classList){
							this.classesList.push( [response.data.classList[item].id,
													response.data.classList[item].instructor_id, 
													response.data.classList[item].class_name, 
													response.data.classList[item].start_date, 
													response.data.classList[item].end_date,
													response.data.classList[item].description]  )
						}
					
					}else{
						console.log(" getClassMembers failed")
						this.classesList = []
					}

				})
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
			.then(response => {
				if(response.data.status == "success"){
					if (this.isInst){
						console.log("isInstructor") 
						for (var item in response.data.files){
							 
							this.classDocumentList.push([response.data.files[item].id,response.data.files[item].filename,response.data.files[item].type,response.data.files[item].publish ])
						}
					}else{
						console.log("isStudent")
						for (var item in response.data.files){
							if (response.data.files[item].publish == true){
								console.log("visible")
								this.classDocumentList.push([response.data.files[item].id,response.data.files[item].filename,response.data.files[item].type])
							}else{
								console.log("notvisible")
							}
							
						}
					}
				
				}else{
					console.log(" getClassDocumentList failed")
					this.classDocumentList = []
				}

			})
			.catch(error => (this.classDocumentList = []))
		},
		addClassDocument(cid,aid){

		},
		removeClassDocument(cid,aid){

		},
		getClassAssignmentList(cid){
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/class/get-class-assignments-list/'+uid +"&"+ this.classID + "&" + token
		
			axios
			.get(reqString)
			.then(response => {
				if(response.data.status == "success"){
				
				
				}else{
					console.log(" getClassMembers failed")
					this.classMemberList = []
				}

			})
			.catch(error => (this.classAssignmentList = []))
		},
		getClassAssignment(cid,aid){

		},
		getClassMembers(cid){
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/class/get-class-students/'+uid +"&"+ this.classID + "&" + token
			
			axios
			.get(reqString)
			.then(response => {
				if(response.data.status == "success"){
				
					for (var item in response.data.studentList){
						this.classMemberList.push( [response.data.studentList[item].id,response.data.studentList[item].username] )
					}
				
				}else{
					console.log(" getClassMembers failed")
					this.classMemberList = []
				}

			})
			.catch(error => (this.classMemberList = []))

		},


	},
	mounted(){
		var uid = this.$refs.accounts.getUserID()
		var token = this.$refs.accounts.getUserToken()
		this.getClasses(uid,token)
		this.getClassDocumentList()
		this.getClassAssignmentList(1)
		this.getClassMembers(1)
		this.isInst = this.$refs.accounts.isInstructor()
	}
}




</script>