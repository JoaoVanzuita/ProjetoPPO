package model.bean;

import java.time.LocalDate;
import java.util.ArrayList;

public class NFEntrada {

    private int idNFEntrada;
    private LocalDate data;
    private Double valorTotal;
    private ArrayList<ItemNFEntrada> itensNFEntrada;

    public NFEntrada() {
    }

    public NFEntrada(int idNFEntrada, LocalDate data, Double valorTotal, ArrayList<ItemNFEntrada> itensNFEntrada) {
        this.idNFEntrada = idNFEntrada;
        this.data = data;
        this.valorTotal = valorTotal;
        this.itensNFEntrada = itensNFEntrada;
    }

    public int getIdNFEntrada() {
        return idNFEntrada;
    }

    public void setIdNFEntrada(int idNFEntrada) {
        this.idNFEntrada = idNFEntrada;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public ArrayList<ItemNFEntrada> getItensNFEntrada() {
        return itensNFEntrada;
    }

    public void setItensNFEntrada(ArrayList<ItemNFEntrada> itensNFEntrada) {
        this.itensNFEntrada = itensNFEntrada;
    }
}
