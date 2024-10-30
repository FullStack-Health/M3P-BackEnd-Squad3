package com.labinc.Lab.Inc.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize(using = AllowedRolesDeserializer.class)
public enum AllowedRoles implements GrantedAuthority {
    SCOPE_ADMIN,
    SCOPE_MEDICO,
    SCOPE_PACIENTE;

    @Override
    public String getAuthority() {
        return toString();
    }
}
