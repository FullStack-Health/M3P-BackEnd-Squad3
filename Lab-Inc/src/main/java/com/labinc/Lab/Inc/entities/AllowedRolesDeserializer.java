package com.labinc.Lab.Inc.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.io.IOException;

public class AllowedRolesDeserializer extends JsonDeserializer<AllowedRoles> {
    private static final Logger logger = LoggerFactory.getLogger(AllowedRolesDeserializer.class);

    @Override
    public AllowedRoles deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String role = p.getText();
        logger.debug("Deserializing role: {}", role);

        if (role == null || role.isEmpty()) {
            logger.error("roleName cannot be empty");
            throw new IOException("roleName cannot be empty");
        }

        try {
            AllowedRoles allowedRole = AllowedRoles.valueOf(role);
            logger.debug("Successfully deserialized role: {}", allowedRole);
            return allowedRole;
        } catch (IllegalArgumentException e) {
            logger.error("Invalid role name: {}", role);
            throw new IOException("Invalid role name: " + role);
        }
    }
}