package ejercicio1;

/*
Programa que lance un hilo que cuente del 1 al 10,
tardando un segundo en cambiar cada número.
*/
public class ContadorHilo extends Thread {

    //constante que define el número final de la cuenta
    public static final int CONTADOR = 10;

    //main
    public static void main(String[] args) {

        //creación del hilo para iniciar el contador
        ContadorHilo contadorHilo = new ContadorHilo();

        //inicia el hilo
        contadorHilo.start();
    }

    //método run que será llamado tras ejecutar un método start()
    @Override
    public void run() {

        // Bucle para contar del 1 al 10
        for (int i = 1; i <= CONTADOR; i++) {

            System.out.println("Segundo " + i); //imprime el segundo

            try {

                Thread.sleep(1000); //pausa de 1 segundo

            } catch (InterruptedException e) {
                e.printStackTrace(System.err); //muestra error si el hilo es interrumpido
            }
        }

        System.out.println("Fin de la cuenta con éxito!");

    }
}
