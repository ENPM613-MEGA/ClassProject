<template>
	<v-app>
		<div>
			<sidebar ref="sidebarData"></sidebar>
		</div>
		<v-content>
			<v-container fluid fill-height class="grey lighten-4">
				<v-layout justify-center align-center>
					<v-flex xs12 sm6>
						Put content in here
					</v-flex>
				</v-layout>
			</v-container>
		</v-content>
	</v-app>
</template>
<script>
import sidebar from "@/components/sidebar"

export default {
	methods: {
		readTotalgrades() {},

		readgradesByAssignment() {},

		listRedeemableProducts() {},

		redeemgrades() {},

		addgrades() {},

	},
	components: {
		sidebar
	},
	data: () => ({
		drawer: null,
		items: [
			{ icon: 'home', text: 'Home', model: false, link: '/Classes' },
			{
				icon: 'class',
				'icon-alt': 'class',
				text: 'Classes',
				link: "/Classes",
				model: true,
				children: [
					{ icon: 'all_inclusive', model: false, text: 'Syllabus', link: '/Syllabus' },
					{ icon: 'list_alt', model: false, text: 'Modules', link: '/Modules' },
					{ icon: 'assignment', model: false, text: 'Assignments', link: '/Assignments' },
					{ icon: 'assessment', model: true, text: 'Grades', link: '/Grades' }
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
			{ icon: 'loyalty', text: 'Points', link: '/Classes' },
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
