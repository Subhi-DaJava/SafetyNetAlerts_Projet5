package com.safetynet.SafetyNetAlerts.repository;

import com.safetynet.SafetyNetAlerts.CustomProperties;
import com.safetynet.SafetyNetAlerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonRepository implements CRUD_Method<Person> {
    @Autowired
    CustomProperties customProperties;
    @Autowired
    private ReadFromJason_DAO readFromJason_dao;

    //Repository centrale
    private Repository repository;

    private List<Person> persons;


    public PersonRepository(){

    }

    private void loadPersonsFromJasonFile(){
        //lire le fichier data enregistrer dans Repository
        repository = readFromJason_dao.readFromJsonFile();
        //Extraire les personnes
        persons = repository.getPersons();
    }
    //retourne tous les Persons
    @Override
    public List<Person> getAll() {
        if(repository == null)
            loadPersonsFromJasonFile();
        return persons;
    }

    @Override
    public Boolean save(Person p) {
        if(repository == null)
            loadPersonsFromJasonFile();
        return persons.add(p);
    }


    @Override
    public Boolean delete(Person p) {
       if (repository == null)
           loadPersonsFromJasonFile();
       return persons.remove(p);
    }

    @Override
    public Person update(int i, Person p) {
        if(repository == null)
            loadPersonsFromJasonFile();
        //Inserts the specified element at the specified position in this list (optional operation).
        //Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
        return persons.set(i,p);
    }
}