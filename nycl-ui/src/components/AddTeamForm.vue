<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <div v-if="saved" class="alert alert-success" role="alert">
      <h4 class="alert-heading">Success!</h4>
      <p>Your team has been successfully saved.</p>
    </div>

    <div v-if="error" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Save failure!</h4>
      <p>Something went wrong saving your team.</p>
    </div>

    <div v-if="status === 'complete'" class="alert alert-danger" role="alert">
      <h4 class="alert-heading">Application Complete</h4>
      <p>You can't make changes to a complete application.</p>
    </div>

    <form class="form-add-team" id="addTeamFrm">
      <h2>{{getClubName}}</h2>
      <h3>Add Team {{ageGroup}}</h3>
      <div class="form-group">
        <label for="ageGroupSelect">Select age group</label>
        <select class="form-control" v-model="team.ageGroup" id="ageGroupSelect" name="ageGroupSelect">
          <option>Under10</option>
          <option>Under11</option>
          <option>Under13_8S</option>
          <option>Under12</option>
          <option>Under13</option>
          <option>Under15_8S</option>
          <option>Under14</option>
          <option>Under15</option>
          <option>GIRLS</option>
        </select>
      </div>

      <label for="teamName" class="sr-only">Team Name</label>
      <input type="text" v-model="team.teamName" id="teamName" name="teamName" class="form-control" placeholder="Team Name">

      <div class="form-group">
        <h3>Select preferred home days</h3>
        <div><label><input v-model="team.playHomeMondays" class="form-check-input" type="checkbox">Monday Evening</label></div>
        <div><label><input v-model="team.playHomeTuesdays" class="form-check-input" type="checkbox">Tuesday Evening</label></div>
        <div><label><input v-model="team.playHomeWednesdays" class="form-check-input" type="checkbox">Wednesday Evening</label></div>
        <div><label><input v-model="team.playHomeThursdays" class="form-check-input" type="checkbox">Thursday Evening</label></div>
        <div><label><input v-model="team.playHomeFridays" class="form-check-input" type="checkbox">Friday Evening</label></div>
        <div><label><input v-model="team.playHomeSundays" class="form-check-input" type="checkbox">Sunday Morning</label></div>
      </div>

      <h3>First Contact</h3>
      <p>
        <label for="firstContactName" class="sr-only">Name</label>
        <input type="text" v-model="team.primaryContact.contactName" id="firstContactName" name="firstContactName" class="form-control" placeholder="Name">

        <label for="firstContactAddress" class="sr-only">Address</label>
        <textarea v-model="team.primaryContact.contactAddress" id="firstContactAddress" name="firstContactAddress" class="form-control" placeholder="Address"></textarea>

        <label for="firstContactPostcode" class="sr-only">Postcode</label>
        <input v-model="team.primaryContact.contactPostcode" type="text" id="firstContactPostcode" name="firstContactPostcode" class="form-control" placeholder="Postcode">

        <label for="firstContactHomePhone" class="sr-only">Home phone</label>
        <input v-model="team.primaryContact.contactHomePhone" type="tel" id="firstContactHomePhone" name="firstContactHomePhone" class="form-control" placeholder="Home phone">

        <label for="firstContactMobilePhone" class="sr-only">Mobile phone</label>
        <input v-model="team.primaryContact.contactMobilePhone" type="tel" id="firstContactMobilePhone" name="firstContactMobilePhone" class="form-control" placeholder="Mobile phone">

        <label for="firstContactEmail" class="sr-only">Email</label>
        <input v-model="team.primaryContact.contactEmail" type="email" id="firstContactEmail" name="firstContactEmail" class="form-control" placeholder="Email">
      </p>

      <h3>Second Contact</h3>
      <p>
        <label for="secondContactName" class="sr-only">Name</label>
        <input type="text" v-model="team.secondaryContact.contactName" id="secondContactName" name="secondContactName" class="form-control" placeholder="Name">

        <label for="secondContactAddress" class="sr-only">Address</label>
        <textarea v-model="team.secondaryContact.contactAddress" id="secondContactAddress" name="secondContactAddress" class="form-control" placeholder="Address"></textarea>

        <label for="secondContactPostcode" class="sr-only">Postcode</label>
        <input v-model="team.secondaryContact.contactPostcode" type="text" id="secondContactPostcode" name="secondContactPostcode" class="form-control" placeholder="Postcode">

        <label for="secondContactHomePhone" class="sr-only">Home phone</label>
        <input v-model="team.secondaryContact.contactHomePhone" type="tel" id="secondContactHomePhone" name="secondContactHomePhone" class="form-control" placeholder="Home phone">

        <label for="secondContactMobilePhone" class="sr-only">Mobile phone</label>
        <input v-model="team.secondaryContact.contactMobilePhone" type="tel" id="secondContactMobilePhone" name="secondContactMobilePhone" class="form-control" placeholder="Mobile phone">

        <label for="secondContactEmail" class="sr-only">Email</label>
        <input v-model="team.secondaryContact.contactEmail" type="email" id="secondContactEmail" name="secondContactEmail" class="form-control" placeholder="Email">
      </p>

      <div class="form-group" v-if="status !== 'complete'">
        <b-btn variant="outline-success"  class="btn" type="button" v-on:click='saveOnSubmit' id="submitAndMore">Add</b-btn>
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
        season: '2018',
        ageGroup: '',
        saved: false,
        error: false,
        status: 'open',
        team: {
          clubName: this.$store.getters.getClub,
          ageGroup: '',
          teamName: '',
          playHomeMondays: false,
          playHomeTuesdays: false,
          playHomeWednesdays: false,
          playHomeThursdays: false,
          playHomeFridays: false,
          playHomeSundays: false,
          primaryContact: {
            contactName: '',
            contactAddress: '',
            contactPostcode: '',
            contactHomePhone: '',
            contactMobilePhone: '',
            contactEmail: '',
            contactType: 'FIRST_CONTACT'
          },
          secondaryContact: {
            contactName: '',
            contactAddress: '',
            contactPostcode: '',
            contactHomePhone: '',
            contactMobilePhone: '',
            contactEmail: '',
            contactType: 'SECOND_CONTACT'
          }
        }
      }
    },

    mounted: function () {
      var self = this
      axios.get('http://localhost:8081/status?clubName=' + this.$store.getters.getClub).then(function (response) {
        self.status = response.data
      })
    },

    methods: {
      saveOnSubmit: function (event) {
        if (confirm('Please confirm you wish to save this team ') === true) {
          var self = this
          axios.post('http://localhost:8081/addteam', this.team,
            {headers: {'Content-Type': 'application/json'}}).then(function () {
              self.saved = true
            }).catch(function () {
              self.error = true
            })
        }
      }
    },

    computed: {
      getClubName () {
        return this.$store.getters.getClub
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
