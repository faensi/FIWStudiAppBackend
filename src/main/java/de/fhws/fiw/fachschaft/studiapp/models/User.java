package de.fhws.fiw.fachschaft.studiapp.models;

import com.owlike.genson.annotation.JsonConverter;
import com.owlike.genson.annotation.JsonIgnore;
import de.fhws.fiw.fachschaft.studiapp.converters.UserRoleStateConverter;
import de.fhws.fiw.sutton.api.converter.DateTimeConverter;
import de.fhws.fiw.sutton.api.converter.LinkConverter;
import de.fhws.fiw.sutton.models.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractModel {
    private long id;
    private String kNumber;
    private String name;

    private LocalDateTime accessDate;

    private Role role;
    private boolean isDeleted;

    private String degreeProgram;

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    @InjectLink( style = InjectLink.Style.ABSOLUTE, value = "persons/${instance.id}",
            type = "application/json", rel = "self" )
    protected Link selfUri;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getkNumber() {
        return kNumber;
    }

    public void setkNumber(String kNumber) {
        this.kNumber = kNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
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


    @JsonConverter( UserRoleStateConverter.class )
    public Role getState() {
        return this.role;
    }

    @JsonConverter( UserRoleStateConverter.class )
    public void setState(Role role) {
        this.role = role;
    }

    @JsonConverter( DateTimeConverter.class)
    public LocalDateTime getStatusTime() {
        return this.accessDate;
    }

    @JsonConverter( DateTimeConverter.class)
    public void setStatusTime(LocalDateTime accessDate) {
        this.accessDate = accessDate;
    }

}
