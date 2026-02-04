package com.spring.security.springsecuritydemo.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultaService {

    public Page<DadosListagemConsulta> listar(Pageable paginacao);

    public void cadastrar(DadosAgendamentoConsulta dados);

    public DadosAgendamentoConsulta carregarPorId(Long id);

    public void excluir(Long id);
}
