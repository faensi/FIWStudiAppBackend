package de.fhws.fiw.fachschaft.studiapp.models;

import de.fhws.fiw.sutton.models.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends AbstractModel {
    private String city;
    private String cn;
    private String degreeProgram;
    private String emailAddress;
    private String facultyName;
    private String firstName;
    private String lastName;
    private String officeName;
    private String postalCode;
    private String role;
    private String semester;
    private String streetAddress;
    private String telephoneNumber;
    private String thumbnailPhoto;
    private String userCertificate;
    private String userPrincipalName;
}
