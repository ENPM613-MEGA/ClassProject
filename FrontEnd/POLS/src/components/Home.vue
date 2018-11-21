<template>
<v-app>
  <router-view></router-view>
<v-container>
  <v-carousel>
    <v-carousel-item
      v-for="(item,i) in items"
      :key="i"
      :src="item.src"
    ></v-carousel-item>
  </v-carousel>
  </v-container>
</v-app>
</template>

<script>
export default {
    data () {
      return {
        items: [
          {
            src: 'https://media1.pregopillow.org/wp-content/uploads/2018/08/14-Weeks.jpg'
          },
          {
            src: 'https://cdn.motherandbaby.co.uk/web/1/root/bump22_w555.jpg'
          },
          {
            src: 'http://www.ehendrick.org/Uploads/Public/Images/PregnancyBirth/Pregnancy-Birth_rop_image-500x470.jpg'
          },
          {
            src: 'http://www.actionbehavior.com/wp-content/uploads/2017/07/family-toddler-hapy-happy-160688.jpg'
          }
        ]
      }
    }
  }

</script>


<style lang="css">
</style>
