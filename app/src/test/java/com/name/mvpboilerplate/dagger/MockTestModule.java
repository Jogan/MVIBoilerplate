package com.name.mvpboilerplate.dagger;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;

@Module
public class MockTestModule extends ApiModule {
  @Override
  protected void addOkHttpConfig(OkHttpClient.Builder builder) {
  }

  private final MockWebServer mockWebServer;

  public MockTestModule() {
    this.mockWebServer = new MockWebServer();
  }

  @Override
  protected String getBaseUrl() {
    return mockWebServer.url("/").toString();
  }

  @Provides
  @Singleton
  public MockWebServer provideDefaultMockWebServer() {
    return mockWebServer;
  }
}