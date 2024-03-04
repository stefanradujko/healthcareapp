package it.engineering.stefanradujkobe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import it.engineering.stefanradujkobe.dto.PatientDto;

public interface PatientService {
	PatientDto save(PatientDto patient);
	Page<PatientDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String name, String surname, String organization, String practName, String practSurname, Boolean unassigned);
	PatientDto update(PatientDto patient) throws RuntimeException;
	Optional<PatientDto> findById(Long id);
	void delete(Long id) throws RuntimeException;
	List<PatientDto> findAll();
	Optional<PatientDto> findByNameAndSurname(String name, String surname);
	void deletePractitioner(Long practitionerId); //deletes reference to practitioner when patient is deleted
	void deleteOrganization(Long organizationId); //deletes reference to organization when organization is deleted
}
