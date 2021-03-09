package com.example.quipuxprueba.app;

import android.app.Application;

import com.example.quipuxprueba.models.Person;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApplication extends Application {
    public static AtomicInteger PersonID = new AtomicInteger();
    @Override
    public void onCreate() {
        super.onCreate();
        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();
        PersonID = getIdbyTable(realm,Person.class);
        realm.close();
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
