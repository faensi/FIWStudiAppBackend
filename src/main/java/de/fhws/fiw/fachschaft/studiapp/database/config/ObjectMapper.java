package de.fhws.fiw.fachschaft.studiapp.database.config;

import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.fachschaft.studiapp.models.State;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObjectMapper {
    public static CoffeeMachine convertToCoffeeMachine(ResultSet resultSet) throws Exception{
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return CoffeeMachine.builder()
                .id(resultSet.getLong(1))
                .state(State.getByValue(resultSet.getInt(2)))
                .userName(resultSet.getString(3))
                .statusTime(LocalDateTime.parse(resultSet.getString(4),format))
                .build();
    }

    public static Long getGeneratedId(Connection con) throws SQLException
    {
        ResultSet rs = con.prepareStatement("SELECT * FROM coffee_machine_history").executeQuery();
        long anInt = 0L;
        while (rs.next()) anInt = rs.getLong(1);
        rs.close();
        return anInt;
    }
}
