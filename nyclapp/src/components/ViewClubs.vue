<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

      <h2>Registered Clubs {{season}}</h2>

    <table class="table table-bordered table-striped">
      <thead class="thead">
      <tr>
        <th>Club Name</th>
        <th>Main Contact</th>
        <th>Main Contact Email</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in clubs"><td>{{item.clubName}}</td><td>{{item.mainContact.contactName}}</td><td>{{item.mainContact.contactEmail}}</td><td>{{item.applicationStatus}}</td></tr>
      </tbody>
    </table>

  </div>
</template>

<script>
  import axios from 'axios'
  export default {

    name: 'ViewClubs',
    data () {
      return {
        season: '2018',
        clubs: ''
      }
    },

    mounted: function () {
      var self = this
      axios.get('http://localhost:8081/getclubs').then(function (response) {
        self.clubs = response.data
      })
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
