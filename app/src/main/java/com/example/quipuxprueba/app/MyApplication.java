package com.example.quipuxprueba.app;

import android.app.Application;

import com.example.quipuxprueba.models.Person;
import com.onesignal.OneSignal;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static com.example.quipuxprueba.Utils.CostumerUtils.getKeyOneSingle;

public class MyApplication extends Application {
    public static AtomicInteger PersonID = new AtomicInteger();

    private static final String ONESIGNAL_APP_ID = getKeyOneSingle();
    @Override
    public void onCreate() {
        super.onCreate();
        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();
        PersonID = getIdbyTable(realm,Person.class);
        realm.close();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }

    private void setUpRealmConfig(){
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();


        Realm.setDefaultConfiguration(config);

    }


    private <T extends RealmObject> AtomicInteger getIdbyTable(Realm realm,Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll();
        return (result.size()>0) ? new AtomicInteger(result.max("id").intValue()) : new AtomicInteger();
    }



}
