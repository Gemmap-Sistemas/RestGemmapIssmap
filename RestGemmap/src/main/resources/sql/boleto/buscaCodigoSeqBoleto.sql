SELECT
    COALESCE(MAX(nroseq), 0) + 1 AS nro
FROM
    c2_carne
WHERE
    nro_parc = :numeroParcela
  AND cgarq_ex_ano = :anoEx
  AND mob_nro = :nroMobiliario
  AND flg_tipo = 'F'