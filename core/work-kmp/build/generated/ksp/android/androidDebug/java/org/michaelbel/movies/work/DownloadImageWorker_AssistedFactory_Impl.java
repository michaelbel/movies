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
public final class DownloadImageWorker_AssistedFactory_Impl implements DownloadImageWorker_AssistedFactory {
  private final DownloadImageWorker_Factory delegateFactory;

  DownloadImageWorker_AssistedFactory_Impl(DownloadImageWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public DownloadImageWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<DownloadImageWorker_AssistedFactory> create(
      DownloadImageWorker_Factory delegateFactory) {
    return InstanceFactory.create(new DownloadImageWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<DownloadImageWorker_AssistedFactory> createFactoryProvider(
      DownloadImageWorker_Factory delegateFactory) {
    return InstanceFactory.create(new DownloadImageWorker_AssistedFactory_Impl(delegateFactory));
  }
}
