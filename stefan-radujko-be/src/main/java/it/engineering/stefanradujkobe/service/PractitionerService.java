package it.engineering.stefanradujkobe.service;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;

import it.engineering.stefanradujkobe.dto.PractitionerDto;

public interface PractitionerService {
	PractitionerDto save(PractitionerDto practitioner);
	Page<PractitionerDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String name, String surname, String qualification, String organization, Boolean unassigned);
	PractitionerDto update(PractitionerDto practitioner) throws RuntimeException;
	Optional<PractitionerDto> findById(Long id);
	void delete(Long id) throws RuntimeException;
	Optional<PractitionerDto> findByNameAndSurnameAndOrganization(String name, String surname, Long id) throws RuntimeException;
	int countByOrganization(Long organizationId);
	void deleteOrganization(Long organizationId); //deletes reference to organization when organization is deleted
	List<PractitionerDto> findByOrganization(Long id);
}
