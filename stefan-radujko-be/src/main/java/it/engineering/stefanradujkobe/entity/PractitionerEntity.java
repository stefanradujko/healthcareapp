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

@Entity
@Table(name = "practitioner")
@SQLDelete(sql = "UPDATE Practitioner SET active = false WHERE id=?")
public class PractitionerEntity implements it.engineering.stefanradujkobe.entity.Entity{
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
	private String qualification;
	@Column(unique = true)
	private String username;
	private String password;
	@ManyToOne
	@JoinColumn(name = "organization_id")
	private OrganizationEntity organization;
	
	
	public PractitionerEntity() {
	}

	public PractitionerEntity(Long id, String identifier, Boolean active, String name, String surname, String gender,
			Date birthDate, String adress, String phone, String email, String qualification, String username,
			String password, OrganizationEntity organization) {
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
		this.qualification = qualification;
		this.username = username;
		this.password = password;
		this.organization = organization;
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

	public Boolean getActive() {
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

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	@Override
	public String toString() {
		return "PractitionerEntity [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", surname=" + surname + ", gender=" + gender + ", birthDate=" + birthDate + ", adress=" + adress
				+ ", phone=" + phone + ", email=" + email + ", qualification=" + qualification + ", username="
				+ username + ", password=" + password + ", organization=" + organization + "]";
	}
	
}
