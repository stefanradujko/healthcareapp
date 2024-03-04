package it.engineering.stefanradujkobe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.engineering.stefanradujkobe.dto.PractitionerDto;
import it.engineering.stefanradujkobe.security.PractitionerUserDetails;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("auth")
public class AuthenticationController {
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/login")
    public ResponseEntity<PractitionerUserDetails> authenticateUser(@RequestBody PractitionerDto practitionerDto){
//		System.out.println("practitionerDto:" + practitionerDto);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        		practitionerDto.getUsername(), practitionerDto.getPassword()));
//        System.out.println(authentication);
        PractitionerUserDetails userDetails = (PractitionerUserDetails) authentication.getPrincipal();
//        System.out.println("user details: " + userDetails.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
