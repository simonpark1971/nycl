<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

      <h2>Registered Clubs {{season}}</h2>

    <table class="table table-bordered table-striped">
      <thead class="thead">
      <tr>
        <th>Club Name</th>
        <th>Main Contact</th>
        <th>Main Contact Email</th>
        <th>Status</th>
        <th>Fee</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in clubs">
        <td>{{item.clubName}}</td>
        <td>{{item.mainContact.contactName}}</td>
        <td>{{item.mainContact.contactEmail}}</td>
        <td>{{item.applicationStatus}}</td>
        <td>£{{getRegistrationFee(item) + getCupFee(item)}}</td></tr>
      </tbody>
    </table>

    <button type="button" class="btn btn-primary" v-on:click="download()">Download Club Details</button>
    <button type="button" class="btn btn-primary" v-on:click="downloadContacts()">Download Club Contacts</button>

  </div>
</template>

<script>
  import axios from 'axios'
  export default {

    name: 'ViewClubs',
    data () {
      return {
        season: '2018',
        clubs: ''
      }
    },

    mounted: function () {
      var self = this
      axios.get('http://localhost:8081/getclubs').then(function (response) {
        self.clubs = response.data
      })
    },

    methods: {
      getRegistrationFee (club) {
        return club.clubTeams.length * 15
      },
      getCupFee (club) {
        var fee = 0
        club.enterU11Cup ? fee += 5 : fee += 0
        club.enterU12Cup ? fee += 5 : fee += 0
        club.enterU14Cup ? fee += 5 : fee += 0
        return fee
      },
      download () {
        axios.get('http://localhost:8081/download/clubs', {responseType: 'arraybuffer'}).then(function (response) {
          let blob = new Blob([response.data], { type: 'text/csv' })
          let url = window.URL.createObjectURL(blob)
          window.open(url)
        })
      },
      downloadContacts () {
        axios.get('http://localhost:8081/download/contacts', {responseType: 'arraybuffer'}).then(function (response) {
          let blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
          let url = window.URL.createObjectURL(blob)
          window.open(url)
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
