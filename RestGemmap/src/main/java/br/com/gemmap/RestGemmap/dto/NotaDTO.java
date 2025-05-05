package br.com.gemmap.RestGemmap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaDTO {
    private int id_Banco;
    private String local_Execucao;
}
