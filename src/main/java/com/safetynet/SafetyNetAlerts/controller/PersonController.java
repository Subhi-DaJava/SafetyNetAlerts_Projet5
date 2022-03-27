package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.CommunityEmailDTO;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    //Api REST pour afficher toutes les personnes, Endpoints
    @GetMapping(value = "/person")
    public ResponseEntity<Iterable<Person>> getAllPerson(){
        Iterable<Person> persons = personService.getAllPersons();
        if(persons != null) {
            return ResponseEntity.ok().body(persons);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //Rajout un Person dans Repository mais pas dans le fichier Json <Person> / <Void> sont Pareil
    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Person p){
        Boolean isAdded = personService.savePerson(p);
        if (isAdded){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //Mettre à jour une personne déjà existante(sauf les champs firstName & lastName)
    @PutMapping(value ="/person")
    public  ResponseEntity<Void> updatePerson(@RequestBody Person p){
        Person candidate = personService.updatePerson(p);
        if (candidate != null){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //Supprimer une personne (nom et prénom comme identificateur unique)
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        Boolean isDeleted = personService.deletePerson(firstName,lastName);
        if(isDeleted){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //Tri par adresse
    @GetMapping(value = "/person/{address}")
    public ResponseEntity<Iterable<Person>> getByAddress(@PathVariable("address") String address){

        Iterable<Person> listFamilleByAddress = personService.getByAddress(address);
        if (listFamilleByAddress != null){
            return ResponseEntity.ok().body(listFamilleByAddress);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    //Retourner les adresses mail de tous les habitants d'une ville
    @GetMapping("/communityEmail")
    public ResponseEntity<Iterable<CommunityEmailDTO>> getAllEmailsOfGivenCity(@RequestParam (name="city") String city){
        List<CommunityEmailDTO> allEmails = personService.getAllEmailsFromGivenCity(city);
        if(allEmails != null){
            return ResponseEntity.ok().body(allEmails);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
