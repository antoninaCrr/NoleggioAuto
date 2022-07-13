package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.PriorityQueue;

import it.polito.tdp.noleggio.model.Event.EventType;

public class Simulatore {
	
	// Parametri di ingresso
	private int NC ; // number of cars --> vero param di simulazione
	private Duration T_IN = Duration.ofMinutes(10) ; // numero di min che intercorrono tra due potenziali clienti
	// usiamo degli oggetti Java per rappresentare il tempo
	private Duration T_TRAVEL = Duration.ofHours(1) ; // 1, 2, o 3 volte tanto
	
	// Valori calcolati in uscita --> SCOPO DEL NOSTRO SIMULATORE
	private int nClientiTot ;
	private int nClientiInsoddisfatti ;
	
	// Stato del mondo
	private int autoDisponibili ;
	
	// Coda degli eventi
	private PriorityQueue<Event> queue ; 
	
	// Costruttore
	public Simulatore(int NC) {
		this.NC = NC ; // "ti obbligo a dichiararlo quando costruisci la classe"
		this.queue = new PriorityQueue<>(); // qui creo la coda vuota di tipo Event
		this.autoDisponibili = NC ; // devo inizializzare lo stato del mondo altrimenti il simulatore non riesce a partire correttamente
	}
	
	// metodi di simulazione vera e propria:
	
	// 1-- esegue la simulazione
	// lui è un grosso loop
	public void run() {
		
		// finchè la coda non è vuota, devo processare il prossimo evento
		while(!this.queue.isEmpty()) {
			// ottiene e rimuove la testa della coda
			Event e = this.queue.poll() ;
			// elaborazione dell'evento
			processEvent(e);
		}
		
	}
	
	// devo riempira la coda prima di lanciare la simulazione
	public void caricaEventi() {
		
		LocalTime ora = LocalTime.of(8, 0) ; // .of prende ora e minuti
		// fincè l'ora è precedente all'ora di chiusura dell'autonoleggio
		while(ora.isBefore(LocalTime.of(16,0))) {
			// aggiungo un evento in cui arriva un cliente alla coda
			this.queue.add(new Event(ora, EventType.NUOVO_CLIENTE)) ;
			ora = ora.plus(T_IN) ; // incremento l'ora
		}
	}

	private void processEvent(Event e) {
		
		switch(e.getType()) {
		case NUOVO_CLIENTE:
			this.nClientiTot++ ;
			if(this.autoDisponibili>0) { // se ho ancora auto disponibili il cliente è soddisfatto, altrimenti ho un cliente insoddisfatto
				this.autoDisponibili-- ; // se do un'auto in prestito, devo decrementare il mio num di auto disponibili
				int ore = (int)(Math.random()*3)+1; // devo estrarre un numero casuale tra 1 e 3
				LocalTime oraRientro = e.getTime().plus(Duration.ofHours(ore * T_TRAVEL.toHours())) ; // l'ora di rientro sarà pari all'ora attuale, info fornita dall'evento stesso, più un duration di 1h o 2h o 3h 
				this.queue.add(new Event(oraRientro, EventType.AUTO_RESTITUITA)); // creo un nuovo oggetto di tipo evento ( lo creo di tipo auto_restituita poichè so che l'auto che sta uscendo prima o poi rientrerà) e lo aggiungo alla coda
			} else {
				this.nClientiInsoddisfatti++ ;
			}
			break;
		case AUTO_RESTITUITA:
			this.autoDisponibili++ ;
			// non ho bisogno di aggiornare nuovi param o di generare nuovi eventi
			break;
		}
	}

	// metodi per restituire i valori elaborati dal simulatore
	public int getnClientiTot() {
		return nClientiTot;
	}

	public int getnClientiInsoddisfatti() {
		return nClientiInsoddisfatti;
	}

	// metodi per impostare i valori in input
	public void setNC(int nC) {
		NC = nC;
	}

	public void setT_IN(Duration t_IN) {
		T_IN = t_IN;
	}

	public void setT_TRAVEL(Duration t_TRAVEL) {
		T_TRAVEL = t_TRAVEL;
	}
	
	
	

}
