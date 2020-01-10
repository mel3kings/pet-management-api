package com.simple.dao;

import com.simple.data.Pet;
import com.simple.data.PetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
public class DatabaseDao implements Dao {

    private final String SQL_FIND_PET = "select * from pet where id = ? and deleted_at is null";
    private final String SQL_DELETE_PET = "update pet set deleted_at = now() where id = ?";
    private final String SQL_GET_ALL = "select * from pet where deleted_at is null";
    private final String SQL_INSERT_PET = "insert into pet(name, created_at, updated_at, s3_url) values(?,?,?,?)";
    private final String SQL_GET_NAMES = "select distinct(name) from pet where deleted_at is null";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Pet> fetchAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new PetMapper());
    }

    public List<String> fetchBreedNames() {
        return jdbcTemplate.queryForList(SQL_GET_NAMES, String.class);
    }

    public Optional<Pet> fetch(int id) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_PET, new Object[]{id}, new PetMapper()));
    }

    public int createPet(Pet p) {
        return jdbcTemplate.update(SQL_INSERT_PET, p.getName(), LocalDateTime.now(), LocalDateTime.now(), p.getS3URL());
    }

    public boolean deletePet(int id) {
        return jdbcTemplate.update(SQL_DELETE_PET, id) > 0;
    }

}
