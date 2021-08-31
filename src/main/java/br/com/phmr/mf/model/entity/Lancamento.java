package br.com.phmr.mf.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.com.phmr.mf.model.enums.StatusLancamento;
import br.com.phmr.mf.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lancamento", schema = "financas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Integer mes;
	private Integer ano;

	@ManyToOne
	@JoinColumn(name = "id_isuario")
	private Usuario usuario;

	private BigDecimal valor;

	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;

	@Enumerated(value = EnumType.STRING)
	private TipoLancamento tipo;

	@Enumerated(value = EnumType.STRING)
	private StatusLancamento status;

}
