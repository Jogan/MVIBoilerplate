package com.name.mvpboilerplate.dagger.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.name.mvpboilerplate.BuildConfig;
import com.name.mvpboilerplate.data.remote.AutoValueGsonTypeAdapter;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

  public ApiModule() {

  }

  @Provides
  @Singleton
  public MvpBoilerplateService provideApiService(Retrofit retrofit) {
    return retrofit.create(MvpBoilerplateService.class);
  }

  @Provides
  @Singleton
  public HttpLoggingInterceptor getLoggingInterceptor() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    return logging;
  }

  @Provides
  @Singleton
  Gson provideGson() {
    return new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapterFactory(AutoValueGsonTypeAdapter.create())
        .create();
  }

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS);
    addOkHttpConfig(builder);
    return builder.build();
  }

  protected void addOkHttpConfig(OkHttpClient.Builder builder) {

  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  protected String getBaseUrl() {
    return BuildConfig.POKEAPI_API_URL;
  }
}
