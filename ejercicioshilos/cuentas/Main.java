package cuentas;

import java.util.Random;

public class Main {

	static Random random = new Random();
	
	private static final int NUM_RETIRADAS = 10;
	
	private static final int DINERO_CUENTA1 = 1000;
	private static final int DINERO_CUENTA2 = 500;
	
	//main
    public static void main(String[] args) throws InterruptedException {

        CuentaBancaria cuentaDeRetiro = new CuentaBancaria(DINERO_CUENTA1);
        CuentaBancaria cuentaDeIngreso = new CuentaBancaria(DINERO_CUENTA2);

        Thread [] hilos = new Thread[NUM_RETIRADAS];
        
        for (int i = 0; i < NUM_RETIRADAS; i++) {
			
        	int dineroRetirado = random.nextInt(301 + 100); //100-400
        	
        	//crea hilos para realizar la transferencia
        	TransferirDinero transferirDinero = new TransferirDinero(cuentaDeRetiro, cuentaDeIngreso, dineroRetirado);
        	hilos[i] = new Thread(transferirDinero);
        	
		}

        //Lanza los hilos
        for (Thread t : hilos) {
        	
        	t.start();
        	
        }
        
        //Espera a que todos los hilos terminen
        for (int i = 0; i < NUM_RETIRADAS; i++) {
        	
            hilos[i].join();
            
        }
        
    }
	
    //CLASE Cuentas bancarias
    static class CuentaBancaria {
    	
        private int saldo;

        public CuentaBancaria(int saldoInicial) {
            this.saldo = saldoInicial;
        }

        //Método sincronizado para sacar dinero de una cuenta
        public synchronized boolean retirar(int cantidad) {
            if (cantidad <= saldo) {
                saldo -= cantidad;
                System.out.println("Se retiraron " + cantidad + " de la CUENTA 1 [Saldo restante: " + saldo + "]");
                return true; //Retiro exitoso
            } else {
                System.out.println("Saldo INSUFICIENTE para retirar " + cantidad + " de la cuenta.");
                return false; //No se pudo retirar por falta de fondos
            }
        }

        // Método sincronizado para ingresar dinero a una cuenta
        public synchronized void ingresar(int cantidad) {
            saldo += cantidad;
            System.out.println("Se ingresaron " + cantidad + " en la CUENTA 2 [Saldo actual: " + saldo + "]");
        }

        public synchronized int getSaldo() {
            return saldo;
        }
    }

    //CLASE que representa la tarea de transferencia entre cuentas
    static class TransferirDinero implements Runnable {
    	
        private CuentaBancaria cuenta1;
        private CuentaBancaria cuenta2;
        private int cantidad;

        public TransferirDinero(CuentaBancaria cuenta1, CuentaBancaria cuenta2, int cantidad) {
            this.cuenta1 = cuenta1;
            this.cuenta2 = cuenta2;
            this.cantidad = cantidad;
        }

        @Override
        public void run() {
            //Intenta transferir dinero de la cuenta1 a la cuenta2
            synchronized (cuenta1) {
            	
                System.out.println(Thread.currentThread().getName() + " intentando retirar " + cantidad + " de la CUENTA 1.");

                if (cuenta1.retirar(cantidad)) {
                	
                    synchronized (cuenta2) {
                        //si la transferencia es exitosa, ingresamos el dinero a la cuenta2
                        cuenta2.ingresar(cantidad);
                        System.out.println(Thread.currentThread().getName() + " ha transferido " + cantidad + " de CUENTA 1 a CUENTA 2.");
                    }
                    
                }
                
            }
            
        }
        
    }
}
