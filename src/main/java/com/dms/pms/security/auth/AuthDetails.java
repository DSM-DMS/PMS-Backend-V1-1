package com.dms.pms.security.auth;

import com.dms.pms.entity.pms.user.Parent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class AuthDetails implements UserDetails, OAuth2User {

    AuthDetails(Parent parent, RoleType roleType) {
        this.parent = parent;
        authorities.add(new SimpleGrantedAuthority(roleType.toString()));
    }

    public AuthDetails(Parent parent, Map<String, Object> attributes) {
        this.parent = parent;
        this.authorities.add(new SimpleGrantedAuthority(RoleType.USER.toString()));     // 소셜 유저는 어드민 권한을 가질 수 없음.
        this.attributes = attributes;
    }

    private Parent parent;
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
        return parent.getPassword();
    }

    @Override
    public String getUsername() {
        return parent.getEmail();
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
        return parent.getName();
    }
}
