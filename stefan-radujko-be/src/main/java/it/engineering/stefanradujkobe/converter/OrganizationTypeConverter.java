package it.engineering.stefanradujkobe.converter;

import org.springframework.stereotype.Component;

import it.engineering.stefanradujkobe.dto.OrganizationTypeDto;
import it.engineering.stefanradujkobe.entity.OrganizationTypeEntity;

@Component
public class OrganizationTypeConverter implements GenericConverter<OrganizationTypeDto, OrganizationTypeEntity> {

	@Override
	public OrganizationTypeEntity toEntity(OrganizationTypeDto dto) {
		return new OrganizationTypeEntity(dto.getId(), dto.getIdentifier(), dto.getName());
	}

	@Override
	public OrganizationTypeDto toDto(OrganizationTypeEntity entity) {
		return new OrganizationTypeDto(entity.getId(), entity.getIdentifier(), entity.getName());
	}

}
