package com.name.mvpboilerplate.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.name.mvpboilerplate.R;
import com.name.mvpboilerplate.data.model.Pokemon;
import com.name.mvpboilerplate.data.model.Statistic;
import com.name.mvpboilerplate.ui.base.BaseActivity;
import com.name.mvpboilerplate.ui.common.ErrorView;
import com.name.mvpboilerplate.ui.detail.widget.StatisticView;
import io.reactivex.Observable;
import javax.inject.Inject;
import timber.log.Timber;

public class DetailActivity extends BaseActivity implements DetailView, ErrorView.ErrorListener {

  public static final String EXTRA_POKEMON_NAME = "EXTRA_POKEMON_NAME";

  @Inject DetailPresenter detailPresenter;

  @BindView(R.id.view_error) ErrorView errorView;
  @BindView(R.id.image_pokemon) ImageView pokemonImage;
  @BindView(R.id.progress) ProgressBar progress;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.layout_stats) LinearLayout statLayout;
  @BindView(R.id.layout_pokemon) View pokemonLayout;

  private String pokemonName;

  public static Intent getStartIntent(Context context, String pokemonName) {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(EXTRA_POKEMON_NAME, pokemonName);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent().inject(this);
    setContentView(R.layout.activity_detail);
    ButterKnife.bind(this);

    pokemonName = getIntent().getStringExtra(EXTRA_POKEMON_NAME);
    if (pokemonName == null) {
      throw new IllegalArgumentException("Detail Activity requires a pokemon name");
    }

    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    setTitle(pokemonName.substring(0, 1).toUpperCase() + pokemonName.substring(1));

    errorView.setErrorListener(this);

    detailPresenter.attachView(this);
  }

  @Override public Observable<String> loadDataIntent() {
    return Observable.just(pokemonName).doOnComplete(() -> Timber.d("loadData completed"));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    detailPresenter.detachView(false);
  }

  @Override public void render(DetailViewState viewState) {
    if (viewState.loading()) {
      errorView.setVisibility(View.GONE);
      progress.setVisibility(View.VISIBLE);
    } else if (viewState.data() != null) {
      final Pokemon pokemon = viewState.data();
      if (pokemon.sprites() != null && pokemon.sprites().frontDefault() != null) {
        Glide.with(this)
             .load(pokemon.sprites().frontDefault())
             .into(pokemonImage);
      }
      if (pokemon.stats() != null && !pokemon.stats().isEmpty()) {
        for (Statistic stat : pokemon.stats()) {
          StatisticView statisticView = new StatisticView(this);
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