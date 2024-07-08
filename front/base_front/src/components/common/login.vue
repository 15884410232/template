<template>
  <div class="box_one">
    <div class="login_box">
      <span class="login_title">登录{{$store.state.name}}</span>
      <div class="divider_my"></div>
      <el-form ref="loginForm" :model="userDto" :rules="rules">
        <el-form-item prop="username">
          <el-input
            size="medium"
            v-model="userDto.username"
            autocomplete="on"
            type="text"
            placeholder="账户名"
            class="form_input"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            size="medium"
            v-model="userDto.password"
            autocomplete="on"
            type="password"
            placeholder="密码"
            class="form_input"
          ></el-input>
        </el-form-item>
        <el-form-item prop="captcha">
          <el-input
            id="captcha"
            size="medium"
            class="code_input"
            v-model="userDto.captcha"
            autocomplete="on"
            type="text"
            placeholder="验证码"
          ></el-input>
          <img class="code_img" :src="code_src" @click="refreshCode" />
        </el-form-item>

        <el-form-item prop="rememberPsd">
          <div class="line-box" style="margin-top: 20px"></div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="medium"
            class="commit-button"
            @click="login('loginForm')"
          >登录</el-button>
        </el-form-item>
        <div class="line-box">
          <span class="no_account">还没有账号？</span>
          <span class="register" @click="toRegister()">立即注册</span>
        </div>
      </el-form>
    </div>
    <el-dialog :visible.sync="selectRoleBoxShow" :show-close="false">
      <div class="select_role_main">
        <div class="select_role_title">角色选择</div>
        <div class="role_list">
          <div
            class="select_role_btn"
            v-for="item in this.$store.state.roles"
            :key="item.id"
            @click="findMenuByRoleId(item)"
          >{{item.roleName}}</div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
  
  <script>
import { mapMutations } from "vuex";
import { doLogin,captcha } from "@/api/loginApi";
export default {
  data() {
    return {
      //选择角色弹窗是否显示
      selectRoleBoxShow: false,
      // name:this.$store.state.name,
      code_src: "",
      radio: "1",
      userDto: {
        username: "",
        password: "",
        captcha: ""
      },
      rules: {
        username: [
          {
            required: true,
            message: "请输入账号",
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur"
          }
        ],
        per: ["test", "main"]
      }
    };
  },

  mounted: function() {},
  methods: {
    ...mapMutations([
      "setLoginUser",
      "setPermissions",
      "setRoles",
      "setMenus",
      "setCurrentRole",
      "setUnreadNoticeCount"
    ]),
    findMenuByRoleId(checkRole) {
      this.$http({
        method: "post",
        url: this.$url + "/sys/menu/back/get", //这里是发送给后台的数据
        params: {
          roleId: checkRole.id
        }
      }).then(response => {
        var res = response.data;
        this.setCurrentRole(checkRole);
        this.setMenus(res.data.menus);
        this.setPermissions(res.data.btns);
        sessionStorage.setItem("store", JSON.stringify(this.$store.state));
        this.$router.push(res.data.menus[0].children[0].url);
      });
    },


    refreshCode() {
      // var timestamp = new Date().getTime();
      // this.code_src = this.$url + "/pass/captcha" + "?timestamp=" + timestamp;
      // this.fetchCaptcha();

      
      captcha().then((res) => {
              console.log(res.data.captchaImage)
              const imageUrl = URL.createObjectURL(new Blob([res.data.captchaImage], { type: 'image/png' }));
              console.log(imageUrl)
              document.getElementById('captcha').src = imageUrl;
              this.code_src = imageUrl
              console.log(res.data.captchaImage)
          }).catch(()=>{

          }).finally(() => {

          })
    },
    login(formName) {
      this.refreshCode();
      this.$refs[formName].validate(valid => {
        if (valid) {

          doLogin(this.userDto).then((res) => {
              // console.log(res)
          }).catch(()=>{

          }).finally(() => {

          })
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    toRegister() {
      this.$router.push("/register");
    }
  }
};
</script>
  
  <style scoped>
.role_list {
  margin-top: 42px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.select_role_btn {
  width: 160px;
  display: inline-block;
  font-size: 18px;
  padding: 15px 18px;
  color: #409eff;
  background: #ecf5ff;
  border: 1px #b3d8ff solid;
  border-radius: 5px;
}

.select_role_btn:hover {
  background: #88baec;
  color: white;
  cursor: pointer;
}

.select_role_main {
  text-align: center;
  font-size: 24px;
  font-weight: 500;
  margin-top: 160px;
}

.select_role {
  width: 12px;
  height: 40px;
}

.el-dialog__wrapper >>> .el-dialog__header {
  padding: 0 !important;
}

/*对话框标题背景*/
.el-dialog__wrapper >>> .el-dialog__header {
  padding: 20px 20px 10px;
  background: #fdfdfd !important;
}

.el-dialog__wrapper >>> .el-dialog {
  width: 600px;
  height: 415px;
  background: url("../../assets/pic/select_role_bg.png");
}

.el-input >>> .el-input__inner {
  border-radius: 0px;
}

.code_input {
  color: red;
  margin-top: 20px;
  width: 200px;
  display: inline-block;
}

.code_img:hover {
  cursor: pointer;
}

.code_img {
  margin-top: 20px;
  margin-left: 20px;
  width: 100px;
  height: 40px;
  display: inline-block;
  position: absolute;
  border: 0px;
}

.box_one {
  height: calc(100vh);
  background-image: url(../../assets/pic/login-bg.jpg);
  align-content: center;
}

.login_box {
  width: 360px;
  height: 380px;
  box-shadow: 0px 5px 25px 0px rgb(213 192 169);
  background-color: rgba(255, 255, 255, 0.959);
  margin: auto;
  padding: 10px 20px;
  text-align: left;
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  right: 0;
}

.login_title {
  display: block;
  line-height: 60px;
  font-size: 22px;
  color: #367fa9 !important;
  font-weight: bold;
  text-align: center;
}

.divider_my {
  height: 2px;
  border-bottom: 2px solid rgba(0, 0, 0, 0.26);
}

.form_input {
  margin: auto;
  margin-top: 12px;
}

.el-form-item {
  margin-bottom: 0px;
}

.commit-button {
  width: 100%;
  background: #367fa9 !important;
}

.line-box {
  width: 100%;
  line-height: 30px;
}

.remenberme {
  float: left;
}

.forget_password {
  float: right;
  color: rgb(0, 174, 255);
}

.forget_password:hover {
  cursor: pointer;
}

.register {
  color: rgb(0, 174, 255);
  float: left;
  font-size: 14px;
}

.register:hover {
  cursor: pointer;
}

.no_account {
  float: left;
  font-size: 14px;
}
</style>
  