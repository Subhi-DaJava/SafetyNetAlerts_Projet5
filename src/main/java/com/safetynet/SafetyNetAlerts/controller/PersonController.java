package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    //Api REST pour afficher toutes les personnes, Endpoints
    @GetMapping(value = "/person")
    public ResponseEntity<Iterable<Person>> getAllPerson(){
        Iterable<Person> persons = personService.getAllPersons();

        if(persons == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(persons);
        }
    }

}
