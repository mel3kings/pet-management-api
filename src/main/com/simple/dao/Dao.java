package com.simple.dao;

import com.simple.data.Pet;

import java.util.List;
import java.util.Optional;

public interface Dao {

    Optional<Pet> fetch(int id);

    List<Pet> fetchAll();

    List<String> fetchBreedNames();

    int createPet(Pet p);

    boolean deletePet(int id);

}
