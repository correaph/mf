package br.com.phmr.mf.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.phmr.mf.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	//Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
