/*
 * Copyright (c) peter.braun@fhws.de
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.sutton.api.converter;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by braunpet on 10.12.15.
 */
public class DateTimeConverter implements Converter<LocalDateTime>
{
	@Override
	public void serialize( final LocalDateTime convert, final ObjectWriter objectWriter, final Context context )
		throws Exception
	{
		final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		final ZonedDateTime zoned = ZonedDateTime.of( convert, ZoneId.systemDefault( ) );
		final String output = zoned.format( formatter );
		objectWriter.writeString( output );
	}

	@Override public LocalDateTime deserialize( final ObjectReader objectReader, final Context context )
		throws Exception
	{
		try
		{
			final String date = objectReader.valueAsString( );
			return LocalDateTime.from( NorburyDateTimeParser.parse( date ) );
		}
		catch ( ParseException e )
		{
			return null;
		}
	}
}