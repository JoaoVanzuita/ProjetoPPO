package model.bean;

import java.time.LocalDate;
import java.util.ArrayList;

public class NFSaida {

    private int idNFSaida;
    private Cliente cliente;
    private Usuario usuario;
    private LocalDate data;
    private Double valorTotal;
    private ArrayList<ItemNFSaida> itensNFSaida;

    public NFSaida() {
    }

    public NFSaida(int idNFSaida, Cliente cliente, Usuario usuario, LocalDate data, Double valorTotal, ArrayList<ItemNFSaida> itensNFSaida) {
        this.idNFSaida = idNFSaida;
        this.cliente = cliente;
        this.usuario = usuario;
        this.data = data;
        this.valorTotal = valorTotal;
        this.itensNFSaida = itensNFSaida;
    }

    public int getIdNFSaida() {
        return idNFSaida;
    }

    public void setIdNFSaida(int idNFSaida) {
        this.idNFSaida = idNFSaida;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public ArrayList<ItemNFSaida> getItensNFSaida() {
        return itensNFSaida;
    }

    public void setItensNFSaida(ArrayList<ItemNFSaida> itensNFSaida) {
        this.itensNFSaida = itensNFSaida;
    }
}
