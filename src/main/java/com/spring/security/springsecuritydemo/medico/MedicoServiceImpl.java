package com.spring.security.springsecuritydemo.medico;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements MedicoService{

    private final MedicoRepository repository;

    public MedicoServiceImpl(MedicoRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<Medico> findById(Long id) {
        return repository.findById(id);
    }

    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    @Transactional
    public void cadastrar(DadosCadastroMedico dados) {
        if (repository.isJaCadastrado(dados.email(), dados.crm(), dados.id())) {
            throw new RuntimeException("E-mail ou CRM já cadastrado para outro médico!");
        }

        if (dados.id() == null) {
            repository.save(new Medico(dados));
        } else {
            var medico = repository.findById(dados.id()).orElseThrow();
            medico.atualizarDados(dados);
        }
    }

    public DadosCadastroMedico carregarPorId(Long id) {
        var medico = repository.findById(id).orElseThrow();
        return new DadosCadastroMedico(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade());
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<DadosListagemMedico> listarPorEspecialidade(Especialidade especialidade) {
        return repository.findByEspecialidade(especialidade).stream().map(DadosListagemMedico::new).toList();
    }

}
