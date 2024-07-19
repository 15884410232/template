<template>
  <div>
    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
  <el-radio-button :label="false">展开</el-radio-button>
  <el-radio-button :label="true">收起</el-radio-button>
</el-radio-group>
<el-menu mode="vertical" default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse"       background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
  <el-submenu index="1">
    <template slot="title">
      <i class="el-icon-location"></i>
      <span slot="title">导航一</span>
    </template>
      <el-menu-item index="1-1">选项1</el-menu-item>
      <el-menu-item index="1-2">选项2</el-menu-item>
      <el-menu-item index="1-1">选项1</el-menu-item>
      <el-menu-item index="1-2">选项2</el-menu-item>
  </el-submenu>
  <el-submenu index="2">
    <template slot="title">
      <i class="el-icon-location"></i>
      <span slot="title">导航二</span>
    </template>
      <el-menu-item index="2-1">选项1</el-menu-item>
      <el-menu-item index="2-2">选项2</el-menu-item>
      <el-menu-item index="2-1">选项1</el-menu-item>
      <el-menu-item index="2-2">选项2</el-menu-item>
  </el-submenu>
</el-menu>
  </div>
</template>

<script>
import {getUserList} from '@/api/loginApi'
export default {
  name: 'HelloWorld',
  data() {
    return {
      isCollapse: true
      // 数据属性在这里定义
    };
  },
  created() {
    // 实例创建后，挂载前调用。
    // 适合初始化状态或获取数据。
  },
  mounted() {
    // 实例挂载完成后调用。
    // 适合访问DOM或调用API。
    this.test();
  },
  methods:{
    handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
    test(){
      let user={
        username:'admin'
      }
      
      getUserList(user).then(res=>{
        console.log(res)
      })
    }
  }
};
</script>

<style scoped>
/* 组件样式在这里定义 */
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
</style>