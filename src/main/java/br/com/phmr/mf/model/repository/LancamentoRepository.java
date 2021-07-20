package br.com.phmr.mf.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.phmr.mf.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
