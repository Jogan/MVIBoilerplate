package com.name.mvpboilerplate.ui.main;

import com.google.gson.Gson;
import com.name.mvpboilerplate.dagger.BaseLogicTest;
import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest extends BaseLogicTest {

  @Inject MvpBoilerplateService service;
  @Inject Gson gson;

  @Override
  public void setUp() throws Exception {
    getComponent().inject(this);
    super.setUp();
  }

  @Test public void loadingFirstPage() {
    //
    // Prepare mock server to deliver mock response on incoming http request
    //
    List<String> mockPokemon = Arrays.asList("Bulbasaur", "Charmander", "Pikachu", "Squirtle");
    getMockWebServer().enqueue(new MockResponse().setBody(gson.toJson(mockPokemon)));
    //
    // init the robot to drive to View which triggers intents on the presenter
    //
    DataManager dataManager = new DataManager(service);
    MainViewDriver driver = new MainViewDriver(new MainPresenter(schedulersProvider, dataManager));
    //
    // We are ready, so let's start: fire an intent
    //
    driver.fireLoadFirstPageIntent();
    //
    // we expect that 2 view.render() events happened with the following HomeViewState:
    // 1. show loading indicator
    // 2. show the items with the first page
    //
    MainViewState loadingFirstPage = MainViewState.builder().loadingFirstPage(true).build();
    MainViewState data = MainViewState.builder().data(mockPokemon).build();
    // Check if as expected
    driver.assertViewStateRendered(loadingFirstPage, data);
  }

  @Test public void loadingFirstFailsWithNoConnectionError() throws IOException {
    //
    // Prepare mock server to deliver mock response on incoming http request
    //
    getMockWebServer().shutdown(); // Simulate no internet connection to the server
    //
    // init the robot to drive to View which triggers intents on the presenter
    //
    DataManager dataManager = new DataManager(service);
    MainViewDriver driver = new MainViewDriver(new MainPresenter(schedulersProvider, dataManager));
    //
    // We are ready, so let's start: fire an intent
    //
    driver.fireLoadFirstPageIntent();
    //
    // we expect that 2 view.render() events happened with the following HomeViewState:
    // 1. show loading indicator
    // 2. show error indicator
    //
    MainViewState loadingFirstPage = MainViewState.builder().loadingFirstPage(true).build();
    MainViewState errorFirstPage = MainViewState.builder().firstPageError(new ConnectException()).build();
    // Check if as expected
    driver.assertViewStateRendered(loadingFirstPage, errorFirstPage);
  }
}
