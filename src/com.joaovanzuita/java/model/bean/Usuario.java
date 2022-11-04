package model.bean;

public class Usuario {

    String nome, email, senha;
    int idUsuario, tipoDeUsuario;
    Endereco endereco;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, int id, int tipoDeUsuario, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idUsuario = id;
        this.tipoDeUsuario = tipoDeUsuario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(int tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
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