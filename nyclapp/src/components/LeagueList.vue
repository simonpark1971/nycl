<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <ul class="nav nav-pills nav-stacked">
      <li v-for="item in leagues" role="presentation" class="active" v-on:click="fetchData(item.name)">{{item.title}}</li>
    </ul>
    <p></p>
    <div v-if="currentLeague">
      <h1>NYCL {{getLeagueTitle().title}}</h1>
      <div v-for="(division, index) in currentLeague.leagueConfig.divisions" class="row col-sm">
        <h2>Division {{index + 1}} of {{currentLeague.leagueConfig.divisions.length}}</h2>
        <table id="leagueTable" class="table table-bordered">
          <thead>
          <tr><th>Club</th><th>Team</th><th v-if="currentLeague.leagueConfig.numberOfDivisions === 2"></th></tr>
          </thead>
          <tbody id="clubTableBody">
          <tr v-for="team in division.divisionTeams">
            <td>{{team.clubName}}</td>
            <td>{{team.teamName}}</td>
            <td v-if="currentLeague.leagueConfig.numberOfDivisions === 2"><button type="button" class="btn btn-primary" v-on:click="move(team.id, index)">Move</button></td>
          </tr>
          </tbody>
        </table>
      </div>
      <button v-if="currentLeague.leagueConfig.numberOfDivisions === 1" type="button" class="btn btn-primary" v-on:click="split()">Split</button>
      <button v-if="currentLeague.leagueConfig.numberOfDivisions === 2" type="button" class="btn btn-primary" v-on:click="merge()">Merge</button>
    </div>

    <button type="button" class="btn btn-primary" v-on:click="update()">Update</button>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'LeagueList',
    data () {
      return {
        leagues: [{name: 'Under10', title: 'Under 10'},
          {name: 'Under11', title: 'Under 11'},
          {name: 'Under12', title: 'Under 12'},
          {name: 'Under13', title: 'Under 13'},
          {name: 'Under13_8S', title: 'Under 13 8-a-side'},
          {name: 'Under14', title: 'Under 14'},
          {name: 'Under15', title: 'Under 15'},
          {name: 'Under15_8S', title: 'Under 15 8-a-side'},
          {name: 'GIRLS', title: 'Girls'}],
        currentLeague: null
      }
    },
    methods: {
      fetchData (leagueId) {
        var self = this
        axios.get('http://localhost:8081/getleague?league=' + leagueId).then(function (response) {
          self.currentLeague = response.data
        })
      },
      getLeagueTitle: function () {
        var leagues = this.leagues
        var self = this
        return leagues.find(function (name) {
          return name.name === self.currentLeague.ageGroup
        })
      },
      split: function () {
        this.currentLeague.leagueConfig.numberOfDivisions = 2
        this.currentLeague.leagueConfig.divisions.push({ divisionTeams: [] })
      },
      merge: function () {
        this.currentLeague.leagueConfig.numberOfDivisions = 1
      },
      move: function (teamId, fromDiv) {
        var toDivision = fromDiv === 0 ? 1 : 0
        for (var i = 0; i < this.currentLeague.leagueConfig.divisions[fromDiv].divisionTeams.length; i++) {
          if (this.currentLeague.leagueConfig.divisions[fromDiv].divisionTeams[i].id === teamId) {
            this.currentLeague.leagueConfig.divisions[toDivision].divisionTeams.push(this.currentLeague.leagueConfig.divisions[fromDiv].divisionTeams[i])
            this.currentLeague.leagueConfig.divisions[fromDiv].divisionTeams.splice(i, 1)
          }
        }
      },
      update: function () {
        axios.post('http://localhost:8081/updateleagues', this.currentLeague).then(function (response) {
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
