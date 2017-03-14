package com.name.mvpboilerplate.ui.detail;

import com.name.mvpboilerplate.ui.base.mvi.MviView;
import io.reactivex.Observable;

public interface DetailView extends MviView {

    Observable<String> loadDataIntent();

    void render(DetailViewState viewState);
}