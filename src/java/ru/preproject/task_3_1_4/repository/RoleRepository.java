package ru.preproject.task_3_1_4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.preproject.task_3_1_4.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
