<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />


    <div v-for="division in league.leagueConfig.divisions">
      <table id="leagueTable" class="table table-bordered">
        <thead>
        <tr><th>Club</th><th>Team</th></tr>
        </thead>
        <tbody id="clubTableBody">
          <tr v-for="team in division.divisionTeams">
            <td>{{team.clubName}}</td>
            <td>{{team.teamName}}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <br/>

    <div>
      <button type="button" id="split" class="btn btn-primary btn-lg">Split</button>
    </div>

    <br/>

    <div class="btn-group" data-toggle="buttons">
      <label class="btn btn-primary">
        <input type="radio" name="options" id="homeAndAwayOn" autocomplete="off" checked>Home and Away On
      </label>
      <label class="btn btn-primary">
        <input type="radio" name="options" id="homeAndAwayOff" autocomplete="off">Home and Away Off
      </label>
    </div>

    <div id="division1" style="display: none">
      <h2>Division 1</h2>
      <table class="table table-bordered">
        <thead><tr><th>Club</th></tr></thead>
        <tbody id="d1body"></tbody>
      </table>
    </div>

    <div id="division2" style="display: none">
      <h2>Division 2</h2>
      <table class="table table-bordered">
        <thead><tr><th>Club</th></tr></thead>
        <tbody id="d2body"></tbody>
      </table>
    </div>


  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'League',
    data () {
      return {
        league: null
      }
    },
    created () {
      // fetch the data when the view is created and the data is
      // already being observed
      this.fetchData()
    },
    methods: {
      fetchData () {
        var self = this
        axios.get('http://localhost:8081/getleague?league=' + this.$route.params.leagueId).then(function (response) {
          self.league = response.data
        })
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
