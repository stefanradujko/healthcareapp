package it.engineering.stefanradujkobe.converter;

import org.springframework.stereotype.Component;

import it.engineering.stefanradujkobe.dto.ServiceTypeDto;
import it.engineering.stefanradujkobe.entity.ServiceTypeEntity;

@Component
public class ServiceTypeConverter implements GenericConverter<ServiceTypeDto, ServiceTypeEntity> {

	@Override
	public ServiceTypeEntity toEntity(ServiceTypeDto dto) {
		return new ServiceTypeEntity(dto.getId(), dto.getIdentifier(), dto.getName());
	}

	@Override
	public ServiceTypeDto toDto(ServiceTypeEntity entity) {
		return new ServiceTypeDto(entity.getId(), entity.getIdentifier(), entity.getName());
	}

}
