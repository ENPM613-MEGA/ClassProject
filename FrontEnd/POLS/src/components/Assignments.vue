<template>
	<v-app>
		<div>
			<sidebar ref="sidebarData"></sidebar>
		</div>
		<v-content>
			<v-container fluid fill-height class="grey lighten-4">
				<v-layout justify-center align-center>
					<div v-if="submitAssignment">
						<div>
							<v-subheader><b>Submit Assignment</b></v-subheader>
							<v-select v-model="fileDefault" ref="uploadFileType" :items="fileTypes" label="File Type"></v-select>
							<input type="file" accept="*" class="input-file" ref="fileLoc">
							<v-btn small color="white" @click="finishSubmitting(fileDefault)">Upload</v-btn>
							<v-btn small color="white" @click="deleteAssignment(item.fileID)">delete assignment </v-btn>
							<v-btn small color="white" @click="editAssignment(item.fileID)">edit assignment </v-btn>
						</div>	
					</div>
				</v-layout>
			</v-container>
		</v-content>
	</v-app>
</template>
<script>
import sidebar from '@/components/sidebar'
import Router from 'vue-router'
export default {
	methods: {
getAssignmentState() {
        if (this.assignmentInfo.fid != 0 && this.assignmentInfo.uid != 0) {

        console.log('localhost:8080/v1/assignment/get-assignment/' + this.assignmentInfo.assId + "&" + this.uid + "&" + this.token)

        axios
          .get('localhost:8080/v1/assignment/get-assignment/' + this.assignmentInfo.assId + "&" + this.uid + "&" + this.token)
          .then(response => (this.assignmentInfo.assignContent = response))
          .catch(error => (this.assignmentInfo.assignContent = null))

        console.log("finished request")
      } else {
        this.assignmentInfo.assignContent = null
      }
  },
  
  setAssignmentState() {
  },
  
  addAssignment() {
    	var returnData = 0;
			if (type != "video") {
				var data = new FormData();
				data.append("assName", this.assName);
				data.append("uId", 1);
        data.append("cId", 1);
        data.append("token", this.token);
				data.append("dueDate", this.dueDate);
				data.append("publish", "true");
				
				var reqString = 'localhost:8080/v1/assignment/create/';
				//console.log(reqString);
				axios
					.post(reqString, data, {
						headers: {
							'Content-Type': 'multipart/form-data'
						}
					})
					.then(response => {
						console.log("upload assignment successful")
						location.reload(); 
						
					})
					.catch(error => (console.log("upload assignment failed")))
			} 
  },

  getAssignment() {
      if (this.assignmentInfo.fid != 0 && this.assignmentInfo.uid != 0) {

      console.log('localhost:8080/v1/assignment/get-assignment/' + this.assignmentInfo.assId + "&" + this.uid + "&" + this.token)

      axios
        .get('localhost:8080/v1/assignment/get-assignment/' + this.assignmentInfo.assId + "&" + this.uid + "&" + this.token)
        .then(response => (this.assignmentInfo.assignContent = response))
        .catch(error => (this.assignmentInfo.assignContent = null))

      console.log("finished request")
     } else {
      this.assignmentInfo.assignContent = null
      }
  },
  
  editAssignment() {
    var returnData = false
    if (this.uId != 0 && this.token != 0 && this.assID != 0) {

      axios
        .post('http://localhost:8080/v1/assignment/update/', {
          params: {
            [assID]: this.assID,
            [assName]: this.assName,
            [uId]: this.uid,
            [token]: this.token,
            [publish]: false,
            [data]: this.Data,
          }
        })
        .then(response => (returnData = true))
        .catch(error => (returnData = false))

    } else {
      returnData = false
    }
  },
  
  getSubmittedAssignment() {
  },
  
  submitAssignment () {
    var returnData = false
    if (this.uId != 0 && this.token != 0 && this.assID != 0) {

      axios
        .post('http://localhost:8080/v1/assignment/submit/', {
          params: {
            [uId]: this.uid,
            [token]: this.token,
            [assID]: this.assID,
            [answers]: this.answers,
          }
        })
        .then(response => (returnData = true))
        .catch(error => (returnData = false))

    } else {
      returnData = false
    }
  },  
  
  deleteAssignment () {
      var returnData = false
			if (this.uid != 0 && fid != 0) {
			  var reqString = 'http://localhost:8080/v1/assignment/delete/' + assId + "&" + this.uid + "&" + this.token
				console.log(reqString);
				axios
					.post(reqString)
					.then(response => {
						console.log(response.data)
						location.reload(); 
					})
					.catch(error => (console.log("failed to delete")))
			} else {
				console.log("cant delete")
			}
  },
 
},
	components: {
		sidebar
	},
	data: () => ({
		drawer: null,
		items: [
			{ icon: 'home', text: 'Home', model: true, link: '/Classes' },
			{
				icon: 'class',
				'icon-alt': 'class',
				text: 'Classes',
				link: "/Classes",
				model: false,
				children: [
					{ icon: 'all_inclusive', text: 'Syllabus', link: '/Syllabus' },
					{ icon: 'list_alt', model: false, text: 'Modules', link: '/Modules' },
					{ icon: 'assignment', text: 'Assignments', link: '/Assignments' },
					{ icon: 'assessment', text: 'Grades', link: '/Grades' }
				]
			},
			{
				icon: 'face',
				'icon-alt': 'face',
				text: 'Tutorial',
				link: '/tutorial',
				model: false,
				children: [
					{ icon: 'pregnant_woman', text: 'Prebirth', link: '/Classes' },
					{ icon: 'exposure_zero', text: '0-12 Months', link: '/Classes' },
					{ icon: 'exposure_plus_1', text: '12-24 Months', link: '/Classes' },
					{ icon: 'exposure_plus_2', text: '24-36 Months', link: '/Classes' }
				]
			},
			{ icon: 'loyalty', text: 'Points', odel: false, link: '/Classes' },
		]
	}),
	props: {
		source: String
	},
	mounted() {
		this.$refs.sidebarData.setData(this.items)
	}
}

</script>
