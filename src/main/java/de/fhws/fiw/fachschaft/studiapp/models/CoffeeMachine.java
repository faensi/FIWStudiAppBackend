package de.fhws.fiw.fachschaft.studiapp.models;

import com.owlike.genson.annotation.JsonConverter;
import com.owlike.genson.annotation.JsonIgnore;
import de.fhws.fiw.fachschaft.studiapp.converters.CoffeeMachineStateConverter;
import de.fhws.fiw.sutton.api.converter.DateTimeConverter;

import de.fhws.fiw.sutton.api.converter.LinkConverter;
import de.fhws.fiw.sutton.models.AbstractModel;
import lombok.*;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CoffeeMachine extends AbstractModel {

    private long id;
    private State state;
    private String userName;
    private LocalDateTime statusTime;
    @InjectLink( style = InjectLink.Style.ABSOLUTE, value = "persons/${instance.id}",
            type = "application/json", rel = "self" )
    protected Link selfUri;

    public CoffeeMachine(State state, String userName, LocalDateTime statusTime)
    {
        super();
        this.userName=userName;
        this.statusTime=statusTime;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonConverter( CoffeeMachineStateConverter.class )
    public State getState() {
        return state;
    }

	@JsonConverter( CoffeeMachineStateConverter.class )
    public void setState(State state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonConverter( DateTimeConverter.class)
    public LocalDateTime getStatusTime() {
        return statusTime;
    }

    @JsonConverter( DateTimeConverter.class)
    public void setStatusTime(LocalDateTime statusTime) {
        this.statusTime = statusTime;
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
