package com.spring.security.springsecuritydemo.paciente;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl {

    private final PacienteRepository repository;

    public PacienteServiceImpl(PacienteRepository repository) {
        this.repository = repository;
    }

    public Page<DadosListagemPaciente> listar(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemPaciente::new);
    }

    @Transactional
    public void cadastrar(DadosCadastroPaciente dados) {
        if (repository.isJaCadastrado(dados.email(), dados.cpf(), dados.id())) {
            throw new RuntimeException("E-mail ou CPF já cadastrado para outro paciente!");
        }

        if (dados.id() == null) {
            repository.save(new Paciente(dados));
        } else {
            var paciente = repository.findById(dados.id()).orElseThrow();
            paciente.modificarDados(dados);
        }
    }

    public DadosCadastroPaciente carregarPorId(Long id) {
        var paciente = repository.findById(id).orElseThrow();
        return new DadosCadastroPaciente(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

}
