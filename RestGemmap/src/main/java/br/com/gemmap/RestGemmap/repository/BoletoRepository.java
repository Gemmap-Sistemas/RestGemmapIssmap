package br.com.gemmap.RestGemmap.repository;

import br.com.gemmap.RestGemmap.util.SqlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

@Repository
@RequiredArgsConstructor
public class BoletoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SqlReader sqlReader;

    public BoletoRepository(NamedParameterJdbcTemplate jdbcTemplate, SqlReader sqlReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlReader = sqlReader;
    }

    public Integer buscaSeqBoleto(Integer anoEx, Integer numeroMes, Integer nroMobiliario, JdbcTemplate jdbcTemplate) {
        String sql = sqlReader.load("sql/boleto/buscaCodigoSeqBoleto.sql");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("numeroParcela", numeroMes)
                .addValue("anoEx", anoEx)
                .addValue("nroMobiliario", nroMobiliario);

        Integer resultado = new NamedParameterJdbcTemplate(jdbcTemplate).queryForObject(sql, params, Integer.class);

        return resultado != null ? resultado : 1;
    }

    public String buscaDataVencimentoBoleto(Integer anoEx, Integer numeroParcela, JdbcTemplate jdbcTemplate) {
        String sql = sqlReader.load("sql/boleto/buscaVencimentoBoleto.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("anoEx", anoEx);
        params.addValue("numeroParcela", numeroParcela);

        return new NamedParameterJdbcTemplate(jdbcTemplate)
                .queryForObject(sql, params, String.class);
    }

    public Integer insereBoleto(
            Integer anoEx,
            Integer numeroMes,
            Double valorDocumento,
            Integer numeroControle,
            Integer ficha,
            Integer nroMobiliario,
            String dataVencimento,
            JdbcTemplate jdbcTemplate
    ) {
        try {
            // 1) Busca sequência do boleto
            Integer numeroSeq = buscaSeqBoleto(anoEx, numeroMes, nroMobiliario, jdbcTemplate);

            // 2) Carrega SQL
            String sql = sqlReader.load("sql/boleto/insertBoleto.sql");

            // 3) Prepara parâmetros
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("exAno", anoEx)
                    .addValue("flgTipo", "F")
                    .addValue("nroParc", numeroMes)
                    .addValue("qtdParc", 12)
                    .addValue("vencimento", new SimpleDateFormat("dd/MM/yyyy").parse(dataVencimento))
                    .addValue("vlrIss", valorDocumento)
                    .addValue("vlrTax", 0)
                    .addValue("vlrInc", 0)
                    .addValue("vlrTex", 0)
                    .addValue("descIss", 0)
                    .addValue("descTax", 0)
                    .addValue("descInc", 0)
                    .addValue("flgExcluido", "N")
                    .addValue("cgarqExAno", anoEx)
                    .addValue("cgarqNro", numeroControle)
                    .addValue("cgarqNroFicha", ficha)
                    .addValue("descTex", 0)
                    .addValue("diasCarencia", 0)
                    .addValue("mobNro", nroMobiliario)
                    .addValue("vlrMulta", 0)
                    .addValue("vlrJuros", 0)
                    .addValue("vlrCorrecao", 0)
                    .addValue("flgPortal", "N")
                    .addValue("flgStatus", "N")
                    .addValue("nroSeq", numeroSeq)
                    .addValue("flgNfe", "S");

            // 4) Executa
            new NamedParameterJdbcTemplate(jdbcTemplate).update(sql, params);

            // 5) Retorna sequência gerada
            return numeroSeq;

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir boleto: " + ex.getMessage(), ex);
        }
    }

    
}
