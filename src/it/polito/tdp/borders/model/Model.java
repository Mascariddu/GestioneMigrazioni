package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Simulatore simulatore;
	private Graph<Country, DefaultEdge> graph ;
	private List<Country> countries ;
	private Map<Integer,Country> countriesMap ;
	
	public Model() {
		this.countriesMap = new HashMap<>() ;
		simulatore = new Simulatore();
	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;

		BordersDAO dao = new BordersDAO() ;
		
		//vertici
		dao.getCountriesFromYear(anno,this.countriesMap) ;
		Graphs.addAllVertices(graph, this.countriesMap.values()) ;
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno) ;
		for( Adiacenza c: archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), 
					this.countriesMap.get(c.getState2no())) ;
			
		}
	}
	
	public List<CountryAndNumber> getCountryAndNumber() {
		List<CountryAndNumber> list = new ArrayList<>() ;
		
		for(Country c: graph.vertexSet()) {
			list.add(new CountryAndNumber(c, graph.degreeOf(c))) ;
		}
		Collections.sort(list);
		return list ;
	}

	public List<Country> getCountries() {
		// TODO Auto-generated method stub
		List<Country> result = new ArrayList<Country>();
		for(Country country : this.countriesMap.values())
			result.add(country);
		Collections.sort(result);
		return result;
	}

	public void simula(Country partenza) {
		// TODO Auto-generated method stub
		System.out.println("Seno");
		simulatore.init(partenza,this.graph);
		System.out.println("Seno");
		simulatore.run();
		System.out.println("Seno");

	}

	public int getLastT() {
		// TODO Auto-generated method stub
		return simulatore.getT();
	}

	public List<CountryAndNumber> getStanziali() {
		// TODO Auto-generated method stub
		Map<Country, Integer> stanziali = this.simulatore.getStanziali();
		List<CountryAndNumber> stanzialiList = new ArrayList<CountryAndNumber>();
		for(Country country : stanziali.keySet()) {
			CountryAndNumber cn = new CountryAndNumber(country, stanziali.get(country));
			stanzialiList.add(cn);
		}
		
		Collections.sort(stanzialiList);
		return stanzialiList;
	}
	
	

}
