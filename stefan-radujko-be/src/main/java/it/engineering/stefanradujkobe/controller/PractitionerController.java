package it.engineering.stefanradujkobe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityExistsException;
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

import it.engineering.stefanradujkobe.dto.PractitionerDto;
import it.engineering.stefanradujkobe.service.PractitionerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("practitioners")
public class PractitionerController {
	private final PractitionerService practitionerService;

	public PractitionerController(PractitionerService practitionerService) {
		super();
		this.practitionerService = practitionerService;
	}
	
	@PostMapping()
	public ResponseEntity<Object> save(@Valid @RequestBody PractitionerDto practitionerDto) {
		return ResponseEntity.status(HttpStatus.OK).body(practitionerService.save(practitionerDto));
	}
	
	@GetMapping("filter")
	public ResponseEntity<Page<PractitionerDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize,
			@RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortOrder,
			@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String surname,
			@RequestParam(defaultValue = "") String qualification, @RequestParam(defaultValue = "") String organization,
			@RequestParam(defaultValue = "false") Boolean unassigned) {
		return new ResponseEntity<Page<PractitionerDto>>(practitionerService.findAll(pageNo, pageSize, sortBy, sortOrder, name, surname, qualification, organization, unassigned), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public @ResponseBody ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PractitionerDto practitionerDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(practitionerService.update(practitionerDto));
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<PractitionerDto> practitioner = practitionerService.findById(id);
		if (practitioner.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(practitioner.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Practitioner id is invalid!");
	}
	
	@GetMapping("find/{name}&{surname}&{id}")
	public ResponseEntity<Object> findByNameAndSurnameAndOrganization(@PathVariable String name, @PathVariable String surname, @PathVariable Long id) {
		Optional<PractitionerDto> practitioner = practitionerService.findByNameAndSurnameAndOrganization(name, surname, id);
		if (practitioner.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(practitioner.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Practitioner not found!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EntityExistsException {
			try{
				practitionerService.delete(id);
			} catch(RuntimeException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
			return ResponseEntity.ok("Deleted practitioner!");
	}
	
	@GetMapping("count/{id}")
	public int  countByOrganization(@PathVariable Long id) {
		return practitionerService.countByOrganization(id);
	}
	
	@GetMapping("findbyorg/{id}")
	public List<PractitionerDto> findByOrganization(@PathVariable Long id) {
		return practitionerService.findByOrganization(id);
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
