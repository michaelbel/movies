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
public final class AccountUpdateWorker_AssistedFactory_Impl implements AccountUpdateWorker_AssistedFactory {
  private final AccountUpdateWorker_Factory delegateFactory;

  AccountUpdateWorker_AssistedFactory_Impl(AccountUpdateWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public AccountUpdateWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<AccountUpdateWorker_AssistedFactory> create(
      AccountUpdateWorker_Factory delegateFactory) {
    return InstanceFactory.create(new AccountUpdateWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<AccountUpdateWorker_AssistedFactory> createFactoryProvider(
      AccountUpdateWorker_Factory delegateFactory) {
    return InstanceFactory.create(new AccountUpdateWorker_AssistedFactory_Impl(delegateFactory));
  }
}
