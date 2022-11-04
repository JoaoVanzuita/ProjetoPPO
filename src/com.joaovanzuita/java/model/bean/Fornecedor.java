package model.bean;

public class Fornecedor {

    private String nome, email;
    private int idFornecedor;
    private Endereco endereco;

    public Fornecedor() {
    }

    public Fornecedor(String nome, String email, int idFornecedor, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.idFornecedor = idFornecedor;
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

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
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
