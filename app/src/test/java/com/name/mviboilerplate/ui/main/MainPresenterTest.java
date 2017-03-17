package com.name.mviboilerplate.ui.main;

import com.google.gson.Gson;
import com.name.mviboilerplate.dagger.BaseMockTest;
import com.name.mviboilerplate.data.DataManager;
import com.name.mviboilerplate.data.model.NamedResource;
import com.name.mviboilerplate.data.remote.MvpBoilerplateService;
import com.name.mviboilerplate.ui.home.HomePresenter;
import com.name.mviboilerplate.ui.home.HomeViewState;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Test;

public class MainPresenterTest extends BaseMockTest {

  @Inject MvpBoilerplateService service;
  @Inject Gson gson;

  @Override
  public void setUp() throws Exception {
    getComponent().inject(this);
    super.setUp();
  }

  @Test public void loadingFirstPage() {
    /* TODO

    //
    // Prepare mock server to deliver mock response on incoming http request
    //
    List<NamedResource> mockPokemon = Arrays.asList(
        NamedResource.builder().name("Bulbasaur").build(),
        NamedResource.builder().name("Charmander").build(),
        NamedResource.builder().name("Squirtle").build(),
        NamedResource.builder().name("Pikachu").build()
    );
    MvpBoilerplateService.PokemonListResponse mockResponse = new MvpBoilerplateService.PokemonListResponse();
    mockResponse.results = mockPokemon;
    getMockWebServer().enqueue(new MockResponse().setBody(gson.toJson(mockResponse)));
    //
    // init the robot to drive to View which triggers intents on the presenter
    //
    DataManager dataManager = new DataManager(service);
    MainViewDriver driver = new MainViewDriver(new HomePresenter(schedulersProvider, dataManager));
    //
    // We are ready, so let's start: fire an intent
    //
    driver.fireLoadFirstPageIntent();
    //
    // we expect that 2 view.render() events happened with the following HomeViewState:
    // 1. show loading indicator
    // 2. show the items with the first page
    //
    List<String> expectedData = Arrays.asList(
        "Bulbasaur",
        "Charmander",
        "Squirtle",
        "Pikachu"
    );
    HomeViewState loadingFirstPage = HomeViewState.builder().loadingFirstPage(true).build();
    HomeViewState data = HomeViewState.builder().data(expectedData).build();
    // Check if as expected
    testScheduler.triggerActions();
    driver.assertViewStateRendered(loadingFirstPage, data);

    */
  }

  @Test public void loadingFirstFailsWithNoConnectionError() throws IOException {
    /* TODO

    //
    // Prepare mock server to deliver mock response on incoming http request
    //
    getMockWebServer().shutdown(); // Simulate no internet connection to the server
    //
    // init the robot to drive to View which triggers intents on the presenter
    //
    DataManager dataManager = new DataManager(service);
    MainViewDriver driver = new MainViewDriver(new HomePresenter(schedulersProvider, dataManager));
    //
    // We are ready, so let's start: fire an intent
    //
    driver.fireLoadFirstPageIntent();
    //
    // we expect that 2 view.render() events happened with the following HomeViewState:
    // 1. show loading indicator
    // 2. show error indicator
    //
    HomeViewState loadingFirstPage = HomeViewState.builder().loadingFirstPage(true).build();
    HomeViewState errorFirstPage = HomeViewState.builder().firstPageError(new ConnectException()).build();
    // Check if as expected
    testScheduler.triggerActions();
    driver.assertViewStateRendered(loadingFirstPage, errorFirstPage);

    */
  }
}
