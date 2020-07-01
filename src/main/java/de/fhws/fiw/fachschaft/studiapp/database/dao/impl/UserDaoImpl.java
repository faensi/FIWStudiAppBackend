package de.fhws.fiw.fachschaft.studiapp.database.dao.impl;

import de.fhws.fiw.fachschaft.studiapp.database.config.ConnectionManager;
import de.fhws.fiw.fachschaft.studiapp.database.config.ObjectMapper;
import de.fhws.fiw.fachschaft.studiapp.database.dao.UserDao;
import de.fhws.fiw.fachschaft.studiapp.models.Role;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import org.glassfish.jersey.internal.util.Base64;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.fhws.fiw.fachschaft.studiapp.database.config.ConnectionManager.getConnection;

public class UserDaoImpl implements UserDao
{

	private Connection con;
	private PreparedStatement ps;

	@Override
	public User create( final User user ) throws Exception
	{

		try
		{
			con = ConnectionManager.getConnection( );
			ps = Objects.requireNonNull( con )
						.prepareStatement(
							"INSERT INTO user (k_number, name, allowance_date, role, degree_program) VALUES(?, ?, ?, ?, ?, ?)" );

			ps.setString( 1, user.getkNumber( ) );
			ps.setString( 2, user.getName( ) );
			ps.setTimestamp( 3,
				new Timestamp(
					( ( user.getStatusTime( ) ).atZone( ZoneId.systemDefault( ) ).toInstant( ).toEpochMilli( ) ) ) );
			ps.setObject( 4, user.getState( ).getValue( ) );
			ps.setString( 5, user.getDegreeProgram( ) );

			final int update = ps.executeUpdate( );

			user.setId( ObjectMapper.getGeneratedId( con ) );

		}
		catch ( final Exception e )
		{
			System.out.println( e );
		}
		finally
		{
			ConnectionManager.closeConnection( con );
		}

		return user;
	}

	@Override
	public User readById( final Long id ) throws Exception
	{
		con = getConnection( );
		User user = new User( );

		try
		{
			System.out.println( "Creating statement..." );

			ps = con
				.prepareStatement( "SELECT * FROM user WHERE id = ?" );
			ps.setLong( 1, id );
			final ResultSet rs = ps.executeQuery( );
			//STEP 5: Extract data from result set
			while ( rs.next( ) )
			{
				final String kNumber = rs.getString( "k_number" );
				final String name = rs.getString( "name" );
				final LocalDateTime accessDate = rs.getTimestamp( "allowance_date" ).toLocalDateTime( );
				final Role role = Role.getByValue( rs.getInt( "role" ) );

				user = User.builder( )
						   .accessDate( accessDate )
						   .id( id )
						   .kNumber( kNumber )
						   .name( name )
						   .role( role ).build( );
			}
			rs.close( );
		}
		catch ( final Exception se )
		{
			se.printStackTrace( );
		}//Handle errors for Class.forName
		finally
		{
			//finally block used to close resources
			try
			{
				if ( ps != null )
					con.close( );
			}
			catch ( final SQLException ignored )
			{
			}// do nothing
			try
			{
				if ( con != null )
					con.close( );
			}
			catch ( final SQLException se )
			{
				se.printStackTrace( );
			}//end finally try
		}//end try
		return user;
	}

	@Override
	public User readByUsername( final String username ) throws Exception
	{
		con = getConnection( );
		User user = null;

		try
		{
			System.out.println( "Creating statement..." );

			ps = con
				.prepareStatement( "SELECT * FROM public.user WHERE k_number = ?" );
			ps.setString( 1, username );
			final ResultSet rs = ps.executeQuery( );
			//STEP 5: Extract data from result set
			while ( rs.next( ) )
			{
				final String name = rs.getString( "name" );
				final LocalDateTime accessDate = rs.getTimestamp( "allowance_date" ).toLocalDateTime( );
				final Role role = Role.getByValue( rs.getInt( "role" ) );
				final long id = rs.getLong( "id" );

				user = User.builder( )
						   .accessDate( accessDate )
						   .id( id )
						   .kNumber( username )
						   .name( name )
						   .role( role )
						   .build( );
			}
			rs.close( );
		}
		catch ( final Exception se )
		{
			se.printStackTrace( );
		}//Handle errors for Class.forName
		finally
		{
			//finally block used to close resources
			try
			{
				if ( ps != null )
					con.close( );
			}
			catch ( final SQLException ignored )
			{
			}// do nothing
			try
			{
				if ( con != null )
					con.close( );
			}
			catch ( final SQLException se )
			{
				se.printStackTrace( );
			}//end finally try
		}//end try
		return user;
	}

	@Override
	public List<User> readAll( ) throws Exception
	{
		Statement stmt = null;
		con = getConnection( );
		final List<User> users = new ArrayList<>( );

		try
		{
			System.out.println( "Creating statement..." );
			stmt = con.createStatement( );

			final String sql = "SELECT * FROM \"user\" where is_deleted = false";
			final ResultSet rs = stmt.executeQuery( sql );
			//STEP 5: Extract data from result set
			while ( rs.next( ) )
			{
				final String kNumber = rs.getString( "k_number" );
				final long id = rs.getLong( "id" );
				final String name = rs.getString( "name" );
				final LocalDateTime accessDate = rs.getTimestamp( "allowance_date" ).toLocalDateTime( );
				final Role role = Role.getByValue( rs.getInt( "role" ) );

				users.add( User.builder( )
							   .accessDate( accessDate )
							   .id( id )
							   .kNumber( kNumber )
							   .name( name )
							   .role( role ).build( ) );
			}
			rs.close( );
		}
		catch ( final Exception se )
		{
			se.printStackTrace( );
		}//Handle errors for Class.forName
		finally
		{
			//finally block used to close resources
			try
			{
				if ( stmt != null )
					con.close( );
			}
			catch ( final SQLException se )
			{
			}// do nothing
			try
			{
				if ( con != null )
					con.close( );
			}
			catch ( final SQLException se )
			{
				se.printStackTrace( );
			}//end finally try
		}//end try
		return users;
	}

	@Override
	public void deleteById( final Long id ) throws Exception
	{
		try
		{
			con = getConnection( );

			//STEP 4: Execute a query
			System.out.println( "Creating statement..." );

			ps = con.prepareStatement( "UPDATE  \"user\" SET is_deleted = true " +
				"WHERE id = ?" );
			ps.setLong( 1, id );
			ps.executeUpdate( );
			ps.close( );

		}
		catch ( final Exception se )
		{
			//Handle errors for JDBC
			se.printStackTrace( );
		}//Handle errors for Class.forName
		finally
		{
			//finally block used to close resources
			try
			{
				if ( ps != null )
					Objects.requireNonNull( con ).close( );
			}
			catch ( final SQLException ignored )
			{
			}// do nothing
			try
			{
				if ( con != null )
					con.close( );
			}
			catch ( final SQLException se )
			{
				se.printStackTrace( );
			}//end finally try
			//end try
		}
	}

	@Override
	public void delete( final User user ) throws Exception
	{
		deleteById( user.getId( ) );
	}

	@Override
	public void update( final User user ) throws Exception
	{

	}

	public void update( final User user, final String changedAttribute, final Object changeValue ) throws Exception
	{
		try
		{

			con = getConnection( );

			//STEP 4: Execute a query
			System.out.println( "Creating statement..." );
			ps = con.prepareStatement( "UPDATE user " +
				"SET ? = ? WHERE id = ?" );
			ps.setString( 1, changedAttribute );
			ps.setObject( 2, changeValue );
			ps.setObject( 3, user.getId( ) );
			ps.executeUpdate( );
			ps.close( );
		}
		catch ( final Exception se )
		{
			//Handle errors for JDBC
			se.printStackTrace( );
		}//Handle errors for Class.forName
		finally
		{
			//finally block used to close resources
			try
			{
				if ( ps != null )
					con.close( );
			}
			catch ( final SQLException se )
			{
			}// do nothing
			try
			{
				if ( con != null )
					con.close( );
			}
			catch ( final SQLException se )
			{
				se.printStackTrace( );
			}//end finally try
		}//end try
	}
}

