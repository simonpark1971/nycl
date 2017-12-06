<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <table class="table table-bordered">
      <thead>
      <tr><th>League</th><th>View</th></tr>
      </thead>
      <tbody id="clubTableBody"></tbody>
    </table>



  </div>
</template>

<script>
export default {
  name: 'LeagueList',
  data () {
    return {
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
