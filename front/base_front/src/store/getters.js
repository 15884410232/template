const getters={
    token:state=>state.user.token,
    permissions:state=>state.user.permissions,
    userInfo:state=>state.user.userInfo,
    userId:state=>state.user.userId,
    roles:state=>state.user.roles,
    currentRole:state=>state.user.currentRole,
}
export default getters