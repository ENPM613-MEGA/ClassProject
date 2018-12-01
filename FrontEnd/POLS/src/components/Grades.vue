<template>
  <v-app>
  <div>
  <sidebar ref="sidebarData"></sidebar>
  </div>
    <v-content>
    <v-container fluid fill-height class="grey lighten-4">
    <v-layout justify-center align-center>
      <v-flex shrink>
      Put content in here
      </v-flex>
    </v-layout>
    </v-container>
  </v-content>
  </v-app>
</template>

<script>
import sidebar from "@/components/sidebar"
methods: {
  readTotalgrades: function() {
  },
  
  readgradesByAssignment: function() {
  },
  
  listRedeemableProducts: function() {
  },
  
  redeemgrades: function() {
  },
  
  addgrades: function() {
  },
   
}
  export default {
    components:{
      sidebar
    },
    data: () => ({
      drawer: null,
      items: [
        { icon: 'home', text: 'Home',model:false, link:'/AfterLogin'},
        {
          icon: 'class',
          'icon-alt': 'class',
          text: 'Classes',
          link: "/Classes",
          model:true,
          children: [
            { icon: 'all_inclusive',model:false, text: 'Syllabus', link:'/Syllabus'},
            { icon: 'list_alt', model:false, text: 'Modules', link:'/Modules' },
            { icon: 'assignment',model:false, text: 'Assignments', link:'/Assignments' },
            { icon: 'assessment',model:true, text: 'Grades', link:'/Grades' }
          ]
        },
        {
          icon: 'face',
          'icon-alt': 'face',
          text: 'Tutorial',
          link:'/tutorial', 
          model: false,
          children: [
            { icon: 'pregnant_woman', text: 'Prebirth', link:'/AfterLogin'  },
            { icon: 'exposure_zero', text: '0-12 Months', link:'/AfterLogin'  },
            { icon: 'exposure_plus_1', text: '12-24 Months', link:'/AfterLogin'  },
            { icon: 'exposure_plus_2', text: '24-36 Months', link:'/AfterLogin'  }
          ]
        },
        { icon: 'loyalty', text: 'Points', link:'/AfterLogin'  },
      ]
    }),
    props: {
      source: String
    },
    mounted(){
      this.$refs.sidebarData.setData(this.items) 
    }
  }
</script>
