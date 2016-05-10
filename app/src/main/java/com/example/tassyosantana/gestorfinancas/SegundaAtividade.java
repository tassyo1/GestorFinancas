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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import DAO.CategoriaDAO;
import DAO.FrequenciaDAO;
import Model.Frequencia;


public class SegundaAtividade extends AppCompatActivity
        implements View.OnFocusChangeListener, AdapterView.OnItemSelectedListener{
    private EditText dataCampo;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePickerDialog;
    private EditText valorCampo;
    private Spinner spinner;
    private RadioButton radioDespesa;
    private RadioButton radioReceita;
    private EditText campoNome;
    private Frequencia frequencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_atividade);

        findViewsById();

        //DropDown
        FrequenciaDAO frequenciaDados = new FrequenciaDAO(getBaseContext());
        ArrayAdapter<Frequencia> adapter = new ArrayAdapter<Frequencia>(this,android.R.layout.simple_spinner_item,frequenciaDados.getAllFrequencia()){
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
        spinner.setOnItemSelectedListener(this);

        //data
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dataCampo.setInputType(InputType.TYPE_NULL);
        setDateTimeField();

        //Campo valor
        //Formatação do campo
        //valorCampo.addTextChangedListener(new NumberTextWatcher(valorCampo,"###.###.###,##"));


    }
    public void telaAnterior(View view){

        Intent Activity2 = new Intent(SegundaAtividade.this, AtividadePrincipal.class);
        startActivityForResult(Activity2, 1);
        finish();
    }

    //Salvar Categoria
    public void salvar(View v){
        CategoriaDAO categoriaDAO = new CategoriaDAO(getBaseContext());
        String tipo ="";

        if (radioDespesa.isChecked())
            tipo ="D";
        if (radioReceita.isChecked())
            tipo = "R";

        String mensagem = categoriaDAO.inserir(tipo,campoNome.getText().toString().trim(),frequencia.getId(),
                dataCampo.getText().toString().trim() ,Float.parseFloat(valorCampo.getText().toString()));

        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();

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
        }, novoCalendario.get(Calendar.YEAR), novoCalendario.get(Calendar.MONTH),
                novoCalendario.get(Calendar.DAY_OF_MONTH));

    }

    private void findViewsById(){
        spinner = (Spinner) findViewById(R.id.listaFrequencia);

        dataCampo = (EditText) findViewById(R.id.textData);

        valorCampo = (EditText) findViewById(R.id.textValor);

        radioDespesa = (RadioButton) findViewById(R.id.radioDespesa);

        radioReceita = (RadioButton) findViewById(R.id.radioReceita);

        campoNome = (EditText) findViewById(R.id.textNome);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus){
        if(hasFocus)
            datePickerDialog.show();
        valorCampo.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        frequencia = (Frequencia) parent.getSelectedItem();

        Toast.makeText(this, "Frequencia ID: " + frequencia.getId() + ",  Frequencia : " + frequencia.getDescricao(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

}
