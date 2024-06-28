// 导入Vue和Vuex库
import Vue from 'vue'
import Vuex from 'vuex'

// 导入全局getter函数
import getters from './getters'

// 导入vuex状态持久化插件及加密存储库
import createPersistedState from 'vuex-persistedstate'
import SecureLS from "secure-ls"

// 从localStorage工具模块中导入getItem函数
import {
  getItem
} from '@/util/localStorage'

// 应用Vuex到Vue
Vue.use(Vuex)

// 动态加载模块：遍历./modules目录下的所有.js文件
const modulesFiles = require.context('./modules', true, /\.js$/)

// 自动注册模块：遍历模块文件路径，构建模块对象
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // 提取模块名称（去掉路径和扩展名）
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  // 获取模块导出的默认值
  const value = modulesFiles(modulePath)
  // 将模块添加到modules对象中
  modules[moduleName] = value.default
  return modules
}, {})

// 初始化加密存储实例变量
let ll;
let key;

// 获取或初始化加密的LocalStorage实例
function getLs() {
  // 获取公钥
  const pk = getItem('pk')
  
  // 如果公钥存在且与当前key一致，则直接返回已存在的加密存储实例
  if (!!pk && pk.publicKey === key) {
    return ll;
  }
  
  // 更新加密密钥为公钥（如果存在）或默认密钥
  key = !!pk ? pk.publicKey : '_vuexkey';

  // 根据更新后的密钥创建新的SecureLS实例
  ll = new SecureLS({
    encodingType: "aes",       // 使用AES加密
    isCompression: true,      // 启用数据压缩
    encryptionSecret: key     // 设置加密密钥
  });

  // 返回新创建或已存在的加密存储实例
  return ll;
}

// 创建默认的不依赖公钥的SecureLS实例，使用默认密钥
const ls = new SecureLS({
  encodingType: "aes",
  isCompression: true,
  encryptionSecret: '_vuexkey'
});

// 持久化存储的键名
const vuexKey = '_vuex'

// 创建Vuex Store实例
const store = new Vuex.Store({
  // 注册动态加载的模块
  modules,
  // 添加全局getter
  getters,
  // 应用插件以实现状态持久化，使用自定义的加密存储
  plugins: [
    createPersistedState({
      // 持久化存储的键
      key: vuexKey,
      // 自定义存储接口，使用加密的LocalStorage
      storage: {
        getItem: key => getLs().get(key),    // 获取加密数据
        setItem: (key, value) => getLs().set(key, value), // 存储加密数据
        removeItem: key => getLs().remove(key) // 删除加密数据
      },
    })
  ]
})

// 导出Vuex Store实例供应用使用
export default store