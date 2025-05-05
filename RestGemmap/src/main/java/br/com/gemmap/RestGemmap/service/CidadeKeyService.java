package br.com.gemmap.RestGemmap.service;

import br.com.issmap.dominios.enumeracao.CidadesEnum;
import org.springframework.stereotype.Service;

@Service
public class CidadeKeyService {
    public String buscarChavePorCidade(int idCidade) {
        try {
            CidadesEnum cidade = CidadesEnum.getValue(idCidade);
            if (cidade != null) {
                return cidade.getChaveCriptografia();
            } else {
                throw new IllegalArgumentException("Cidade não encontrada para o código: " + idCidade);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar chave da cidade: " + e.getMessage(), e);
        }
    }
}
