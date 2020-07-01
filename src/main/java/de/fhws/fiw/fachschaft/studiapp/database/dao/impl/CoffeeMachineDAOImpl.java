package de.fhws.fiw.fachschaft.studiapp.database.dao.impl;

import de.fhws.fiw.fachschaft.studiapp.database.config.ConnectionManager;
import de.fhws.fiw.fachschaft.studiapp.database.config.ObjectMapper;
import de.fhws.fiw.fachschaft.studiapp.database.dao.CoffeeMachineDAO;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.fachschaft.studiapp.models.State;
import de.fhws.fiw.sutton.database.results.SingleModelResult;

import java.sql.*;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

public class CoffeeMachineDAOImpl implements CoffeeMachineDAO
{

    private Connection con;
    private PreparedStatement ps;

    @Override
    public CoffeeMachine create(CoffeeMachine machine) throws Exception {
        try {
            con = ConnectionManager.getConnection();
            ps = con
                    .prepareStatement("INSERT INTO coffee_machine_history (status, user_name, status_time) VALUES(?, ?, ?)");

            ps.setObject(1, machine.getState().getValue());
            ps.setString(2, machine.getUserName());
            ps.setTimestamp(3, new Timestamp(((machine.getStatusTime()).atZone( ZoneId.systemDefault()).toInstant().toEpochMilli())));

            int update = ps.executeUpdate();

            machine.setId(ObjectMapper.getGeneratedId(con));

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            ConnectionManager.closeConnection(con);
        }
        return machine;
    }

    @Override
    public List<CoffeeMachine> getAllStates() throws Exception {
        Statement stmt = null;
        con = ConnectionManager.getConnection();
        List<CoffeeMachine> coffeeMachineList=new LinkedList<>();


        try {
            stmt = con.createStatement();
            String sql = "SELECT * FROM coffee_machine_history";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                CoffeeMachine coffeeMachine = new CoffeeMachine();
                coffeeMachine.setId(rs.getLong("id"));
                coffeeMachine.setState(State.getByValue(rs.getInt("status")));
                coffeeMachine.setUserName(rs.getString("user_name"));
                coffeeMachine.setStatusTime(rs.getTimestamp("status_time").toLocalDateTime());
                coffeeMachineList.add(coffeeMachine);
            }
            rs.close();
        } catch (Exception se) {
            se.printStackTrace();
        }//Handle errors for Class.forName
        finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    con.close();
            } catch (SQLException se) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return coffeeMachineList;
    }

    @Override
    public SingleModelResult<CoffeeMachine> getCurrentState() throws Exception {
        con = ConnectionManager.getConnection();
        CoffeeMachine coffeeMachine1=new CoffeeMachine();
        SingleModelResult<CoffeeMachine> result= new SingleModelResult<>(coffeeMachine1);

        try
        {
            ps = con.prepareStatement("SELECT * FROM coffee_machine_history ORDER BY id DESC LIMIT 1");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                coffeeMachine1.setId(rs.getLong("id"));
                coffeeMachine1.setState(State.getByValue(rs.getInt("status")));
                coffeeMachine1.setUserName(rs.getString("user_name"));
                coffeeMachine1.setStatusTime(rs.getTimestamp("status_time").toLocalDateTime());

            }
            rs.close();
        } catch (Exception se) {
            se.printStackTrace();
        }
        finally {

            try {
                if (ps != null)
                    con.close();
            } catch (SQLException ignored) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }


}
