<template>
  <v-app>
    <v-navigation-drawer fixed clipped class="grey lighten-4" app v-model="drawer" ripple>
      <v-list dense class="grey lighten-4">
        <template v-for="item in items">
          <v-layout row v-if="item.heading" align-center :key="item.heading" ripple>
            <v-flex xs6>
              <v-subheader v-if="item.heading" class="blue--text" ripple>
                {{ item.heading }}
              </v-subheader>
            </v-flex>
          </v-layout>
          <v-list-group v-else-if="item.children" v-model="item.model" :key="item.text" :prepend-icon="item.model ? item.icon : item['icon-alt']" append-icon=""  ripple>
            <v-list-tile slot="activator" :to ="item.link">
              <v-list-tile-content>
                <div v-if="item.model">
                  <v-list-tile-title class="black--text"  ripple>
                    {{ item.text }}
                  </v-list-tile-title>
                </div>
                <div v-else>
                  <v-list-tile-title class="grey--text" ripple>
                    {{ item.text }}
                  </v-list-tile-title>
                </div>
              </v-list-tile-content>
            </v-list-tile>
            <v-list-tile v-for="(child, i) in item.children" :key="i" @click="" :to ="child.link" ripple>
              <v-list-tile-action v-if="child.icon">
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <div v-if="child.model">
                  <v-list-tile-title class="black--text" ripple>
                    {{ child.text }}
                  </v-list-tile-title>
                </div>
                <div v-else>
                  <v-list-tile-title class="grey--text" ripple>
                    {{ child.text }}
                  </v-list-tile-title>
                </div>
              </v-list-tile-content>
            </v-list-tile>
          </v-list-group>
          <v-list-group v-else-if="item.divider" @click="" :key="item.divider" ripple>
            <v-divider dark></v-divider>
          </v-list-group>
          <v-list-tile v-else @click="" :key="item.text" :to ="item.link"  ripple>
            <v-list-tile-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <div v-if="item.model">
                <v-list-tile-title class="black--text" ripple>
                  {{ item.text }}
                </v-list-tile-title>
              </div>
              <div v-else>
                <v-list-tile-title class="grey--text" ripple>
                  {{ item.text }}
                </v-list-tile-title>
              </div>
            </v-list-tile-content>
          </v-list-tile>
        </template>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar color="amber" app absolute clipped-left>
      <v-toolbar-side-icon @click="drawer = !drawer"></v-toolbar-side-icon>
      <span class="title ml-3 mr-5">Parents Online Learning System</span></span>
      <v-spacer></v-spacer>
    </v-toolbar>
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
  export default {
    data: () => ({
      drawer: null,
      items: [
        { icon: 'home', text: 'Home', link:'/AfterLogin'},
        {
          icon: 'class',
          'icon-alt': 'class',
          text: 'Classes',
          link: "/Classes",
          model:true,
          children: [
            { icon: 'all_inclusive', text: 'Syllabus', link:'/Syllabus'},
            { icon: 'list_alt', model:true, text: 'Modules', link:'/Modules' },
            { icon: 'assignment', text: 'Assignments', link:'/Assignments' },
            { icon: 'assessment', text: 'Grades', link:'/Grades' }
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
    }
  }
</script>