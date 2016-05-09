package Model;

import java.util.Date;

/**
 * Created by tassyosantana on 08/05/16.
 */
public class Movimento {
    private String data_lancamento;
    private String nome_categoria;
    private Float saldo_atual;
    private Float valor;

    public Movimento(){

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

}
