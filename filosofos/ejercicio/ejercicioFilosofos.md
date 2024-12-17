# Problema de los Filósofos Comiendo

## Descripción

- 5 filósofos sentados en una mesa circular
- Un tazón mágico de espaguetis en el centro
- Los filósofos alternan entre pensar y comer
- Necesitan 2 palillos para comer (izquierdo y derecho)
- Los palillos están compartidos entre filósofos adyacentes

## Restricciones

- No pueden comer y pensar simultáneamente
- Deben tener ambos palillos para comer
- La mesa es circular, por lo que algunos palillos son compartidos

## Objetivos

- Garantizar que todos los filósofos que quieran comer puedan hacerlo
- Evitar deadlocks

## Posibles soluciones

- Usar hilos diferentes para cada filósofo
- Implementar métodos para pensar, tomar palillos y comer
- Utilizar semáforos o monitores para sincronización