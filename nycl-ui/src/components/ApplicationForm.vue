<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <div v-if="saved" class="alert alert-success" role="alert">
      <h4 class="alert-heading">Success!</h4>
      <p>Your application has been successfully saved.</p>
    </div>

    <div v-if="error" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Save failure!</h4>
      <p>Something went wrong saving your application.</p>
    </div>

    <div v-if="application.club.applicationStatus === 'complete'" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Application Complete</h4>
      <p>You can't make changes to a complete application.</p>
    </div>

    <h2>{{getClubName}}</h2>
    <form>
      <h2 class="form-signin-heading">NYCL Application Form {{season}}</h2>

      <div class="form-group">
        <label for="clubName" class="sr-only">Email address</label>
        <input type="text" v-model="getClubName" id="clubName" name="clubName" class="form-control" placeholder="Club name">
      </div>

      <div class="form-group">
        <p class="form-control-static">I confirm that our club wishes to participate in the Nottingham Youth Cricket League for the {{season}} season:
          in returning this form the club agrees to abide by the rules of the league.</p>
      </div>

      <h3>Applicant</h3>
      <div class="form-group">
        <input v-model="application.club.applicantName" type="text" id="applicantName" name="applicantName" class="form-control" placeholder="Name">
      </div>

      <div class="form-group">
        <label for="applicantPosition" class="sr-only">Password</label>
        <input v-model="application.club.applicantPosition" type="text" id="applicantPosition" name="applicantPosition" class="form-control" placeholder="Position">
      </div>

      <div class="form-group">
        <label for="pitchesAvailable">Number of games that can be hosted at the same time</label>
        <select v-model="application.club.pitches" class="form-control" id="pitchesAvailable">
          <option>1</option>
          <option>2</option>
          <option>3</option>
          <option>4</option>
          <option>5</option>
        </select>
      </div>

      <h3>Main Club Contact Details</h3>
      <div class="form-group">
        <div class="input-group">
          <label for="clubContactName" class="sr-only row">Name</label>
          <input v-model="application.club.mainContact.contactName" type="text" id="clubContactName" name="clubContactName" class="form-control" placeholder="Name">
        </div>

        <div class="input-group">
          <label for="clubContactAddress" class="sr-only">Address</label>
          <input v-model="application.club.mainContact.contactAddress" type="text" id="clubContactAddress" name="clubContactAddress" class="form-control" placeholder="Address">
        </div>

        <div class="input-group">
          <label for="clubContactPostcode" class="sr-only">Postcode</label>
          <input v-model="application.club.mainContact.contactPostcode" type="text" id="clubContactPostcode" name="clubContactPostcode" class="form-control" placeholder="Postcode">
        </div>

        <div class="input-group">
          <label for="clubContactHomePhone" class="sr-only">Home phone</label>
          <input v-model="application.club.mainContact.contactHomePhone" type="tel" id="clubContactHomePhone" name="clubContactHomePhone" class="form-control" placeholder="Home phone">
        </div>

        <div class="input-group">
          <label for="clubContactMobilePhone" class="sr-only">Mobile phone</label>
          <input v-model="application.club.mainContact.contactMobilePhone" type="tel" id="clubContactMobilePhone" name="clubContactMobilePhone" class="form-control" placeholder="Mobile phone">
        </div>

        <div class="input-group">
          <label for="clubContactEmail" class="sr-only">Email</label>
          <input v-model="application.club.mainContact.contactEmail" type="email" id="clubContactEmail" name="clubContactEmail" class="form-control" placeholder="Email">
        </div>
      </div>

      <div class="form-group">
        <b-btn variant="outline-success" class="btn btn-primary" type="button" v-on:click='copyMainToFixtures'>Copy main contact to fixtures contact</b-btn>
      </div>

      <h3>Contact For ALL Fixtures</h3>
      <div class="form-group">
        <div class="input-group">
          <label for="fixtureContactName" class="sr-only">Name</label>
          <input v-model="application.club.fixturesContact.contactName" type="text" id="fixtureContactName" name="fixtureContactName" class="form-control" placeholder="Name">
        </div>

        <div class="input-group">
          <label for="fixtureContactAddress" class="sr-only">Address</label>
          <input v-model="application.club.fixturesContact.contactAddress" type="text" id="fixtureContactAddress" name="fixtureContactAddress" class="form-control" placeholder="Address">
        </div>

        <div class="input-group">
          <label for="fixtureContactPostcode" class="sr-only">Postcode</label>
          <input v-model="application.club.fixturesContact.contactPostcode" type="text" id="fixtureContactPostcode" name="fixtureContactPostcode" class="form-control" placeholder="Postcode">
        </div>

        <div class="input-group">
          <label for="fixtureContactHomePhone" class="sr-only">Home phone</label>
          <input v-model="application.club.fixturesContact.contactHomePhone" type="tel" id="fixtureContactHomePhone" name="fixtureContactHomePhone" class="form-control" placeholder="Home phone">
        </div>

        <div class="input-group">
          <label for="fixtureContactMobilePhone" class="sr-only">Mobile phone</label>
          <input v-model="application.club.fixturesContact.contactMobilePhone" type="tel" id="fixtureContactMobilePhone" name="fixtureContactMobilePhone" class="form-control" placeholder="Mobile phone">
        </div>

        <div class="input-group">
          <label for="fixtureContactEmail" class="sr-only">Email</label>
          <input v-model="application.club.fixturesContact.contactEmail" type="email" id="fixtureContactEmail" name="fixtureContactEmail" class="form-control" placeholder="Email">
        </div>
      </div>

      <div class="form-group" v-if="application.club.applicationStatus !== 'complete'">
        <b-btn variant="outline-success"  class="btn" type="button" v-on:click='saveOnSubmit'>Save</b-btn>
      </div>

    </form>

  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'applicationForm',
    data () {
      return {
        saved: false,
        error: false,
        season: '2018',
        application: {
          club: {
            id: '',
            clubName: this.$store.getters.getClub,
            applicantName: '',
            applicantPosition: '',
            pitches: '',
            mainContact: {
              id: '',
              contactName: '',
              contactAddress: '',
              contactPostcode: '',
              contactHomePhone: '',
              contactMobilePhone: '',
              contactEmail: '',
              contactType: 'MAIN_CONTACT'
            },
            fixturesContact: {
              id: '',
              contactName: '',
              contactAddress: '',
              contactPostcode: '',
              contactHomePhone: '',
              contactMobilePhone: '',
              contactEmail: '',
              contactType: 'FIXTURES_CONTACT'
            }
          }
        }
      }
    },

    computed: {
      getClubName () {
        return this.$store.getters.getClub
      }
    },

    mounted: function () {
      var self = this
      axios.get('http://localhost:8081/getclub?clubName=' + this.$store.getters.getClub).then(function (response) {
        if (!response.data.mainContact) {
          response.data.mainContact = {}
        }
        if (!response.data.fixturesContact) {
          response.data.fixturesContact = {}
        }
        self.application.club = response.data
      })
    },

    methods: {
      saveOnSubmit: function (event) {
        var self = this
        this.error = false
        if (confirm('Please confirm you wish to save this application') === true) {
          axios.post('http://localhost:8081/apply', this.application.club,
            {headers: {'Content-Type': 'application/json'}}).then(function () {
              self.saved = true
              window.scrollTo(0, 0)
            }).catch(function () {
              window.scrollTo(0, 0)
              self.error = true
            })
        }
      },

      copyMainToFixtures: function (event) {
        this.application.club.fixturesContact = this.application.club.mainContact
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
