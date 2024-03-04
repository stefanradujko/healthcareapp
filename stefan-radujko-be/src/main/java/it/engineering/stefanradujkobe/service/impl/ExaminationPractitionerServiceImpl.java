package it.engineering.stefanradujkobe.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import it.engineering.stefanradujkobe.converter.ExaminationPractitionerConverter;
import it.engineering.stefanradujkobe.dao.ExaminationPractitionerDao;
import it.engineering.stefanradujkobe.dto.ExaminationPractitionerDto;
import it.engineering.stefanradujkobe.entity.ExaminationEntity;
import it.engineering.stefanradujkobe.entity.ExaminationPractitionerEntity;
import it.engineering.stefanradujkobe.service.ExaminationPractitionerService;

@Service
public class ExaminationPractitionerServiceImpl implements ExaminationPractitionerService {
	private ExaminationPractitionerDao examPractDao;
	private ExaminationPractitionerConverter converter;
	
	public ExaminationPractitionerServiceImpl(ExaminationPractitionerDao examPractDao,
			ExaminationPractitionerConverter converter) {
		super();
		this.examPractDao = examPractDao;
		this.converter = converter;
	}

	@Override
	public ExaminationPractitionerDto save(ExaminationPractitionerDto examinationPractitioner) {
		ExaminationPractitionerEntity savedEntity = examPractDao.save(converter.toEntity(examinationPractitioner));
		return converter.toDto(savedEntity);
	}


	@Override
	public Optional<ExaminationPractitionerDto> findById(Long id) {
		Optional<ExaminationPractitionerEntity> examPract = examPractDao.findById(id);
		if (examPract.isPresent()) {
			return Optional.of(converter.toDto(examPract.get()));
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public void delete(Long id){
		ExaminationEntity examination = new ExaminationEntity();
		examination.setId(id);
		examPractDao.deleteByExamination(examination);
		
	}

	@Override
	public List<ExaminationPractitionerDto> findByExamination(Long id) {
		ExaminationEntity examination = new ExaminationEntity();
		examination.setId(id);
		List<ExaminationPractitionerEntity> list = examPractDao.findByExamination(examination);
		return list.stream().map(examPract -> {
			return converter.toDto(examPract);
		}).collect(Collectors.toList());
	}

}
