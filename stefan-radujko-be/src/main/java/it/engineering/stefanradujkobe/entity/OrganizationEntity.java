package it.engineering.stefanradujkobe.entity;

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
@Table(name = "organization")
@Where(clause = "active = true")
@SQLDelete(sql = "UPDATE Organization SET active = false WHERE id=?")
public class OrganizationEntity implements it.engineering.stefanradujkobe.entity.Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String identifier;
	private Boolean active;
	@Column(unique = true)
	private String name;
	private String adress;
	private String phone;
	private String email;
	@ManyToOne
	@JoinColumn(name="type_id")
	private OrganizationTypeEntity organizationType;
	
	public OrganizationEntity() {
	}

	public OrganizationEntity(Long id, String identifier, Boolean active, String name, String adress, String phone,
			String email, OrganizationTypeEntity organizationType) {
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

	public OrganizationTypeEntity getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(OrganizationTypeEntity organizationType) {
		this.organizationType = organizationType;
	}

	@Override
	public String toString() {
		return "OrganizationEntity [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", adress=" + adress + ", phone=" + phone + ", email=" + email + ", organizationType="
				+ organizationType + "]";
	}

	
}
