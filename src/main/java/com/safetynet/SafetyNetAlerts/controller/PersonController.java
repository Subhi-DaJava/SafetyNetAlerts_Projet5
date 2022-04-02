package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.CommunityEmailDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonInfoDTO;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private static Logger logger = LoggerFactory.getLogger(PersonController.class);
    @Autowired
    private PersonService personService;

    //Api REST pour afficher toutes les personnes, Endpoints
    @GetMapping(value = "/person")
    public ResponseEntity<Iterable<Person>> getAllPerson(){
        //
        Iterable<Person> persons = personService.getAllPersons();

        if(persons != null) {
            logger.info("Get all persons and their information");
            return ResponseEntity.status(HttpStatus.OK).body(persons);
        } else {
            logger.error("No person in the DataBase or no success !");
            return ResponseEntity.notFound().build();
        }
    }
    //Rajout un Person dans Repository mais pas dans le fichier Json <Person> / <Void> sont Pareil

    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Person p){
        Boolean isAdded = personService.savePerson(p);
        if (isAdded){
            //Rajout d'une location
            return ResponseEntity.status(HttpStatus.CREATED).build();
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
            //ça passe ?
            return ResponseEntity.ok(listFamilleByAddress);
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


    @GetMapping(value = "/personInfo")
    public ResponseEntity<Iterable<PersonInfoDTO>> getInformationOfSameFamily(@RequestParam(value = "firstName") String firstName, @RequestParam("lastName") String lastName){
        List<PersonInfoDTO> infoListOfFamily = personService.getInformationOfSameFamily(firstName,lastName);
        if(infoListOfFamily != null){
            return ResponseEntity.ok().body(infoListOfFamily);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
