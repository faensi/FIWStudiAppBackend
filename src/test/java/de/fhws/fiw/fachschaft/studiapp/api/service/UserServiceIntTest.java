package de.fhws.fiw.fachschaft.studiapp.api.service;

import com.owlike.genson.Genson;
import de.fhws.fiw.fachschaft.studiapp.models.Role;
import de.fhws.fiw.fachschaft.studiapp.models.User;
import de.fhws.fiw.sutton.client.Link;
import okhttp3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserServiceIntTest {
    private final static String BASE_URL = "http://localhost:8080/studi-app/api";
    private final static String CREATE_USER = "createUser";
    private final static String GET_ALL_USERS = "getAllUser";
    private final static String UPDATE_SINGLE_USER = "updateUser";

    private OkHttpClient client;

    private Genson genson;

    @Before
    public void setUp() {
        this.client = new OkHttpClient();
        this.genson = new Genson();
    }

    @After
    public void tearDown() {

    }


    @Test
    public void testPost() throws IOException {
        final Optional<Link> createPersonLink = callDispatcherAndGetHeaderLinkWithRelType(CREATE_USER);

        final Link theCreateLink = createPersonLink.get();
        User userN =  User.builder()
                .kNumber("k41050")
                .name("Julia")
                .accessDate(LocalDateTime.now())
                .role(Role.ADMIN)
                .degreeProgram("BIN")
                .isDeleted( false)
                .build();

        System.out.println(this.genson.serialize(userN));

        final RequestBody body = RequestBody.create(
                MediaType.parse(theCreateLink.getMediaType()),
                this.genson.serialize(userN));
        final Request requestPost = new Request.Builder().url(theCreateLink.getUrl()).post(body).build();
        final Response responsePost = executeRequest(requestPost);
        assertEquals("Object was not created!", 201, responsePost.code());
    }

    @Test
    public void testGetCollection() {
        final Optional<Link> getAllPersons = callDispatcherAndGetHeaderLinkWithRelType(GET_ALL_USERS);

        final Link theGetAllLink = getAllPersons.get();
        final Request requestGetAll = new Request.Builder().url(theGetAllLink.getUrl())
                .get()
                .build();

        final Response responseGetAll = executeRequest(requestGetAll);

        assertEquals("Get request failed!", 200, responseGetAll.code());
    }

    @Test
    public void testPut() throws Exception {
        final Optional<Link> createPersonLink = callDispatcherAndGetHeaderLinkWithRelType(CREATE_USER);

        final Link theCreateLink = createPersonLink.get();
        User userN =  User.builder().kNumber("k41050").name("Julia").accessDate(LocalDateTime.now()).role(Role.ADMIN).isDeleted( false).build();

        final RequestBody body = RequestBody.create(
                MediaType.parse(theCreateLink.getMediaType()),
                this.genson.serialize(userN));
        final Request requestPost = new Request.Builder().url(theCreateLink.getUrl()).post(body).build();
        final Response responsePost = executeRequest(requestPost);
        assertEquals("Object was not created!", 201, responsePost.code());

        /* Get single person that was just created */
        final String locationHeader = responsePost.header("Location");
        final Request requestGetSingle = new Request.Builder().url(locationHeader).get().build();
        final Response responseGetSingle = executeRequest(requestGetSingle);

        final User personResponse =
                this.genson.deserialize(responseGetSingle.body().string(), User.class);
        assertEquals("Julia", personResponse.getName());

        /* Update this person */
        final Optional<Link> linkToUpdate = getResponseHeaderLink(responseGetSingle, UPDATE_SINGLE_USER);
        assertTrue(String.format("No link of relType '%s' found.", UPDATE_SINGLE_USER),
                linkToUpdate.isPresent());

        personResponse.setName("rumy");

        final RequestBody putBody = RequestBody.create(
                MediaType.parse(linkToUpdate.get().getMediaType()),
                this.genson.serialize(personResponse));
        final Request requestPut = new Request.Builder().url(linkToUpdate.get().getUrl()).put(putBody).build();
        final Response responsePut = executeRequest(requestPut);
        assertEquals("Object was not updated!", 204, responsePut.code());

    }

    protected Response executeRequest(final Request request) {
        Response response;

        try {
            response = this.client.newCall(request).execute();
        } catch (final IOException e) {
            response = null;
        }

        return response;
    }

    protected Optional<Link> callDispatcherAndGetHeaderLinkWithRelType(final String relType) {
        final Request requestDispatcher = new Request.Builder().url(BASE_URL).get().build();
        final Response responseDispatcher = executeRequest(requestDispatcher);
        final Optional<Link> link = getResponseHeaderLink(responseDispatcher, relType);
        assertTrue(String.format("No link of relType '%s' found.", relType), link.isPresent());
        return link;
    }

    protected Optional<Link> getResponseHeaderLink(final Response response, final String relType) {
        return response.headers("Link")
                .stream()
                .map(Link::parseFromHttpHeader)
                .filter(l -> l.getRelationType().equalsIgnoreCase(relType))
                .findFirst();
    }

}
