package it.engineering.stefanradujkobe.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.engineering.stefanradujkobe.converter.OrganizationTypeConverter;
import it.engineering.stefanradujkobe.dao.OrganizationTypeDao;
import it.engineering.stefanradujkobe.dto.OrganizationTypeDto;
import it.engineering.stefanradujkobe.entity.OrganizationTypeEntity;
import it.engineering.stefanradujkobe.service.OrganizationTypeService;

@Service
public class OrganizationTypeServiceImpl implements OrganizationTypeService {
	private OrganizationTypeDao orgTypeDao;
	private OrganizationTypeConverter converter;
	

	public OrganizationTypeServiceImpl(OrganizationTypeDao orgTypeDao, OrganizationTypeConverter converter) {
		super();
		this.orgTypeDao = orgTypeDao;
		this.converter = converter;
	}

	@Override
	public List<OrganizationTypeDto> findAll() {
		List<OrganizationTypeEntity> orgTypeList = orgTypeDao.findAll();
		return orgTypeList.stream().map(orgType -> {
			return converter.toDto(orgType);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<OrganizationTypeDto> findById(Long id) {
		Optional<OrganizationTypeEntity> orgType = orgTypeDao.findById(id);
		if (orgType.isPresent()) {
			return Optional.of(converter.toDto(orgType.get()));
		}
		return Optional.empty();
	}

}
