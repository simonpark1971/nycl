<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <div v-if="registered" class="alert alert-success" role="alert">
      <h4 class="alert-heading">Success!</h4>
      <p>You have successfully registered</p>
    </div>

    <b-form>
      <h2>NYCL Club Registration {{season}}</h2>

      <div class="form-group">
        <label for="clubName" class="sr-only">Email address</label>
        <input type="text" v-model="clubName" id="clubName" name="clubName" class="form-control" placeholder="Club name">
      </div>

      <h3>Main Club Contact Details</h3>
      <div class="form-group">
        <div class="input-group">
          <label for="clubContactName" class="sr-only row">Name</label>
          <input type="text" v-model="mainContact.contactName" id="clubContactName" name="clubContactName" class="form-control" placeholder="Name">
        </div>

        <p />

        <div class="input-group">
          <label for="clubContactAddress" class="sr-only">Address</label>
          <input type="text" v-model="mainContact.contactAddress" id="clubContactAddress" name="clubContactAddress" class="form-control" placeholder="Address">
        </div>

        <p />

        <div class="input-group">
          <label for="clubContactPostcode" class="sr-only">Postcode</label>
          <input type="text" v-model="mainContact.contactPostcode" id="clubContactPostcode" name="clubContactPostcode" class="form-control" placeholder="Postcode">
        </div>

        <p />

        <div class="input-group">
          <label for="clubContactHomePhone" class="sr-only">Home phone</label>
          <input type="tel" v-model="mainContact.contactHomePhone" id="clubContactHomePhone" name="clubContactHomePhone" class="form-control" placeholder="Phone">
        </div>

        <p />

        <div class="input-group">
          <label for="clubContactEmail" class="sr-only">Email</label>
          <input type="email" v-model="mainContact.contactEmail" id="clubContactEmail" name="clubContactEmail" class="form-control" placeholder="Email">
        </div>
      </div>

      <button v-if="!registered" type="button" style="float: right" v-on:click='saveOnSubmit'>Register</button>
    </b-form>

  </div>
</template>

<script>

  import axios from 'axios'

  export default {

    name: 'registerClub',
    data () {
      return {
        registered: false,
        season: '2018',
        clubName: '',
        mainContact: {
          contactName: '',
          contactAddress: '',
          contactPostcode: '',
          contactHomePhone: '',
          contactEmail: ''
        }
      }
    },

    methods: {
      saveOnSubmit: function (event) {
        var self = this
        if (confirm('Please confirm you wish to register ' + this.clubName + ' for the ' + this.season) === true) {
          axios.post('http://localhost:8081/register',
            {clubName: this.clubName,
              mainContact: this.mainContact},
            { headers: {'Content-Type': 'application/json'} }).then(function () {
              self.registered = true
            }).catch(function (error) {
              console.error(error)
            })
        }
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
