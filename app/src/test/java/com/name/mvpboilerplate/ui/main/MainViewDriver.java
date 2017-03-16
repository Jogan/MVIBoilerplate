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

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.Assert;

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
public class MainViewDriver {

  private final MainPresenter presenter;
  private final PublishSubject<Boolean> loadFirstPageSubject = PublishSubject.create();
  private final PublishSubject<Boolean> pullToRefreshSubject = PublishSubject.create();
  protected final ReplaySubject<MainViewState> renderEventSubject = ReplaySubject.create();
  protected final List<MainViewState> renderEvents = new CopyOnWriteArrayList<>();

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
    this.presenter.attachView(view);
  }

  public void fireLoadFirstPageIntent() {
    loadFirstPageSubject.onNext(true);
  }

  public void firePullToRefreshIntent() {
    pullToRefreshSubject.onNext(true);
  }

  /**
   * Blocking waits for view.render() calls
   */
  public void assertViewStateRendered(MainViewState... expectedViewStates) {

    if (expectedViewStates == null) {
      throw new NullPointerException("expectedViewStates == null");
    }

    int eventsCount = expectedViewStates.length;
    renderEventSubject.take(eventsCount).blockingSubscribe();

    /*
    // Wait for few milli seconds to ensure that no more render events have occurred
    // before finishing the test and checking expectations (asserts)
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    */

    if (renderEventSubject.getValues().length > eventsCount) {
      Assert.fail("Expected to wait for "
          + eventsCount
          + ", but there were "
          + renderEventSubject.getValues().length
          + " Events in total, which is more than expected: "
          + arrayToString(renderEventSubject.getValues()));
    }

    Assert.assertEquals(Arrays.asList(expectedViewStates), renderEvents);
  }

  /**
   * Simple helper function to print the content of an array as a string
   */
  private String arrayToString(Object[] array) {
    StringBuffer buffer = new StringBuffer();
    for (Object o : array) {
      buffer.append(o.toString());
      buffer.append("\n");
    }

    return buffer.toString();
  }
}
