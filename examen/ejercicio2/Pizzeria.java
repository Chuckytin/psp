/*
Programa que simule el servicio de una pizzería.
Vamos a tener 3 cocineros (Mario, Luigi y Giulia) que preparan pizzas y las dejarán en la zona de comandas.
Hay 2 camareros (Giovani y Paolo) que recogen las pizzas de la zona de comandas y las llevan a las mesas.
La zona de comandas tiene una capacidad de 10 pizzas.
Los cocineros están cocinando pizzas constantemente y cada pizza cuesta 100 ms en estar lista.
Los camareros las recogen en cuánto están listas y tardan 20ms en servirlas.
El servicio reparte 1000 pizzas al día.
*/
package ejercicio2;

import java.util.ArrayList;
import java.util.List;

//Clase que representa a una pizzeria donde trabajan los cocineros y camareros
public class Pizzeria {

    //capacidad máxima comandas
    private static final int CAPACIDAD_COMANDAS = 10;
    private static final List<String> listaDeComandas = new ArrayList<>();

    //Pizzas que deben servirse
    private static final int PIZZAS_TOTALES = 100;

    //contador de pizzas servidas
    private static int pizzasServidas = 0;

    //main
    public static void main(String[] args) {

        //creación de los hilos cocineros
        Thread mario = new Thread(new Cocinero("Mario"));
        Thread luigi = new Thread(new Cocinero("Luigi"));
        Thread giulia = new Thread(new Cocinero("Giulia"));

        //creación de los hilos camareros
        Thread giovani = new Thread(new Camarero("Giovani"));
        Thread paolo = new Thread(new Camarero("Paolo"));

        //empiezan a trabajar
        mario.start();
        luigi.start();
        giulia.start();
        giovani.start();
        paolo.start();

        //espera a que todos los hilos terminen antes de seguir trabajando
        try {

            mario.join();
            luigi.join();
            giulia.join();
            giovani.join();
            paolo.join();

        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }

        //mensaje final una vez terminado el servicio
        System.out.println("Servicio completado! Se han servido con éxito todas las pizzas");
    }

    //Clase que representa a un cocinero que prepara pizzas.
    static class Cocinero implements Runnable {

        private final String nombre;

        Cocinero(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {

            while (true) {

                //synchronized porque solo un cocinero debe recoger la comanda y no varios para que se hagan de una en una
                synchronized (listaDeComandas) {
                    //verifica si se han servido todas las pizzas
                    if (pizzasServidas >= PIZZAS_TOTALES) {
                        break;
                    }

                    //verifica si hay espacio para más pizzas en la lista de comandas
                    if (listaDeComandas.size() < CAPACIDAD_COMANDAS) {
                        listaDeComandas.add("pizza preparada por " + nombre);
                        System.out.println(nombre + " ha dejado una pizza en la zona de comandas.");
                    }
                }

                try {

                    Thread.sleep(100); //100 ms de preparación de una pizza

                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }

     //clase que representa un camarero que sirve las pizzas hechas por los cocineros
    static class Camarero implements Runnable {

        private final String nombre;

        Camarero(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {

            while (true) {

                String pizza = null;

                //synchronized porque solo un camarero puede recoger la pizza hecha y servirla y no los dos a la vez
                synchronized (listaDeComandas) {
                    //verifica si se han servido todas las pizzas
                    if (pizzasServidas >= PIZZAS_TOTALES) {
                        break;
                    }
                    //verifica si hay pizzas para recoger
                    if (!listaDeComandas.isEmpty()) {
                        pizza = listaDeComandas.remove(0);
                    }
                }

                if (pizza != null) {
                    System.out.println(nombre + " ha recogido la " + pizza + " para servir.");

                    try {

                        Thread.sleep(20); //20 ms de recoger la pizza

                    } catch (InterruptedException e) {
                        e.printStackTrace(System.err);
                    }

                    servirPizza();

                }
            }
        }
    }

    //método synchronized que incrementa el contador de las pizzas servidas
    private static synchronized void servirPizza() {

        pizzasServidas++;
        System.out.println("Pizza servida. Total de pizzas servidas: " + pizzasServidas);
    }
}
