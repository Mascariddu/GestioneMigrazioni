package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {

	//Modello, stato del sistema ad ogni passo
	private Graph<Country, DefaultEdge> grafo;
	
	//tipi di evento che danno origine alla coda prioritaria
	private PriorityQueue<Evento> queue;
	
	//parametri simulazione
	private int numeroMigranti = 1000;
	private Country partenza;
	
	//output
	private int T;
	private Map<Country, Integer> stanziali;
	
	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		
		//ricevo parametri
		this.partenza = partenza;
		this.grafo = grafo;
		
		//impostazione stato iniziale
		this.T = 1;
		stanziali = new HashMap<>();
		for (Country c : this.grafo.vertexSet()) {
			stanziali.put(c, 0);
		}
		queue = new PriorityQueue<>();
		
		//inserisco il primo evento
		queue.add(new Evento(T, numeroMigranti, partenza));
	}
	
	public void run() {
		//Estraggo un evento per volta alla coda e lo eseguo
		//finchè la coda non si svuota
		Evento e;
		
		while( (e = queue.poll()) != null) {
			//ESEGUO EVENTO
			this.T = e.getT();
			
			int nPersone  = e.getMigranti();
			Country stato = e.getStato();
			
			List<Country> confinanti = Graphs.neighborListOf(this.grafo, stato);
			
			int migranti = (nPersone/2)/confinanti.size();
			
			if(migranti > 0) {
				//le persone si possono muovere
				for(Country confinante : confinanti)
					queue.add(new Evento(e.getT()+1, migranti, confinante));
			}
			
			int stanziali = nPersone - migranti*confinanti.size();
			this.stanziali.put(stato, this.stanziali.get(stato)+stanziali);
		}
	}

	public int getT() {
		// TODO Auto-generated method stub
		return T;
	}

	public Map<Country, Integer> getStanziali() {
		// TODO Auto-generated method stub
		return this.stanziali;
	}
}
