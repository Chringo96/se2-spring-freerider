package de.freerider.datamodel;


public class Customer{

private String id;
private String lastName;
private String firstName;
private String contact;
private  Status status;
public enum Status{
	New,
	InRegistration,
	Active,
	Suspended,
	Deleted;
}

public Customer(String lastName, String firstName, String contact){
		setLastName(lastName);
		setFirstName(firstName);
		setContact(contact);
        id = null;
        status = Status.New;
    }

    public String getId() {
	return id;
}
// Anmerkung: Es wurde gesagt ALLE Attribute sollen einen getter/setter bekommen
public void setId(String id)  {

	if(this.id == null || id == null )
		this.id = id;
	
		
	}


public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	if(lastName != null)
		this.lastName = lastName;
	else
		this.lastName = "";
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	if(firstName!=  null)
		this.firstName = firstName;
	else
		this.firstName = "";
}

public String getContact() {
	return contact;
}

public void setContact(String contact) {
	if(contact != null)
		this.contact = contact;
	else
		this.contact = "";
}

public Status getStatus() {
	return status;
}

public void setStatus(Status status) {
	this.status = status;
}

	
}