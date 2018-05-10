<template>
    <el-col :span="6" :offset="9" style="margin-top: 60px">
      <el-form ref="login" :model="login" label-width="60px">
        <el-form-item label="用户名">
          <el-input v-model="login.username"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="login.password"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="Login">登陆</el-button>
          <el-button type="primary" @click="Register" plain>注册</el-button>
        </el-form-item>
      </el-form>
    </el-col>
</template>

<script>
    export default {
        name: "",
        data() {
          return {
            login: {
              username : 'admin',
              password : '123456'
            }
          }
        },
        methods: {
          Login: function () {
            // let params = new URLSearchParams()
            // params.append('username', this.login.username)
            // params.append('password', this.login.password)
            this.$http.get('/login', {
              params : this.login
            }).then(res => {
              alert(res.data.msg)
            })
          },

          Register: function () {
            this.$http.get('/register', {
              params : this.login
            }).then(res => {
              alert(res.data.msg)
            })
          }
        }
    }

</script>

<style scoped>

</style>
