/*
Copyright 2015 Hannes Dorfmann

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.name.mvpboilerplate.ui.base.mvi;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * A {@link MviPresenter} implementation that implements the <a href="https://en.wikipedia.org/wiki/Null_Object_pattern">null
 * object pattern</a> for the attached mvp view. So whenever the view gets detached from this
 * presenter by calling{@link #detachView(boolean)}, a new "null object" view gets dynamically
 * instantiated by using reflections and set as the current
 * view (instead of null). The new "null object" view simply does nothing. This avoids
 * NullPointerExceptions and checks like {@code if (getView() != null)}
 *
 * <p>
 * Please note that when creating the "null object" the first generic parameter (left depth-first
 * search) that will be found that is subtype of {@link MviView} will be used as the type of the
 * view. So avoid having multiple generic parameters for "View" like this {@code
 * MyPresenter<FooMvpView, OtherMvpView>} because we can't know whether FooMvpView or OtherMvpView
 * is
 * the
 * real type of this presenter's view. In that case (left depth-first search) FooMvpView will be
 * used (may cause ClassCastException if OtherMvpView was the desired one)
 * </p>
 *
 * @param <V> The type of the {@link MviView}
 * @author Jens Dirller , Hannes Dorfmann
 * @since 1.2.0
 */
public abstract class MviNullObjectBasePresenter<V extends MviView, VS extends MviViewState>
    implements MviPresenter<V, VS> {

  private WeakReference<V> view;
  private final V nullView;
  protected boolean viewAttachedAtLeastOnce = false;

  public MviNullObjectBasePresenter() {
    try {

      // Scan the inheritance hierarchy until we reached MvpNullObjectBasePresenter
      Class<V> viewClass = null;
      Class<?> currentClass = getClass();

      while (viewClass == null) {

        Type genericSuperType = currentClass.getGenericSuperclass();

        while (!(genericSuperType instanceof ParameterizedType)) {
          // Scan inheritance tree until we find ParameterizedType which is probably a MvpSubclass
          currentClass = currentClass.getSuperclass();
          genericSuperType = currentClass.getGenericSuperclass();
        }

        Type[] types = ((ParameterizedType) genericSuperType).getActualTypeArguments();

        for (int i = 0; i < types.length; i++) {
          Class<?> genericType = (Class<?>) types[i];
          if (genericType.isInterface() && isSubTypeOfMvpView(genericType)) {
            viewClass = (Class<V>) genericType;
            break;
          }
        }

        // Continue with next class in inheritance hierachy (see genericSuperType assignment at start of while loop)
        currentClass = currentClass.getSuperclass();
      }

      nullView = NoOp.of(viewClass);
    } catch (Throwable t) {
      throw new IllegalArgumentException(
          "The generic type <V extends MvpView> must be the first generic type argument of class "
              + getClass().getSimpleName()
              + " (per convention). Otherwise we can't determine which type of View this"
              + " Presenter coordinates.", t);
    }
  }

  /**
   * Scans the interface inheritnace hierarchy and checks if on the root is MvpView.class
   *
   * @param klass The leaf interface where to begin to scan
   * @return true if subtype of MvpView, otherwise false
   */
  private boolean isSubTypeOfMvpView(Class<?> klass) {
    if (klass.equals(MviView.class)) {
      return true;
    }
    Class[] superInterfaces = klass.getInterfaces();
    for (int i = 0; i < superInterfaces.length; i++) {
      if (isSubTypeOfMvpView(superInterfaces[i])) {
        return true;
      }
    }
    return false;
  }

  @UiThread @Override public void attachView(@NonNull V view) {
    this.view = new WeakReference<>(view);
    viewAttachedAtLeastOnce = true;
  }

  @UiThread @NonNull protected V getView() {
    if (!viewAttachedAtLeastOnce) {
      throw new IllegalStateException("No view has ever been attached to this presenter!");
    }
    if (view != null) {
      V realView = view.get();
      if (realView != null) {
        return realView;
      }
    }

    return nullView;
  }

  @UiThread @Override public void detachView(boolean retainInstance) {
    if (view != null) {
      view.clear();
      view = null;
    }
  }
}