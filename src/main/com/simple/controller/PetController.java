package com.simple.controller;

import com.simple.dao.Dao;
import com.simple.data.Pet;
import com.simple.data.PetNotFoundException;
import com.simple.data.PetS3Request;
import com.simple.external.ExternalDogAPIResponse;
import com.simple.external.ExternalDogAPIService;
import com.simple.service.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class PetController {

    @Autowired Dao petDao;
    @Autowired ExternalDogAPIService externalDogService;
    @Autowired S3UploadService uploadController;

    @RequestMapping(value = "/pet", method = RequestMethod.GET)
    public List<Pet> getAllPets() {
        return petDao.fetchAll();
    }

    @RequestMapping(value = "/pet/{id}", method = RequestMethod.GET)
    public Pet getPet(@PathVariable("id") int id) throws PetNotFoundException {
        return petDao.fetch(id).orElseThrow(PetNotFoundException::new);
    }

    @RequestMapping(value = "/pet/trigger", method = RequestMethod.GET)
    public String savePet() {
        ExternalDogAPIResponse response = externalDogService.GetDogDetails();
        PetS3Request s3Request = PetS3Request.builder().
                externalURL(response.getMessage()).
                fileName(response.getFileName()).build();
        String s3URL = uploadController.UploadFileToS3(s3Request);
        Pet p = Pet.builder().s3URL(s3URL).
                name(response.getDogName()).createdAt(LocalDateTime.now()).
                build();
        int insertStatus = petDao.createPet(p);
        if (insertStatus == 0) {
            return "Failed";
        }
        return "Success";
    }

    @RequestMapping(value = "/pet/{id}", method = RequestMethod.DELETE)
    public String deletePet(@PathVariable("id") int id) {
        Optional<Pet> p = petDao.fetch(id);
        p.ifPresent(delete -> {
            PetS3Request s3Request = PetS3Request.builder().
                    fileName(delete.getS3URL()).build();
            uploadController.DeleteFileS3(s3Request);
            petDao.deletePet(delete.getId());
        });
        return "Success";
    }

    @RequestMapping(value = "/pet/breeds", method = RequestMethod.GET)
    public List<String> getDogBreeds() {
        return petDao.fetchBreedNames();
    }


}
