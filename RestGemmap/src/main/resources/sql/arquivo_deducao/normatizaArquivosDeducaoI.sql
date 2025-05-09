SELECT
    a.nro
FROM
    nfse_arq_deducao a
WHERE
    a.data_emissao = TO_DATE(':dataDeSincronizacao','DD-MM-YYYY')
ORDER BY
    a.nro