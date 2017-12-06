<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <table id="leagueTable" class="table table-bordered">
      <thead>
      <tr><th>League</th><th>View</th><th>Delete</th><th>Rank</th></tr>
      </thead>
      <tbody id="clubTableBody"></tbody>
    </table>

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
export default {
  name: 'League',
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
