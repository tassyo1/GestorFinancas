package Model;

import java.util.Date;

/**
 * Created by tassyosantana on 08/05/16.
 */
public class Movimento {
    private int id;
    private String data_lancamento;
    private String nome_categoria;
    private Float saldo_atual;
    private Float valor;
    private int categoria_id;

    public Movimento(){
        this.saldo_atual = 0f;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getData_lancamento(){
        return this.data_lancamento;
    }
    public void setData_lancamento(String data_lancamento){
        this.data_lancamento = data_lancamento;
    }

    public String getNome_categoria(){
        return this.nome_categoria;
    }
    public void setNome_categoria(String nome_categoria){
        this.nome_categoria = nome_categoria;
    }

    public Float getSaldo_atual(){
        return saldo_atual;
    }
    public void setSaldo_atual(Float saldo_atual){
        this.saldo_atual = saldo_atual;
    }

    public Float getValor(){
        return valor;
    }
    public void setValor(Float valor){
        this.valor = valor;
    }

    public int getCategoria_id() { return categoria_id;}
    public void setCategoria_id(int categoria_id) { this.categoria_id = categoria_id;}

    public Float atualizaSaldo(Float valorCategoria, String tipoCategoria){
        if (tipoCategoria.equals("R"))
            this.saldo_atual = this.saldo_atual + valorCategoria;
        else
            this.saldo_atual = this.saldo_atual - valorCategoria;

        return this.saldo_atual;
    }
}
