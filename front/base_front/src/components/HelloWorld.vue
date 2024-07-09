<template>
  <div>
  testdddddd
  </div>
</template>

<script>
import {getUserList} from '@/api/loginApi'
export default {
  name: 'HelloWorld',
  data() {
    return {
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
    test(){
      getUserList().then(res=>{
        console.log(res)
      })
    }
  }
};
</script>

<style scoped>
/* 组件样式在这里定义 */
</style>