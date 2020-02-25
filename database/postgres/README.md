# Postgres

## Startup
Dopo aver lanciato il dockerfile forwardare la porta la porta 5432 in 5532 e collegarsi tramite client MongoDB Compass alla seguente url:

| Parametro | Valore        |
|-----------|---------------|
| host      | localhost     |
| port      | 5532          |
| database  | postgres      |
| user      | postgres      |
| password  | postgres      |

## Export
Esportare lo schema.sql e data.sql con la i dati della tabella city

L'export può essere anche eseguito con il comando:
pg_dump -U postgres postgres > city.pgsql