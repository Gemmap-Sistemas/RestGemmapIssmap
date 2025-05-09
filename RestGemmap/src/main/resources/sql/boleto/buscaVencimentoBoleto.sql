SELECT TO_CHAR(vencimento, 'dd/MM/yyyy') vencimento
FROM c2_par_venc
WHERE c2p_ex_ano = :anoEx
  AND flg_tipo = 'F'
  AND nro_parc = :numeroParcela