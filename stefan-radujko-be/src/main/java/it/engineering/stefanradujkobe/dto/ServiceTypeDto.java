package it.engineering.stefanradujkobe.dto;

public class ServiceTypeDto implements Dto {
	private Long id;
	private String identifier;
	private String name;
	
	public ServiceTypeDto() {
	}

	public ServiceTypeDto(Long id, String identifier, String name) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ServiceTypeDto [id=" + id + ", identifier=" + identifier + ", name=" + name + "]";
	}

	
	
}
