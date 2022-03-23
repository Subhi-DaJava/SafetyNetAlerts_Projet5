package com.safetynet.SafetyNetAlerts.repository;

import java.util.List;
//Class générale
public interface CRUD_Method<Type>{

    List<Type> getAll();

    Boolean save(Type type);

    Boolean delete(Type type);

    Type update(int i, Type type);
}
