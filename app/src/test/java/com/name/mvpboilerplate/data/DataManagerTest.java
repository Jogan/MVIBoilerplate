package com.name.mvpboilerplate.data;

import com.google.android.apps.secrets.test.common.TestDataFactory;
import com.name.mvpboilerplate.data.model.NamedResource;
import com.name.mvpboilerplate.data.model.Pokemon;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import com.name.mvpboilerplate.util.RxSchedulersOverrideRule;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    MvpBoilerplateService mockMvpBoilerplateService;
    DataManager dataManager;

    @Rule
    // Must be added to every test class that targets app code that uses RxJava
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        dataManager = new DataManager(mockMvpBoilerplateService);
    }

    @Test
    public void getPokemonListCompletesAndEmitsPokemonList() {
        /*List<NamedResource> namedResourceList = TestDataFactory.makeNamedResourceList(5);
        MvpBoilerplateService.PokemonListResponse pokemonListResponse =
                new MvpBoilerplateService.PokemonListResponse();
        pokemonListResponse.results = namedResourceList;

        when(mockMvpBoilerplateService.getPokemonList(anyInt()))
                .thenReturn(Single.just(pokemonListResponse));

        TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>();
        dataManager.getPokemonList(10).subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(TestDataFactory.makePokemonNameList(namedResourceList));*/
    }

    @Test
    public void getPokemonCompletesAndEmitsPokemon() {
        /*String name = "charmander";
        Pokemon pokemon = TestDataFactory.makePokemon(name);
        when(mockMvpBoilerplateService.getPokemon(anyString()))
                .thenReturn(Single.just(pokemon));

        TestSubscriber<Pokemon> testSubscriber = new TestSubscriber<>();
        dataManager.getPokemon(name).subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertValue(pokemon);*/
    }

}
