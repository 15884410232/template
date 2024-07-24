<template>
  <el-tab-pane :label="label" :name="name" @close="handleClose">
    <router-view v-if="active"></router-view>
  </el-tab-pane>
</template>
  
  <script>
export default {
  props: {
    name: "",
    label: ""
  },
  data() {
    return {
      active: false
    };
  },
  methods: {
    handleClose() {
      this.$emit("close", this.name);
    }
  },
  watch: {
    $route: {
      immediate: true,
      handler(to, from) {
        this.active = to.name === this.name;
      }
    }
  }
};
</script>