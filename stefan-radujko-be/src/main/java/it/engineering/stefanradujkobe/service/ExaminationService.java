package it.engineering.stefanradujkobe.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import it.engineering.stefanradujkobe.dto.ExaminationDto;


public interface ExaminationService {
	ExaminationDto save(ExaminationDto examination);
	Page<ExaminationDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String organization, String name, String surname, String serviceType, String status, String priority);
	ExaminationDto update(ExaminationDto examination) throws RuntimeException;
	Optional<ExaminationDto> findById(Long id);
	void delete(Long id) throws RuntimeException;
	boolean existsByOrganization(Long organizatioId);  //used for checking if table contains organization 
	//that has status in-progress for any of it's examinations
	boolean existsByPatient(Long patientId); //same as existsByOrganization(...), only for patient
	boolean existsByPractitioner(Long practitionerId); //same as existsByOrganization(...), only for practitioner
	int countByOrganization(Long organizationId); //number of exams for specific organization
	int countActiveByOrganization(Long organizationId); //number of exams in-progress for specific organization
	void deleteByPatient(Long id); // for deleting exam when patient is deleted
	void deleteByOrganization(Long id); //for deleting exam when organization is deleted
}
