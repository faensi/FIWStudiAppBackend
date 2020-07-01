package de.fhws.fiw.fachschaft.studiapp.api.service;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import de.fhws.fiw.fachschaft.studiapp.api.state.users.*;
import de.fhws.fiw.fachschaft.studiapp.database.dao.UserDao;
import de.fhws.fiw.fachschaft.studiapp.database.dao.impl.UserDaoImpl;
import de.fhws.fiw.fachschaft.studiapp.models.Role;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import de.fhws.fiw.fachschaft.studiapp.models.UserDto;
import de.fhws.fiw.sutton.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.sutton.api.services.AbstractService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.apache.tomcat.util.buf.ByteChunk;
import org.glassfish.jersey.internal.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Objects;

@Path("users")
public class UserService extends AbstractService {
    @Context
    protected HttpServletRequest postRequest;

    private final OkHttpClient httpClient = new OkHttpClient();

    private String username;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        final GetAllUsers.GetAllUsersQuery query = new GetAllUsers.GetAllUsersQuery();
        query.setPagingBehavior(new PagingBehaviorUsingOffsetSize(0, 100));

        return new GetAllUsers().setQuery(query)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSinglePersons(@PathParam("id") final long id) {
        return new GetSingleUser().setRequestedId(id)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSinglePersons(@QueryParam("role") int role) {
        String authHeader = postRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null) {
            final String withoutBasic = authHeader.replaceFirst( "[Bb]asic ", "" );
            final String userColonPass = Base64.decodeAsString( withoutBasic );
            parseCredentials(userColonPass.getBytes());

            UserDao userDao = new UserDaoImpl();
            try {
                String result = consume(authHeader);
                if(userDao.readByUsername(username) == null) {
                    if (Objects.requireNonNull(result).startsWith("{")) {
                        Genson genson = new GensonBuilder().setSkipNull(true)
                                .useIndentation(true)
                                .useDateAsTimestamp(false)
                                .create();
                        UserDto userDto = genson.deserialize(Objects.requireNonNull(result), UserDto.class);
                        User user = User.builder()
                                .kNumber(userDto.getCn())
                                .name(userDto.getFirstName() + " " + userDto.getLastName())
                                .role(Role.getByValue(role))
                                .accessDate(LocalDateTime.now())
                                .degreeProgram(userDto.getDegreeProgram())
                                .build();
                        return new PostUser().setModelToStore(user)
                                .setUriInfo(this.uriInfo)
                                .setRequest(this.request)
                                .setHttpServletRequest(this.httpServletRequest)
                                .setContext(this.context)
                                .build();
                    } else return Response.status(Integer.parseInt(result)).build();
                } else{
                    return Response.status(200).build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(400).build();
            }
        }
        return Response.status(400).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSinglePersons(@PathParam("id") final long id, final User User) {
        User.setId(id);
        return new UpdateUser().setModelToUpdate(User)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSinglePersons(@PathParam("id") final long id, final User User) {
        User.setId(id);
        return new DeleteSingleUser().setModelToUpdate(User)
                .setUriInfo(this.uriInfo)
                .setRequest(this.request)
                .setHttpServletRequest(this.httpServletRequest)
                .setContext(this.context)
                .build();
    }

    private String consume(String token){
        Request request = new Request.Builder()
                .url("https://api.fiw.fhws.de/auth/api/users/me")
                .addHeader("Authorization", token)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) return String.valueOf(response.code());
            else return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void parseCredentials(byte[] decoded) throws IllegalArgumentException {
        int colon = -1;

        for(int i = 0; i < decoded.length; ++i) {
            if (decoded[i] == 58) {
                colon = i;
                break;
            }
        }

        if (colon < 0) {
            this.username = new String(decoded, StandardCharsets.ISO_8859_1);
        } else {
            this.username = new String(decoded, 0, colon, StandardCharsets.ISO_8859_1);
            String password = new String(decoded, colon + 1, decoded.length - colon - 1, StandardCharsets.ISO_8859_1);
            if (password.length() > 1) {
                password = password.trim();
            }
        }

        if (this.username.length() > 1) {
            this.username = this.username.trim();
        }

    }
}
