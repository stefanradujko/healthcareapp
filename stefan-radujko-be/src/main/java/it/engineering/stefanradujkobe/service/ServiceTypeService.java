package it.engineering.stefanradujkobe.service;

import java.util.List;
import java.util.Optional;

import it.engineering.stefanradujkobe.dto.ServiceTypeDto;

public interface ServiceTypeService {
	List<ServiceTypeDto> findAll();
	Optional<ServiceTypeDto> findById(Long id);
	void saveAll(List<ServiceTypeDto> list);
}
