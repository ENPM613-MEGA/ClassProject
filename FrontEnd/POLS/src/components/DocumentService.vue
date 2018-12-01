<template>
  <div>
    <v-btn small ripple v-on:click='createDocument(1)'>Upload</v-btn>
    <div v-if="this.ableToEdit">

    </div>
    <div v-else>
      <div v-if="this.publish">
        <div v-html="docContent"></div>
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
export default {

  components: {
    quillEditor
  },

  data() {
    return {
      docData: {
        docType: "",
        docContent: "",
        publish: true,
        fid: 3,
      },
      ableToEdit: false,

      uid: 2,
      token: 0,
    }
  },
  methods: {
    getDocument: function() {
      if (this.docData.fid != 0 && this.docData.uid != 0) {

        console.log('http://localhost:8080/v1/document/download-file/' + this.docData.fid + "&" + this.uid + "&" + this.token)

        axios
          .get('http://localhost:8080/v1/document/download-file/' + this.docData.fid + "&" + this.uid + "&" + this.token)
          .then(response => (this.docData.docContent = response))
          .catch(error => (this.docData.docContent = null))

        console.log("finished request")
      } else {
        this.docData.docContent = null
      }
    },
    updateDocument: function() {
      var returnData = false
      if (this.file != 0 && this.uid != 0 && docData != null) {

        axios
          .post('http://localhost:8080/v1/document/update-file/', {
            params: {
              [u]: this.uid,
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
    createDocument: function(cid) {
      
      if (this.file != 0 && this.uid != 0 && this.cid != 0) {


        axios
          .post('http://localhost:8080/v1/document/upload-file/', {
            params: {
              [file]: docData,
              [cId]: 1,
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
  },
  beforeMount() {
    this.getDocument()
  },


  isAvailableForEdit: function() {
    return {

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


}

</script>
