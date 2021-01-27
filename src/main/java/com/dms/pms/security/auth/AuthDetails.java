package com.dms.pms.security.auth;

import com.dms.pms.entity.pms.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class AuthDetails implements UserDetails, OAuth2User {

    AuthDetails(User user, RoleType roleType) {
        this.user = user;
        authorities.add(new SimpleGrantedAuthority(roleType.toString()));
    }

    public AuthDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.authorities.add(new SimpleGrantedAuthority(RoleType.USER.toString()));     // 소셜 유저는 어드민 권한을 가질 수 없음.
        this.attributes = attributes;
    }

    private User user;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    private Map<String, Object> attributes = new HashMap<>();

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    @Override
    public String getName() {
        return user.getName();
    }
}
