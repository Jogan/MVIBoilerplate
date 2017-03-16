package com.name.mvpboilerplate.ui.base;

import com.name.mvpboilerplate.ui.base.mvi.MviViewState;
import io.reactivex.subjects.ReplaySubject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.Assert;

public class BaseDriver<T extends MviViewState> {

  protected final ReplaySubject<T> renderEventSubject = ReplaySubject.create();
  protected final List<T> renderEvents = new CopyOnWriteArrayList<>();

  /**
   * Blocking waits for view.render() calls
   */
  public void assertViewStateRendered(T... expectedViewStates) {

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
