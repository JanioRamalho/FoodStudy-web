package com.foodstudy.web.repository;

import com.foodstudy.web.model.auth.UsuarioLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioLoginRepository extends JpaRepository<UsuarioLogin, Long> {
    UsuarioLogin findByEmail(String email);
}
