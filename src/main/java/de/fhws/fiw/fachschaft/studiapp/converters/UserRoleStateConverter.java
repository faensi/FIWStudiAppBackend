package de.fhws.fiw.fachschaft.studiapp.converters;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;
import de.fhws.fiw.fachschaft.studiapp.models.Role;
import de.fhws.fiw.fachschaft.studiapp.models.State;

public class UserRoleStateConverter implements Converter<Role> {
    @Override
    public void serialize(Role role, ObjectWriter objectWriter, Context context) throws Exception {
        objectWriter.writeValue( role.getValue() );
    }

    @Override
    public Role deserialize(ObjectReader objectReader, Context context) throws Exception {
        try
        {
            final int value = objectReader.valueAsInt( );
            return Role.getByValue( value );
        }
        catch ( Exception e )
        {
            return null;
        }

    }
}
