package com.ctlok.microservices.user.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by Lawrence Cheung on 2015/4/10.
 * @author Lawrence Cheung
 */
@Data
@Document
public class User implements UserDetails {

    @Id
    private String id;

    @NotEmpty
    @Indexed( unique = true )
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private Collection<String> roles;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @JsonIgnore
    public String getPassword(){
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if ( roles == null ) {
            return Collections.emptyList();
        } else {
            return roles.stream().map( SimpleGrantedAuthority::new ).collect( Collectors.toSet() );
        }
    }

}
