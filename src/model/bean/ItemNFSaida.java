package model.bean;

public class ItemNFSaida {

    private int quantidade, idItemNFsaida;
    private NFSaida nfSaida;
    private Produto produto;
    Double valorUnitario;

    public ItemNFSaida() {
    }

    public ItemNFSaida(int quantidade, int idItemNFsaida, NFSaida nfSaida, Produto produto, Double valorUnitario) {
        this.quantidade = quantidade;
        this.idItemNFsaida = idItemNFsaida;
        this.nfSaida = nfSaida;
        this.produto = produto;
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public NFSaida getNfSaida() {
        return nfSaida;
    }

    public void setNfSaida(NFSaida nfSaida) {
        this.nfSaida = nfSaida;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public int getIdItemNFsaida() {
        return idItemNFsaida;
    }

    public void setIdItemNFsaida(int idItemNFsaida) {
        this.idItemNFsaida = idItemNFsaida;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
