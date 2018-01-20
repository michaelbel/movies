package org.michaelbel.moviemade.rest.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CONFIGURATIONS {

    @GET("configuration?")
    Call<?> getApiConfiguration(

    );

    @GET("configuration/countries?")
    Call<?> getCountries(

    );

    @GET("configuration/jobs?")
    Call<?> getJobs(

    );

    @GET("configuration/languages?")
    Call<?> getLanguages(

    );

    @GET("configuration/primary_translations?")
    Call<?> getPrimaryTranslations(

    );

    @GET("configuration/timezones?")
    Call<?> getTimezones(

    );
}