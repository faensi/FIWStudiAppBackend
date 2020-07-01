package de.fhws.fiw.fachschaft.studiapp.database.dao;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.sutton.database.results.SingleModelResult;

import java.util.List;

public interface CoffeeMachineDAO
{
    // This method is used to save the given coffee machine
	public CoffeeMachine create(CoffeeMachine coffeemachine) throws Exception;

	// This method is used to output all states
	public List<CoffeeMachine> getAllStates() throws Exception;

	// This method is used to output the current state
	public SingleModelResult<CoffeeMachine> getCurrentState() throws Exception;
}
