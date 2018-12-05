<template>
	<div>
		<AccountServices ref="accounts"></AccountServices>
		<div v-if="colorBlind">
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
						<v-list-group v-else-if="item.children" v-model="item.model" :key="item.text" :prepend-icon="item.model ? item.icon : item['icon-alt']" append-icon="" ripple>
							<v-list-tile slot="activator" :to="item.link">
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
							<v-list-tile v-for="(child, i) in item.children" :key="i" @click="" :to="child.link" ripple>
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
						<v-list-tile v-else @click="" :key="item.text" :to="item.link" ripple>
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
			<v-toolbar color="grey" app absolute clipped-left>
				<v-toolbar-side-icon @click="drawer = !drawer"></v-toolbar-side-icon>
				<span class="title ml-3 mr-5">Parents Online Learning System</span></span>
				<v-spacer></v-spacer>
			</v-toolbar>
		</div>
		<div v-else>
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
						<v-list-group v-else-if="item.children" v-model="item.model" :key="item.text" :prepend-icon="item.model ? item.icon : item['icon-alt']" append-icon="" ripple>
							<v-list-tile slot="activator" :to="item.link">
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
							<v-list-tile v-for="(child, i) in item.children" :key="i" @click="" :to="child.link" ripple>
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
						<v-list-tile v-else @click="" :key="item.text" :to="item.link" ripple>
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
		</div>
	</div>
</template>
<script>
import axios from 'axios';
import AccountServices from '@/components/AccountServices'
export default {
	components: {
		AccountServices,
	},

	methods: {
		setData(itemsList) {
			this.items = itemsList
		},
		getColorBlindMode() {
			var uid = this.$refs.accounts.getUserID()
			var token = this.$refs.accounts.getUserToken()
			var reqString = 'http://localhost:8080/v1/account/get-account/' + uid + "&" + token
			axios
				.get(reqString)
				.then(response => {
					console.log(response);
					if (response.data.status == "success") {
						this.colorBlind = response.data.account.colorBlind;
					} else {
						console.log("failed in colorBlind Mode")
					}

				})
				.catch(error => (console.log(error)))
		}
	},
	data: () => ({
		drawer: null,
		colorBlind: false,
		items: [],
	}),
	mounted() {
		this.getColorBlindMode()
	}
}

</script>
