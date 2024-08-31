package ru.slavich.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.slavich.project.models.User;

public interface UserRepository  extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
