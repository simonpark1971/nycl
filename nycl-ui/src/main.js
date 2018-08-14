// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import BootstrapVue from 'bootstrap-vue'
import About from './components/About'
import ApplicationForm from './components/ApplicationForm'
import AddTeamForm from './components/AddTeamForm'
import RegisterClub from './components/RegisterClub'
import LoginForm from './components/Login'
import LeagueList from './components/LeagueList'
import TeamList from './components/TeamList'
import Cups from './components/Cups'
import ViewClubs from './components/ViewClubs'
import CompleteApplication from './components/CompleteApplication'
import SeasonConfiguration from './components/SeasonConfiguration'
import Fixtures from './components/Fixtures'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import Vuex from 'vuex'
import axios from 'axios'
import CupEntries from './components/CupEntries'
import RingLoader from 'vue-spinner/src/RingLoader.vue'

Vue.use(BootstrapVue)
Vue.use(VueRouter)

Vue.component('RingLoader', RingLoader)

const routes = [

  { path: '/ViewClubs', component: ViewClubs, meta: {requiresLogin: true} },

  { path: '/registerClub', component: RegisterClub },

  { path: '/AppForm', component: ApplicationForm, meta: {requiresLogin: true} },

  { path: '/AddTeam', component: AddTeamForm, meta: {requiresLogin: true} },

  { path: '/TeamList', component: TeamList, meta: {requiresLogin: true} },

  { path: '/CupEntries', component: CupEntries, meta: {requiresLogin: true} },

  { path: '/CompleteApplication', component: CompleteApplication, meta: {requiresLogin: true} },

  { path: '/LeagueList', component: LeagueList, meta: {requiresLogin: true} },

  { path: '/Cups', name: 'cups', component: Cups, meta: {requiresLogin: true} },

  { path: '/Fixtures', name: 'fixtures', component: Fixtures, meta: {requiresLogin: true} },

  { path: '/Configuration', name: 'config', component: SeasonConfiguration, meta: {requiresLogin: true} },

  { path: '/LoginForm', component: LoginForm, name: 'login' },

  { path: '/about', component: About }
]

const router = new VueRouter({
  routes, // short for routes: routes
  mode: 'history'
})

router.beforeEach((to, from, next) => {
  if (to.meta && to.meta.requiresLogin && !store.state.user.isLoggedIn) {
    next({name: 'login'})
  } else {
    next()
  }
})

Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    applicationName: 'NYCL',
    season: 2018,
    user: {
      isLoggedIn: false,
      role: '',
      club: ''
    }
  },

  mutations: {
    'LOGIN_SUCCESS' (state, data) {
      state.user.isLoggedIn = true
      state.user.role = data.role
      state.user.club = data.clubName
    },
    'LOGOUT' (state) {
      state.user.isLoggedIn = false
      state.user.role = ''
    }
  },

  actions: {
    login ({commit}, credentials) {
      return axios.post('http://localhost:8081/security/login', {username: credentials.username, password: credentials.password},
        { headers: {'Content-Type': 'application/json'} }).then(function (response) {
          commit('LOGIN_SUCCESS', response.data)
        })
    },

    logout ({commit}) {
      return axios.get('http://localhost:8081/security/logout').then(function (response) {
        commit('LOGOUT')
      })
    }
  },

  getters: {
    isLoggedIn: function (state) {
      return state.user.isLoggedIn
    },
    getRole: function (state) {
      return state.user.role
    },
    getClub: function (state) {
      return state.user.club
    }
  }
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App },
  store: store

}).$mount('#app')
