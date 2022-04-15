package com.safetynet.SafetyNetAlerts.repository;

import java.util.List;
//Class générale, comme une classe DAO
public interface Methods_CRUD<Type>{

    List<Type> getAll();

    Boolean save(Type type);

    Boolean delete(Type type);

    Type update(int i, Type type);

    List<Type> getByType(String type);
}
