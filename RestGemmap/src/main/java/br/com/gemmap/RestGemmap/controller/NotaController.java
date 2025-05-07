package br.com.gemmap.RestGemmap.controller;

import br.com.gemmap.RestGemmap.datasource.DataSourceManager;
import br.com.gemmap.RestGemmap.dto.NotaDTO;
import br.com.gemmap.RestGemmap.service.CidadeKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final DataSourceManager dataSourceManager;
    private final CidadeKeyService cidadeKeyService;

    @GetMapping("{codigoCidade}/teste")
    public List<NotaDTO> listarNotas(@PathVariable int codigoCidade) {
        String chaveCidade = cidadeKeyService.buscarChavePorCidade(codigoCidade);

        JdbcTemplate jdbcTemplate = dataSourceManager.getJdbcTemplate(codigoCidade);

        return jdbcTemplate.query(
                "SELECT * FROM c2_nfs_e WHERE cpf_cnpj_tomador = '464.842.758-07'",
                (rs, rowNum) -> new NotaDTO(rs.getInt("ID_BANCO"), rs.getString("LOCAL_EXECUCAO"))
        );
    }
}
