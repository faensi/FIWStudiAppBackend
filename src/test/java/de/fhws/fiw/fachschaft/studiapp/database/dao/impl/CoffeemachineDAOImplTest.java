package de.fhws.fiw.fachschaft.studiapp.database.dao.impl;

import de.fhws.fiw.fachschaft.studiapp.database.dao.CoffeeMachineDAO;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.fachschaft.studiapp.models.State;
import de.fhws.fiw.sutton.database.results.SingleModelResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CoffeemachineDAOImplTest {

	@Test
	public void testCreate() throws Exception {
		CoffeeMachineDAO coffeemachineDAO = new CoffeeMachineDAOImpl();
		CoffeeMachine machine = new CoffeeMachine();
		machine.setId(1L);
		machine.setState(State.READY);
		machine.setUserName("Julia");
		machine.setStatusTime(LocalDateTime.now());
		CoffeeMachine coffeeMachine = coffeemachineDAO.create(machine);
		assertEquals(machine,coffeeMachine);
	}

	@Test
	public void testGetCurrentState() throws Exception
	{
		CoffeeMachineDAO coffeeMachineDao = new CoffeeMachineDAOImpl();
		SingleModelResult<CoffeeMachine> getState = coffeeMachineDao.getCurrentState();
		CoffeeMachine coffeeMachine = new CoffeeMachine();
		coffeeMachine.setId(1L);
		coffeeMachine.setState(State.READY);
		coffeeMachine.setUserName("Julia");
		coffeeMachine.setStatusTime(LocalDateTime.now());
		assertEquals(coffeeMachine.getUserName(), getState.getResult().getUserName());
	}

	@Test
	public void testReadAll() throws Exception
	{
		CoffeeMachineDAO coffeeMachineDao = new CoffeeMachineDAOImpl();
		List<CoffeeMachine> allCoffeeMachines = coffeeMachineDao.getAllStates();
		assertFalse(allCoffeeMachines.isEmpty());
	}


}
