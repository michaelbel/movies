package org.michaelbel.movies.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class MoviesDatabaseWorker_AssistedFactory_Impl implements MoviesDatabaseWorker_AssistedFactory {
  private final MoviesDatabaseWorker_Factory delegateFactory;

  MoviesDatabaseWorker_AssistedFactory_Impl(MoviesDatabaseWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public MoviesDatabaseWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<MoviesDatabaseWorker_AssistedFactory> create(
      MoviesDatabaseWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MoviesDatabaseWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<MoviesDatabaseWorker_AssistedFactory> createFactoryProvider(
      MoviesDatabaseWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MoviesDatabaseWorker_AssistedFactory_Impl(delegateFactory));
  }
}
