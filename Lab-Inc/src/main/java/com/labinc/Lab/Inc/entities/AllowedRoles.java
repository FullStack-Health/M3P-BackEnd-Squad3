package com.labinc.Lab.Inc.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AllowedRolesDeserializer.class)
public enum AllowedRoles {
    ROLE_ADMIN,
    ROLE_MEDICO
}
