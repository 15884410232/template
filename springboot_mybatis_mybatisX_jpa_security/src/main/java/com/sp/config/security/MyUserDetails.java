package com.sp.config.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.sp.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails  implements UserDetails {

    /**
     * 当前登录用户
     */
    public User user;

    /**
     * 因为SimpleGrantedAuthority通过fastjson序列化时会报错，所以使用List<String>
     */
    @Setter
    @Getter
    private List<String> permissions;

    /**
     * 权限列表
     * 因为SimpleGrantedAuthority通过fastjson序列化时会报错，所以这里不序列化
     */
    @JSONField(serialize=false)
    List<SimpleGrantedAuthority> authorities;

    // 必须有默认构造函数
    public MyUserDetails() {
    }

    public MyUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }

        // 权限列表
        if(permissions!=null){
            authorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
