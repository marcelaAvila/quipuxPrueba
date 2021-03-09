package com.example.quipuxprueba.activities;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quipuxprueba.R;
import com.example.quipuxprueba.adapter.PersonAdapter;
import com.example.quipuxprueba.models.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Realm realm;
    View lyData, lyNoData;
    ListView listViewPerson;
    Switch swAddPerson;
    Button btAdd;

    PersonAdapter personAdapter;

    RealmResults<Person> persons;
    Boolean switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUi();
        realm = Realm.getDefaultInstance();
        persons = realm.where(Person.class).findAll();
        persons.addChangeListener(new RealmChangeListener<RealmResults<Person>>() {
            @Override
            public void onChange(RealmResults<Person> people) {
                personAdapter.notifyDataSetChanged();
                if (persons.size()>0){
                    lyData.setVisibility(View.VISIBLE);
                    lyNoData.setVisibility(View.GONE);
                }else{
                    lyData.setVisibility(View.GONE);
                    lyNoData.setVisibility(View.VISIBLE);
                }
            }
        });
        swAddPerson.setTextOn("On");
        swAddPerson.setTextOff("Off");

        if (persons.size()>0){
            lyData.setVisibility(View.VISIBLE);
            lyNoData.setVisibility(View.GONE);
        }else{
            lyData.setVisibility(View.GONE);
            lyNoData.setVisibility(View.VISIBLE);
        }
        personAdapter = new PersonAdapter(this, persons, R.layout.list_person);
        listViewPerson.setAdapter(personAdapter);
    }

    public void bindUi(){
        lyData = findViewById(R.id.lyData);
        lyNoData = findViewById(R.id.lyNoData);
        listViewPerson = findViewById(R.id.listViewPerson);
        swAddPerson = findViewById(R.id.swAddPerson);
        btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(this);

        swAddPerson.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btAdd){
            switchState = swAddPerson.isChecked();
            if (switchState){
                createPerson();
            }else{
                Toast.makeText(this, "No es posible crear una nueva persona", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void createPerson(){
        Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
        startActivity(intent);
    }


}