package br.com.phmr.mf.api.resource;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.phmr.mf.api.dto.LancamentoDTO;
import br.com.phmr.mf.api.dto.StatusDTO;
import br.com.phmr.mf.exceptions.RegraNegocioException;
import br.com.phmr.mf.model.entity.Lancamento;
import br.com.phmr.mf.model.entity.Usuario;
import br.com.phmr.mf.model.enums.StatusLancamento;
import br.com.phmr.mf.model.enums.TipoLancamento;
import br.com.phmr.mf.service.LancamentoService;
import br.com.phmr.mf.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {

	private final LancamentoService service;
	private final UsuarioService usrService;

	@PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento lancamento = getLancamento(dto);
			Lancamento lancSalvo = service.salvar(lancamento);
			return new ResponseEntity(lancSalvo, HttpStatus.CREATED);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
		try {
			Optional<Lancamento> lancamento = service.obterPorId(id);
			if (lancamento.isPresent()) {
				Lancamento lancUpd = getLancamento(dto);
				lancUpd.setId(id);
				return ResponseEntity.ok(service.atualizar(lancUpd));
			} else {
				return ResponseEntity.badRequest().body("Lancamento não encontrado para id " + id);
			}
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PutMapping("{id}/atualizar-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody StatusDTO statusDTO) {
		try {
			Optional<Lancamento> lancamento = service.obterPorId(id);
			if (lancamento.isPresent()) {
				Lancamento lancUpd = lancamento.get();
				lancUpd.setStatus(StatusLancamento.valueOf(statusDTO.getStatus()));
				return ResponseEntity.ok(service.atualizar(lancUpd));
			} else {
				return ResponseEntity.badRequest().body("Lancamento não encontrado para id " + id);
			}
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		try {
			service.deletar(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam(value = "usuario", required = true) long idUsr) {
		try {
			Lancamento lancFiltro = new Lancamento();
			lancFiltro.setDescricao(descricao);
			lancFiltro.setAno(ano);
			lancFiltro.setMes(mes);
			Usuario usuario = usrService.obterPorId(idUsr).get();
			return ResponseEntity.ok(service.buscar(lancFiltro));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	private Lancamento getLancamento(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		Optional<Usuario> usuario = usrService.obterPorId(dto.getUsuario());
		if (usuario.isPresent()) {
			lancamento.setUsuario(usuario.get());
		} else {
			throw new RegraNegocioException("Usuário não encontrado para id " + dto.getUsuario());
		}
		lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		if (dto.getStatus() == null) {
			lancamento.setStatus(StatusLancamento.valueOf("PENDENTE"));
		} else {
			lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		}
		// lancamento.setDataCadastro(dto.getDataCadastro());
		return lancamento;
	}

}