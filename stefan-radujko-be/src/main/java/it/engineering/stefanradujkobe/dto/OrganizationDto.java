package it.engineering.stefanradujkobe.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class OrganizationDto implements Dto {
	private Long id;
	@Length(min = 5, message = "Identifier must be at least 5 characters!")
	private String identifier;
	@NotNull(message = "Active is required!")
	private Boolean active;
	@NotBlank(message = "Name is required!")
	@Length(min = 5, message = "Name must be at least 5 characters!")
	private String name;
	private String adress;
	private String phone;
	@Pattern(regexp="[a-z0-9.]+@[a-z0-9.]+", message = "Email must contain @ character!")
	private String email;
	@NotNull(message="Organization type is required!")
	private OrganizationTypeDto organizationType;
	
	public OrganizationDto() {
	}

	public OrganizationDto(Long id, String identifier, Boolean active, String name, String adress, String phone,
			String email, OrganizationTypeDto organizationType) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.active = active;
		this.name = name;
		this.adress = adress;
		this.phone = phone;
		this.email = email;
		this.organizationType = organizationType;
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

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public OrganizationTypeDto getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(OrganizationTypeDto organizationType) {
		this.organizationType = organizationType;
	}

	@Override
	public String toString() {
		return "OrganizationDto [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", adress=" + adress + ", phone=" + phone + ", email=" + email + ", organizationType="
				+ organizationType + "]";
	}

	
	
}
