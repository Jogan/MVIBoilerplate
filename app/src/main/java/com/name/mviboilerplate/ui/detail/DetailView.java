package com.name.mviboilerplate.ui.detail;

import com.name.mviboilerplate.ui.base.mvi.MviView;
import io.reactivex.Observable;

public interface DetailView extends MviView {

    Observable<String> loadDataIntent();

    void render(DetailViewState viewState);
}