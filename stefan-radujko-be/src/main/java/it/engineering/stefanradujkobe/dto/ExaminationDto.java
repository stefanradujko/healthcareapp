package it.engineering.stefanradujkobe.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ExaminationDto implements Dto {
	private Long id;
	@Length(min = 5, message = "Identifier must be at least 5 characters!")
	private String identifier;
	@NotNull(message = "Patient is required!")
	private PatientDto patient;
	@NotNull(message = "Organization is required!")
	private OrganizationDto organization;
	@NotBlank(message = "Status is required!")
	private String status;
	@NotNull(message = "Service type is required!")
	private ServiceTypeDto serviceType;
	private String priority;
	private Date startDate;
	private Date endDate;
	private String diagnosis;
	
	public ExaminationDto() {
	}

	public ExaminationDto(Long id, String identifier, PatientDto patient, OrganizationDto organization,
			String status, ServiceTypeDto serviceType, String priority, Date startDate,
			Date endDate, String diagnosis) {
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

	public PatientDto getPatient() {
		return patient;
	}

	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}

	public OrganizationDto getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDto organization) {
		this.organization = organization;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ServiceTypeDto getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceTypeDto serviceType) {
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
		return "ExaminationDto [id=" + id + ", identifier=" + identifier + ", patient=" + patient + ", organization="
				+ organization + ", status=" + status + ", serviceType="
				+ serviceType + ", priority=" + priority + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", diagnosis=" + diagnosis + "]";
	}

	
}
