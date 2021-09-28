package br.com.phmr.mf.service;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.phmr.mf.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvarUsuario(Usuario usuario);

	void validarEmail(String email);

	Optional<Usuario> obterPorId(Long id);

	BigDecimal obterSaldoPorUsuario(Long idUsuario);

}
