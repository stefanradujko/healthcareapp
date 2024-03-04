package it.engineering.stefanradujkobe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.engineering.stefanradujkobe.dto.OrganizationTypeDto;
import it.engineering.stefanradujkobe.service.OrganizationTypeService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("organizations/type")
public class OrganizationTypeController {
private final OrganizationTypeService orgTypeService;
	
	public OrganizationTypeController(OrganizationTypeService orgTypeService) {
		this.orgTypeService = orgTypeService;
	}
	
	@GetMapping
	public List<OrganizationTypeDto> findAll() {
		return orgTypeService.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<OrganizationTypeDto> organization = orgTypeService.findById(id);
		if (organization.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(organization.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization type id is invalid!");
	}
}
