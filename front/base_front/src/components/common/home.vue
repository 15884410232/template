<template>
  <div>
    <el-container>
      <!-- <el-aside class="aside" direction="vertical"> -->

      <div class="aside">
        <div class="logo">FRONT</div>

        <el-menu
          mode="vertical"
          default-active="2"
          class="el-menu-vertical-demo"
          :collapse="isCollapse"
          background-color="#001529"
          text-color="#fff"
          active-text-color="#ffd04b"
          router
          @select="changeMenu"
        >
          <template v-for="(item, index) in menus">
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
            <el-menu-item v-else :index="item.url" :key="item.name">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.name }}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </div>
      <!-- </el-aside> -->
      <el-container>
        <el-header>
          <div class="collapse-btn mouse-hover" @click="changeCollapse">
            <i class="el-icon-s-fold" style="color:white"></i>
          </div>
        </el-header>
        <el-main>
          <div class="el-main_box">
            <tabs ref="tabs"></tabs>

            <!-- <router-view></router-view> -->
          </div>
        </el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getMenu } from "@/api/accountApi";

import Tabs from "../compo/tabs.vue";

export default {
  components: {
    Tabs
  },
  name: "home",
  data() {
    return {
      isCollapse: false,
      // 数据属性在这里定义
      menus: [],
      tabName:"",
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
        this.menus = res.data[0].children;

      });
    },
    changeCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    changeMenu(index,indexPath){

      this.getName(index,this.menus);
      //  this.tabName=indexPath
       this.$refs.tabs.addNewTab(index,this.tabName);
    },
    getName(index,menus) {
      for(let item of menus){
        if(item.url==index){
          this.tabName=item.name
          break;
        }else if(item.children){
          this.getName(index,item.children);
        }
      }
    }

    
  }
};
</script>

<style scoped>
.el-menu--collapse {
  /* transition: width 2.5s; */
  /* width: 100% !important; */
}
.aside {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 60px);
}
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
  height: 60px;
  background: rgb(11 64 111);
  line-height: 60px;
  box-shadow: 0px 5px 25px 0px rgb(150, 148, 148);
  color: white;
  min-width: 64px;
}

.collapse-btn {
  width: 40px;
}

.mouse-hover {
  cursor: pointer;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 100%;
}

.el-header {
  background-color: #3b6984;
  color: #333;
  text-align: center;
  height: calc(10vh);
}

.el-header {
  padding: 0px;
  /* box-shadow: 0px 5px 25px 0px rgb(150, 148, 148); */
  background: #1f597ad7 !important;
}

.el-aside {
  background-color: #ffffff;
  color: #333;
  text-align: center;
  min-height: calc(100vh - 60px);
  width: 200px !important;
  box-shadow: 0px 5px 25px 0px rgb(124, 123, 123);
}

.el-main {
  color: #333;
  text-align: center;
  padding: 10px !important;
  padding: 10px;
  height: calc(100vh - 120px);
}

.el-main_box {
  background: white;
  height: 100%;
}

.el-menu-vertical-demo {
  text-align: left;
  min-height: 100%;
}
</style>