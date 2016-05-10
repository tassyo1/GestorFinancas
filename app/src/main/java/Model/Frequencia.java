package Model;


/**
 * Created by tassyosantana on 09/05/16.
 */
public class Frequencia {
    private String descricao;
    private int id;
    public Frequencia(int id,String descricao){
        this.id =id;
        this.descricao = descricao;
    }
    public Frequencia(){}

    public String getDescricao(){ return this.descricao;}
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String toString() {
        return descricao;
    }
    public int getId() { return  this.id;}
    public void setId(int  id){ this.id = id;}
}
