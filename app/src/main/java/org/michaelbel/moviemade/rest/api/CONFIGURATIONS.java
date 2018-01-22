package org.michaelbel.moviemade.rest.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CONFIGURATIONS {

    @GET("configuration?")
    Observable<?> getApiConfiguration(

    );

    @GET("configuration/countries?")
    Observable<?> getCountries(

    );

    @GET("configuration/jobs?")
    Observable<?> getJobs(

    );

    @GET("configuration/languages?")
    Observable<?> getLanguages(

    );

    @GET("configuration/primary_translations?")
    Observable<?> getPrimaryTranslations(

    );

    @GET("configuration/timezones?")
    Observable<?> getTimezones(

    );
}