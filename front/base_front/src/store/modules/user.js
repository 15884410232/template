const state={
    token:'',
    userInfo:{},
    userId:'',
    permissions:[],
    roles:[],
    currentRole:''
}
/**
 * 同步操作： Mutations 必须是同步函数，用于直接修改 Vuex 的 state。
 * 状态变更记录： 因为是同步的，Vuex 可以很容易地追踪状态的变化，这对于调试和理解状态变更流程很有帮助。
 * 触发方式： Mutations 通过 store.commit 方法被触发，例如：this.$store.commit('increment')。
 */
const mutations={
    setToken(state,token){
        state.token=token
    },
    setUserInfo(state,userInfo){
        state.userInfo=userInfo
    },
    setUserId(state,userId){
        state.userId=userId
    },
    setPermissions(state,permissions){
        state.permissions=permissions
    },
    setRoles(state,roles){
        state.roles=roles
    },
    setCurrentRole(state,currentRole){
        state.currentRole=currentRole
    }
}
/**
 * 异步支持： Actions 支持异步操作，可以处理 Promise 或者使用 async/await 来调用 API、延迟更新等。
 * 间接修改状态： Actions 不能直接改变状态，而是通过触发 mutations 来间接更新 state。这样使得状态变更的行为更容易被追踪和测试。
 * 分发方式： Actions 通过 store.dispatch 方法被触发，例如：this.$store.dispatch('fetchData')。
 * 携带参数与返回值： Actions 可以接收参数，也可以返回 Promise，这使得处理异步逻辑更加灵活。
 */
const actions={

}

export default{
    state,
    mutations
}