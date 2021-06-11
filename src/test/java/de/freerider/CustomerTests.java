package de.freerider;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.model.Customer;
import de.freerider.model.Customer.Status;

@SpringBootTest
class CustomerTests {
	
private Customer mats;
private Customer thomas;


public CustomerTests() {
}

@BeforeEach
public void setUpEach() {
	mats = new Customer("Matsenstein", "Mats","Matsen97@web.de" );
	thomas = new Customer("Anders", "Thomas", "moderntom@gmx.de" );
}


@Test
void testIdNull() {
	assertNull(mats.getId());
	assertNull(thomas.getId());
}

@Test
void testSetId() {
	mats.setId("A231");
	assertNotNull(mats.getId());
}

@Test
void testSetIdOnlyOnce() {
	mats.setId("A222");
	assertNotNull(mats.getId());
	mats.setId("N100");
	assertEquals(mats.getId(), "A222");
}

@Test
void testResetId() {
	mats.setId("newId");
	mats.setId(null);
	assertNull(mats.getId());
}

@Test
void testNamesInitial() {
	assertNotNull(mats.getFirstName(), mats.getLastName());
	assertNotNull(thomas.getFirstName(), thomas.getLastName());
}

@Test
void testNamesSetNull() {
	mats.setFirstName(null);
	mats.setLastName(null);
	assertEquals(mats.getFirstName(),"");
	assertEquals(mats.getLastName(),"");
}

@Test
void testSetNames() {
	mats.setFirstName("Heinrich");
	mats.setLastName("Heinrichsen");
	assertEquals(mats.getFirstName(),"Heinrich");
	assertEquals(mats.getLastName(), "Heinrichsen");
	
}

@Test
void testContactsInitial() {
	assertNotNull(mats.getContact());
	assertNotNull(thomas.getContact());
}

@Test
void testContactsSetNull() {
	mats.setContact(null);
	assertEquals(mats.getContact(),"");
}

@Test
void testSetContact() {
	mats.setContact("Silvio Zahn");
	assertEquals(mats.getContact(),"Silvio Zahn");
}

@Test
void testStatusInitial() {
	assertEquals(mats.getStatus(),Customer.Status.New);
}

@Test
void testSetStatus() {
	mats.setStatus(Status.Active);
	assertEquals(mats.getStatus(),Customer.Status.Active);
	mats.setStatus(Status.Deleted);
	assertEquals(mats.getStatus(),Customer.Status.Deleted);
	mats.setStatus(Status.InRegistration);
	assertEquals(mats.getStatus(),Customer.Status.InRegistration);
	mats.setStatus(Status.Suspended);
	assertEquals(mats.getStatus(),Customer.Status.Suspended);
	mats.setStatus(Status.New);
	assertEquals(mats.getStatus(),Customer.Status.New);
}



















}