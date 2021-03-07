# Static-Fault-Tree-Repeated-Events

## Obiettivi
Rappresentare uno Static Fault Tree con eventi ripetuti.
I Gate implementati sono 3: 
  1. And a N ingressi;
  2. Or a N ingressi;
  3. K/N

I casi di interesse sono invece 4: 
  1. calcolare la probabilità che il Top Event non si sia ancora realizzato prima di un assegnato tempo di missione;
  2. ottimizzare gli interventi di manutenzione che possono prolungare il tempo di vita del sistema per una durata assegnata;
  3. assegnato un budget, ottimizzare il guadagno di affidabilità la scelta dei Basic Events la cui manutenzione rientra entro il budget;
  4. calcolare le importance measures di Fussel-Vesely e Birnbaum, che associano ciascun componente con una misura di quanto esso contribuisce alla total unreliability del sistema ad un tempo assegnato.


## Dipendenze
Per poter essere eseguito necessita di 2 librerie esterne: 
  * [OR-Tools] (https://developers.google.com/optimization): utilizzato per la risoluzione di problemi di ricerca operativa 
  * [Apache Commons Mat] (https://commons.apache.org/proper/commons-math/): utilizzata per il calcolo efficiente delle funzioni di ripartizione 

