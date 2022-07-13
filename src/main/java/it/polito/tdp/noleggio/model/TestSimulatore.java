package it.polito.tdp.noleggio.model;

public class TestSimulatore {

	public static void main(String[] args) {
		Simulatore sim = new Simulatore(12); // creo un nuovo simulatore in cui passo il numero di auto in possesso al lancio della simulazione

		// carico gli eventi
		sim.caricaEventi();
		// lancio il run
		sim.run();

		System.out.println(
				"Clienti: " + sim.getnClientiTot() + " di cui " + 
				sim.getnClientiInsoddisfatti() + " insoddisfatti\n");

	}
	
	// il simulatore ti dà gli strumenti per capire le conseguenze di determinate scelte

}
