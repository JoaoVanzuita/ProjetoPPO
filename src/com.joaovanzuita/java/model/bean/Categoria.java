package model.bean;

public class Categoria {

    private String nome;
    private int idCategoria;

    public Categoria() {
    }

    public Categoria(String nome, int idCategoria) {
        this.nome = nome;
        this.idCategoria = idCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
