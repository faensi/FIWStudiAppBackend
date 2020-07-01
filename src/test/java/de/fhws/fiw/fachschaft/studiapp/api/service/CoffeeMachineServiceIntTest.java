package de.fhws.fiw.fachschaft.studiapp.api.service;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import de.fhws.fiw.fachschaft.studiapp.models.CoffeeMachine;
import de.fhws.fiw.fachschaft.studiapp.models.State;
import de.fhws.fiw.sutton.client.Link;
import okhttp3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoffeeMachineServiceIntTest {
    private final static String BASE_URL = "http://localhost:8080/studi-app/api";
    private final static String CREATE_COFFEE_MACHINE = "createCoffeeMachine";
    private final static String GET_ALL_STATES = "getAllStates";

    private OkHttpClient client;
    private Genson genson;

    @Before
    public void setUp() {
        this.client = new OkHttpClient();
        this.genson = new GensonBuilder().setSkipNull(true)
                .useIndentation(true)
                .useDateAsTimestamp(false)
                .useDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"))
                .create();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testPost() throws IOException {
        final Optional<Link> createCoffeeMachineLink = callDispatcherAndGetHeaderLinkWithRelType(CREATE_COFFEE_MACHINE);

        final Link theCreateLink = createCoffeeMachineLink.get();

        final CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.setId(1L);
        coffeeMachine.setUserName("Julia");
        coffeeMachine.setState(State.READY);
        coffeeMachine.setStatusTime(LocalDateTime.now());

        final RequestBody body = RequestBody.create(
                MediaType.parse(theCreateLink.getMediaType()),
                this.genson.serialize(coffeeMachine));
        final Request requestPost = new Request.Builder().url(theCreateLink.getUrl()).post(body).build();
        final Response responsePost = executeRequest(requestPost);
        assertEquals("Object was not created!", 201, responsePost.code());
    }

    @Test
    public void testGetAllStates( )
    {
        final Optional<Link> getAllStates = callDispatcherAndGetHeaderLinkWithRelType(GET_ALL_STATES);

        final Link theGetAllLink = getAllStates.get( );
        final Request requestGetAll = new Request.Builder( ).url( theGetAllLink.getUrl( ) )
                                                            .get( )
                                                            .build( );

        final Response responseGetAll = executeRequest( requestGetAll );

        assertEquals("Get request failed!", 200, responseGetAll.code());
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
