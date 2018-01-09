<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <table class="table table-bordered">
      <thead>
      <tr><th>League</th><th>View</th></tr>
      </thead>
      <tbody id="clubTableBody">
        <tr v-for="item in leagues">
          <td>{{item.title}}</td>
          <td><router-link :to="{ name: 'league', params: { leagueId: item.name }}">Go</router-link></td>
        </tr>
      </tbody>
    </table>



  </div>
</template>

<script>
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
        {name: 'GIRLS', title: 'Girls'}]
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
