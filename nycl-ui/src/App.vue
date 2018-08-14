<template>
  <div id="app">

    <b-navbar toggleable>

        <b-nav-toggle target="nav_collapse"></b-nav-toggle>

        <b-link class="navbar-brand" to="#">
          <span>NYCL Admin</span>
        </b-link>

        <b-collapse is-nav id="nav_collapse">

          <b-nav is-nav-bar v-if="!isLoggedIn">
            <b-nav-item><router-link v-bind:to="'/RegisterClub'">Register</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="leagueAdmin">
            <b-nav-item><router-link v-bind:to="'/ViewClubs'">View Clubs</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="clubAdmin">
            <b-nav-item><router-link v-bind:to="'/AppForm'">Application</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="clubAdmin">
            <b-nav-item><router-link v-bind:to="'/AddTeam'">Add Team</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="clubAdmin">
            <b-nav-item><router-link v-bind:to="'/TeamList'">View Teams</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="clubAdmin">
            <b-nav-item><router-link v-bind:to="'/CupEntries'">Cup Entries</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="clubAdmin">
            <b-nav-item><router-link v-bind:to="'/CompleteApplication'">Complete</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="leagueAdmin">
            <b-nav-item><router-link v-bind:to="'/LeagueList'">Leagues</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="leagueAdmin">
            <b-nav-item><router-link v-bind:to="'/Cups'">Cups</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="leagueAdmin">
            <b-nav-item><router-link v-bind:to="'/Configuration'">Configure Season</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="leagueAdmin">
            <b-nav-item><router-link v-bind:to="'/Fixtures'">Fixtures</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar v-if="!isLoggedIn">
            <b-nav-item><router-link v-bind:to="'/LoginForm'">Login</router-link></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar>
            <b-nav-item><a href="#" v-if="isLoggedIn" @click="logout">Logout</a></b-nav-item>
          </b-nav>

          <b-nav is-nav-bar>
            <b-nav-item><router-link v-bind:to="'/about'">About</router-link></b-nav-item>
          </b-nav>

        </b-collapse>
    </b-navbar>

    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'app',
    data () {
      return {
      }
    },

    methods: {
      logout () {
        this.$store.dispatch('logout')
        this.$router.push('/')
      }
    },

    computed: {
      isLoggedIn () {
        return this.$store.getters.isLoggedIn
      },
      leagueAdmin () {
        return (this.$store.getters.getRole === 'leagueAdmin')
      },
      clubAdmin () {
        return (this.$store.getters.getRole === 'clubAdmin')
      }
    }
  }
</script>
<!-- styling for the component -->
<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

  .navbar {
    background-color: darkgreen;
  }

  a {
    color: white;
  }
</style>
