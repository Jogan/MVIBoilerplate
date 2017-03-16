package com.name.mviboilerplate.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.name.mviboilerplate.R;
import com.name.mviboilerplate.ui.base.BaseMviController;
import com.name.mviboilerplate.ui.common.ErrorView;
import com.name.mviboilerplate.ui.detail.DetailController;
import io.reactivex.Observable;
import javax.inject.Inject;
import timber.log.Timber;

public class HomeController extends BaseMviController<HomeView, HomeViewState, HomePresenter>
    implements HomeView, PokemonAdapter.ClickListener, ErrorView.ErrorListener {

  @BindView(R.id.view_error) ErrorView errorView;
  @BindView(R.id.progress) ProgressBar progress;
  @BindView(R.id.recycler_pokemon) RecyclerView pokemonRecycler;
  @BindView(R.id.swipe_to_refresh) SwipeRefreshLayout swipeRefreshLayout;

  @Inject PokemonAdapter pokemonAdapter;

  public HomeController() {
    this(null);
  }

  public HomeController(Bundle args) {
    super(args);
  }

  @Override protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_home, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    super.onViewBound(view);
    swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
    swipeRefreshLayout.setColorSchemeResources(R.color.white);

    pokemonAdapter.setClickListener(this);
    pokemonRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    pokemonRecycler.setAdapter(pokemonAdapter);

    errorView.setErrorListener(this);
  }

  @Override protected void injectController() {
    getControllerComponent().inject(this);
  }

  @Override public Observable<Boolean> loadFirstPageIntent() {
    return Observable.just(true).doOnComplete(() -> Timber.d("firstPage completed"));
  }

  @Override public Observable<Boolean> pullToRefreshIntent() {
    return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout).map(ignored -> true);
  }

  @Override public void render(HomeViewState viewState) {
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

  private void renderShowData(HomeViewState viewState) {
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
      Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_LONG).show();
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

    final DetailController detailController = new DetailController(pokemon);

    getRouter()
        .pushController(RouterTransaction
            .with(detailController)
            .popChangeHandler(new FadeChangeHandler())
            .popChangeHandler(new FadeChangeHandler()));
  }

  // TODO replace with intent
  @Override
  public void onReloadData() {
    // TODO mainPresenter.getPokemon(POKEMON_COUNT);
  }
}
