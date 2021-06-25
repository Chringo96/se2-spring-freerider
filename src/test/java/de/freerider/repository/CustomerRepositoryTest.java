package de.freerider.repository;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.model.*;

@SpringBootTest
class CustomerRepositoryTest{
	
	@Autowired
	CrudRepository<Customer,String> customerRepository;
	// two sample customers
	private Customer mats;
	private Customer thomas;
	ArrayList<Customer> liste = new ArrayList<>();
	
	
	@BeforeEach
	public void setUpEach() {
		mats = new Customer("Matsenstein", "Mats","Matsen97@web.de" );
		thomas = new Customer("Anders", "Thomas", "moderntom@gmx.de" );
		customerRepository.deleteAll();
		liste.clear();
	}
	
	@Test
	void constructerTest() {
		assertEquals(0,customerRepository.count());
	}
	
	
	@Test
	void saveTest() {
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(2,customerRepository.count());
		assertEquals(thomas,customerRepository.findById(thomas.getId()).get());
		assertEquals(mats,customerRepository.findById(mats.getId()).get());
	}
	
	@Test
	void saveNullTestId() {
		Customer mark = new Customer("Isenberg","Mark","markimark@web.de");
		mark.setId(null);
		assertThrows( IllegalArgumentException.class, () -> {
			customerRepository.delete( mark );
		});
		customerRepository.save(mark);
		assertNotNull(mark.getId());
		assertNotNull(customerRepository.findById(mark.getId()));
		assertEquals(1,customerRepository.count());
	}
	
	@Test
	void saveNotNullId() {
		mats.setId("A222");
		customerRepository.save(mats);
		assertEquals(mats,customerRepository.findById("A222").get());
	}
	@Test
	void saveNull() {
		assertThrows(IllegalArgumentException.class, () ->customerRepository.save(null));
		assertEquals(0,customerRepository.count());
	}
	
	@Test
	void saveSameObject() {
		customerRepository.save(mats);
		customerRepository.save(mats);
		assertEquals(1,customerRepository.count());
		
	}
	
	@Test
	void saveSameID() {
		mats.setId("B123");
		thomas.setId("B123");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(1,customerRepository.count());
		assertEquals(thomas,customerRepository.findById("B123").get());
		assertTrue(customerRepository.findById("B123").get()==thomas);
		assertFalse(customerRepository.findById("B123").get()==mats);

	}
	
	@Test
	void saveAll() {
		liste.add(mats);
		liste.add(thomas);
		customerRepository.saveAll(liste);
		assertEquals(2,customerRepository.count());
		assertNotNull(customerRepository.findById(mats.getId()));
		assertNotNull(customerRepository.findById(thomas.getId()));
	}
	
	@Test
	void saveAllNullId() {
		mats.setId(null);
		liste.add(mats);
		liste.add(thomas);
		customerRepository.saveAll(liste);
		assertEquals(2,customerRepository.count());
		assertNotNull(customerRepository.findById(mats.getId()));
		assertNotNull(mats.getId());
	}
	
	@Test
	void saveAllCorrectId() {
		mats.setId("333");
		liste.add(mats);
		liste.add(thomas);
		customerRepository.saveAll(liste);
		assertEquals(mats,customerRepository.findById("333").get());
		
	}
	
	@Test
	void saveAllNull() {
		liste.add(mats);
		liste.add(null);
		liste.add(thomas);
		assertThrows(IllegalArgumentException.class, () ->customerRepository.saveAll(liste));
		assertEquals(1,customerRepository.count());
	}
	
	@Test
	void saveAllSameId() {
		mats.setId("222");
		thomas.setId("222");	
		liste.add(mats);
		liste.add(thomas);
		customerRepository.saveAll(liste);
		assertEquals(1,customerRepository.count());
	}
	
	@Test
	void findById() {
		thomas.setId("555");
		customerRepository.save(thomas);
		assertEquals(thomas, customerRepository.findById("555").get());
	}
	
	@Test
	void findByIdAfterDelete() {
		thomas.setId("555");
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertEquals(thomas, customerRepository.findById("555").get());
		customerRepository.delete(thomas);
		assertEquals(Optional.empty(),customerRepository.findById("555"));
	}
	
	@Test
	void findByIdNull() {
		customerRepository.save(thomas);
		assertThrows(IllegalArgumentException.class,() ->customerRepository.findById(null));
	}
	
	@Test
	void findAll() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		liste.addAll((Collection)customerRepository.findAll());
		assertTrue(liste.contains(mats));
		assertTrue(liste.contains(thomas));
	}
	@Test
	void findAllEmpty() {
		liste.addAll((Collection)customerRepository.findAll());
		assertTrue(liste.isEmpty());
	}
	
	@Test
	void findAllById() {
		liste.add(mats);
		liste.add(thomas);
		ArrayList<String> k = new ArrayList<>();
		customerRepository.saveAll(liste);
		k.add(mats.getId());
		k.add(thomas.getId());
		assertEquals(liste,customerRepository.findAllById(k));

	}
	@Test
	void findAllByIdSomeWrong() {
		ArrayList<Customer> expectedList = new ArrayList<>();
		expectedList.add(mats);
		liste.add(mats);
		liste.add(thomas);
		ArrayList<String> k = new ArrayList<>();
		customerRepository.save(mats);
		k.add(mats.getId());
		k.add(thomas.getId());
		assertThrows(IllegalArgumentException.class,() ->customerRepository.findAllById(k));
	}
	
	@Test
	void findAllByIdNull() {
		ArrayList<Customer> expectedList = new ArrayList<>();
		expectedList.add(mats);
		liste.add(mats);
		liste.add(thomas);
		ArrayList<String> k = new ArrayList<>();
		customerRepository.saveAll(liste);
		k.add(mats.getId());
		k.add(null);
		assertThrows(IllegalArgumentException.class, () -> customerRepository.findAllById(k));
	}
	@Test
	void count() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertEquals(2,customerRepository.count());
	}
	@Test
	void delete() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertEquals(mats,customerRepository.findById(mats.getId()).get());
		customerRepository.delete(mats);
		assertEquals(Optional.empty(),customerRepository.findById(mats.getId()));
	}
	@Test
	void deleteNull() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertEquals(2,customerRepository.count());
		assertThrows(IllegalArgumentException.class, () -> customerRepository.delete( null ));
		assertEquals(2,customerRepository.count());
	}
	@Test
	void countAfterDelete() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertEquals(2,customerRepository.count());
		customerRepository.delete(mats);
		assertEquals(1,customerRepository.count());

	}
	@Test
	void deleteById() {
		mats.setId("333");
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertEquals(mats,customerRepository.findById("333").get());
		customerRepository.deleteById("333");
		assertEquals(Optional.empty(),customerRepository.findById("333"));
	}
	
	@Test
	void deleteByIdNull() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertThrows(IllegalArgumentException.class,() ->customerRepository.deleteById(null));
		assertEquals(2,customerRepository.count());
	}
	@Test
	void deleteAllById() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		ArrayList<String> k = new ArrayList<>();
		k.add(mats.getId());
		k.add(thomas.getId());
		assertEquals(2,customerRepository.count());
		customerRepository.deleteAllById(k);
		assertEquals(0,customerRepository.count());
	}
	@Test
	void deleteAllByIdNotThere() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		ArrayList<String> k = new ArrayList<>();
		k.add(mats.getId());
		k.add("111");
		k.add(thomas.getId());
		assertEquals(2,customerRepository.count());
		customerRepository.deleteAllById(k);
		assertEquals(0,customerRepository.count());
	}
	@Test
	void deleteAllByIdNull() {
		customerRepository.save(thomas);
		customerRepository.save(mats);
		ArrayList<String> k = new ArrayList<>();	
		k.add(null);
		k.add(thomas.getId());
		assertEquals(2,customerRepository.count());
		assertThrows(IllegalArgumentException.class,() ->customerRepository.deleteAllById(k));
		assertEquals(2,customerRepository.count());
	}
	@Test
	void deleteAllList() {
		Customer mark = new Customer("Isenberg","Mark","markimark@web.de");
		customerRepository.save(mark);
		customerRepository.save(thomas);
		customerRepository.save(mats);	
		liste.add(mats);
		liste.add(thomas);
		assertEquals(3,customerRepository.count());
		customerRepository.deleteAll(liste);
		assertEquals(1,customerRepository.count());
	}
	@Test
	void deleteAllListNotThere() {	
		Customer mark = new Customer("Isenberg","Mark","markimark@web.de");
		mark.setId("333");
		customerRepository.save(thomas);
		customerRepository.save(mats);	
		liste.add(mats);
		liste.add(mark);
		assertEquals(2,customerRepository.count());
		customerRepository.deleteAll(liste);
		assertEquals(1,customerRepository.count());
	}
	@Test
	void deleteAllListNull() {
		Customer mark = new Customer("Isenberg","Mark","markimark@web.de");
		customerRepository.save(mark);		
		customerRepository.save(thomas);
		customerRepository.save(mats);	
		liste.add(null);
		liste.add(mark);
		assertEquals(3,customerRepository.count());
		assertThrows(IllegalArgumentException.class, () -> customerRepository.deleteAll(liste));
		assertEquals(3,customerRepository.count());
	}
	@Test
	void deleteAll() {
		Customer mark = new Customer("Isenberg","Mark","markimark@web.de");
		customerRepository.save(mark);		
		customerRepository.save(thomas);
		customerRepository.save(mats);
		assertEquals(3,customerRepository.count());
		customerRepository.deleteAll();
		assertEquals(0,customerRepository.count());
		
	}

	
}