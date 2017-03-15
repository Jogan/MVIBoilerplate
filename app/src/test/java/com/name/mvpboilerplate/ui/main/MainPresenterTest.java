package com.name.mvpboilerplate.ui.main;

import com.name.mvpboilerplate.dagger.BaseLogicTest;
import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest extends BaseLogicTest {

  @Inject MvpBoilerplateService service;

  @Override
  public void setUp() throws Exception {
    getComponent().inject(this);
    super.setUp();
  }

  @Before public void beforeEachTest() throws Exception {
    getMockWebServer().start();
  }

  @After public void afterEachTest() throws Exception {
    getMockWebServer().shutdown();
  }

  @Test public void loadingFirstPage() {
    final DataManager dataManager = new DataManager(service);

    /*

    //
    // Prepare mock server to deliver mock response on incoming http request
    //

    getMockWebServer().enqueue(new MockResponse().setBody(adapter.toJson(mockProducts)));

    //
    // init the robot to drive to View which triggers intents on the presenter
    //
    HomePresenter presenter =
        new DependencyInjection().newHomePresenter();   // In a real app you could use dagger or instantiate the Presenter manually like new HomePresenter(...)
    HomeViewRobot robot = new HomeViewRobot(presenter);

    //
    // We are ready, so let's start: fire an intent
    //
    robot.fireLoadFirstPageIntent();

    //
    // we expect that 2 view.render() events happened with the following HomeViewState:
    // 1. show loading indicator
    // 2. show the items with the first page
    //
    List<FeedItem> expectedData = Arrays.asList(
        new SectionHeader("category1"),
        mockProducts.get(0),
        mockProducts.get(1),
        mockProducts.get(2),
        new AdditionalItemsLoadable(2, "category1", false, null)
    );

    HomeViewState loadingFirstPage = new HomeViewState.Builder().firstPageLoading(true).build();
    HomeViewState firstPage = new HomeViewState.Builder().data(expectedData).build();

    // Check if as expected
    robot.assertViewStateRendered(loadingFirstPage, firstPage);

    */

  }
}
