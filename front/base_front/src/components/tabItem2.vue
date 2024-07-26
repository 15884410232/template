<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleTabClick" @tab-remove="handleTabRemove">
      <el-tab-pane
        v-for="(tab, index) in tabs"
        :key="index"
        :label="tab.title"
        :name="tab.name"
        closable>
        <!-- 使用 <router-view> 渲染当前活动的标签页内容 -->
        <router-view v-if="tab.name === activeName"></router-view>
      </el-tab-pane>
      <el-tab-pane label="新增窗口" name="newTab">
        <button @click="addNewTab">新增窗口</button>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  props: {
    tabName: "",
  },
  data() {
    return {
      activeName: 'first', // 默认激活的标签页名称
      tabs: [
        { title: '窗口1', name: 'window1', path: '/window1' },
        // ... 其他标签页
      ],
    };
  },
  methods: {
    handleTabClick(tab) {
      // 导航到点击的标签页对应的路由
      this.$router.push({ name: tab.name });
    },
    addNewTab() {
      // 假设我们有一个新的窗口（或标签页）的路由信息
      const newTab = { title: '新窗口', name: tabName, path: '/newWindow' + (this.tabs.length + 1) };
      // 添加到标签页列表中
      this.tabs.push(newTab);
      // 激活并导航到新的标签页
      this.activeName = newTab.name;
      this.$router.push({ name: newTab.name });
    },
    handleTabRemove(tabName) {
      // 找到要删除的标签页
      const tabIndex = this.tabs.findIndex(tab => tab.name === tabName);
      if (tabIndex !== -1) {
        // 移除标签页
        this.tabs.splice(tabIndex, 1);
        // 如果删除的是当前活动的标签页，则导航到下一个或上一个标签页
        if (this.activeName === tabName) {
          // 如果有下一个标签页，则激活下一个
          if (tabIndex < this.tabs.length) {
            this.activeName = this.tabs[tabIndex].name;
          } else if (tabIndex > 0) {
            // 否则激活上一个
            this.activeName = this.tabs[tabIndex - 1].name;
          } else {
            // 如果没有其他标签页，可以设置一个默认标签页或进行其他操作
            this.activeName = 'first';
          }
          this.$router.push({ name: this.activeName });
        }
      }
    },
  },
  watch: {
    // 监听路由变化，更新当前活动的标签页
    '$route.name'(newName) {
      // 如果新的路由名称不在标签页列表中，则可能需要添加一个新的标签页（这里省略添加逻辑）
      this.activeName = newName;
    },
  },
  created() {
    // 在组件创建时，根据路由来设置默认激活的标签页
    this.activeName = this.$route.name;
  },
};
</script>