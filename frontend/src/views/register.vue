<template>
  <div class="register">
<!--    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">-->
<!--      <h3 class="title">若依后台管理系统</h3>-->
<!--      <el-form-item prop="username">-->
<!--        <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号">-->
<!--          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />-->
<!--        </el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item prop="password">-->
<!--        <el-input-->
<!--          v-model="registerForm.password"-->
<!--          type="password"-->
<!--          auto-complete="off"-->
<!--          placeholder="密码"-->
<!--          @keyup.enter.native="handleRegister"-->
<!--        >-->
<!--          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />-->
<!--        </el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item prop="confirmPassword">-->
<!--        <el-input-->
<!--          v-model="registerForm.confirmPassword"-->
<!--          type="password"-->
<!--          auto-complete="off"-->
<!--          placeholder="确认密码"-->
<!--          @keyup.enter.native="handleRegister"-->
<!--        >-->
<!--          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />-->
<!--        </el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item prop="code" v-if="captchaEnabled">-->
<!--        <el-input-->
<!--          v-model="registerForm.code"-->
<!--          auto-complete="off"-->
<!--          placeholder="验证码"-->
<!--          style="width: 63%"-->
<!--          @keyup.enter.native="handleRegister"-->
<!--        >-->
<!--          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />-->
<!--        </el-input>-->
<!--        <div class="register-code">-->
<!--          <img :src="codeUrl" @click="getCode" class="register-code-img"/>-->
<!--        </div>-->
<!--      </el-form-item>-->
<!--      <el-form-item style="width:100%;">-->
<!--        <el-button-->
<!--          :loading="loading"-->
<!--          size="medium"-->
<!--          type="primary"-->
<!--          style="width:100%;"-->
<!--          @click.native.prevent="handleRegister"-->
<!--        >-->
<!--          <span v-if="!loading">注 册</span>-->
<!--          <span v-else>注 册 中...</span>-->
<!--        </el-button>-->
<!--        <div style="float: right;">-->
<!--          <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>-->
<!--        </div>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
<!--    &lt;!&ndash;  底部  &ndash;&gt;-->
<!--    <div class="el-register-footer">-->
<!--      <span>Copyright © 2018-2023 ruoyi.vip All Rights Reserved.</span>-->
<!--    </div>-->
    <div class="content">
      <div class="login-wrapper">
        <div class="left-img">
          <div class="glass">
            <div class="tips">
              <div class="title">
                THE SPACE NETWORK
              </div>
              <h1>Explore The Universe</h1>
              <span>5 Million+ people have joined our network.</span>
              <span>we invite you to join the tribe</span>
            </div>
          </div>
        </div>
        <div class="right-login-form">
          <div class="form-wrapper">
            <h1>Sign up</h1>
            <div class="input-items">
                        <span class="input-tips">
                            Email Address
                        </span>
              <input type="text" class="inputs" placeholder="Enter your email">
            </div>
            <div class="input-items">
                        <span class="input-tips">
                            Password
                        </span>
              <input type="password" class="inputs" placeholder="Enter password">
              <span class="forgot">Forgot Password</span>
            </div>
            <button class="btn">Sign up</button>
            <div class="siginup-tips">
              <span>Already Have An Account?</span>
              <router-link class="link-type" :to="'/login'">Login</router-link>
            </div>
            <div class="other-login">
              <div class="divider">
                <span class="line"></span>
                <span class="divider-text">or</span>
                <span class="line"></span>
              </div>
              <div class="other-login-wrapper">
                <div class="other-login-item">
                  <img src="../assets/images/QQ.png" alt="QQ">
                </div>
                <div class="other-login-item">
                  <img src="../assets/images/WeChat.png" alt="WeChat">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg, register } from "@/api/login";

export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      codeUrl: "",
      registerForm: {
        username: "",
        password: "",
        confirmPassword: "",
        code: "",
        uuid: ""
      },
      registerRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
          { min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, trigger: "blur", message: "请再次输入您的密码" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      captchaEnabled: true
    };
  },
  created() {
    this.getCode();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.registerForm.uuid = res.uuid;
        }
      });
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true;
          register(this.registerForm).then(res => {
            const username = this.registerForm.username;
            this.$alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            }).catch(() => {});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          })
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
* {
  padding: 0;
  margin: 0;
  font-family: 'Aguazyuan', Courier, monospace;
}

.content {
  width: 100vw;
  height: 100vh;
  background-color: rgb(186, 175, 221);
  position: relative;
  .login-wrapper {
    width: 70vw;
    height: 80vh;
    background-color: #fff;
    border-radius: 40px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    .left-img {
      border-radius: 40px;
      flex: 1;
      background: url(../assets/images/bg.jpg) no-repeat;
      background-size: cover;
      position: relative;
      .glass {
        width: 60%;
        padding: 20px;
        color: #fff;
        position: absolute;
        top: 20%;
        left: 50%;
        transform: translate(-50%, -20%);
        background: rgba(255, 255, 255, 0.1);
        box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
        backdrop-filter: blur(10px);
        -webkit-backdrop-filter: blur(10px);
        border-radius: 15px;
        border: 1px solid rgba(255, 255, 255, 0.48);
        .tips {
          .title {
            width: 25%;
            font-weight: 600;
            font-size: 15px;
            text-align: center;
            padding: 10px;
            overflow: hidden;
            background: rgba(255, 255, 255, 0.1);
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            border-radius: 15px;
            border: 1px solid rgba(255, 255, 255, 0.48);
          }
          h1 {
            margin: 15px 0;
          }
          span {
            margin: 5px 0;
            display: block;
            font-weight: 100;
          }
        }
      }
    }
    .right-login-form {
      flex: 1;
      position: relative;
      .form-wrapper {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        h1 {
          font-size: 30px;
          text-align: center;
        }
        .input-items {
          margin: 20px 0;
          .input-tips {
            display: inline-block;
            font-weight: 600;
            font-size: 20px;
            margin: 10px 0;
          }
          .inputs {
            width: 100%;
            height: 50px;
            border-radius: 10px;
            border: 1px solid rgb(227, 227, 227);
            padding: 20px;
            box-sizing: border-box;
            outline: none;
            transition: .5s;
            &:focus {
              border: 1px solid rgb(128, 106, 196);
            }
          }
          .forgot {
            float: right;
            font-weight: 600;
            text-align: right;
            margin: 5px 0;
            font-size: 13px;
            cursor: pointer;
          }
        }
        .btn {
          width: 100%;
          height: 50px;
          background-color: rgb(128, 106, 196);
          border: 0;
          border-radius: 10px;
          color: #fff;
          font-size: 25px;
          margin: 20px 0;
          cursor: pointer;
        }
        .siginup-tips {
          text-align: center;
          font-weight: 600;
          margin: 10px 0;
          span:last-child {
            color: rgb(110, 87, 179);
            cursor: pointer;
          }
        }
        .other-login {
          .divider {
            width: 100%;
            margin: 20px 0;
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: space-between;
            .line {
              display: inline-block;
              max-width: 35%;
              width: 35%;
              flex: 1;
              height: 1px;
              background-color: rgb(162, 172, 185);
            }
            .divider-text {
              vertical-align: middle;
              margin: 0 20px;
              display: inline-block;
              width: 50px;
              color: rgb(162, 172, 185);
              white-space: normal;
            }
          }
          .other-login-wrapper {
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            .other-login-item {
              width: 70px;
              padding: 10px;
              text-align: center;
              border-radius: 10px;
              cursor: pointer;
              font-weight: 600;
              color: rgb(51, 49, 116);
              margin: 0 10px;
              transition: .4s;
              img {
                width: 40px;
                height: 40px;
                vertical-align: middle;
              }
              span {
                vertical-align: middle;
              }
              &:hover {
                width: 80px;
                height: 50%;
                background-color: #fff;
                border: 0;
                box-shadow: 1px 10px 32px 1px rgba(186, 175, 221, 0.37);
              }
            }
          }
        }
      }
    }
  }
}
@media (max-width: 1024px) {
  .login-wrapper {
    padding: 20px;
    .left-img {
      display: none;
    }
    .right-login-form {
      padding: 20px;
    }
  }
}
</style>
