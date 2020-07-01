package de.fhws.fiw.sutton.api.converter;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Optional;

/**
 * Created by braunpet on 13.08.16.
 */
public class NorburyDateTimeParser
{
	private static final DateTimeFormatter[] DATE_TIME_FORMATTERS;
	private static final DateTimeFormatter[] DATE_FORMATTERS;
	private static final DateTimeFormatter[] TIME_FORMATTERS;

	static
	{
		DATE_TIME_FORMATTERS = new DateTimeFormatter[] {
			DateTimeFormatter.ISO_OFFSET_DATE_TIME,
			DateTimeFormatter.ISO_DATE_TIME,
			DateTimeFormatter.ISO_LOCAL_DATE_TIME,
			DateTimeFormatter.ISO_ZONED_DATE_TIME,
			new DateTimeFormatterBuilder( ).appendPattern( "dd.MM.yyyy HH:mm" ).toFormatter( ),
			new DateTimeFormatterBuilder( ).appendPattern( "dd.MM.yyyy HH:mm:ss" ).toFormatter( ),
			new DateTimeFormatterBuilder( ).appendPattern( "dd.MM. HH:mm" )
										   .parseDefaulting( ChronoField.SECOND_OF_MINUTE, 0 )
										   .parseDefaulting( ChronoField.YEAR,
											   LocalDate.now( ).getYear( ) ).toFormatter( ),
			new DateTimeFormatterBuilder( ).appendPattern( "dd.MM. HH:mm:ss" )
										   .parseDefaulting( ChronoField.YEAR,
											   LocalDate.now( ).getYear( ) ).toFormatter( ),
			DateTimeFormatter.RFC_1123_DATE_TIME
		};

		DATE_FORMATTERS = new DateTimeFormatter[] {
			DateTimeFormatter.ISO_LOCAL_DATE,
			new DateTimeFormatterBuilder( ).appendPattern( "dd.MM." )
										   .parseDefaulting( ChronoField.YEAR, LocalDate.now( ).getYear( ) )
				.toFormatter( ),
			DateTimeFormatter.ofPattern( "dd.MM.yyyy" )
		};

		TIME_FORMATTERS = new DateTimeFormatter[] {
			DateTimeFormatter.ISO_LOCAL_TIME
		};

	}

	/**
	 * Parse the given date in any time zone and return a date in the current time zone.
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static LocalDateTime parse( final String date ) throws ParseException
	{
		final Optional<LocalDateTime> firstTry = parseToLocalDateTime( date );

		if ( firstTry.isPresent( ) )
		{
			return firstTry.get( );
		}
		else
		{
			final Optional<LocalDateTime> secondTry = parseToLocalTime( date );

			if ( secondTry.isPresent( ) )
			{
				return secondTry.get( );
			}
			else
			{
				final Optional<LocalDateTime> thirdTry = parseToLocalDate( date );

				if ( thirdTry.isPresent( ) )
				{
					return thirdTry.get( );
				}
				else
				{
					throw new ParseException( date, 0 );
				}
			}
		}
	}

	private static Optional<LocalDateTime> parseToLocalDateTime( final String date )
	{
		for ( int i = 0; i < DATE_TIME_FORMATTERS.length; i++ )
		{
			try
			{
				final ZonedDateTime value = ZonedDateTime.parse( date, DATE_TIME_FORMATTERS[ i ] );
				return Optional.of( convertToLocalTimeZone( value ) );
			}
			catch ( final DateTimeParseException e )
			{
				try
				{
					final LocalDateTime value = LocalDateTime.parse( date, DATE_TIME_FORMATTERS[ i ] );
					return Optional.of( value );
				}
				catch ( final DateTimeParseException j )
				{
					// ignore
				}
			}
		}

		return Optional.empty( );
	}

	private static LocalDateTime convertToLocalTimeZone( final ZonedDateTime zonedDateTime )
	{
		return LocalDateTime.ofInstant( zonedDateTime.toInstant( ), ZoneId.systemDefault( ) );
	}

	private static Optional<LocalDateTime> parseToLocalTime( final String date )
	{
		for ( int i = 0; i < TIME_FORMATTERS.length; i++ )
		{
			try
			{
				final LocalTime value = LocalTime.parse( date, TIME_FORMATTERS[ i ] );
				final LocalDateTime returnValue = LocalDateTime.of( LocalDate.now( ), value );

				return Optional.of( returnValue );
			}
			catch ( final DateTimeParseException e )
			{
				// ignore
			}
		}

		return Optional.empty( );

	}

	private static Optional<LocalDateTime> parseToLocalDate( final String date )
	{
		for ( int i = 0; i < DATE_FORMATTERS.length; i++ )
		{
			try
			{
				final LocalDate value = LocalDate.parse( date, DATE_FORMATTERS[ i ] );
				final LocalDateTime returnValue = LocalDateTime.of( value, LocalTime.of( 0, 0, 0 ) );

				return Optional.of( returnValue );
			}
			catch ( final DateTimeParseException e )
			{
				// ignore
			}
		}

		return Optional.empty( );
	}
}