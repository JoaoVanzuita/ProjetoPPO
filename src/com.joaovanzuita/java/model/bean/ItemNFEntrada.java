package model.bean;

public class ItemNFEntrada {

    private int idItemNFEntrada, quantidade;
    private Produto produto;
    private NFEntrada nfEntrada;
    private Double valorUnitario;

    public ItemNFEntrada() {
    }

    public ItemNFEntrada(int idItemNFEntrada, int quantidade, Produto produto, NFEntrada nfEntrada, Double valorUnitario) {
        this.idItemNFEntrada = idItemNFEntrada;
        this.quantidade = quantidade;
        this.produto = produto;
        this.nfEntrada = nfEntrada;
        this.valorUnitario = valorUnitario;
    }

    public int getIdItemNFEntrada() {
        return idItemNFEntrada;
    }

    public void setIdItemNFEntrada(int idItemNFEntrada) {
        this.idItemNFEntrada = idItemNFEntrada;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public NFEntrada getNfEntrada() {
        return nfEntrada;
    }

    public void setNfEntrada(NFEntrada nfEntrada) {
        this.nfEntrada = nfEntrada;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "Produto: " + this.produto.getNome() + "\n" +
                "Categoria: " + this.produto.getCategoria().getNome() + "\n" +
                "Fornecedor: " + this.produto.getFornecedor().getNome() + "\n" +
                "Quantidade: " + this.quantidade + "\n" +
                "Valor unitário: R$" + this.valorUnitario;
    }
}
