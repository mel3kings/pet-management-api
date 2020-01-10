package com.simple.data;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PetMapper implements RowMapper<Pet> {


    @Override
    public Pet mapRow(ResultSet rs, int i) throws SQLException {
        return Pet.builder().id(rs.getInt("id")).
                createdAt(rs.getTimestamp("created_at").toLocalDateTime()).
                updatedAt(rs.getTimestamp("updated_at").toLocalDateTime()).
                name(rs.getString("name")).
                s3URL(rs.getString("s3_url")).
                build();
    }


}
