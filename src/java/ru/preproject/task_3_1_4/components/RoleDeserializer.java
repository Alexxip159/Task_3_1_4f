package ru.preproject.task_3_1_4.components;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.preproject.task_3_1_4.model.Role;
import ru.preproject.task_3_1_4.service.RoleService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoleDeserializer extends JsonDeserializer<Set<Role>> {

    @Autowired
    private RoleService roleService;

    @Override
    public Set<Role> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        HashSet<Role> roles = new HashSet<>();

        if (treeNode.isArray()) {
            for (JsonNode roleNode : treeNode) {
                long roleId = roleNode.asLong();
                roles.add(roleService.findRoleById(roleId));
            }
        } else {
            long roleId = treeNode.asLong();
            roles.add(roleService.findRoleById(roleId));
        }
        return roles;
    }
}
