package it.engineering.stefanradujkobe.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "patient")
@Where(clause = "active = true")
@SQLDelete(sql = "UPDATE Patient SET active = false WHERE id=?")
public class PatientEntity implements it.engineering.stefanradujkobe.entity.Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String identifier;
	private Boolean active;
	private String name;
	private String surname;
	private String gender;
	private Date birthDate;
	private String adress;
	private String phone;
	private String email;
	private Boolean deceased;
	private String maritialStatus;
	@ManyToOne
	@JoinColumn(name = "custodian_organization_id")
	private OrganizationEntity organization;
	@ManyToOne
	@JoinColumn(name = "primary_practitioner_id")
	private PractitionerEntity practitioner;
	
	public PatientEntity() {
	}

	public PatientEntity(Long id, String identifier, Boolean active, String name, String surname, String gender,
			Date birthDate, String adress, String phone, String email, Boolean deceased, String maritialStatus,
			OrganizationEntity organization, PractitionerEntity practitioner) {
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

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	public PractitionerEntity getPractitioner() {
		return practitioner;
	}

	public void setPractitioner(PractitionerEntity practitioner) {
		this.practitioner = practitioner;
	}

	@Override
	public String toString() {
		return "PatientEntity [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", surname=" + surname + ", gender=" + gender + ", birthDate=" + birthDate + ", adress=" + adress
				+ ", phone=" + phone + ", email=" + email + ", deceased=" + deceased + ", maritialStatus="
				+ maritialStatus + ", organization=" + organization + ", practitioner=" + practitioner + "]";
	}

	
	
}
