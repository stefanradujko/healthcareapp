package it.engineering.stefanradujkobe.service;

import java.util.List;
import java.util.Optional;

import it.engineering.stefanradujkobe.dto.ExaminationPractitionerDto;

public interface ExaminationPractitionerService {
	ExaminationPractitionerDto save(ExaminationPractitionerDto examinationPractitioner);
	Optional<ExaminationPractitionerDto> findById(Long id);
	void delete(Long id);
	List<ExaminationPractitionerDto> findByExamination(Long id);
}
