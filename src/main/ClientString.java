package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class ClientString {
	
	private Socket client;
	private PrintStream output;
	private BufferedReader input;
	
	public ClientString(String hostname, int port) {
		try {
			client = new Socket(hostname, port);
			output = new PrintStream(client.getOutputStream());
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			System.out.println("=== Delete all");
			output.println("supprimer Nom1");
			this.readAnswer();
			output.println("supprimer Nom2");
			this.readAnswer();
			
			// Test de dump sans aucun nom
			System.out.println("=== Dump sans noms (@expected : chaine vide)");
			this.dumpAllNames();
			
			System.out.println("=== Ajout (@expected : Nom1 -> s1 s2 s3)");
			// Test d'ajout
			output.println("ajouter Nom1 s1 s2 s3");
			this.readAnswer();
			
			// Devrait afficher Nom1 -> s1 s2 s3
			this.dumpAllNames();
			
			System.out.println("=== Ajout de surnoms a un nom existant " +
					"(@expected : Nom1 -> s1 s2 s3 s4 s5 s6)");
			// Si le nom existe, ajouter ceux qui n'existent pas mais pas ceux qui existent
			output.println("ajouter Nom1 s5 s1 s4 s3 s6");
			this.readAnswer();
			
			// Devrait afficher Nom1 -> s1 s2 s3 s4 s5 s6
			this.dumpAllNames();
			
			System.out.println("=== Suppression de surnoms (@expected : Nom1 -> s1 s3 s5 s6)");
			// Suppression de surnoms
			output.println("supprimer Nom1 s2 s4");
			this.readAnswer();
			
			// Devrait afficher Nom1 -> s1 s3 s5 s6
			this.dumpAllNames();
			
			System.out.println("=== Ajout d'un nouveau nom (@expected : Nom1 -> s1 s3 s5 s6 // Nom2 -> s1 s8 s9)");
			// Ajout d'un nouveau nom
			output.println("ajouter Nom2 s1 s8 s9");
			this.readAnswer();
			
			// Devrait afficher Nom1 -> s1 s3 s5 s6 // Nom2 -> s1 s8 s9
			// (/!\ Meme surnom pour deux noms !!!
			this.dumpAllNames();
			
			// Affichage de Nom1
			System.out.println("=== Affichage de Nom1 (@expected : Nom1 -> s1 s3 s5 s6)");
			this.dumpName("Nom1");
			
			// Affichage d'un nom inexistant
			System.out.println("=== Ajout d'un nom inexistant (@expected : chaine vide)");
			this.dumpName("Nom3");
			
			// Affichage de Nom2
			System.out.println("=== Affichage de Nom2 (@expected : Nom2 -> s1 s8 s9)");
			this.dumpName("Nom2");

			// Suppression d'un nom
			System.out.println("=== Suppression d'un nom (@expected : Nom2 -> s1 s8 s9)");
			output.println("supprimer Nom1");
			this.readAnswer();
			
			// Devrait afficher Nom2 -> s1 s8 s9
			this.dumpAllNames();
			
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void dumpAllNames() throws IOException {
		output.println("lister");
		this.readAnswer();
	}
	
	private void dumpName(String name) throws IOException {
		output.println("lister " + name);
		this.readAnswer();
	}
	
	private void readAnswer() throws IOException {
		String s = "";
		do {
			s = input.readLine();
			System.out.println(s);
		} while(!s.equals("ok"));
	}
	
	public static void main(String[] args) {
		new ClientString("157.169.100.88", 4242);
	}
	
}
