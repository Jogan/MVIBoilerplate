package com.name.mviboilerplate.dagger;

import android.support.annotation.NonNull;
import com.name.mviboilerplate.data.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import javax.inject.Inject;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;

public class BaseMockTest {

  private MockTestComponent component;

  @Inject MockWebServer mockWebServer;

  protected MockWebServer getMockWebServer() {
    return mockWebServer;
  }

  protected TestScheduler testScheduler = new TestScheduler();

  protected BaseSchedulerProvider schedulersProvider = new BaseSchedulerProvider() {

    @NonNull
    @Override
    public Scheduler io() {
      return testScheduler;
    }

    @NonNull
    @Override
    public Scheduler ui() {
      return testScheduler;
    }

    @NonNull
    @Override
    public Scheduler computation() {
      return testScheduler;
    }
  };

  @Before
  public void setUp() throws Exception {

  }

  protected MockTestComponent getComponent() {
    if (component == null) {
      component = DaggerMockTestComponent
          .builder()
          .mockTestModule(new MockTestModule())
          .build();
    }

    return component;
  }

  @After
  public void tearDown() throws Exception {
    mockWebServer.shutdown();
  }
}