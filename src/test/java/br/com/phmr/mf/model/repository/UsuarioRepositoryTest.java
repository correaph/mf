package br.com.phmr.mf.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.phmr.mf.model.entity.Usuario;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Test
	public void verificarExistenciaEmail() {
		// Cenário
		Usuario usuario = Usuario.builder().nome("Teste").senha("Teste").email("teste@gmail.com").build();
		repository.save(usuario);
		// Execução
		boolean result = repository.existsByEmail("teste@gmail.com");
		// Verificação
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void verificarNaoExistenciaEmail() {
		// Execução
		boolean result = repository.existsByEmail("teste222@gmail.com");
		// Verificação
		Assertions.assertThat(result).isFalse();
	}
	

}
