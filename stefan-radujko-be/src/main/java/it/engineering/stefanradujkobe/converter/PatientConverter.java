package it.engineering.stefanradujkobe.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.engineering.stefanradujkobe.dto.PatientDto;
import it.engineering.stefanradujkobe.entity.OrganizationEntity;
import it.engineering.stefanradujkobe.entity.OrganizationTypeEntity;
import it.engineering.stefanradujkobe.entity.PatientEntity;
import it.engineering.stefanradujkobe.entity.PractitionerEntity;

@Component
public class PatientConverter implements GenericConverter<PatientDto, PatientEntity> {
	@Autowired
	private OrganizationConverter orgConverter;
	@Autowired
	private PractitionerConverter practConverter;
	
	
	@Override
	public PatientEntity toEntity(PatientDto dto) {
		return new PatientEntity(dto.getId(), dto.getIdentifier(), dto.isActive(), dto.getName(), dto.getSurname(), dto.getGender(),
				dto.getBirthDate(), dto.getAdress(), dto.getPhone(), dto.getEmail(), dto.isDeceased(), dto.getMaritialStatus(),
				orgConverter.toEntity(dto.getOrganization()), practConverter.toEntity(dto.getPractitioner()));
	}

	@Override
	public PatientDto toDto(PatientEntity entity) {
		if(entity.getOrganization() == null) {
			OrganizationEntity deletedOrg = new OrganizationEntity();
			deletedOrg.setOrganizationType(new OrganizationTypeEntity());
			entity.setOrganization(deletedOrg);
		}
		if(entity.getPractitioner() == null) {
			PractitionerEntity deletedPract = new PractitionerEntity();
			OrganizationEntity deletedOrg = new OrganizationEntity();
			deletedOrg.setOrganizationType(new OrganizationTypeEntity());
			deletedPract.setOrganization(deletedOrg);
			;
			entity.setPractitioner(deletedPract);
		}
		return new PatientDto(entity.getId(), entity.getIdentifier(), entity.isActive(), entity.getName(), entity.getSurname(),	entity.getGender(),
				entity.getBirthDate(), entity.getAdress(), entity.getPhone(), entity.getEmail(), entity.isDeceased(), entity.getMaritialStatus(),
				orgConverter.toDto(entity.getOrganization()), practConverter.toDto(entity.getPractitioner()));
	}

}
