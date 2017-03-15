/*
 * Copyright 2017 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.name.mvpboilerplate.ui.main;

import com.name.mvpboilerplate.base.BaseDriver;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * This class is responsible to "drive" the MainView.
 * Internally this creates a {@link MainView} and attaches it to the {@link MainPresenter}
 * and offers public API to fire view intents and to check for expected view.render() events.
 *
 * <p>
 * <b>Create a new instance for every unit test</b>
 * </p>
 *
 * Shout out to Hannes Dorfmann for his great MVI blog posts
 * See https://github.com/sockeqwe/mosby/blob/master/sample-mvi/src/test/java/com/hannesdorfmann/mosby3/sample/mvi/view/home/HomeViewRobot.java
 * for more
 *
 * Each intent from MainView should have a corresponding PublishSubject for testing.
 */
public class MainViewDriver extends BaseDriver {

  private final MainPresenter presenter;
  private final PublishSubject<Boolean> loadFirstPageSubject = PublishSubject.create();
  private final PublishSubject<Boolean> pullToRefreshSubject = PublishSubject.create();

  private MainView view = new MainView() {
    @Override public Observable<Boolean> loadFirstPageIntent() {
      return loadFirstPageSubject;
    }

    @Override public Observable<Boolean> pullToRefreshIntent() {
      return pullToRefreshSubject;
    }

    @Override public void render(MainViewState viewState) {
      renderEvents.add(viewState);
      renderEventSubject.onNext(viewState);
    }
  };

  public MainViewDriver(MainPresenter presenter) {
    this.presenter = presenter;
    presenter.attachView(view);
  }

  public void fireLoadFirstPageIntent() {
    loadFirstPageSubject.onNext(true);
  }

  public void firePullToRefreshIntent() {
    pullToRefreshSubject.onNext(true);
  }
}
