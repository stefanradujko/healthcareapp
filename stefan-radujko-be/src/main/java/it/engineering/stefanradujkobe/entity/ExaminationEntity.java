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
@Table(name = "examination")
@Where(clause = "status != 'entered-in-error'")
@SQLDelete(sql = "UPDATE Examination SET status = 'entered-in-error' WHERE id=?")
public class ExaminationEntity implements it.engineering.stefanradujkobe.entity.Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String identifier;
	@ManyToOne
	@JoinColumn(name="patient_id")
	private PatientEntity patient;
	@ManyToOne
	@JoinColumn(name="organization_id")
	private OrganizationEntity organization;
	private String status;
	@ManyToOne
	@JoinColumn(name="service_type_id")
	private ServiceTypeEntity serviceType;
	private String priority;
	private Date startDate;
	private Date endDate;
	private String diagnosis;
	
	public ExaminationEntity() {
	}

	public ExaminationEntity(Long id, String identifier, PatientEntity patient, OrganizationEntity organization,
			String status, ServiceTypeEntity serviceType, String priority,
			Date startDate, Date endDate, String diagnosis) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.patient = patient;
		this.organization = organization;
		this.status = status;
		this.serviceType = serviceType;
		this.priority = priority;
		this.startDate = startDate;
		this.endDate = endDate;
		this.diagnosis = diagnosis;
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

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ServiceTypeEntity getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceTypeEntity serviceType) {
		this.serviceType = serviceType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Override
	public String toString() {
		return "ExaminationEntity [id=" + id + ", identifier=" + identifier + ", patient=" + patient + ", organization="
				+ organization + ", status=" + status + ", serviceType="
				+ serviceType + ", priority=" + priority + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", diagnosis=" + diagnosis + "]";
	}

	

	
	
}
