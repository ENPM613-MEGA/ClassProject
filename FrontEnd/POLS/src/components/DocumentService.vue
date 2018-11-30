<script>
import axios from 'axios';

Vue.component('DocumentService', {



  data: function() {
    return {
      docData: ""
    }
  },
  isAvailableForEdit: function() {
    return {
      ableToEdit: true
    }
  },
  fileID: function() {
    return {
      fid: 1
    }
  },
  userID: function() {
    return {
      uid: 2
    }
  },
  gettoken: function() {
    return {
      token: 0
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

})

</script>
