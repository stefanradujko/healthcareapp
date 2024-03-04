package it.engineering.stefanradujkobe.service.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import it.engineering.stefanradujkobe.converter.OrganizationConverter;
import it.engineering.stefanradujkobe.dao.OrganizationDao;
import it.engineering.stefanradujkobe.dto.OrganizationDto;
import it.engineering.stefanradujkobe.entity.OrganizationEntity;
import it.engineering.stefanradujkobe.service.ExaminationService;
import it.engineering.stefanradujkobe.service.OrganizationService;
import it.engineering.stefanradujkobe.service.PatientService;
import it.engineering.stefanradujkobe.service.PractitionerService;

@Service
public class OrganizationServiceImpl implements OrganizationService {
	
	private OrganizationDao organizationDao;
	private OrganizationConverter converter;
	private ExaminationService examinationService;
	private PractitionerService practitionerService;
	private PatientService patientService;
	

	public OrganizationServiceImpl(OrganizationDao organizationDao, OrganizationConverter converter, 
			ExaminationService examinationService, PractitionerService practitionerService, PatientService patientService) {
		super();
		this.organizationDao = organizationDao;
		this.converter = converter;
		this.examinationService = examinationService;
		this.practitionerService = practitionerService;
		this.patientService = patientService;
	}

	@Override
	public OrganizationDto save(OrganizationDto organization) throws EntityExistsException {
		Optional<OrganizationEntity> org = organizationDao.findById(organization.getId());
		if (org.isPresent()) {
			throw new EntityExistsException("Organization already exists!");
		}
		OrganizationEntity savedEntity = organizationDao.save(converter.toEntity(organization));
		return converter.toDto(savedEntity);
	}

	@Override
	public Page<OrganizationDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String name, String orgType) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		Page<OrganizationDto> entites =	organizationDao.findByFilters(pageable, name, orgType).map(converter :: toDto);
		return entites;
	}

	@Override
	public OrganizationDto update(OrganizationDto organization) throws RuntimeException {
		Optional<OrganizationEntity> org = organizationDao.findById(organization.getId());
		if (!org.isPresent()) {
			throw new RuntimeException("Organization doesn't exists!");
		}
		OrganizationEntity savedEntity = organizationDao.save(converter.toEntity(organization));
		return converter.toDto(savedEntity);
	}

	@Override
	public Optional<OrganizationDto> findById(Long id) {
		Optional<OrganizationEntity> org = organizationDao.findById(id);
		if (org.isPresent()) {
			return Optional.of(converter.toDto(org.get()));
		}
		return Optional.empty();
	}

	@Override
	public void delete(Long id) throws RuntimeException {
		Optional<OrganizationEntity> org = organizationDao.findById(id);
		if (!org.isPresent()) {
			throw new RuntimeException("Organization doesn't exists!");
		}
		if(examinationService.existsByOrganization(id)) {
			throw new RuntimeException("Organization has examinations in progress!");
		}
		examinationService.deleteByOrganization(id);
		practitionerService.deleteOrganization(id);
		patientService.deleteOrganization(id);
		organizationDao.deleteById(id);
		
	}

	@Override
	public List<OrganizationDto> findAll() {
		List<OrganizationEntity> orgList = organizationDao.findAll();
		return orgList.stream().map(org -> {
			return converter.toDto(org);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<OrganizationDto> findByName(String name) {
		Optional<OrganizationEntity> org = organizationDao.findByName(name);
		if (org.isPresent()) {
			return Optional.of(converter.toDto(org.get()));
		}
		return Optional.empty();
	}
	
	

}
