package org.michaelbel.movies.work;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = DownloadImageWorker.class
)
public interface DownloadImageWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("org.michaelbel.movies.work.DownloadImageWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      DownloadImageWorker_AssistedFactory factory);
}
