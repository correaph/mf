package br.com.phmr.mf.api.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LancamentoDTO {

	private Long id;
	private Integer mes;
	private Integer ano;
	private String descricao;
	private Long usuario;
	private BigDecimal valor;
	private String tipo;
	private String status;
	private String dataCadastro;

}
