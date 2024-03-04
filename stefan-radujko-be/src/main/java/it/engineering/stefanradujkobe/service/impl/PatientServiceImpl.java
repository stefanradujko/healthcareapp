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

import it.engineering.stefanradujkobe.converter.PatientConverter;
import it.engineering.stefanradujkobe.dao.PatientDao;
import it.engineering.stefanradujkobe.dto.PatientDto;
import it.engineering.stefanradujkobe.entity.PatientEntity;
import it.engineering.stefanradujkobe.service.ExaminationService;
import it.engineering.stefanradujkobe.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
	private PatientDao patientDao;
	private PatientConverter converter;
	private ExaminationService examinationService;
	
	public PatientServiceImpl(PatientDao patientDao, PatientConverter converter, ExaminationService examinationService) {
		super();
		this.patientDao = patientDao;
		this.converter = converter;
		this.examinationService = examinationService;
	}

	@Override
	public PatientDto save(PatientDto patient) {
		PatientEntity savedEntity = patientDao.save(converter.toEntity(patient));
		return converter.toDto(savedEntity);
	}

	@Override
	public Page<PatientDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String name, String surname, String organization, String practName, String practSurname, Boolean unassigned) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		if(unassigned) {
			Page<PatientDto> entites = patientDao.findByUnassigned(pageable, name, surname, organization, practName, practSurname).map(converter :: toDto);
			return entites;
		} else {
			Page<PatientDto> entites =	patientDao.findByFilter(pageable, name, surname, organization, practName, practSurname).map(converter :: toDto);
			return entites;
		}
	}

	@Override
	public PatientDto update(PatientDto patient) throws RuntimeException {
		Optional<PatientEntity> pat = patientDao.findById(patient.getId());
		if (!pat.isPresent()) {
			throw new RuntimeException("Patient doesn't exists!");
		}
		PatientEntity savedEntity = patientDao.save(converter.toEntity(patient));
		return converter.toDto(savedEntity);
	}

	@Override
	public Optional<PatientDto> findById(Long id) {
		Optional<PatientEntity> patient = patientDao.findById(id);
		if (patient.isPresent()) {
			return Optional.of(converter.toDto(patient.get()));
		}
		return Optional.empty();
	}

	@Override
	public void delete(Long id) throws RuntimeException {
		Optional<PatientEntity> patient = patientDao.findById(id);
		if (!patient.isPresent()) {
			throw new RuntimeException("Patient doesn't exists!");
		}
		if(examinationService.existsByPatient(id)) {
			throw new RuntimeException("Patient has examination/s in progress!");
		}
		examinationService.deleteByPatient(id);
		patientDao.deleteById(id);
	}

	@Override
	public List<PatientDto> findAll() {
		List<PatientEntity> patientList = patientDao.findAll();
		return patientList.stream().map(org -> {
			return converter.toDto(org);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<PatientDto> findByNameAndSurname(String name, String surname) throws RuntimeException {
		Optional<PatientEntity> patient = patientDao.findByNameAndSurname(name, surname);
		if (patient.isPresent()) {
			return Optional.of(converter.toDto(patient.get()));
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public void deletePractitioner(Long id) {
		patientDao.deletePractitioner(id);
	}

	@Override
	@Transactional
	public void deleteOrganization(Long id) {
		patientDao.deleteOrganization(id);
	}

}
