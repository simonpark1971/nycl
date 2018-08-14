<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <table class="table table-bordered">
      <thead>
      <tr><th>Team Name</th><th>Age Group</th><th>Main Contact</th></tr>
      </thead>
      <tbody>
      <tr v-for="item in teams"><td>{{item.teamName}}</td><td>{{item.ageGroup}}</td><td>{{item.primaryContact ? item.primaryContact.contactName : ''}}</td></tr>
      </tbody>
    </table>



  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'TeamList',
    data () {
      return {
        teams: ''
      }
    },

    mounted: function () {
      var self = this
      axios.get('http://localhost:8081/getteams?clubName=' + this.$store.getters.getClub).then(function (response) {
        self.teams = response.data
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
