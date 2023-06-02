package ceos.backend.global.config.user;

import ceos.backend.domain.admin.domain.Admin;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AdminDetails implements UserDetails {

    private Admin admin;

    public AdminDetails(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        //collection.add((GrantedAuthority) () -> String.valueOf(admin.getRole()));
        collection.add((GrantedAuthority) () -> String.valueOf(admin.getRole()));

        return collection;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
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
