<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <div id="u11">
      <h2>Under 11 Cup</h2>
      <table class="table table-bordered">
        <thead><tr><th>Club</th></tr></thead>
        <tbody>
        <tr v-for="club in cupEntries['Under11']">
          <td>{{club.clubName}}</td>
        </tr></tbody>
      </table>
    </div>

    <div id="u12">
      <h2>Under 12 Cup</h2>
      <table class="table table-bordered">
        <thead><tr><th>Club</th></tr></thead>
        <tbody>
        <tr v-for="club in cupEntries['Under12']">
          <td>{{club.clubName}}</td>
        </tr></tbody>
      </table>
    </div>

    <div id="u14">
      <h2>Under 14 Cup</h2>
      <table class="table table-bordered">
        <thead><tr><th>Club</th></tr></thead>
        <tbody>
        <tr v-for="club in cupEntries['Under14']">
          <td>{{club.clubName}}</td>
        </tr></tbody>
      </table>
    </div>


  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'Cups',
    data () {
      return {
        cupEntries: {
          'Under11': {},
          'Under12': {},
          'Under14': {}
        }
      }
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      this.fetchData()
    },
    methods: {
      fetchData () {
        var self = this
        axios.get('http://localhost:8081/getcupentries').then(function (response) {
          self.cupEntries.Under11 = response.data['Under11']
          self.cupEntries.Under12 = response.data['Under12']
          self.cupEntries.Under14 = response.data['Under14']
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
