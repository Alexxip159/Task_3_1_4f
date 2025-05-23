package ru.preproject.task_3_1_4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.preproject.task_3_1_4.repository.RoleRepository;
import ru.preproject.task_3_1_4.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}
