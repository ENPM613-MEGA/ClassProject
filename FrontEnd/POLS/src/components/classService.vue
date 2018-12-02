<template>
	<div>
		<AccountServices ref="accounts"></AccountServices>
		<div v-if="classSelected">
			<v-toolbar color="teal" dark>
				<v-toolbar-title>Class Information</v-toolbar-title>
			</v-toolbar>
			<v-list three-line subheader>
				<v-subheader ><b>ClassDocuments</b></v-subheader>
				<v-list-tile v-for="item in classDocumentList" @click="" avatar>
					<v-list-tile-content>
						<v-list-tile-title>{{ item.filename}}</v-list-tile-title>
						<v-list-tile-sub-title>File Type:{{ item.filetype }}</v-list-tile-sub-title>
						<div v-if="isInst">
							<v-list-tile-sub-title>Is File Visible:{{ item.filepublish }}</v-list-tile-sub-title>
						</div>
					</v-list-tile-content>
				</v-list-tile>
			</v-list>
			
			<v-list three-line subheader>
				<v-subheader ><b>Class Assignment</b></v-subheader>
				<v-list-tile v-for="item in classAssignmentList" @click="" avatar>
					<v-list-tile-content>
						<v-list-tile-title>{{ item.filename}}</v-list-tile-title>
						<v-list-tile-sub-title>File Type:{{ item.filetype }}</v-list-tile-sub-title>
						<div v-if="isInst">
							<v-list-tile-sub-title>Is File Visible:{{ item.filepublish }}</v-list-tile-sub-title>
						</div>
					</v-list-tile-content>
				</v-list-tile>
			</v-list>
			<v-list subheader>
				<v-subheader><b>Class Member</b></v-subheader>
				<v-list-tile v-for="item in classMemberList" @click="" avatar>
					<v-list-tile-content>
						<v-list-tile-sub-title>{{ item.username}}</v-list-tile-sub-title>
						
					</v-list-tile-content>
				</v-list-tile>
			</v-list>
		</div>
		<div v-else>
			<v-toolbar-title>Classes</v-toolbar-title>
			<template v-for="item in classesList">
				<v-card>
					<v-img src="https://cdn.vuetifyjs.com/images/cards/desert.jpg" aspect-ratio="2.75"></v-img>
					<v-card-title primary-title>
						<div>
							<h3 class="headline mb-0">{{item.className}} </h3>
							<div>
								{{item.description}}
							</div>
						</div>
					</v-card-title>
					<v-card-actions>
						<v-btn flat color="orange" @click="selectClass(item.idvalue)">Select</v-btn>
					</v-card-actions>
				</v-card>
			</template>
		</div>
	</div>
</template>
<script>
import AccountServices from '@/components/AccountServices'
import axios from 'axios'

export default {
	components: {
		axios,
		AccountServices
	},

	data() {
		return {
			classID: 1,
			classesList: [],
			classMemberList: [],
			classDocumentList: [],
			classAssignmentList: [],
			classAssignmentListOk: false,
			classSelected: false,
			token: 0,
			isInst: false
		}
	},

	methods: {
		addClasses() {

		},
		getClasses(uid, token) {
			if (uid > 0 && token > 0) {
				var reqString = 'http://localhost:8080/v1/class/get-class-list/' + uid + "&" + token
				axios
					.get(reqString)
					.then(response => {
						if (response.data.status == "success") {

							for (var item in response.data.classList) {
								this.classesList.push({
									idvalue: response.data.classList[item].id,
									instructor_id: response.data.classList[item].instructor_id,
									className: response.data.classList[item].class_name,
									startDate: response.data.classList[item].start_date,
									endDate: response.data.classList[item].end_date,
									description: response.data.classList[item].description
								})
							}

						} else {
							this.classesList = []
						}

					})
					.catch(error => (this.classData = null))
			}
		},
		getClassDocumentList(cid) {
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()

			var reqString = 'http://localhost:8080/v1/class/get-class-files/' + cid + "&" + uid + "&" + token

			axios
				.get(reqString)
				.then(response => {
					if (response.data.status == "success") {
						if (this.isInst) {

							for (var item in response.data.files) {

								this.classDocumentList.push({
									fileID: response.data.files[item].id,
									filename: response.data.files[item].filename,
									filetype: response.data.files[item].type,
									filepublish: response.data.files[item].publish
								})
							}
						} else {
							for (var item in response.data.files) {
								if (response.data.files[item].publish == true) {

									this.classDocumentList.push({
										fileID: response.data.files[item].id,
										filename: response.data.files[item].filename,
										filetype: response.data.files[item].type,
									})
								}
							}
						}

					} else {
						console.log(" getClassDocumentList failed")
						this.classDocumentList = []
					}

				})
				.catch(error => (this.classDocumentList = []))
		},
		addClassDocument(cid, aid) {

		},
		removeClassDocument(cid, aid) {

		},
		getClassAssignmentList(cid) {
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/class/get-class-assignments-list/' + uid + "&" + this.classID + "&" + token

			axios
				.get(reqString)
				.then(response => {
					if (response.data.status == "success") {


					} else {
						console.log(" getClassMembers failed")
						this.classMemberList = []
					}

				})
				.catch(error => (this.classAssignmentList = []))
		},
		getClassAssignment(cid, aid) {

		},
		getClassMembers(cid) {
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/class/get-class-students/' + uid + "&" + this.classID + "&" + token

			axios
				.get(reqString)
				.then(response => {
					if (response.data.status == "success") {

						for (var item in response.data.studentList) {
							this
								.classMemberList.push({
									uid: response.data.studentList[item].id,
									username: response.data.studentList[item].username
								})
						}

					} else {
						console.log(" getClassMembers failed")
						this.classMemberList = []
					}

				})
				.catch(error => (this.classMemberList = []))

		},
		selectClass(cid) {
			this.classSelected = true;
			this.classID = cid;

			this.getClassDocumentList(this.classID)
			this.getClassAssignmentList(this.classID)
			this.getClassMembers(this.classID)

		}

	},
	mounted() {
		var uid = this.$refs.accounts.getUserID()
		var token = this.$refs.accounts.getUserToken()
		this.getClasses(uid, token)
		this.isInst = this.$refs.accounts.isInstructor()
	}
}

</script>
