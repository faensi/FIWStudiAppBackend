package de.fhws.fiw.fachschaft.studiapp.converters;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;
import de.fhws.fiw.fachschaft.studiapp.models.State;

public class CoffeeMachineStateConverter implements Converter<State>
{
	@Override
	public void serialize( final State convert, final ObjectWriter objectWriter, final Context context )
		throws Exception
	{
		objectWriter.writeValue( convert.getValue() );
	}

	@Override public State deserialize( final ObjectReader objectReader, final Context context )
		throws Exception
	{
		try
		{
			final int value = objectReader.valueAsInt( );
			return State.getByValue( value );
		}
		catch ( Exception e )
		{
			return null;
		}
	}
}
