package com.name.mviboilerplate.ui.home;

import java.util.List;

public interface HomeViewPartialStateChanges {

  HomeViewState computeNewState(HomeViewState previousState);

  /**
   * Indicates that the first page is loading
   */
  final class FirstPageLoading implements HomeViewPartialStateChanges {

    @Override public String toString() {
      return "FirstPageLoadingState{}";
    }

    @Override public HomeViewState computeNewState(HomeViewState previousState) {
      return previousState.toBuilder()
                          .loadingFirstPage(true)
                          .firstPageError(null)
                          .build();
    }
  }

  /**
   * Indicates that an error has occurred while loading the first page
   */
  final class FirstPageError implements HomeViewPartialStateChanges {
    private final Throwable error;

    public FirstPageError(Throwable error) {
      this.error = error;
    }

    public Throwable getError() {
      return error;
    }

    @Override public String toString() {
      return "FirstPageErrorState{" +
          "error=" + error +
          '}';
    }

    @Override public HomeViewState computeNewState(HomeViewState previousState) {
      return previousState.toBuilder()
                          .firstPageError(getError())
                          .loadingFirstPage(false)
                          .build();
    }
  }

  /**
   * Indicates that the first page data has been loaded successfully
   */
  final class FirstPageLoaded implements HomeViewPartialStateChanges {
    private final List<String> data;

    public FirstPageLoaded(List<String> data) {
      this.data = data;
    }

    public List<String> getData() {
      return data;
    }

    @Override public HomeViewState computeNewState(HomeViewState previousState) {
      return previousState.toBuilder()
                          .data(getData())
                          .loadingFirstPage(false)
                          .firstPageError(null)
                          .build();
    }
  }

  /**
   * Indicates that loading the newest items via pull to refresh has started
   */
  final class PullToRefreshLoading implements HomeViewPartialStateChanges {

    @Override public HomeViewState computeNewState(HomeViewState previousState) {
      return previousState.toBuilder()
                          .loadingPullToRefresh(true)
                          .pullToRefreshError(null)
                          .build();
    }
  }

  /**
   * Indicates that an error while loading the newest items via pull to refresh has occurred
   */
  final class PullToRefeshLoadingError implements HomeViewPartialStateChanges {
    private final Throwable error;

    public PullToRefeshLoadingError(Throwable error) {
      this.error = error;
    }

    public Throwable getError() {
      return error;
    }

    @Override public HomeViewState computeNewState(HomeViewState previousState) {
      return previousState.toBuilder()
                          .pullToRefreshError(getError())
                          .loadingPullToRefresh(false)
                          .build();
    }
  }

  /**
   * Indicates that data has been loaded successfully over pull-to-refresh
   */
  final class PullToRefreshLoaded implements HomeViewPartialStateChanges {
    private final List<String> data;

    public PullToRefreshLoaded(List<String> data) {
      this.data = data;
    }

    public List<String> getData() {
      return data;
    }

    @Override public HomeViewState computeNewState(HomeViewState previousState) {
      return previousState.toBuilder()
                          .loadingPullToRefresh(false)
                          .pullToRefreshError(null)
                          .data(getData())
                          .build();
    }
  }
}
