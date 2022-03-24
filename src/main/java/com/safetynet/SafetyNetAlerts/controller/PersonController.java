package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/person")
    //Rajout un Person dans Repository mais pas dans le fichier Json <Person> / <Void> sont Pareil
    public ResponseEntity<Person> savePerson(@RequestBody Person p){
        Boolean candidate = personService.savePerson(p);
        if (candidate){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
