package com.name.mviboilerplate.ui.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.name.mviboilerplate.R;
import com.name.mviboilerplate.dagger.ActivityComponent;
import com.name.mviboilerplate.data.model.Pokemon;
import com.name.mviboilerplate.data.model.Statistic;
import com.name.mviboilerplate.ui.base.BaseMviController;
import com.name.mviboilerplate.ui.common.ErrorView;
import com.name.mviboilerplate.ui.detail.widget.StatisticView;
import com.name.mviboilerplate.util.BundleBuilder;
import io.reactivex.Observable;
import javax.inject.Inject;
import timber.log.Timber;

public class DetailController extends BaseMviController<DetailView, DetailViewState, DetailPresenter> implements DetailView, ErrorView.ErrorListener {

  public static final String ARG_POKEMON_NAME = "pokemon_name";

  @Inject DetailPresenter detailPresenter;

  @BindView(R.id.view_error) ErrorView errorView;
  @BindView(R.id.image_pokemon) ImageView pokemonImage;
  @BindView(R.id.progress) ProgressBar progress;
  @BindView(R.id.layout_stats) LinearLayout statLayout;
  @BindView(R.id.layout_pokemon) View pokemonLayout;

  private String pokemonName;

  public DetailController(String pokemonName) {
    this(new BundleBuilder(new Bundle())
        .putString(ARG_POKEMON_NAME, pokemonName)
        .build());
  }

  public DetailController(Bundle args) {
    super(args);
  }

  @Override protected void injectController() {
    getControllerComponent().inject(this);
  }

  @Override protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    return inflater.inflate(R.layout.controller_detail, container, false);
  }

  @Override protected void onViewBound(@NonNull View view) {
    super.onViewBound(view);

    pokemonName = getArgs().getString(ARG_POKEMON_NAME);
    if (pokemonName == null) {
      throw new IllegalArgumentException("DetailController requires a pokemon name");
    }

    // TODO ACTION BAR
    /*setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    setTitle(pokemonName.substring(0, 1).toUpperCase() + pokemonName.substring(1));*/

    errorView.setErrorListener(this);
  }

  @Override public Observable<String> loadDataIntent() {
    return Observable.just(pokemonName).doOnComplete(() -> Timber.d("loadData completed"));
  }

  @Override public void render(DetailViewState viewState) {
    if (viewState.loading()) {
      errorView.setVisibility(View.GONE);
      progress.setVisibility(View.VISIBLE);
    } else if (viewState.data() != null) {
      final Pokemon pokemon = viewState.data();
      if (pokemon.sprites() != null && pokemon.sprites().frontDefault() != null) {
        Glide.with(getActivity())
             .load(pokemon.sprites().frontDefault())
             .into(pokemonImage);
      }
      if (pokemon.stats() != null && !pokemon.stats().isEmpty()) {
        for (Statistic stat : pokemon.stats()) {
          StatisticView statisticView = new StatisticView(getActivity());
          statisticView.setStat(stat);
          statLayout.addView(statisticView);
        }
      }
      errorView.setVisibility(View.GONE);
      progress.setVisibility(View.GONE);
      pokemonLayout.setVisibility(View.VISIBLE);
    } else if (viewState.error() != null) {
      progress.setVisibility(View.GONE);
      pokemonLayout.setVisibility(View.GONE);
      errorView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onReloadData() {
    //detailPresenter.getPokemon(pokemonName);
  }
}