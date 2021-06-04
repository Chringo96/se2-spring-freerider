package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.freerider.model.Customer;


@Component
public class CustomerRepository implements CrudRepository<Customer, String> {
	//
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );
	HashMap<String,Customer> customers = new HashMap<>();

	@Override
	public <S extends Customer> S save(S entity) {
		if(entity.getId() == null || entity.getId().equals("")) {
			String id;
			while(true) {
				id = idGen.nextId();
				if(customers.containsKey(id)==false) 
				break;
			}
			entity.setId(id);
				}
		customers.put(entity.getId(), entity);
		return entity;
	}
	

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		for (Customer customer: entities)
			save(customer);
		return entities;
	}

	@Override
	public Optional<Customer> findById(String id) {
		Customer customer = customers.get(id);
		return Optional.of(customer);
	}

	@Override
	public boolean existsById(String id) {
		if(customers.containsKey(id))
			return true;
		return false;
	}

	@Override
	public Iterable<Customer> findAll() {
		
		return customers.values();
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> ids) {
		ArrayList<Customer> list = new ArrayList<>();
		for (String key : ids)
			findById(key);
		return list;
	}

	@Override
	public long count() {
		return customers.size();
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		customers.remove(id);
	}

	@Override
	public void delete(Customer entity) {
		customers.remove(entity.getId(), entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub
		for(String key : ids)
			deleteById(key);
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		for(Customer customer : entities)
			delete(customer);
	}

	@Override
	public void deleteAll() {
		customers.clear();	
	}


	
}
