/*
Crea un programa que simule la cocina de una pizzería.
Los 3 cocineros van a estar cocinando a la vez pero tienen varios recursos que necesitan utilizar,
primero tienen que conseguir una masa de pizza.
Después necesitan a la vez tener acceso a el bote de salsa de tomate, el orégano y el resto de ingredientes.
Finalmente, necesitan usar uno de los 2 hornos.
Todas las acciones cuestan 10ms menos la cocina en el horno que cuesta 80ms.
*/
package ejercicio3;

/*
Clase CocinaPizzeria donde trabajan 3 cocineros a la vez.
Cada cocinero sigue 3 pasos:
  1 - conseguir la masa de la pizza
  2 - añadir la salsa, el orégano y otros ingredientes (estos recursos deben ser usados a la vez)
  3 - usar uno de los dos hornos disponibles para hornear la pizza
*/
public class CocinaPizzeria {

    //objetos que representan
    private static Object masa = new Object();
    private static Object salsa = new Object();
    private static Object oregano = new Object();
    private static Object ingredientes = new Object();
    private static Object horno1 = new Object();
    private static Object horno2 = new Object();

    public static void main(String[] args) {

        //creación de los hilos cocinero
        Thread mario = new Thread(new Cocinero("Mario"));
        Thread luigi = new Thread(new Cocinero("Luigi"));
        Thread giulia = new Thread(new Cocinero("Giulia"));

        //inicio de los cocineros a trabajar
        mario.start();
        luigi.start();
        giulia.start();

        //espera a que todos los hilos terminen para seguir trabajando
        try {

            mario.join();
            luigi.join();
            giulia.join();

        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }

        System.out.println("Todos los cocineros han terminado de preparar las pizzas.");
    }

    //clase interna que representa a un cocinero
    static class Cocinero implements Runnable {

        private final String nombre;

        Cocinero(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {

            try {

                prepararPizza();

            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }

        //método que prepara la pizza
        private void prepararPizza() throws InterruptedException {

            //Paso 1 de conseguir la masa
            synchronized (masa) {
                System.out.println(nombre + " ha conseguido la masa.");
                Thread.sleep(10); //tarda 10 ms en conseguir la masa
            }

            //paso 2 para añadir salsa, orégano y otros ingredientes a la vez
            synchronized (salsa) {
                synchronized (oregano) {
                    synchronized (ingredientes) {

                        System.out.println(nombre + " está añadiendo salsa, orégano y demás ingredientes.");
                        Thread.sleep(10); //tarda 10 ms en añadir los ingredients
                    }
                }
            }

            //Paso 3 para intentar usar uno de los dos hornos
            boolean hornoAsignado = false;
            while (!hornoAsignado) {

                //Intenta usar el horno 1 SI EL SEGUNDO ESTÁ OCUPADO
                if (intentarUsarHorno(horno1, "1")) {
                    hornoAsignado = true;
                }

                //intenta usar el horno 2 SI EL PRIMERO ESTÁ OCUPADO
                else if (intentarUsarHorno(horno2, "2")) {

                    hornoAsignado = true;

                }
            }
        }

        //Método para intentar usar un horno específico
        private boolean intentarUsarHorno(Object horno, String nombreHorno) throws InterruptedException {

            synchronized (horno) {
                System.out.println(nombre + " está usando el horno " + nombreHorno + ".");
                Thread.sleep(80); // Simula el tiempo de cocción en el horno
                System.out.println(nombre + " ha terminado de usar el horno " + nombreHorno + ".");
                return true;
            }
        }
    }
}

