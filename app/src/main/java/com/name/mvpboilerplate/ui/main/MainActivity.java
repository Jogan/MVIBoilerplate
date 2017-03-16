package com.name.mvpboilerplate.ui.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.name.mvpboilerplate.R;
import com.name.mvpboilerplate.dagger.ActivityComponent;
import com.name.mvpboilerplate.ui.base.BaseActivity;
import com.name.mvpboilerplate.ui.common.ErrorView;
import com.name.mvpboilerplate.ui.detail.DetailActivity;
import io.reactivex.Observable;
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
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mainPresenter.attachView(this);

    setSupportActionBar(toolbar);

    swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
    swipeRefreshLayout.setColorSchemeResources(R.color.white);

    pokemonAdapter.setClickListener(this);
    pokemonRecycler.setLayoutManager(new LinearLayoutManager(this));
    pokemonRecycler.setAdapter(pokemonAdapter);

    errorView.setErrorListener(this);
  }

  @Override protected void injectFrom(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mainPresenter.detachView(false);
  }

  @Override public Observable<Boolean> loadFirstPageIntent() {
    return Observable.just(true).doOnComplete(() -> Timber.d("firstPage completed"));
  }

  @Override public Observable<Boolean> pullToRefreshIntent() {
    return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout).map(ignored -> true);
  }

  @Override public void render(MainViewState viewState) {
    Timber.d("render %s", viewState);
    if (!viewState.loadingFirstPage() && viewState.firstPageError() == null) {
      renderShowData(viewState);
    } else if (viewState.loadingFirstPage()) {
      renderFirstPageLoading();
    } else if (viewState.firstPageError() != null) {
      renderFirstPageError();
    } else {
      throw new IllegalStateException("Unknown view state " + viewState);
    }
  }

  private void renderShowData(MainViewState viewState) {
    progress.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
    pokemonRecycler.setVisibility(View.VISIBLE);
    swipeRefreshLayout.setVisibility(View.VISIBLE);

    pokemonAdapter.setPokemon(viewState.data());
    pokemonAdapter.notifyDataSetChanged();

    boolean pullToRefreshFinished = swipeRefreshLayout.isRefreshing()
        && !viewState.loadingPullToRefresh()
        && viewState.pullToRefreshError() == null;
    if (pullToRefreshFinished) {
      // Swipe to refresh finished successfully so scroll to the top of the list (to see inserted items)
      pokemonRecycler.smoothScrollToPosition(0);
    }

    swipeRefreshLayout.setRefreshing(viewState.loadingPullToRefresh());

    if (viewState.pullToRefreshError() != null) {
      Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show();
    }
  }

  private void renderFirstPageError() {
    pokemonRecycler.setVisibility(View.GONE);
    swipeRefreshLayout.setVisibility(View.GONE);
    progress.setVisibility(View.GONE);
    errorView.setVisibility(View.VISIBLE);
  }

  private void renderFirstPageLoading() {
    pokemonRecycler.setVisibility(View.GONE);
    swipeRefreshLayout.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
    progress.setVisibility(View.VISIBLE);
  }

  @Override
  public void onPokemonClick(String pokemon) {
    startActivity(DetailActivity.getStartIntent(this, pokemon));
  }

  // TODO replace with intent
  @Override
  public void onReloadData() {
    // TODO mainPresenter.getPokemon(POKEMON_COUNT);
  }
}