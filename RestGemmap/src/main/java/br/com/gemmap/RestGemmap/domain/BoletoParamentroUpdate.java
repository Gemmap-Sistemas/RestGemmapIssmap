package br.com.gemmap.RestGemmap.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoletoParamentroUpdate {

    private Integer anoExercicio;
    private Integer numeroParcela;
    private Integer numeroSequencia;
    private Integer mobiliarioNro;

}
