export function setItem(key, object) {
    try {
      window.localStorage.setItem(key, JSON.stringify(object))
      return true
    } catch (e) {
      console.log(e)
      return false
    }
  }
  
  export function getItem(key) {
    try {
      return JSON.parse(window.localStorage.getItem(key))
    } catch (e) {
      console.log(e)
      return null
    }
  }
  
  export function removeItem(key) {
    try {
      window.localStorage.removeItem(key)
      return true
    } catch (e) {
      console.log(e)
      return null
    }
  }
  