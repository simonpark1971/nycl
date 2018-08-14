<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <div v-if="club.applicationStatus === 'complete'" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Application Complete</h4>
      <p>You can't make changes to a complete application.</p>
    </div>

    <div v-if="cupsEntered" class="alert alert-success" role="alert">
      <h4 class="alert-heading">Success!</h4>
      <p>You have successfully submitted your cup entries</p>
    </div>

    <div v-if="submissionError" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Failed!</h4>
      <p>The was a problem with your submission please report this to the NYCL.</p>
    </div>

    <form>
      <div class="form-group">
        <div class="form-group">
          <h3>Select the competitions you wish to enter</h3>
          <div><label><input v-model="club.enterU11Cup" class="form-check-input" type="checkbox">Enter U11 Cup</label></div>
          <div><label><input v-model="club.enterU12Cup" class="form-check-input" type="checkbox">Enter U12 Cup</label></div>
          <div><label><input v-model="club.enterU14Cup" class="form-check-input" type="checkbox">Enter U14 Cup</label></div>
        </div>
      </div>

      <b-btn v-if="club.applicationStatus !== 'complete'" variant="outline-success" type="button" v-on:click='doSaveClub' id="login" name="login" class="btn btn-default">Submit</b-btn>
    </form>
  </div>
</template>

<script>

  import axios from 'axios'

  export default {
    name: 'loginForm',
    data () {
      return {
        cupsEntered: false,
        submissionError: false,
        club: {
          enterU11Cup: false,
          enterU12Cup: false,
          enterU14Cup: false
        }
      }
    },

    methods: {
      doSaveClub () {
        var self = this
        if (confirm('Please confirm your cup entries') === true) {
          axios.post('http://localhost:8081/updatecupentries', this.club,
            { headers: {'Content-Type': 'application/json'} }).then(function () {
              self.cupsEntered = true
              self.submissionError = false
            }).catch(function () {
              self.cupsEntered = false
              self.submissionError = true
            })
        }
      }
    },

    mounted: function () {
      var self = this
      axios.get('http://localhost:8081/getclub?clubName=' + this.$store.getters.getClub).then(function (response) {
        self.club = response.data
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
