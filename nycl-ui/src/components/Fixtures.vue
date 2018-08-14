<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />
    <table class="table table-bordered">
      <thead>
      <tr><th>Age Group</th><th>Date</th><th>Home Team</th><th>Away Team</th></tr>
      </thead>
      <tbody>
        <tr v-for="fixture in season.fixtures">
          <td>{{fixture.homeTeam.ageGroup}}</td><td>{{fixture.formattedFixtureDate}}</td><td>{{fixture.homeTeam.teamName || fixture.homeTeam.clubName}}</td><td>{{fixture.awayTeam.teamName || fixture.awayTeam.clubName}}</td>
        </tr>
      </tbody>
    </table>

    <button type="button" class="btn btn-primary" v-on:click="download()">Download</button>

  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'LeagueList',
    data () {
      return {
        season: {}
      }
    },
    mounted () {
      var self = this
      axios.get('http://localhost:8081/getfixtures?season=2018').then(function (response) {
        self.season = response.data
      })
    },
    methods: {
      download () {
        axios.get('http://localhost:8081/download/fixtures', {responseType: 'arraybuffer'}).then(function (response) {
          let blob = new Blob([response.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'})
          let url = window.URL.createObjectURL(blob)
          window.open(url)
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
