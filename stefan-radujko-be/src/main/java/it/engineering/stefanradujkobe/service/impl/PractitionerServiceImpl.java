package it.engineering.stefanradujkobe.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.engineering.stefanradujkobe.converter.PractitionerConverter;
import it.engineering.stefanradujkobe.dao.PractitionerDao;
import it.engineering.stefanradujkobe.dto.PractitionerDto;
import it.engineering.stefanradujkobe.entity.PractitionerEntity;
import it.engineering.stefanradujkobe.service.ExaminationService;
import it.engineering.stefanradujkobe.service.PatientService;
import it.engineering.stefanradujkobe.service.PractitionerService;

@Service
public class PractitionerServiceImpl implements PractitionerService {
	private PractitionerDao practitionerDao;
	private PractitionerConverter converter;
	private ExaminationService examinationService;
	private PatientService patientService;
	
	public PractitionerServiceImpl(PractitionerDao practitionerDao, PractitionerConverter converter, ExaminationService examinationService, PatientService patientService) {
		super();
		this.practitionerDao = practitionerDao;
		this.converter = converter;
		this.examinationService = examinationService;
		this.patientService = patientService;
	}

	@Override
	public PractitionerDto save(PractitionerDto practitioner){
		PractitionerEntity savedEntity = practitionerDao.save(converter.toEntity(practitioner));
		return converter.toDto(savedEntity);
	}

	@Override
	public Page<PractitionerDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String name, String surname, String qualification, String organization, Boolean unassigned) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		if(unassigned) {
			Page<PractitionerDto> entites =	practitionerDao.findByFilterUnassigned(pageable, name, surname, qualification).map(converter :: toDto);
			return entites;
		} else {
			Page<PractitionerDto> entites =	practitionerDao.findByFilter(pageable, name, surname, qualification, organization).map(converter :: toDto);
			return entites;
		}
	}

	@Override
	public PractitionerDto update(PractitionerDto practitioner) throws RuntimeException {
		Optional<PractitionerEntity> pract = practitionerDao.findById(practitioner.getId());
		if (!pract.isPresent()) {
			throw new RuntimeException("Practitioner doesn't exists!");
		}
		PractitionerEntity savedEntity = practitionerDao.save(converter.toEntity(practitioner));
		return converter.toDto(savedEntity);
	}

	@Override
	public Optional<PractitionerDto> findById(Long id) {
		Optional<PractitionerEntity> pract = practitionerDao.findById(id);
		if (pract.isPresent()) {
			return Optional.of(converter.toDto(pract.get()));
		}
		return Optional.empty();
	}

	@Override
	public void delete(Long id) throws RuntimeException {
		Optional<PractitionerEntity> pract = practitionerDao.findById(id);
		if (!pract.isPresent()) {
			throw new RuntimeException("Practitioner doesn't exists!");
		}
		if(examinationService.existsByPractitioner(id)) {
			throw new RuntimeException("Practitioner has examination/s in progress!");
		}
		patientService.deletePractitioner(id);
		practitionerDao.deleteById(id);
	}

	@Override
	public Optional<PractitionerDto> findByNameAndSurnameAndOrganization(String name, String surname, Long id) throws RuntimeException {
		Optional<PractitionerEntity> pract = practitionerDao.findByNameAndSurnameAndOrganization(name, surname, id);
		if (pract.isPresent()) {
			return Optional.of(converter.toDto(pract.get()));
		}
		return Optional.empty();
	}

	@Override
	public int countByOrganization(Long organizationId) {
		return practitionerDao.countByOrganization(organizationId);
	}

	@Override
	@Transactional
	public void deleteOrganization(Long organizationId) {
		practitionerDao.deleteOrganization(organizationId);
	}

	@Override
	public List<PractitionerDto> findByOrganization(Long id) {
		List<PractitionerEntity> practList = practitionerDao.findByOrganization(id);
		return practList.stream().map(pract -> {
			return converter.toDto(pract);
		}).collect(Collectors.toList());
	}
	


}
