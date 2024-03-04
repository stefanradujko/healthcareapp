package it.engineering.stefanradujkobe.dto;

import javax.validation.constraints.NotNull;

public class ExaminationPractitionerDto implements Dto {
	private Long id;
	@NotNull(message = "Examination is required!")
	private ExaminationDto examination;
	@NotNull(message = "Practitioner is required!")
	private PractitionerDto practitioner;
	
	public ExaminationPractitionerDto() {
	}

	public ExaminationPractitionerDto(Long id, ExaminationDto examination, PractitionerDto practitioner) {
		super();
		this.id = id;
		this.examination = examination;
		this.practitioner = practitioner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExaminationDto getExamination() {
		return examination;
	}

	public void setExamination(ExaminationDto examination) {
		this.examination = examination;
	}

	public PractitionerDto getPractitioner() {
		return practitioner;
	}

	public void setPractitioner(PractitionerDto practitioner) {
		this.practitioner = practitioner;
	}

	@Override
	public String toString() {
		return "ExaminationPractitionerDto [id=" + id + ", examination=" + examination + ", practitioner="
				+ practitioner + "]";
	}
	
}
