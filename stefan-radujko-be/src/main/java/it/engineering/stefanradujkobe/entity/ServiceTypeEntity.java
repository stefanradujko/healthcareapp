package it.engineering.stefanradujkobe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_type")
public class ServiceTypeEntity implements it.engineering.stefanradujkobe.entity.Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String identifier;
	@Column(unique = true)
	private String name;
	
	public ServiceTypeEntity() {
	}

	public ServiceTypeEntity(Long id, String identifier, String name) {
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
		return "ServiceTypeEntity [id=" + id + ", identifier=" + identifier + ", name=" + name + "]";
	}
	
	
}
