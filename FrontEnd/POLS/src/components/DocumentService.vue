<template>
	<div>
		<AccountServices ref="accounts"></AccountServices>
		<div v-if="show">
			<div v-if="this.ableToEdit">
				<div>
					<v-btn small color="primary" @click="">Save file </v-btn>
				</div>
			</div>
			<div v-else>
				<v-container>
					<v-subheader><b>ClassDocuments</b></v-subheader>
					<div v-if="isInst">
						<div>
							<v-btn small color="primary" @click="">Upload File</v-btn>
						</div>
					</div>
					<v-container v-for="item in classDocumentList" :key="item.fileID" @click="" avatar>
						<v-list-tile-content>
							<h3 class="headline mb-0">{{ item.filename}}</h3>
							<v-list-tile-sub-title>File Type:{{ item.filetype }}</v-list-tile-sub-title>
							<div v-if="isInst">
								<v-list-tile-sub-title>Is File Visible:{{ item.filepublish }}</v-list-tile-sub-title>
								<div>
									<v-btn small color="primary" @click="">view file </v-btn>
								</div>
								<div v-if="isInst">
									<v-btn small color="primary" @click="">delete file </v-btn>
								</div>
								<div v-if="isInst">
									<v-btn small color="primary" @click="">edit file </v-btn>
								</div>
							</div>
						</v-list-tile-content>
					</v-container>
				</v-container>
			</div>
		</div>
		<div v-else>
			<div v-if="this.ableToEdit">
				<div right @click="saveDocument()">
					<v-btn small color="primary">Save</v-btn>
				</div>
				<quill-editor v-model="docData.docContent"></quill-editor>
			</div>
			<div v-else>
				<div v-if="this.isInst" right @click="enableEditDocument()">
					<v-btn small color="primary">edit</v-btn>
				</div>
				<div v-html="this.docData.docContent"></div>
			</div>
		</div>
	</div>
</template>
<script>
import axios from 'axios';
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import { quillEditor } from 'vue-quill-editor'
import AccountServices from '@/components/AccountServices'

export default {

	components: {
		axios,
		quillEditor,
		AccountServices
	},

	data() {
		return {
			docData: {
				docType: [],
				docContent: [],
				publish: false,
				fid: 1,
				classID: 0,
				createDate: 0,

			},
			classDocumentList: [],

			ableToEdit: false,
			show: true,
			uid: 1,
			token: 0,
			isInst: false,
		}
	},
	methods: {
		getDocument(fid) {
			if (fid != 0 && this.docData.uid != 0) {

				var reqString = ('http://localhost:8080/v1/document/download-file/' + fid + "&" + this.uid + "&" + this.token)
				//console.log(reqString)
				axios
					.get(reqString)
					.then(response => {

						if (response.status == "200") {
							//console.log(JSON.stringify(response.headers))
							if (JSON.stringify(response.headers) == "{\"content-type\"\:\"text/html\"}") {
								//console.log("is html")
								this.docData.docContent = response.data;

							} else {

								if (response.data.filename == "video") {
									console.log("is video")
									this.docData.fid = response.data.id;
									this.docData.classID = response.data.classId;
									this.docData.fileName = response.data.filename;
									this.docData.docType = response.data.type;
									this.docData.docContent = response.data.path;
									this.docData.createDate = response.data.createDate;
									this.docData.publish = response.data.publish;
								} else {
									console.log("is not video")
									this.docData.fid = response.data.id;
									this.docData.classID = response.data.classId;
									this.docData.fileName = response.data.filename;
									this.docData.docType = response.data.type;
									this.docData.docContent = response.data.path;
									this.docData.createDate = response.data.createDate;
									this.docData.publish = response.data.publish;
								}
							}

						} else {

						}



					})
					.catch(error => {

						console.log("bad at getting document")
					})

				//console.log("finished request")
			} else {
				this.docData.docContent = null
			}
		},
		updateDocument() {
			var returnData = false
			if (this.file != 0 && this.uid != 0 && docData != null) {

				var reqString = 'http://localhost:8080/v1/document/update-file/' + this.uId +
					"&" + this.fid + "&" + this.token + "&" + this.docData + "&" + true;
				axios
					.post(reqString)
					.then(response => (returnData = true))
					.catch(error => (returnData = false))

			} else {
				returnData = false
			}
		},
		createDocument: function(cid, type) {
			var returnData = 0;
			if (this.file != 0 && this.uid != 0 && this.cid != 0) {

				if (type != "video") {

					var data = new FormData();
					data.append("file", "test");
					data.append("cId", cid);
					data.append("uId", this.uid);
					data.append("type", type);
					data.append("publish", "true");
					data.append("token", this.token);
					var reqString = 'http://localhost:8080/v1/document/upload-file/';
					console.log(reqString);
					axios
						.post(reqString)
						.then(response => (console.log(response)))
						.catch(error => (returnData = 0))
				}

			} else {
				returnData = 0
			}
		},
		getsyllabus() {
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()

			var reqString = 'http://localhost:8080/v1/class/get-class-files/' + 1 + "&" + uid + "&" + token


			axios
				.get(reqString)
				.then(response => {
					if (response.data.status == "success") {
						if (this.isInst) {

							for (var item in response.data.files) {

								this.classDocumentList.push({
									fileID: (response.data.files[item].id),
									filename: (response.data.files[item].filename),
									filetype: (response.data.files[item].type),
									filepublish: (response.data.files[item].publish)
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
						var syllabusID = -1;
						for (var item in this.classDocumentList) {
							if (this.classDocumentList[item].filetype == "syllabus") {
								syllabusID = this.classDocumentList[item].fileID
								break
							}
						}
						//console.log(syllabusID)
						if (syllabusID > 0) {
							this.getDocument(syllabusID)
						} else {
							console.log("failed to find syllabus")
						}

					} else {
						console.log(" getClassDocumentList failed")
						this.classDocumentList = []
					}

				})
				.catch(error => (this.classDocumentList = []))


		},
		hideDocument: function(fid, publish_i) {
			var returnData = false
			if (this.file != 0 && this.uid != 0 && docData != null) {

				axios
					.post('http://localhost:8080/v1/document/update-file/', {
						params: {
							[uId]: this.uid,
							[fid]: this.fid,
							[token]: this.token,
							[data]: docData,
							[publish]: publish_i,

						}
					})
					.then(response => (returnData = true))
					.catch(error => (returnData = false))

			} else {
				returnData = false
			}
		},
		deleteDocument: function(fid) {
			var returnData = false
			if (this.file != 0 && this.uid != 0 && docData != null) {

				axios
					.post('http://localhost:8080/v1/document/delete-file/', {
						params: {
							[uId]: this.uid,
							[fid]: this.fid,
							[token]: this.token,
							[data]: docData,
							[publish]: publish_i,

						}
					})
					.then(response => (returnData = true))
					.catch(error => (returnData = false))

			} else {
				returnData = false
			}
		},
		isAvailableForEdit: function() {
			return {

			}
		},
		enableEditDocument() {
			this.ableToEdit = true;
		},
		saveDocument() {
			this.ableToEdit = false;
		}
	},
	mounted() {
		//this.getDocument()
		this.getsyllabus()
		this.isInst = this.$refs.accounts.isInstructor()
	},




}

</script>
