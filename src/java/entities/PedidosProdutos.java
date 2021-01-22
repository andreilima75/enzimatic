/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrei
 */
@Entity
@Table(name = "pedidosprodutos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedidosprodutos.findAll", query = "SELECT p FROM PedidosProdutos p")
        , @NamedQuery(name = "Pedidosprodutos.findPedidosOpened", query = "SELECT p FROM PedidosProdutos p WHERE p.pedidos.aberto = :aberto")
    , @NamedQuery(name = "Pedidosprodutos.findByIdprodutos", query = "SELECT p FROM PedidosProdutos p WHERE p.pedidosprodutosPK.idprodutos = :idprodutos")
    , @NamedQuery(name = "Pedidosprodutos.findByIdpedidos", query = "SELECT p FROM PedidosProdutos p WHERE p.pedidosprodutosPK.idpedidos = :idpedidos order by p.produtos.laboratoriotipo.laboratorio.nome")
    , @NamedQuery(name = "Pedidosprodutos.findPedidoByIdpedidos", query = "SELECT p FROM Pedidos p WHERE p.idpedidos = :idpedidos")
    , @NamedQuery(name = "Pedidosprodutos.findByQuantidade", query = "SELECT p FROM PedidosProdutos p WHERE p.quantidade = :quantidade")
    , @NamedQuery(name = "Pedidosprodutos.findByIdlaboratorio", query = "SELECT p FROM PedidosProdutos p WHERE p.pedidosprodutosPK.idlaboratorio = :idlaboratorio")
    , @NamedQuery(name = "Pedidosprodutos.findByTipoAndLaboratorio", query = "SELECT p FROM Produtos p WHERE p.produtosPK.idlaboratorio = :idlaboratorio and p.produtosPK.idtipo = :idtipo")
    , @NamedQuery(name = "Pedidosprodutos.findLaboratorioTipo", query = "SELECT p FROM LaboratorioTipo p WHERE p.laboratorio.idlaboratorio = :idlaboratorio and p.tipo.idtipo = :idtipo")
    , @NamedQuery(name = "Pedidosprodutos.updatePedidos", query = "update Pedidos set valorcobrado = :valorcobrado, valorreal = :valorreal, lucro = :lucro where idpedidos = :idpedidos")
    , @NamedQuery(name = "Pedidosprodutos.findByIdtipo", query = "SELECT p FROM PedidosProdutos p WHERE p.pedidosprodutosPK.idtipo = :idtipo")})
public class PedidosProdutos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public PedidosProdutosPK pedidosprodutosPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade")
    private int quantidade;
    @Column(name = "valorcobrado")
    private Double valorcobrado;
    @Column(name = "valorreal")
    private Double valorreal;
    @Column(name = "lucro")
    private Double lucro;
    @JoinColumn(name = "idpedidos", referencedColumnName = "idpedidos", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedidos pedidos;
    @JoinColumns({
        @JoinColumn(name = "idprodutos", referencedColumnName = "idprodutos", insertable = false, updatable = false)
        , @JoinColumn(name = "idlaboratorio", referencedColumnName = "idlaboratorio", insertable = false, updatable = false)
        , @JoinColumn(name = "idtipo", referencedColumnName = "idtipo", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Produtos produtos;

    public PedidosProdutos() {
    }

    public PedidosProdutos(PedidosProdutosPK pedidosprodutosPK) {
        this.pedidosprodutosPK = pedidosprodutosPK;
    }

    public PedidosProdutos(PedidosProdutosPK pedidosprodutosPK, int quantidade) {
        this.pedidosprodutosPK = pedidosprodutosPK;
        this.quantidade = quantidade;
    }

    public PedidosProdutos(int idprodutos, int idpedidos, int idlaboratorio, int idtipo) {
        this.pedidosprodutosPK = new PedidosProdutosPK(idprodutos, idpedidos, idlaboratorio, idtipo);
    }

    public PedidosProdutosPK getPedidosprodutosPK() {
        return pedidosprodutosPK;
    }

    public void setPedidosprodutosPK(PedidosProdutosPK pedidosprodutosPK) {
        this.pedidosprodutosPK = pedidosprodutosPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Pedidos getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidosprodutosPK != null ? pedidosprodutosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidosProdutos)) {
            return false;
        }
        PedidosProdutos other = (PedidosProdutos) object;
        if ((this.pedidosprodutosPK == null && other.pedidosprodutosPK != null) || (this.pedidosprodutosPK != null && !this.pedidosprodutosPK.equals(other.pedidosprodutosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pedidosprodutos[ pedidosprodutosPK=" + pedidosprodutosPK + " ]";
    }

    public Double getValorcobrado() {
        return valorcobrado;
    }

    public void setValorcobrado(Double valorcobrado) {
        this.valorcobrado = valorcobrado;
    }

    public Double getValorreal() {
        return valorreal;
    }

    public void setValorreal(Double valorreal) {
        this.valorreal = valorreal;
    }

    public Double getLucro() {
        return lucro;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }

}
