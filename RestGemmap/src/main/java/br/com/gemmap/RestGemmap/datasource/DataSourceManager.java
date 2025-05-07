package br.com.gemmap.RestGemmap.datasource;

import br.com.issmap.dominios.enumeracao.CidadesEnum;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Component
@RequiredArgsConstructor
public class DataSourceManager {

    private final Environment env;
    private final Map<String, DataSource> allDataSources = new HashMap<>();
    private final Map<Integer, JdbcTemplate> jdbcTemplatesByCidade = new HashMap<>();
    private String user;
    private String password;
    @PostConstruct
    public void init() {
        user = System.getenv("USERNAME_BD_RESTISSMAP");
        password = System.getenv("PASS_BD_RESTISSMAP");

        for (CidadesEnum cidade : CidadesEnum.values()) {
            if (cidade != CidadesEnum.CEP) {
                String contexto = cidade.getContexto().replace("/", "");
                DataSource ds = createDataSource(contexto);

                if (ds == null) {
                    throw new IllegalStateException("❌ Configuração não encontrada para cidade: " + cidade.getNomeCidade());
                }

                allDataSources.put(contexto, ds);
                jdbcTemplatesByCidade.put(cidade.getCodigo(), new JdbcTemplate(ds));
                System.out.println("✅ Inicializado pool para cidade: " + cidade.getNomeCidade());
            }
        }
    }

    private DataSource createDataSource(String cidadeKey) {
        String prefix = "spring.datasource.cidades." + cidadeKey;
        String url = env.getProperty(prefix + ".url");
        String driverClassName = env.getProperty(prefix + ".driver-class-name");

        if (url == null || user == null || password == null || driverClassName == null) {
            return null; // Config faltando
        }

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);

        // Configuração do pool
        ds.setMaximumPoolSize(5);
        ds.setMinimumIdle(2);
        ds.setIdleTimeout(300_000); // 5 minutos
        ds.setMaxLifetime(1_800_000); // 30 minutos
        return ds;
    }

    public JdbcTemplate getJdbcTemplate(int idCidade) {
        JdbcTemplate jdbcTemplate = jdbcTemplatesByCidade.get(idCidade);
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException("❌ Nenhum JdbcTemplate configurado para a cidade de código " + idCidade);
        }
        return jdbcTemplate;
    }
}