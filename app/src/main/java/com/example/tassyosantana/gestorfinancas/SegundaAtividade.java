package com.example.tassyosantana.gestorfinancas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import DAO.FrequenciaDAO;
import Model.Frequencia;

public class SegundaAtividade extends AppCompatActivity implements View.OnFocusChangeListener {
    private EditText dataCampo;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerDialog;
    private EditText valorCampo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_atividade);

        //radioDespesas


        //DropDown
        Spinner spinner = (Spinner) findViewById(R.id.listaFrequencia);

        FrequenciaDAO frequenciaDados = new FrequenciaDAO(getBaseContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,frequenciaDados.getAllFrequencia()){
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,21);
                tv.setGravity(Gravity.CENTER);

                return tv;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

        spinner.setAdapter(adapter);


        //Campo data
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        dataCampo = (EditText) findViewById(R.id.textData);
        dataCampo.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        //Campo valor
        valorCampo = (EditText) findViewById(R.id.textValor);


    }

    public void telaAnterior(View view){

        Intent Activity2 = new Intent(SegundaAtividade.this, AtividadePrincipal.class);
        startActivityForResult(Activity2, 1);
        finish();
    }

    private void setDateTimeField(){
        dataCampo.setOnFocusChangeListener(this);

        Calendar novoCalendario = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar novaData = Calendar.getInstance();
                novaData.set(year,monthOfYear, dayOfMonth);
                dataCampo.setText(dateFormat.format(novaData.getTime()));
            }
        }, novoCalendario.get(Calendar.YEAR), novoCalendario.get(Calendar.MONTH), novoCalendario.get(Calendar.DAY_OF_MONTH));


    }

    private void findViewsById(){

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus){
        if(hasFocus)
            datePickerDialog.show();
        v.clearFocus();
    }

}
