<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <div v-if="club.applicationStatus === 'complete'" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Application Complete</h4>
      <p>You can't make changes to a complete application.</p>
    </div>

    <h2>NYCL Club Registration {{season}}</h2>
    <h4>Complete Registration</h4>

    <div v-if="saved" class="alert alert-success" role="alert">
      <h4 class="alert-heading">Success!</h4>
      <p>Your application has been completed.</p>
    </div>

    <div v-if="error" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Save failure!</h4>
      <p>Something went wrong completing your application.</p>
    </div>

    <div role="tablist">
      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-btn block href="#" v-b-toggle.accordion1>General Info</b-btn>
        </b-card-header>
        <b-collapse id="accordion1" accordion="my-accordion" role="tabpanel">
          <b-card-body>
            <p class="card-text">
              <br><strong>Club Name</strong>: {{club.clubName}}
              <br><strong>Grounds Available</strong>: {{club.pitches}}
              <br><strong>Main Contact</strong>: {{club.mainContact.contactName}}
              <br><strong>Main Contact Email</strong>: {{club.mainContact.contactEmail}}
              <br><strong>Main Contact Mobile</strong>: {{club.mainContact.contactMobilePhone}}
            </p>
          </b-card-body>
        </b-collapse>
      </b-card>
      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-btn block href="#" v-b-toggle.accordion2>Teams</b-btn>
        </b-card-header>
        <b-collapse id="accordion2" accordion="my-accordion" role="tabpanel">
          <b-card-body>
              <ul>
                <li v-for="team in club.clubTeams">{{team.teamName}} ({{team.ageGroup}})</li>
              </ul>
          </b-card-body>
        </b-collapse>
      </b-card>
      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-btn block href="#" v-b-toggle.accordion3>Cups</b-btn>
        </b-card-header>
        <b-collapse id="accordion3" accordion="my-accordion" role="tabpanel">
          <b-card-body>
            <p class="card-text">
              <p v-if="club.enterU11Cup"> Under 11 Cup
              <p v-if="club.enterU12Cup"> Under 12 Cup
              <p v-if="club.enterU14Cup" > Under 14 Cup
            </p>
          </b-card-body>
        </b-collapse>
      </b-card>
      <b-card no-body class="mb-1">
        <b-card-header header-tag="header" class="p-1" role="tab">
          <b-btn block href="#" v-b-toggle.accordion4>Registration Fee</b-btn>
        </b-card-header>
        <b-collapse id="accordion4" visible accordion="my-accordion" role="tabpanel">
          <b-card-body>
            <p class="card-text">
              Club Registration Fee: £{{getRegistrationFee}}
              <br>Cup Registrations: £{{getCupFee}}
              <br>Total Payable: £{{getRegistrationFee + getCupFee}}
            </p>
            <strong>
            <p>
              If paying by bank transfer: NYCL, sort code 54-21-47 a/c number 29569222.  It is important to put the Club name as reference.
            </p><p>
              If paying by cheque, please make out to <u>Nottingham Youth Cricket League</u>, write the club name on the back of the cheque, and send to me at:
              <br> 62 Whitby Crescent, Woodthorpe, Nottingham NG5 4LZ.
            </p>
            </strong>
          </b-card-body>
        </b-collapse>
      </b-card>
    </div>

    <b-btn v-if="club.applicationStatus !== 'complete'" class="btn" type="button" variant="outline-success" v-on:click='completeApplication' id="complete">Complete Application</b-btn>

  </div>
</template>

<script>

  import axios from 'axios'

  export default {

    name: 'registerClub',
    data () {
      return {
        error: false,
        saved: false,
        registered: false,
        season: '2018',
        clubName: '',
        club: ''
      }
    },

    methods: {
      completeApplication: function (event) {
        var self = this
        this.error = false
        if (confirm('Please confirm you wish to complete your application') === true) {
          axios.post('http://localhost:8081/complete', this.club, {headers: {'Content-Type': 'application/json'}}).then(function () {
            self.saved = true
          }).catch(function () {
            self.error = true
          })
        }
      }
    },

    mounted: function () {
      var self = this
      axios.get('http://localhost:8081/getclub?clubName=' + this.$store.getters.getClub).then(function (response) {
        self.club = response.data
      })
    },

    computed: {
      getRegistrationFee () {
        return this.club.clubTeams.length * 15
      },
      getCupFee () {
        var fee = 0
        this.club.enterU11Cup ? fee += 5 : fee += 0
        this.club.enterU12Cup ? fee += 5 : fee += 0
        this.club.enterU14Cup ? fee += 5 : fee += 0
        return fee
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
