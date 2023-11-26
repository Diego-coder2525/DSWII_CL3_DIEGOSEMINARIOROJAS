package com.example.dswii_cl3_diegoseminariorojas.repository;

import com.example.dswii_cl3_diegoseminariorojas.model.bd.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Usuario findByNomusuario(String usuario);
}
