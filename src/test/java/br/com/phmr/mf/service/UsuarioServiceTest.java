package br.com.phmr.mf.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.phmr.mf.exceptions.RegraNegocioException;
import br.com.phmr.mf.model.entity.Usuario;
import br.com.phmr.mf.model.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;

	@Autowired
	UsuarioRepository repository;

	@Test( expected = Test.None.class /* no exception expected */ )
	public void deveValidarEmail() {
		// Cenário
		repository.deleteAll();
		// Ação
		service.validarEmail("teste@teste.com");
	}

	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailJaCadastrado() {
		// Cenário
		Usuario usuario = Usuario.builder().nome("Teste").email("teste@teste.com").build();
		repository.save(usuario);
		//ação
		service.validarEmail("teste@teste.com");
	}

}
