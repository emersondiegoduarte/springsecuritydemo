package com.spring.security.springsecuritydemo.consulta;

import com.spring.security.springsecuritydemo.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosListagemConsulta(Long id, String medico, String paciente, LocalDateTime data, Especialidade especialidade) {

    public DadosListagemConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getNome(), consulta.getPaciente().getNome(), consulta.getData(), consulta.getMedico().getEspecialidade());
    }

}
