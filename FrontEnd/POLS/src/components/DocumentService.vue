<template>
	<div v-if = "this.ableToEdit">
	 	<quill-editor v-model="content"
                  ref="quillEditorA"
                  @blur="onEditorBlur($event)"
                  @focus="onEditorFocus($event)"
                  @ready="onEditorReady($event)">
		</quill-editor>
	</div>
	<div v-else>
		<div v-if ="this.publish">
			<div v-html="docContent"></div>
		</div>
	</div>
</template>
<script>
import axios from 'axios';
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import { quillEditor } from 'vue-quill-editor'
export default{

  components: {
    quillEditor
  },

  data() {
    return {
      docData: {
      	docType: "",
      	docContent: "",
      	publish : true,
      	fid: 1,
      },
      ableToEdit: false,
      
      uid: 2,
      token: 0,
    }
  },
  mounted(){
  	this.docData.docContent = getDocument()
  },
  computed(){

  },
  isAvailableForEdit: function() {
    return {
      
    }
  },
  getDocument: function() {
    if (this.fid != 0 && this.uid != 0) {

      axios
        .get('http://localhost:8080/v1/document/download-file/' + this.fid + "&" + this.uid + "&" + this.token)
        .then(response => (this.docData = response))
        .catch(error => (this.docData = null))

    } else {
      this.docData = null
    }
  },
  createDocument: function(cid) {
    var returnData = 0
    if (this.file != 0 && this.uid != 0 && this.cid != 0) {

      axios
        .post('http://localhost:8080/v1/document/upload-file/', {
          params: {
            [file]: docData,
            [cId]: this.fid,
            [uId]: this.uid,
            [type]: "syllabus",
            [publish]: true,
            [token]: this.token
          }
        })
        .then(response => (returnData = 99999))
        .catch(error => (returnData = 0))

    } else {
      returnData = 0
    }
  }, 
  hideDocument: function(publish_i) {
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
  updateDocument: function() {
    var returnData = false
    if (this.file != 0 && this.uid != 0 && docData != null) {

      axios
        .post('http://localhost:8080/v1/document/update-file/', {
          params: {
          	[uId]: this.uid,
            [fid]: this.fid,
            [token]: this.token,
            [data]: docData,
            [publish]: false,
            
          }
        })
        .then(response => (returnData = true))
        .catch(error => (returnData = false))

    } else {
      returnData = false
    }
  },

}

</script>
