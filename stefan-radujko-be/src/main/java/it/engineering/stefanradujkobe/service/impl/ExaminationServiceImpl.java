package it.engineering.stefanradujkobe.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import it.engineering.stefanradujkobe.converter.ExaminationConverter;
import it.engineering.stefanradujkobe.dao.ExaminationDao;
import it.engineering.stefanradujkobe.dto.ExaminationDto;
import it.engineering.stefanradujkobe.entity.ExaminationEntity;
import it.engineering.stefanradujkobe.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {
	private ExaminationDao examinationDao;
	private ExaminationConverter converter;
	
	public ExaminationServiceImpl(ExaminationDao examinationDao, ExaminationConverter converter) {
		super();
		this.examinationDao = examinationDao;
		this.converter = converter;
	}

	@Override
	public ExaminationDto save(ExaminationDto examination){
		ExaminationEntity savedEntity = examinationDao.save(converter.toEntity(examination));
		return converter.toDto(savedEntity);
	}

	@Override
	public Page<ExaminationDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String organization, String name, String surname, String serviceType, String status, String priority) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		Page<ExaminationDto> entites =	examinationDao.findByFilter(pageable, organization, name, surname, serviceType, status, priority).map(converter :: toDto);
		return entites;
	}

	@Override
	public ExaminationDto update(ExaminationDto examination) throws RuntimeException {
		Optional<ExaminationEntity> exam = examinationDao.findById(examination.getId());
		if (!exam.isPresent()) {
			throw new RuntimeException("Examination doesn't exists!");
		}
		ExaminationEntity savedEntity = examinationDao.save(converter.toEntity(examination));
		return converter.toDto(savedEntity);
	}

	@Override
	public Optional<ExaminationDto> findById(Long id) {
		Optional<ExaminationEntity> exam = examinationDao.findById(id);
		if (exam.isPresent()) {
			return Optional.of(converter.toDto(exam.get()));
		}
		return Optional.empty();
	}

	@Override
	public void delete(Long id) throws RuntimeException {
		Optional<ExaminationEntity> exam = examinationDao.findById(id);
		if (!exam.isPresent()) {
			throw new RuntimeException("Examination doesn't exists!");
		}
		examinationDao.deleteById(id);
		
	}

	@Override
	public boolean existsByOrganization(Long organizationId) {
		return examinationDao.existsByOrganization(organizationId);
	}

	@Override
	public boolean existsByPatient(Long patientId) {
		return examinationDao.existsByPatient(patientId);
	}

	@Override
	public boolean existsByPractitioner(Long practitionerId) {
		return examinationDao.existsByPractitioner(practitionerId);
	}

	@Override
	public int countByOrganization(Long organizationId) {
		return examinationDao.countByOrganization(organizationId);
	}

	@Override
	public int countActiveByOrganization(Long organizationId) {
		return examinationDao.countActiveByOrganization(organizationId);
	}

	@Override
	@Transactional
	public void deleteByPatient(Long practitionerId) {
		examinationDao.deleteByPatient(practitionerId);
	}

	@Override
	@Transactional
	public void deleteByOrganization(Long organizationId) {
		examinationDao.deletByOrganization(organizationId);
	}


}
