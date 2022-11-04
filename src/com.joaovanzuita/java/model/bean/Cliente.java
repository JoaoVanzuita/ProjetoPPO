package model.bean;

public class Cliente {

    private String nome, email;
    private int idCliente;
    private Endereco endereco;

    public Cliente() {
    }

    public Cliente(String nome, String email, int idCliente, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.idCliente = idCliente;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
