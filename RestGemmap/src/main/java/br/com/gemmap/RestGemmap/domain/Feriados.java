package br.com.gemmap.RestGemmap.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feriados implements Serializable {

    private Integer id;
    private String nome;
    private Date dataFeriado;
    private Boolean pontoFacultativo;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    public String getDataFeriadoString() {
        return dataFeriado != null ? SDF.format(dataFeriado) : null;
    }

    public void setDataFeriadoString(String dataFeriadoStr) throws ParseException {
        if (dataFeriadoStr != null && !dataFeriadoStr.isEmpty()) {
            SDF.setLenient(false);
            this.dataFeriado = SDF.parse(dataFeriadoStr);
        } else {
            this.dataFeriado = null;
        }
    }
}
