package it.engineering.stefanradujkobe.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class PatientDto implements Dto {
	private Long id;
	@Length(min = 5, message = "Identifier must be at least 5 characters!")
	private String identifier;
	@NotNull(message = "Active is required!")
	private Boolean active;
	@NotBlank(message = "Name is required!")
	@Length(min = 3, message = "Name must be at least 3 characters!")
	private String name;
	@NotBlank(message = "Surname is required!")
	@Length(min = 3, message = "Surname must be at least 3 characters!")
	private String surname;
	private String gender;
	@NotNull(message = "Birth date is required!")
	private Date birthDate;
	private String adress;
	private String phone;
	@Pattern(regexp="[a-z0-9.]+@[a-z0-9.]+", message = "Email must contain @ character!")
	private String email;
	private Boolean deceased;
	private String maritialStatus;
	@NotNull(message="Organization is required!")
	private OrganizationDto organization;
	@NotNull(message="Practitioner is required!")
	private PractitionerDto practitioner;
	
	public PatientDto() {
	}

	public PatientDto(Long id, String identifier, Boolean active, String name, String surname, String gender,
			Date birthDate, String adress, String phone, String email, Boolean deceased, String maritialStatus,
			OrganizationDto organization, PractitionerDto practitioner) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.active = active;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.adress = adress;
		this.phone = phone;
		this.email = email;
		this.deceased = deceased;
		this.maritialStatus = maritialStatus;
		this.organization = organization;
		this.practitioner = practitioner;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public Boolean isDeceased() {
		return deceased;
	}

	public void setDeceased(Boolean deceased) {
		this.deceased = deceased;
	}

	public String getMaritialStatus() {
		return maritialStatus;
	}

	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}

	public OrganizationDto getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	public PractitionerDto getPractitioner() {
		return practitioner;
	}

	public void setPractitioner(PractitionerDto practitioner) {
		this.practitioner = practitioner;
	}

	@Override
	public String toString() {
		return "PatientDto [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", surname=" + surname + ", gender=" + gender + ", birthDate=" + birthDate + ", adress=" + adress
				+ ", phone=" + phone + ", email=" + email + ", deceased=" + deceased + ", maritialStatus="
				+ maritialStatus + ", organization=" + organization + ", practitioner=" + practitioner + "]";
	}

	
	
	
}
