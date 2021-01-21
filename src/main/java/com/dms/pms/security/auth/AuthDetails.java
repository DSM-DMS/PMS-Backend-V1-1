package com.dms.pms.security.auth;

import com.dms.pms.entity.pms.user.Parent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthDetails implements UserDetails {

    AuthDetails(Parent parent, RoleType roleType) {
        this.parent = parent;
        authorities.add(new SimpleGrantedAuthority(roleType.toString()));
    }

    private Parent parent;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

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
}
