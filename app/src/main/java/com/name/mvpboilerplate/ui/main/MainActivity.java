package com.name.mvpboilerplate.ui.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.name.mvpboilerplate.R;
import com.name.mvpboilerplate.ui.base.BaseActivity;
import com.name.mvpboilerplate.ui.common.ErrorView;
import com.name.mvpboilerplate.ui.detail.DetailActivity;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainView, PokemonAdapter.ClickListener,
    ErrorView.ErrorListener {

  @Inject PokemonAdapter pokemonAdapter;
  @Inject MainPresenter mainPresenter;

  @BindView(R.id.view_error) ErrorView errorView;
  @BindView(R.id.progress) ProgressBar progress;
  @BindView(R.id.recycler_pokemon) RecyclerView pokemonRecycler;
  @BindView(R.id.swipe_to_refresh) SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent().inject(this);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    mainPresenter.attachView(this);

    setSupportActionBar(toolbar);

    swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
    swipeRefreshLayout.setColorSchemeResources(R.color.white);
    //swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.getPokemon(POKEMON_COUNT));

    pokemonAdapter.setClickListener(this);
    pokemonRecycler.setLayoutManager(new LinearLayoutManager(this));
    pokemonRecycler.setAdapter(pokemonAdapter);

    errorView.setErrorListener(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mainPresenter.detachView(false);
  }

  @Override public Observable<Boolean> loadFirstPageIntent() {
    return Observable.just(true).doOnComplete(() -> Timber.d("firstPage completed"));
  }

  @Override public void render(MainViewState viewState) {
    if (viewState.loading()) {
      if (pokemonRecycler.getVisibility() == View.VISIBLE
          && pokemonAdapter.getItemCount() > 0) {
        swipeRefreshLayout.setRefreshing(true);
      } else {
        progress.setVisibility(View.VISIBLE);

        pokemonRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
      }

      errorView.setVisibility(View.GONE);
    } else {
      swipeRefreshLayout.setRefreshing(false);
      progress.setVisibility(View.GONE);
    }

    if(!viewState.pokemon().isEmpty()) {
      pokemonAdapter.setPokemon(viewState.pokemon());
      pokemonAdapter.notifyDataSetChanged();

      pokemonRecycler.setVisibility(View.VISIBLE);
      swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    if(viewState.error()) {
      pokemonRecycler.setVisibility(View.GONE);
      swipeRefreshLayout.setVisibility(View.GONE);
      errorView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onPokemonClick(String pokemon) {
    startActivity(DetailActivity.getStartIntent(this, pokemon));
  }

  @Override
  public void onReloadData() {
    // TODO mainPresenter.getPokemon(POKEMON_COUNT);
  }
}