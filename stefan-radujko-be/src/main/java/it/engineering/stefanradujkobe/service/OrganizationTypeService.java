package it.engineering.stefanradujkobe.service;

import java.util.List;
import java.util.Optional;

import it.engineering.stefanradujkobe.dto.OrganizationTypeDto;


public interface OrganizationTypeService {
	List<OrganizationTypeDto> findAll();
	Optional<OrganizationTypeDto> findById(Long id);
	
}
