package lanzaprocesoescribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class LanzaProcesoEscribeEntrada {

	public static void main(String[] args) {
		
		ProcessBuilder pb = new ProcessBuilder("nslookup");
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		
		try (InputStreamReader isr = new InputStreamReader(System.in, "UTF-8");
				BufferedReader br = new BufferedReader(isr)) {
			
			String linea;
			System.out.println("Introducir nombre de dominio: ");
			
			while ((linea = br.readLine()) != null && linea.length() != 0) {
				
				Process p = pb.start(); //lanza el proceso
				
				try (OutputStream os = p.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
					
					osw.write(linea); //envía línea leída al proceso
					
				}
				
				try {
					
					p.waitFor();
					
				} catch (InterruptedException e) {
					
					System.err.println("ERROR: " + e.getMessage());
					
				}
				
				System.out.println("\nIntroducir nombre de dominio: ");
				
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}
	}
	
}
