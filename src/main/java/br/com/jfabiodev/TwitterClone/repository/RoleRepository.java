package br.com.jfabiodev.TwitterClone.repository;

import br.com.jfabiodev.TwitterClone.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
