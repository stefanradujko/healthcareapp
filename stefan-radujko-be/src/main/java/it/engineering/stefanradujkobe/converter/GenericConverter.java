package it.engineering.stefanradujkobe.converter;

import it.engineering.stefanradujkobe.dto.Dto;
import it.engineering.stefanradujkobe.entity.Entity;

public interface GenericConverter<D extends Dto, E extends Entity> {
	E toEntity(D dto);

	D toDto(E entity);
}
