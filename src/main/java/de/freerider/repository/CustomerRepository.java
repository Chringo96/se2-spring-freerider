package de.freerider.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.freerider.datamodel.Customer;


@Component
@Qualifier("CustomerRepository_Impl")
public class CustomerRepository implements CrudRepository<Customer, String> {
	//
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );
	HashMap<String,Customer> customers = new HashMap<>();

	@Override
	public <S extends Customer> S save(S entity)  {
		Customer customer = entity;
		if(entity!=null) {
			if(entity.getId() == null || entity.getId().equals("")) {
				String id;
				while(true) {
					id = idGen.nextId();
					if(customers.containsKey(id)==false) 
						break;
				}
				entity.setId(id);
					}
			if(customers.containsKey(entity.getId()))
				customer = customers.get(entity.getId());
			customers.put(entity.getId(), entity);
			return (S) customer;
			
//			else {return (S) customers.get(entity.getId());
//			}
			
			}
		else throw new IllegalArgumentException("Customer ist null");
	}
	

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		if(entities!=null)
			for (Customer customer: entities)
				save(customer);
		else throw new IllegalArgumentException("Liste ist null"); 
		return entities;
	}

	@Override
	public Optional<Customer> findById(String id) {
		Customer customer;
		if(id!=null)
			customer = customers.get(id);
		else throw new IllegalArgumentException("Id ist null");
		return Optional.ofNullable(customer);
	}

	@Override
	public boolean existsById(String id) {
		if(id!=null)
			if(customers.containsKey(id))
				return true;
			else return false;
		else throw new IllegalArgumentException("Id ist null");
	}

	@Override
	public Iterable<Customer> findAll() {
		
		return customers.values();
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> ids) {
		ArrayList<Customer> list = new ArrayList<>();
		if(ids!=null)
			for (String key : ids) {
				if((Object)findById(key)!=Optional.empty())
					list.add(findById(key).get());
		}
		else throw new IllegalArgumentException("Liste ist null");
		return list;
	}

	@Override
	public long count() {
		return customers.size();
	}

	@Override
	public void deleteById(String id) {
		if(id!=null)
		customers.remove(id);
		else throw new IllegalArgumentException("Id ist null");
	}

	@Override
	public void delete(Customer entity){
		
		if(entity!=null)
			customers.remove(entity.getId(), entity);
		else throw new IllegalArgumentException("Customer ist null");
		if(entity.getId()==null)
			throw new IllegalArgumentException("ID des Customers ist null");
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		if(ids!=null)
			for(String key : ids)
				deleteById(key);
		else throw new IllegalArgumentException("ID ist null");
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) throws IllegalArgumentException {
		if(entities!=null)
			for(Customer customer : entities)
				delete(customer);
		else throw new IllegalArgumentException("Liste ist null");
	}

	@Override
	public void deleteAll() {
		customers.clear();	
	}


	
}

