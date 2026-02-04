package com.spring.security.springsecuritydemo.consulta;

import com.spring.security.springsecuritydemo.medico.MedicoRepository;
import com.spring.security.springsecuritydemo.medico.MedicoService;
import com.spring.security.springsecuritydemo.paciente.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository repository;
    private final MedicoService medicoService;
    private final PacienteRepository pacienteRepository;

    public ConsultaServiceImpl(ConsultaRepository repository, MedicoService medicoService, PacienteRepository pacienteRepository) {
        this.repository = repository;
        this.medicoService = medicoService;
        this.pacienteRepository = pacienteRepository;
    }

    public Page<DadosListagemConsulta> listar(Pageable paginacao) {
        return repository.findAllByOrderByData(paginacao).map(DadosListagemConsulta::new);
    }

    @Transactional
    public void cadastrar(DadosAgendamentoConsulta dados) {
        var medicoConsulta = medicoService.findById(dados.idMedico()).orElseThrow();
        var pacienteConsulta = pacienteRepository.findByCpf(dados.paciente()).orElseThrow();
        if (dados.id() == null) {
            repository.save(new Consulta(medicoConsulta, pacienteConsulta, dados));
        } else {
            var consulta = repository.findById(dados.id()).orElseThrow();
            consulta.modificarDados(medicoConsulta, pacienteConsulta, dados);
        }
    }

    public DadosAgendamentoConsulta carregarPorId(Long id) {
        var consulta = repository.findById(id).orElseThrow();
        return new DadosAgendamentoConsulta(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getNome(), consulta.getData(), consulta.getMedico().getEspecialidade());
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

}