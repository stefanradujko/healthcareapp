package it.engineering.stefanradujkobe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.engineering.stefanradujkobe.dto.PatientDto;
import it.engineering.stefanradujkobe.service.PatientService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("patients")
public class PatientController {
	private final PatientService patientService;
	
	public PatientController(PatientService patientService) {
		super();
		this.patientService = patientService;
	}

	@PostMapping()
	public ResponseEntity<Object> save(@Valid @RequestBody PatientDto patientDto) {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.save(patientDto));
	}
	
	@GetMapping("filter")
	public ResponseEntity<Page<PatientDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo, 
			@RequestParam(defaultValue = "5") Integer pageSize, 
			@RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortOrder,
			@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String surname,
			@RequestParam(defaultValue = "") String organization, @RequestParam(defaultValue = "") String practName,
			@RequestParam(defaultValue = "") String practSurname,
			@RequestParam(defaultValue = "true") Boolean unassigned) {
		return new ResponseEntity<Page<PatientDto>>(patientService.findAll(pageNo, pageSize, sortBy, sortOrder, name, surname, organization, practName, practSurname, unassigned), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PatientDto patientDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(patientService.update(patientDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<PatientDto> patient = patientService.findById(id);
		if (patient.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(patient.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient id is invalid!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws RuntimeException {
			try{
				patientService.delete(id);
			} catch(RuntimeException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
			return ResponseEntity.ok("Deleted patient!");
	}
	
	@GetMapping
	public List<PatientDto> findAll() {
		return patientService.findAll();
	}
	
	@GetMapping("find/{name}&{surname}")
	public ResponseEntity<Object> findByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
		Optional<PatientDto> patient = patientService.findByNameAndSurname(name, surname);
		if (patient.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(patient.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found!");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
