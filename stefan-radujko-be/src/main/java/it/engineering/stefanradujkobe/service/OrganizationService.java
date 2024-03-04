package it.engineering.stefanradujkobe.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.data.domain.Page;

import it.engineering.stefanradujkobe.dto.OrganizationDto;

public interface OrganizationService {
	OrganizationDto save(OrganizationDto organization) throws EntityExistsException;
	Page<OrganizationDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String name, String orgType);
	OrganizationDto update(OrganizationDto organization) throws RuntimeException;
	Optional<OrganizationDto> findById(Long id);
	void delete(Long id) throws RuntimeException;
	List<OrganizationDto> findAll();
	Optional<OrganizationDto> findByName(String name);
}
