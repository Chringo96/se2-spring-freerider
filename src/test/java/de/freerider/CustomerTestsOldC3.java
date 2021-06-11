package de.freerider;

import java.util.ArrayList;

import de.freerider.model.*;
import de.freerider.repository.*;

class CustomerTestsOldC3 {
	
	public static void main (String[] args) {
	Customer muller= new Customer("Muller", "Chris","chrismuller3@gmx.de" );
	Customer holly= new Customer("Holly", "Pony","ponyholly6@gmx.de" );
	Customer eva= new Customer("Eva", "Erika","erieva@gmx.de" );
	Customer schmidt= new Customer("Schmidt", "Horst","schmidt7@gmail.de" );
	Customer grobian= new Customer("Grobian", "Gerard","grobger@hotmail.com");
	
	
	ArrayList<Customer> liste = new ArrayList<>();
	liste.add(muller);
	liste.add(holly);
	liste.add(eva);
	liste.add(schmidt);
	liste.add(grobian);
	CustomerRepository carSharing = new CustomerRepository();
	
	//save test
		carSharing.save(muller);
		carSharing.save(holly);
	//count test
	System.out.println("count funktioniert + Zwei Objekte sollte drinne sein:  "+ carSharing.count());
	
	// deletebyId test
	carSharing.deleteById(muller.getId());
	
	System.out.println("Ein Objekt "+ carSharing.count());
	
//	Delete test
	carSharing.delete(holly);
	
	System.out.println("Sollte 0 sein: " +carSharing.count());
	// SaveAll Test
	carSharing.saveAll(liste);
	System.out.println("5 Objekte sollten drinne sein:  "+ carSharing.count());

	
	//DeleteAll Test
	carSharing.deleteAll();
	System.out.println("Sollte 0 ausgeben:"+ carSharing.count() );
	
	carSharing.saveAll(liste);
	//DelteById test
	carSharing.deleteById(eva.getId());
	System.out.println("Sollte 4 ausgeben:"+ carSharing.count() );
	//DeleteAllById Test
	ArrayList<String> idTest = new ArrayList<>();
	idTest.add(muller.getId());
	idTest.add(grobian.getId());
	carSharing.deleteAllById(idTest);
	System.out.println("Sollte 2 ausgeben:"+ carSharing.count() );
	
	//deleteAll(Iterable<? extends Customer> entities) test
	carSharing.deleteAll(liste);
	System.out.println("Sollte 0 ausgeben:"+ carSharing.count() );
	
	carSharing.saveAll(liste);
	
	//existsbyId
	System.out.println("Sollte TRUE sein: "+ carSharing.existsById(schmidt.getId()));
	System.out.println("Sollte False sein: "+ carSharing.existsById("77889sada"));
	
	// AUFGABE 8
	System.out.println("Wieviele Objekete:"+ carSharing.count() );
	carSharing.save(muller);
	System.out.println("Wieviele Objekete nach doppelterm Muller:"+ carSharing.count() );
}
	
	
}