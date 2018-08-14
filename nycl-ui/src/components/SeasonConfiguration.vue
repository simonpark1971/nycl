<template>
  <div class="container">

    <div class="jumbotron">
      <img src="../assets/nyclogo4.jpg" class="img-circle" />
      <h1>League Configuration</h1>
    </div>

    <div v-if="currentSeason" class="panel panel-default">

      <!-- List group -->
      <table class="table">
        <thead>
          <tr>
            <th>Age Group</th>
            <th>Divisions</th>
            <th>Division Detail</th>
            <th>Update</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ageGroupConfig in currentSeason.config.ageGroupConfigurations">
            <td>Age Group: {{ageGroupConfig.ageGroup}}</td>
            <td>
              <label for="selectDivisions">Divisions: </label>
              <select class="form-control" id="selectDivisions" v-model="ageGroupConfig.numberOfDivisions">
                <option>1</option>
                <option>2</option>
              </select>
            </td>
            <td>
              <div v-for="division in ageGroupConfig.divisions">
                <p>Division {{division.divisionNumber}}<p>
                <input class="form-check-input" type="checkbox" v-model="division.homeAndAway" id="defaultCheck1" />
                <label class="form-check-label" for="defaultCheck1">Home and Away:</label>
              </p>
              </div>
            </td>
            <td>
              <button v-on:click="updateConfig(ageGroupConfig)" type="button" class="btn btn-primary">Update</button>
            </td>
          </tr>
        </tbody>

      </table>
    </div>
    <button v-on:click="update()" type="button" class="btn btn-primary">Save Season</button>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'SeasonConfiguration',
    data () {
      return {
        changesMade: false,
        currentSeason: null
      }
    },
    methods: {
      updateConfig (ageGroupConfig) {
        console.log(this.currentSeason)
        if (ageGroupConfig.numberOfDivisions === '1') {
          if (ageGroupConfig.divisions.length === 2) {
            ageGroupConfig.divisions.pop()
          }
        } else if (ageGroupConfig.numberOfDivisions === '2') {
          if (ageGroupConfig.divisions.length === 1) {
            ageGroupConfig.divisions.push({divisionNumber: '2', homeAndAway: false})
          }
        }
      },
      update: function () {
        axios.put('http://localhost:8081/saveseason', this.currentSeason).then(function (response) {
        })
      }
    },
    mounted () {
      var self = this
      axios.get('http://localhost:8081/getseason').then(function (response) {
        self.currentSeason = response.data
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
