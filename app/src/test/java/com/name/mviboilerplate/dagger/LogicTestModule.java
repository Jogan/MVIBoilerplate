package com.name.mviboilerplate.dagger;

import android.support.annotation.NonNull;
import com.name.mviboilerplate.data.LocalResponseDispatcher;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;

@Module
public class LogicTestModule extends ApiModule {
  @Override
  protected void addOkHttpConfig(OkHttpClient.Builder builder) {
    builder.readTimeout(2, TimeUnit.SECONDS);
  }

  private final LocalResponseDispatcher dispatcher;
  private final MockWebServer mockWebServer;

  public LogicTestModule() {
    dispatcher = new LocalResponseDispatcher();
    this.mockWebServer = getMockWebServer(dispatcher);
  }

  @Override
  protected String getBaseUrl() {
    return mockWebServer.url("/").toString();
  }

  @Provides
  @Singleton
  public MockWebServer provideDefaultMockWebServer(Dispatcher dispatcher) {
    return mockWebServer;
  }

  @NonNull
  private MockWebServer getMockWebServer(Dispatcher dispatcher) {
    MockWebServer mockWebServer = new MockWebServer();
    mockWebServer.setDispatcher(dispatcher);
    return mockWebServer;
  }

  @Provides
  @Singleton
  public Dispatcher getTestDispatcher() {
    return dispatcher;
  }
}