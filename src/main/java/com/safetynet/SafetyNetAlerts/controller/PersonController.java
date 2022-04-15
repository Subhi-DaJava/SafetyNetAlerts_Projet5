package com.safetynet.SafetyNetAlerts.controller;

import com.safetynet.SafetyNetAlerts.dto.CommunityEmailDTO;
import com.safetynet.SafetyNetAlerts.dto.PersonInfoDTO;
import com.safetynet.SafetyNetAlerts.model.Person;
import com.safetynet.SafetyNetAlerts.service.PersonService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PersonController {
    private static final Logger LOGGER = LogManager.getLogger(PersonController.class);
    @Autowired
    private PersonService personService;

    //Api REST pour afficher toutes les personnes, Endpoints
    @GetMapping(value = "/person")
    public ResponseEntity<Iterable<Person>> getAllPerson(){
        LOGGER.debug("The endpoint(GET /person) starts here");
        Iterable<Person> persons = personService.getAllPersons();
        if(persons != null) {
            LOGGER.info("The endpoint(GET /person) get all persons successfully and their information");
            return ResponseEntity.ok().body(persons);
        } else {
            LOGGER.error("No person in the DataBase or no success with GET /person");
            return ResponseEntity.notFound().build();
        }
    }
    //Rajout un Person dans Repository mais pas dans le fichier Json <Person> / <Void> sont Pareil

    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Person personAdded){
        LOGGER.debug("The endpoint(POST /person) starts here");
        Boolean isAdded = personService.savePerson(personAdded);
        if (isAdded){
            LOGGER.info("Save a "+personAdded+" is successful with POST /person");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(true)
                    .toUri();
            return ResponseEntity.created(location).build();

        }else {
            LOGGER.error("Could not save a "+personAdded+" with POST /person");
            return ResponseEntity.notFound().build();
        }
    }
    //Mettre à jour une personne déjà existante(sauf les champs firstName & lastName)
    @PutMapping(value ="/person/{firstName}/{lastName}")
    public  ResponseEntity<Void> updatePerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @RequestBody Person personUpdated){
        LOGGER.debug("The endpoint(PUT /person) starts here");
        Person candidate = personService.updatePerson(firstName,lastName,personUpdated);
        if (candidate != null){
            LOGGER.info("Update a person :"+firstName+" "+lastName+ " is successful with PUT /person");
            return ResponseEntity.ok().build();
        }else {
            LOGGER.error("Could not update a person :"+firstName+" "+lastName+" with PUT /person");
            return ResponseEntity.notFound().build();
        }
    }
    //Supprimer une personne (nom et prénom comme identificateur unique)
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){
        LOGGER.debug("The endpoint(DELETE /person/firstName/lastName) starts here");
        Boolean isDeleted = personService.deletePerson(firstName,lastName);
        if(isDeleted){
            LOGGER.info("Delete a person, firstName :" +firstName+", and lastName : "+lastName+" is successfully deleted from DELETE /person/firstName/lastName");
            return ResponseEntity.ok().build();
        }else {
            LOGGER.error("Could not delete a person, firstName :" +firstName+", and lastName :"+lastName+" from DELETE /person/firstName/lastName");
            return ResponseEntity.notFound().build();
        }
    }
    //Tri par adresse
    @GetMapping(value = "/person/{address}")
    public ResponseEntity<Iterable<Person>> getByAddress(@PathVariable("address") String address){
        LOGGER.debug("The endpoint (GET /person/address) starts here");
        Iterable<Person> listFamilleByAddress = personService.getByAddress(address);
        if (listFamilleByAddress != null){
            LOGGER.info("Get the persons with the address "+address+" is successful from GET /person/address");
            return ResponseEntity.ok().body(listFamilleByAddress);
        }else {
            LOGGER.error("Could not get the persons with the address "+address+" from GET /person/address");
            return ResponseEntity.notFound().build();
        }
    }
    //Retourner les adresses mail de tous les habitants d'une ville
    @GetMapping("/communityEmail")
    public ResponseEntity<Iterable<CommunityEmailDTO>> getAllEmailsOfGivenCity(@RequestParam (name="city") String city){
        LOGGER.debug("The endpoint url(GET /communityEmail?city=<city>) starts here");
        List<CommunityEmailDTO> allEmails = personService.getAllEmailsFromAGivenCity(city);
        if(allEmails != null){
            LOGGER.info("Get the emails with the city "+city+" given is successful from GET /person/address");
            return ResponseEntity.ok().body(allEmails);
        }else {
            LOGGER.error("Could not get the emails with the city "+city+" given");
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value = "/personInfo")
    public ResponseEntity<Iterable<PersonInfoDTO>> getInformationOfSameFamily(@RequestParam(value = "firstName") String firstName, @RequestParam("lastName") String lastName){
        LOGGER.debug("The endpoint url(GET /personInfo?firstName=<firstName>&lastName=<lastName> starts here");
        List<PersonInfoDTO> infoListOfFamily = personService.getInformationOfSameFamily(firstName,lastName);
        if(infoListOfFamily != null){
            LOGGER.info("Get the person list : firstName is "+firstName+", lastName is "+lastName+" succesfully with GET /personInfo?firstName&LastName");
            return ResponseEntity.ok().body(infoListOfFamily);
        }else {
            LOGGER.error("Could not get the person list : firstName is "+firstName+" and lastName is "+lastName);
            return ResponseEntity.notFound().build();
        }
    }



}
