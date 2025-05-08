package br.com.gemmap.RestGemmap.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArquivoDeducaoRepository {

    private final JdbcTemplate jdbcTemplate;

    // Salva o arquivo
    public void salvarArquivo(String caminho, String idNota, String dataArquivo) throws Exception {
        Integer novoNro = valorMaxNro() + 1;

        String sql = "INSERT INTO NFSE_ARQ_DEDUCAO (NRO, NRO_NFSE, CAMINHO_ARQUIVO, FLG_VISTO, DATA_EMISSAO) " +
                "VALUES (?, ?, ?, ?, ?)";

        int rows = jdbcTemplate.update(sql, novoNro, idNota, caminho, "N", dataArquivo);

        if (rows != 1) {
            throw new Exception("Arquivo não inserido, esperado 1 linha afetada, mas foi: " + rows);
        }
    }

    // Busca o maior NRO
    public Integer valorMaxNro() {
        String sql = "SELECT COALESCE(MAX(NRO), 0) FROM NFSE_ARQ_DEDUCAO";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Parte I da sincronização
    public String sincronizaArquivosDeducaoParteI(String data) throws Exception {
        String sql = carregarSQL().replace(":dataDeSincronizacao", data);

        StringBuilder retorno = new StringBuilder();

        jdbcTemplate.query(sql, (ResultSet rs) -> {
            while (rs.next()) {
                if (retorno.length() > 0) {
                    retorno.append(",");
                }
                retorno.append(rs.getString("id_banco"));
            }
        });

        return retorno.toString();
    }

    // Simulando o carregar do SQL externo
    private String carregarSQL() throws Exception {
        // Aqui você pode carregar do arquivo como antes
        return "SELECT id_banco FROM algum_local WHERE data_sincronizacao = ':dataDeSincronizacao'";
    }
}
