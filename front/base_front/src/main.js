// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import store from './store/index'

import config from './config/dev'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

Vue.prototype.$env = config;
Vue.prototype.$store = store;

Vue.config.productionTip = false


Vue.directive('has', {
  inserted: function (el, binding, vnode) {
    if (!permissionJudge(binding.value)) {
      el.parentNode.removeChild(el);
    }

    function permissionJudge(value) {
      // alert(store.getters.permissions)
      for (let item of store.getters.permissions) {
        if (item.permissionCode === value) {
          return true;
        }
      }
      return false;
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
