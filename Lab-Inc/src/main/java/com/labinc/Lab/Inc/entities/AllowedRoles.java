package com.labinc.Lab.Inc.entities;

import org.springframework.security.core.GrantedAuthority;

public enum AllowedRoles implements GrantedAuthority {
    ADMIN, DOCTOR, PATIENT;

    @Override
    public String getAuthority() {
        return toString();
    }
}
