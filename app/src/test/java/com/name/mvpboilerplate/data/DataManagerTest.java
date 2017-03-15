package com.name.mvpboilerplate.data;

import com.name.mvpboilerplate.dagger.BaseLogicTest;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import io.reactivex.observers.TestObserver;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest extends BaseLogicTest {
  @Inject MvpBoilerplateService pokemonService;

  @Override
  public void setUp() throws Exception {
    getComponent().inject(this);
    super.setUp();
  }

  @Test
  public void testGetPokemonStringObservable() {
    DataManager dataManager = new DataManager(pokemonService);
    TestObserver observer = new TestObserver();
    dataManager.getPokemonList(20).subscribe(observer);
    observer.assertNoErrors();
    observer.awaitTerminalEvent();
    observer.assertComplete();
    observer.assertValueCount(20);
  }
}
