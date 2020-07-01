package de.fhws.fiw.fachschaft.studiapp.models;

import com.owlike.genson.annotation.JsonConverter;
import com.owlike.genson.annotation.JsonIgnore;
import de.fhws.fiw.sutton.api.converter.DateTimeConverter;
import de.fhws.fiw.sutton.api.converter.LinkConverter;
import de.fhws.fiw.sutton.models.AbstractModel;
import lombok.*;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class News extends AbstractModel {

    private long id;
    private String title;
    private String text;
    private String userName;
    private LocalDateTime time;
    private String image;
    private boolean isDeleted;
    @InjectLink( style = InjectLink.Style.ABSOLUTE, value = "persons/${instance.id}",
            type = "application/json", rel = "self" )
    protected Link selfUri;

	public long getId( )
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public String getTitle( )
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getText( )
	{
		return text;
	}

	public void setText( String text )
	{
		this.text = text;
	}

	public String getUserName( )
	{
		return userName;
	}

	public void setUserName( String userName )
	{
		this.userName = userName;
	}

	@JsonConverter( DateTimeConverter.class)
	public LocalDateTime getTime( )
	{
		return time;
	}

	@JsonConverter( DateTimeConverter.class)
	public void setTime( LocalDateTime time )
	{
		this.time = time;
	}

	public boolean isDeleted( )
	{
		return isDeleted;
	}

	public void setDeleted( boolean deleted )
	{
		isDeleted = deleted;
	}

	@JsonConverter( LinkConverter.class )
	public Link getSelfUri( )
	{
		return this.selfUri;
	}

	@JsonIgnore
	public void setSelfUri( final Link selfUri )
	{
		this.selfUri = selfUri;
	}
}
