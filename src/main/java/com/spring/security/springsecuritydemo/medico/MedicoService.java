package com.spring.security.springsecuritydemo.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicoService {

    public Page<DadosListagemMedico> listar(Pageable paginacao);

    public void cadastrar(DadosCadastroMedico dados);

    public DadosCadastroMedico carregarPorId(Long id);

    public void excluir(Long id);

    public List<DadosListagemMedico> listarPorEspecialidade(Especialidade especialidade);

    public Optional<Medico> findById(Long id);
}
