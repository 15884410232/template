<template>
  <div>
    <el-container>
      <el-aside width="200px" direction="vertical">
        <div class="logo">sad</div>
        <el-menu
          mode="vertical"
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          :collapse="isCollapse"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <template v-for="(item, index) in menus[0].children">
            <el-submenu :index="item.url" v-if="item.children && item.children.length" :key="index">
              <template slot="title">
                <i :class="item.icon"></i>
                <span slot="title">{{ item.name }}</span>
              </template>
              <el-menu-item
                v-for="child in item.children"
                :index="child.url"
                :key="child.id"
              >{{ child.name }}</el-menu-item>
            </el-submenu>
            <el-menu-item v-else :index="item.url" :key="item.id">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.name }}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>Header</el-header>
        <el-main>Main</el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getMenu } from "@/api/accountApi";

export default {
  name: "home",
  data() {
    return {
      // 数据属性在这里定义
      menus: []
    };
  },
  created() {
    // 实例创建后，挂载前调用。
    // 适合初始化状态或获取数据。
  },
  mounted() {
    // 实例挂载完成后调用。
    // 适合访问DOM或调用API
    this.getMenus();
  },
  methods: {
    getMenus() {
      getMenu().then(res => {
        console.log(res);
        this.menus = res.data;
      });
    }
  }
};
</script>

<style scoped>
/* 组件样式在这里定义 */
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  height: 100vh;
  /* line-height: 200px; */
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  /* line-height: 160px; */
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.logo {
  height: 56px;
  background: red;
}
</style>