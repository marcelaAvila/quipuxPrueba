package com.example.quipuxprueba.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quipuxprueba.R;
import com.example.quipuxprueba.models.Person;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

public class AddPersonActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner spTypeId, spGender;
    EditText etNumberId, etName, etLastName, etAge;
    Button btCancel, btNext;

    String[] typeIdList = {"Cedula de ciudadania","Tarjeta de identidad","Pasaporte"};
    String[] genderList = {"Femenino","Masculino","Otro"};

    int spTypePosition;
    int spGenderPosition;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        realm = Realm.getDefaultInstance();
        bindUi();

        spTypeId.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,typeIdList));
        spGender.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,genderList));

        spTypePosition = spTypeId.getSelectedItemPosition();
        spGenderPosition = spTypeId.getSelectedItemPosition();

        if (spTypePosition == 0 || spGenderPosition == 0) {
            etNumberId.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etNumberId.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public void bindUi() {
        spTypeId = findViewById(R.id.spTypeId);
        spGender = findViewById(R.id.spGender);
        etNumberId = findViewById(R.id.etNumberId);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etAge = findViewById(R.id.etAge);
        btCancel = findViewById(R.id.btCancel);
        btCancel.setOnClickListener(this);
        btNext = findViewById(R.id.btNext);
        btNext.setOnClickListener(this);

        spTypeId.setOnItemSelectedListener(this);


    }

    public void createNewPerson(){
        realm.beginTransaction();
        Person person = new Person(spTypeId.getSelectedItem().toString(),etNumberId.getText().toString(),etName.getText().toString(),etLastName.getText().toString(),Integer.parseInt(etAge.getText().toString()),spGender.getSelectedItem().toString());
        realm.copyToRealm(person);
        realm.commitTransaction();

        finish();

    }

    public void enviarvalidador() {
        boolean cancel = false;
        View focusView = null;

        String etNumberId1, etName1, etLastName1, etAge1;
        etNumberId1 = etNumberId.getText().toString();
        etName1 = etName.getText().toString();
        etLastName1 = etLastName.getText().toString();
        etAge1 = etAge.getText().toString();

        if (TextUtils.isEmpty(etNumberId1)) {

            etNumberId.setError("Este campo es requerido");
            focusView = etNumberId;
            cancel = true;
        } else if (TextUtils.isEmpty(etName1)) {

            etName.setError("Este campo es requerido");
            focusView = etName;
            cancel = true;
        } else if (TextUtils.isEmpty(etLastName1)) {
            etLastName.setError("Este campo es requerido");
            focusView = etLastName;
            cancel = true;
        } else if (TextUtils.isEmpty(etAge1)) {
            etAge.setError("Este campo es requerido");
            focusView = etAge;
            cancel = true;
        }else if (Integer.parseInt(etAge1)<18){
            etAge.setError("La edad es menor de 18 años");
            focusView = etAge;
            cancel = true;
        }else if (Integer.parseInt(etAge1)>60){
            Toast.makeText(this,"Vamos a ingresar una persona de la tercera edad, su edad es: "+etAge1,Toast.LENGTH_LONG).show();
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            createNewPerson();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btCancel) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmacion");
            builder.setMessage("Desea cancelar la operacion?");
            builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface,int i) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (view.getId() == R.id.btNext) {
            enviarvalidador();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView,View view,int i,long l) {
        if (i == 0 || i == 1) {
            etNumberId.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etNumberId.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (i == 0) {
            etNumberId.setMaxEms(10);
        } else if (i==1){
            etNumberId.setMaxEms(11);
        }else{
            etNumberId.setMaxEms(15);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}