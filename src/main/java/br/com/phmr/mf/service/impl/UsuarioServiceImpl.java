package br.com.phmr.mf.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.phmr.mf.exceptions.ErroAutenticacao;
import br.com.phmr.mf.exceptions.RegraNegocioException;
import br.com.phmr.mf.model.entity.Usuario;
import br.com.phmr.mf.model.enums.TipoLancamento;
import br.com.phmr.mf.model.repository.UsuarioRepository;
import br.com.phmr.mf.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if (!usuario.isPresent()) {
			throw new ErroAutenticacao("Email não encontrado!");
		}
		if (!usuario.get().getSenha().trim().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida!");
		}
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
		}

	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal obterSaldoPorUsuario(Long idUsuario) {
		BigDecimal despesas = repository.obterSaldoPorUsuarioETipo(idUsuario, TipoLancamento.DESPESA);
		BigDecimal receitas = repository.obterSaldoPorUsuarioETipo(idUsuario, TipoLancamento.RECEITA);
		if (despesas == null) {
			despesas = BigDecimal.ZERO;
		}
		if (receitas == null) {
			receitas = BigDecimal.ZERO;
		}
		return receitas.subtract(despesas);
	}

}
