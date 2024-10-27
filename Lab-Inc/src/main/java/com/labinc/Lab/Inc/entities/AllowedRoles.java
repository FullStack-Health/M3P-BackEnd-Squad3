package com.labinc.Lab.Inc.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize(using = AllowedRolesDeserializer.class)
public enum AllowedRoles implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_MEDICO;

    @Override
    public String getAuthority() {
        return toString();
    }
}
