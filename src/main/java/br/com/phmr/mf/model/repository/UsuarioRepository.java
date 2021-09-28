package br.com.phmr.mf.model.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.phmr.mf.model.entity.Usuario;
import br.com.phmr.mf.model.enums.TipoLancamento;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

	boolean existsByEmail(String email);

	// Query JPQL
	@Query(value = "select sum(l.valor) from Lancamento l join l.usuario u "
			+ "where u.id = :idUsuario and l.tipo = :tipo group by u")
	BigDecimal obterSaldoPorUsuarioETipo(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo);

}
