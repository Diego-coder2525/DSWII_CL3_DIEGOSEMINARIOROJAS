package com.example.dswii_cl3_diegoseminariorojas.service;

import com.example.dswii_cl3_diegoseminariorojas.model.bd.Rol;
import com.example.dswii_cl3_diegoseminariorojas.model.bd.Usuario;
import com.example.dswii_cl3_diegoseminariorojas.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNomusuario(username);
        return authUsuario(usuario,
                obtenerListaRolesUsuario(usuario.getRoles()));
    }
    public List<GrantedAuthority> obtenerListaRolesUsuario(Set<Rol> listaRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        for(Rol rol : listaRoles){
            roles.add(new SimpleGrantedAuthority(rol.getNomrol()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    public Usuario findByNomUsuario(String usuario){
        return usuarioRepository.findByNomusuario(usuario);
    }
    private UserDetails authUsuario(Usuario usuario, List<GrantedAuthority> authorityList){
        return new User(usuario.getNomusuario(),
                usuario.getPassword(),
                usuario.getActivo(),
                true,
                true,
                true, authorityList);
    }
}
