package com.name.mviboilerplate.dagger;

import android.support.annotation.NonNull;
import com.name.mviboilerplate.data.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import javax.inject.Inject;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.QueueDispatcher;
import org.junit.After;
import org.junit.Before;

public class BaseLogicTest {

  private LogicTestComponent component;

  @Inject MockWebServer mockWebServer;
  @Inject Dispatcher dispatcher;

  protected MockWebServer getErrorMockWebServer() {
    mockWebServer.setDispatcher(new QueueDispatcher());
    return mockWebServer;
  }

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

  protected LogicTestComponent getComponent() {
    if (component == null) {
      component = DaggerLogicTestComponent
          .builder()
          .logicTestModule(new LogicTestModule())
          .build();
    }

    return component;
  }

  @After
  public void tearDown() throws Exception {
    mockWebServer.setDispatcher(dispatcher);
    mockWebServer.shutdown();
  }
}