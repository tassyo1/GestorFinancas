package Model;

/**
 * Created by tassyosantana on 13/05/16.
 */
public class Categoria {
    private int id;
    private String nome;
    private float valor;
    private String dataAgendada;
    private String tipo;
    private Integer frequencia_id;

    public Categoria(){

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(String dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getFrequencia_id() {
        return frequencia_id;
    }

    public void setFrequencia_id(Integer frequencia_id) {
        this.frequencia_id = frequencia_id;
    }
}
