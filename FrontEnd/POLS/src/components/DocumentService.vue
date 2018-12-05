<template>
	<div>

		<AccountServices ref="accounts"></AccountServices>
		<div v-if="colorBlind">
			<div v-if="show">
				<div v-if="syllabus">
					<div v-if="ableToEdit">
						<div right @click="saveDocument()">
							<v-btn small color="red">Save</v-btn>
						</div>
						<quill-editor v-model="docData.docContent"></quill-editor>
					</div>
					<div v-else>
						<div v-if="isInst" right @click="enableEditDocument()">
							<v-btn small color="red">edit</v-btn>
						</div>
						<div v-html="docData.docContent"></div>
					</div>
				</div>
				<div v-else-if="upload">
					<div>
						<v-subheader><b>Upload File</b></v-subheader>
						<v-select v-model="fileDefault" ref="uploadFileType" :items="fileTypes" label="File Type"></v-select>
						<input type="file" accept="*" class="input-file" ref="fileLoc">
						<v-btn small color="red" @click="finishUpload(fileDefault)">Upload</v-btn>
					</div>
					<div>
						<v-subheader><b>Upload Video</b></v-subheader>
						<v-text-field label="Video Link" v-model="url"></v-text-field>
						<v-btn small color="red" @click="finishUploadVideo()">Upload video</v-btn>
					</div>
				</div>
				<div v-else-if="ableToEdit">
					<div right @click="saveDocument()">
						<v-btn small color="red">Save</v-btn>
					</div>
					<quill-editor v-model="docData.docContent"></quill-editor>
				</div>
				<div v-else>
					<div v-if="showFile" auto-grow:true>
						<div v-if="docData.isVideo">
							<youtube :video-id="videoId" ref="youtube" @playing="playing"></youtube>
						</div>
						<div v-else>
							<v-textarea v-model="docData.docContent" rows="25"></v-textarea>
							<v-btn small color="red" @click="closeFile()">Close file </v-btn>
						</div>
					</div>
					<div v-else>
						<v-container>
							<v-toolbar-title><b>Class Documents</b></v-toolbar-title>
							<div v-if="isInst">
								<div>
									<v-btn small color="red" @click="enableUpload()">Upload File</v-btn>
								</div>
							</div>
							<v-container v-for="item in classDocumentList" :key="item.fileID" @click="" avatar>
								<v-list-tile-content>
									<h3 class="headline mb-0">{{ item.filename}}</h3>
									<v-list-tile-sub-title>File Type:{{ item.filetype }}</v-list-tile-sub-title>
									<div v-if="isInst">
										<v-list-tile-sub-title>Is File Visible:{{ item.filepublish }}</v-list-tile-sub-title>
										<div>
											<v-btn small color="red" @click="viewFile(item.fileID)">view file </v-btn>
										</div>
										<div v-if="isInst">
											<v-btn small color="red" @click="deleteDocument(item.fileID)">delete file </v-btn>
											<v-btn small color="red" @click="editFile(item.fileID)">edit file </v-btn>
											<v-btn small color="red" @click="changeFileVis(item.fileID, item.filepublish)">change Visibility </v-btn>
										</div>
									</div>
								</v-list-tile-content>
							</v-container>
						</v-container>
					</div>
				</div>
			</div>
		</div>
		<div v-else>
			<div v-if="show">
				<div v-if="syllabus">
					<div v-if="ableToEdit">
						<div right @click="saveDocument()">
							<v-btn small color="primary">Save</v-btn>
						</div>
						<quill-editor v-model="docData.docContent"></quill-editor>
					</div>
					<div v-else>
						<div v-if="isInst" right @click="enableEditDocument()">
							<v-btn small color="primary">edit</v-btn>
						</div>
						<div v-html="docData.docContent"></div>
					</div>
				</div>
				<div v-else-if="upload">
					<div>
						<v-subheader><b>Upload File</b></v-subheader>
						<v-select v-model="fileDefault" ref="uploadFileType" :items="fileTypes" label="File Type"></v-select>
						<input type="file" accept="*" class="input-file" ref="fileLoc">
						<v-btn small color="primary" @click="finishUpload(fileDefault)">Upload</v-btn>
					</div>
					<div>
						<v-subheader><b>Upload Video</b></v-subheader>
						<v-text-field label="Video Link" v-model="url"></v-text-field>
						<v-btn small color="primary" @click="finishUploadVideo()">Upload video</v-btn>
					</div>
				</div>
				<div v-else-if="ableToEdit">
					<div right @click="saveDocument()">
						<v-btn small color="primary">Save</v-btn>
					</div>
					<quill-editor v-model="docData.docContent"></quill-editor>
				</div>
				<div v-else>
					<div v-if="showFile" auto-grow:true>
						<div v-if="docData.isVideo">
							<youtube :video-id="videoId" ref="youtube" @playing="playing"></youtube>
						</div>
						<div v-else>
							<v-textarea v-model="docData.docContent" rows="25"></v-textarea>
							<v-btn small color="primary" @click="closeFile()">Close file </v-btn>
						</div>
					</div>
					<div v-else>
						<v-container>
							<v-toolbar-title><b>Class Documents</b></v-toolbar-title>
							<div v-if="isInst">
								<div>
									<v-btn small color="primary" @click="enableUpload()">Upload File</v-btn>
								</div>
							</div>
							<v-container v-for="item in classDocumentList" :key="item.fileID" @click="" avatar>
								<v-list-tile-content>
									<h3 class="headline mb-0">{{ item.filename}}</h3>
									<v-list-tile-sub-title>File Type:{{ item.filetype }}</v-list-tile-sub-title>
									<div v-if="isInst">
										<v-list-tile-sub-title>Is File Visible:{{ item.filepublish }}</v-list-tile-sub-title>
										<div>
											<v-btn small color="primary" @click="viewFile(item.fileID)">view file </v-btn>
										</div>
										<div v-if="isInst">
											<v-btn small color="primary" @click="deleteDocument(item.fileID)">delete file </v-btn>
											<v-btn small color="primary" @click="editFile(item.fileID)">edit file </v-btn>
											<v-btn small color="primary" @click="changeFileVis(item.fileID, item.filepublish)">change Visibility </v-btn>
										</div>
									</div>
								</v-list-tile-content>
							</v-container>
						</v-container>
					</div>
				</div>
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
import Vue from 'vue'
import VueYoutube from 'vue-youtube'
import Vuex from 'vuex';
import Router from 'vue-router'

Vue.use(VueYoutube)
export default {

	components: {
		axios,
		quillEditor,
		AccountServices,
		VueYoutube,
		Vuex
	},

	data() {
		return {
			fileTypes: [
				"syllabus",
				"file",
				"test",
			],

			docData: {
				docType: [],
				docContent: [],
				publish: false,
				fid: 1,
				classID: 0,
				createDate: 0,
				isVideo: false,
				videoId: 'lG0Ys-2d4MA',

			},
			colorBlind: false,
			classDocumentList: [],
			upload: false,

			syllabus: false,
			ableToEdit: false,
			show: true,
			uid: 1,
			token: 0,
			isInst: false,
			showFile: false,
			fileDefault: "file",
			url: "https://www.youtube.com/watch?v=wfYbgdo8e-8",
		}
	},
	methods: {
		getDocument(fid) {
			if (fid != 0 && this.docData.uid != 0) {

				var reqString = ('http://localhost:8080/v1/document/download-file/' + fid + "&" + this.uid + "&" + this.token)
				console.log(reqString)
				axios
					.get(reqString)
					.then(response => {

						if (response.status == "200") {
							//console.log(JSON.stringify(response.data))
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
									this.docData.docContent = (response.data);
									this.docData.docContent = response.data.path;
									this.docData.createDate = response.data.createDate;
									this.docData.publish = response.data.publish;
								}
								this.docData.docContent = (response.data);

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
					"&" + this.fid + "&" + this.token + "&" + this.docData.docContent + "&" + true;
				axios
					.post(reqString)
					.then(response => (returnData = true))
					.catch(error => (returnData = false))

			} else {
				returnData = false
			}
		},
		createDocument: function(type, fileaddress) {
			var returnData = 0;
			var uid = this.$store.state.userProfile.id;

			if (type != "video") {

				var data = new FormData();
				data.append("file", fileaddress);
				data.append("cId", 1);
				data.append("uId", uid);
				data.append("type", type);
				data.append("publish", "true");
				data.append("token", this.token);
				var reqString = 'http://localhost:8080/v1/document/upload-file/';
				//console.log(reqString);
				axios
					.post(reqString, data, {
						headers: {
							'Content-Type': 'multipart/form-data'

						}
					})
					.then(response => {
						console.log("upload file ok")
						

					})
					.catch(error => (console.log("upload file failed")))
			} else {
				//console.log(fileaddress)

				let data = {
					"cId": 1,
					"uId": uid,
					"token": "11",
					"videoname": "test",
					"url": fileaddress
				};
				console.log(data)


				axios({
						url: 'http://localhost:8080/v1/document/upload-video',
						method: 'post',
						data: JSON.stringify(data),
						headers: {
							'Content-Type': 'application/json',

						}
					})
					.then(response => {
						console.log("create video ok")
						

					})
					.catch(error => (console.log(error)))

			}


		},
		updateDocumentList() {
			var uid = this.$store.state.userProfile.id
			var token = this.$refs.accounts.getUserToken()

			var reqString = 'http://localhost:8080/v1/class/get-class-files/' + 1 + "&" + uid + "&" + token


			axios
				.get(reqString)
				.then(response => {
					if (response.data.status == "success") {
						this.classDocumentList = {}
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



					} else {
						console.log(" getClassDocumentList failed")
						this.classDocumentList = []
					}

				})
				.catch(error => (this.classDocumentList = []))


		},
		getsyllabus() {
			var uid = this.$store.state.userProfile.id
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
		changeFileVis: function(fid, publish_i) {
			var returnData = false
			var uid = this.$store.state.userProfile.id
			if (uid != 0) {


				var data = new FormData();
				data.append("uId", uid);
				data.append("fId", fid);
				data.append("token", 0);
				data.append("publish", !publish_i);

				let data = {
					"id": uid,
					"fId": fid,
					"token": "11",
					"publish": !publish_i
				};
				console.log(data)
				axios
					({
						url: 'http://localhost:8080/v1/document/update-document',
						method: 'post',
						data: JSON.stringify(data),
						headers: {
							'Content-Type': 'application/json',

						}
					})
					.then(response => {
						console.log("changed Visibility")
						this.updateDocumentList()


					})
					.catch(error => (console.log(error)))

			} else {
				returnData = false
			}
		},
		deleteDocument: function(fid) {
			var returnData = false
			var uid = this.$store.state.userProfile.id
			if (uid != 0 && fid != 0) {
				var reqString = 'http://localhost:8080/v1/document/delete-file/' + fid + "&" + this.uid + "&" + this.token
				console.log(reqString);
				axios
					.post(reqString)
					.then(response => {

						console.log(response.data)

						router.push("Classes")
					})
					.catch(error => (console.log("failed to delete")))

			} else {
				console.log("cant delete")
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
		},
		enableUpload() {
			this.upload = true;
		},
		finishUpload(type) {
			this.upload = false;

			//upload file 
			this.createDocument(type, this.$refs.fileLoc.files[0])

		},
		finishUploadVideo(type) {
			this.upload = false;
			//upload file 

			this.createDocument("video", this.url)

		},
		viewFile(fid) {
			this.showFile = true
			this.getDocument(fid);

		},
		closeFile() {
			this.showFile = false
		},
		editFile(fid) {
			this.enableEditDocument();
			this.getDocument(fid);
		},
		playVideo() {
			this.player.playVideo()
		},
		playing() {
			console.log('\o/ we are watching!!!')
		},
		getColorBlindMode(){
			var uid = this.$store.state.userProfile.id
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/account/get-account/' + uid + "&" + token
				axios
					.get(reqString)
					.then(response => {
						//console.log(response);
						if (response.data.status == "success") {
							this.colorBlind = response.data.account.colorBlind;
						} else {
							console.log("failed in colorBlind Mode")
						}

					})
					.catch(error => (console.log(error)))
		}
	},
	mounted() {
		//this.getDocument()
		this.getsyllabus()
		this.isInst = this.$store.state.userProfile.colorBlind
		this.getColorBlindMode();
	},
	computed: {
		player() {
			return this.$refs.youtube.player
		}
	}




}

</script>
