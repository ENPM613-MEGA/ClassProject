<template>
	<v-app>
		<div>
			<sidebar ref="sidebarData"></sidebar>
		</div>
		<v-content>
			<v-container fluid fill-height class="grey lighten-4">
				<v-layout justify-center align-center>
					<v-flex xs12 sm10>
						<DocumentService ref="DS"></DocumentService>
					</v-flex>
				</v-layout>
			</v-container>
		</v-content>
	</v-app>
</template>
<script>
import AccountServices from '@/components/AccountServices'
import DocumentService from '@/components/DocumentService'
import sidebar from "@/components/sidebar"

export default {
	components: {
		sidebar,
		AccountServices,
		DocumentService
	},
	data: () => ({
		drawer: null,
		items: [
			{ icon: 'home', text: 'Home', link: '/AfterLogin' },
			{
				icon: 'class',
				'icon-alt': 'class',
				text: 'Classes',
				link: "/Classes",
				model: true,
				children: [
					{ icon: 'all_inclusive', model: true, text: 'Syllabus', link: '/Syllabus' },
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
					{ icon: 'pregnant_woman', text: 'Prebirth', link: '/AfterLogin' },
					{ icon: 'exposure_zero', text: '0-12 Months', link: '/AfterLogin' },
					{ icon: 'exposure_plus_1', text: '12-24 Months', link: '/AfterLogin' },
					{ icon: 'exposure_plus_2', text: '24-36 Months', link: '/AfterLogin' }
				]
			},
			{ icon: 'loyalty', text: 'Points', link: '/AfterLogin' },
		]
	}),
	mounted() {
		this.$refs.sidebarData.setData(this.items)
		this.$refs.DS.syllabus = true
	},
	props: {
		source: String
	}
}

</script>
