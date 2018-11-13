package org.michaelbel.moviemade.realm;

import androidx.annotation.NonNull;
import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

@SuppressWarnings("all")
public class MoviemadeMigration implements RealmMigration {

    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema realmSchema = realm.getSchema();
    }
}