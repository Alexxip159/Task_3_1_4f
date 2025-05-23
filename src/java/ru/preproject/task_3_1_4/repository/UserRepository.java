package ru.preproject.task_3_1_4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.preproject.task_3_1_4.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u join fetch u.roles where u.email = :email")
    User findUserByEmail(String email);
}
