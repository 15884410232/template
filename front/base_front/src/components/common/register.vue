<template>
<div class="box_one">
    <div class="login_box">
        <span class="login_title">注册</span>
        <!-- <div class="divider_my"></div> -->
        <el-form ref="loginForm" :model="userDto" :rules="rules">
            <el-form-item>
                <div>
                    <el-upload class="upload-demo" drag :action="uploadUrl" :http-request="upload" name="myfile" multiple :show-file-list=false>
                        <!-- <el-upload class="upload-demo" drag :action="uploadUrl"  name="myfile" multiple> -->
                        <i class="el-icon-upload" v-if="!avatar"></i>
                        <img :src="avatar" class="upload-demo" v-if="avatar" />
                    </el-upload>
                </div>
            </el-form-item>
            <el-form-item prop="username">
                <el-input v-model="userDto.username" size="medium" autocomplete="on" type="text" placeholder="账户名" class="form_input"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="medium" v-model="userDto.password" autocomplete="on" type="password" placeholder="密码" class="form_input"></el-input>
            </el-form-item>
            <el-form-item prop="phone">
                <el-input size="medium" v-model="userDto.phone" autocomplete="on" type="phone" placeholder="电话" class="form_input"></el-input>
            </el-form-item>
            <el-form-item prop="realName">
                <el-input size="medium" v-model="userDto.realName" autocomplete="on" type="realname" placeholder="姓名" class="form_input"></el-input>
            </el-form-item>
            <el-form-item prop="email">
                <el-input size="medium" v-model="userDto.email" autocomplete="on" type="email" placeholder="邮箱" class="form_input"></el-input>
            </el-form-item>
            <el-form-item prop="roleCode">
                <el-select v-model="userDto.roleCode" placeholder="角色" style="width: 100%; margin-top: 20px">
                    <el-option label="管理员" value="admin"></el-option>
                    <el-option label="普通" value="normal"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item prop="rememberPsd">
                <div class="line-box" style="margin-top: 20px"></div>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" size="medium" class="commit-button" @click="login('loginForm')">注册</el-button>
            </el-form-item>
            <div class="line-box">
                <span class="no_account">已有账号？</span><span class="register" @click="toApply('/')">立即登录</span>
            </div>
        </el-form>
    </div>
</div>
</template>

<script>
export default {
    data() {
        var validatePhone = (rule, value, callback) => {
            var reg_phone = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
            if (value === '') {
                callback(new Error('请输入手机号'));
            } else {
                if (value !== '') {
                    if (!reg_phone.test(value)) {
                        callback(new Error('请输入正确的手机号'));
                    } else {
                        callback()
                    }
                }
                callback(new Error('请输入手机号'));
            }
        };
        return {
            avatar: "",
            radio: "1",
            userDto: {
                realName: "",
                password: "",
                phone: "",
                username: "",
                email: "",
                roleCode: "",
                avatarId: "",
            },
            rules: {
                username: [{
                    required: true,
                    message: "请输入账号",
                    trigger: "change",
                }, ],
                password: [{
                    required: true,
                    message: "请输入密码",
                    trigger: "blur",
                }, ],
                phone: [{
                    validator: validatePhone,
                    trigger: "blur",
                }, ],
                realName: [{
                    required: true,
                    message: "请输入姓名",
                    trigger: "blur",
                }, ],
                email: [{
                    required: true,
                    message: "请输入邮箱",
                    trigger: "blur",
                }, ],
                roleCode: [{
                    required: true,
                    message: "请选择角色",
                    trigger: "change",
                }, ],
                per: ["test", "main"],
            },
            uploadUrl: this.$url + "/pass/file/upload/"
        };
    },
    mounted: function () {},
    methods: {
        upload(fileobj) {
            let param = new FormData()
            param.append('myfile', fileobj.file)
            console.log(param),
                this.$http({
                    method: "post",
                    url: this.uploadUrl, //这里是发送给后台的数据
                    data: param,
                    headers: {
                        "Content-Type": "multipart/form-data"
                    }
                })
                .then((response) => {
                    if (response.data.code = 200) {
                        this.avatar = this.$url + "/pass/file/download?id=" + response.data.data.id;
                        this.userDto.avatarId = response.data.data.id;
                        console.log(this.avatar)
                        this.$message({
                            message: response.data.message,
                            type: 'success'
                        });
                    }
                })
        },
        login(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.$http({
                            method: "post",
                            url: this.$url + "/register/doRegister", //这里是发送给后台的数据
                            params: {
                                realName: this.userDto.realName,
                                password: this.userDto.password,
                                phone: this.userDto.phone,
                                username: this.userDto.username,
                                email: this.userDto.email,
                                roleCode: this.userDto.roleCode,
                                avatarId: this.userDto.avatarId,
                            },
                        })
                        .then((response) => {
                            console.log(response);
                            if (response.data.code == 200) {
                                this.$message({
                                    message: response.data.message,
                                    type: 'success'
                                })
                                setTimeout(() => {
                                    this.$router.push("/");
                                }, 1000);
                            } else {

                            }
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },

        toApply(url) {
            this.$router.push(url);
        },
    },
};
</script>

<style scoped>
.upload-demo>>>.el-upload-dragger {
    border-radius: 50%;
    width: 150px;
    height: 150px;
}

.upload-demo {
    margin: auto;
    width: 150px;
    height: 150px;
}

.el-input>>>.el-input__inner {
    border-radius: 0px;
}

.box_one {
    height: calc(100vh);
    background-image: url(../../assets/pic/login-bg.jpg);
    align-content: center;
}

.login_box {
    width: 360px;
    height: 680px;
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
