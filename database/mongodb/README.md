# MongoDB

## Startup
Dopo aver lanciato il dockerfile forwardare la porta la porta 27017 in 27018 e collegarsi tramite client MongoDB Compass alla seguente url:
- mongodb://mongoadmin:secret@localhost:27018/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false

## Export
Per eseguire un eventuale export dal db esistente, collegarsi alla macchina docker ed eseguire i comandi:
- mongoexport --collection datas --out datas.json --username mongoadmin --password secret --db aperitech --authenticationDatabase admin
- mongoexport --collection weathers --out weathers.json --username mongoadmin --password secret --db aperitech --authenticationDatabase admin

Avere cura di rendere i file json appena esportati sottoforma di array andando ad inserire la parentesi quadra aperta '[' all'inizio del file e quella chiusa alla fine ']'.
Infine aggiungere la virgola su ogni fine riga (replace '\}$\n' con '},\n')

Comprimere i file esportati su un archivio tar compresso attraverso l'esecuzione del seguente comando:
- tar -zcvf mongoBackup.tgz datas.json weather.json