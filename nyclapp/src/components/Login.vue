<template>
  <div class="container">

    <img src="../assets/nyclogo4.jpg" class="img-circle" />

    <div><ring-loader :loading="loading"></ring-loader></div>

    <form>
      <div class="form-group">
        <label for="exampleInputEmail1">Username</label>
        <input type="email" v-model="username" class="form-control" id="exampleInputEmail1" name="email" placeholder="Email">
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" v-model="password" class="form-control" id="exampleInputPassword1" name="password" placeholder="Password">
      </div>
      <div class="alert alert-danger" role="alert" v-if="loginErrorMessageShown">Login Failed</div>
      <b-btn variant="outline-success" type="button" v-on:click='doLogin' id="login" name="login" class="btn btn-default">Submit</b-btn>
    </form>
  </div>
</template>

<script>

  export default {
    name: 'loginForm',
    data () {
      return {
        loading: false,
        color: 'red',
        size: '15px',
        radius: '100%',
        username: '',
        password: '',
        loginErrorMessageShown: false
      }
    },

    methods: {
      doLogin () {
        var self = this
        this.loading = true
        this.$store.dispatch('login', {
          username: this.username,
          password: this.password
        }).then(function () {
          self.loginErrorMessageShown = false
          self.$router.push('/')
        }).catch(function () {
          self.loginErrorMessageShown = true
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
