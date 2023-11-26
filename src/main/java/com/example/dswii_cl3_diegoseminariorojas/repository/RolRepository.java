package com.example.dswii_cl3_diegoseminariorojas.repository;

import com.example.dswii_cl3_diegoseminariorojas.model.bd.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
    Rol findByNomrol(String nombrerol);
}
