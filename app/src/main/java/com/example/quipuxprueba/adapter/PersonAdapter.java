package com.example.quipuxprueba.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quipuxprueba.R;
import com.example.quipuxprueba.models.Person;

import java.util.List;

import io.realm.Realm;

public class PersonAdapter extends BaseAdapter {

    Realm realm;
    private Context context;
    private List<Person> list;
    private int layout;

    public PersonAdapter(Context context,List<Person> list,int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i,View view,ViewGroup viewGroup) {

        ViewHolder vh;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_person, null);
            vh = new ViewHolder();
            vh.txTypeId = (TextView) view.findViewById(R.id.txTypeId);
            vh.txName = (TextView) view.findViewById(R.id.txName);
            vh.txAge = (TextView) view.findViewById(R.id.txAge);
            vh.lyListPerson = (View) view.findViewById(R.id.lyListPerson);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }

        Person person = list.get(i);
        vh.txTypeId.setText(person.getTypeId()+": "+person.getNumberId());
        vh.txName.setText(person.getName()+" "+person.getLastName());
        vh.txAge.setText(String.valueOf(person.getAge()));

        if (person.getGender().equals("Femenino")){
            vh.lyListPerson.setBackgroundColor(Color.LTGRAY);
        }

        vh.lyListPerson.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmacion");
                builder.setMessage("Desea eliminar el registro de "+person.getName()+"?");
                builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,int i) {
                        realm.beginTransaction();
                        person.deleteFromRealm();
                        realm.commitTransaction();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();



                return false;
            }
        });


        return view;
    }

    public class ViewHolder{
        TextView txName, txAge, txTypeId;
        View lyListPerson;
    }
}
