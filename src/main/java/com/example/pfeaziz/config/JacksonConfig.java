package com.example.pfeaziz.config;

import com.example.pfeaziz.model.User_Role;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        // Register a custom deserializer for GrantedAuthority
        module.addDeserializer(GrantedAuthority.class, new GrantedAuthorityDeserializer());

        // Register JavaTimeModule to handle Java 8 date/time types (LocalDateTime, etc.)
        objectMapper.registerModule(module);
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    /**
     * Custom deserializer for GrantedAuthority interface
     * This deserializer creates User_Role instances when deserializing GrantedAuthority
     */
    private static class GrantedAuthorityDeserializer extends StdDeserializer<GrantedAuthority> {

        public GrantedAuthorityDeserializer() {
            super(GrantedAuthority.class);
        }

        @Override
        public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);

            User_Role role = new User_Role();

            // Extract fields from the JSON node
            if (node.has("id")) {
                role.setId(node.get("id").asLong());
            }

            if (node.has("roleName")) {
                role.setRoleName(node.get("roleName").asText());
            } else if (node.has("authority")) {
                // Handle case where the JSON has "authority" field instead of "roleName"
                role.setRoleName(node.get("authority").asText());
            }

            if (node.has("description")) {
                role.setDescription(node.get("description").asText());
            }

            return role;
        }
    }
}
