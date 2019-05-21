package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{

	private int t;
	private int migranti;
	private Country stato;
	
	public Evento(int t, int migranti, Country stato) {
		super();
		this.t = t;
		this.migranti = migranti;
		this.stato = stato;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getMigranti() {
		return migranti;
	}

	public void setMigranti(int migranti) {
		this.migranti = migranti;
	}

	public Country getStato() {
		return stato;
	}

	public void setStato(Country stato) {
		this.stato = stato;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.t - o.t;
	}
	
	
}
