/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andrei
 */
@Entity
@Table(name = "produtos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p")
    , @NamedQuery(name = "Produtos.findByIdprodutos", query = "SELECT p FROM Produtos p WHERE p.produtosPK.idprodutos = :idprodutos")
    , @NamedQuery(name = "Produtos.findByNome", query = "SELECT p FROM Produtos p WHERE p.nome = :nome")
    , @NamedQuery(name = "Produtos.findByReceita", query = "SELECT p FROM Produtos p WHERE p.receita = :receita")
    , @NamedQuery(name = "Produtos.findByValorcobrado", query = "SELECT p FROM Produtos p WHERE p.valorcobrado = :valorcobrado")
    , @NamedQuery(name = "Produtos.findByValorreal", query = "SELECT p FROM Produtos p WHERE p.valorreal = :valorreal")
    , @NamedQuery(name = "Produtos.findByIdlaboratorio", query = "SELECT p FROM Produtos p WHERE p.produtosPK.idlaboratorio = :idlaboratorio")
    , @NamedQuery(name = "Produtos.findByIdtipo", query = "SELECT p FROM Produtos p WHERE p.produtosPK.idtipo = :idtipo")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProdutosPK produtosPK;
    @Column(name = "opcao")
    private String opcao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "receita")
    private String receita;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorcobrado")
    private double valorcobrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorreal")
    private double valorreal;
    @JoinColumns({
        @JoinColumn(name = "idlaboratorio", referencedColumnName = "idlaboratorio", insertable = false, updatable = false)
        , @JoinColumn(name = "idtipo", referencedColumnName = "idtipo", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private LaboratorioTipo laboratoriotipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produtos")
    private Collection<PedidosProdutos> pedidosprodutosCollection;
    @Column(name = "estoque")
    private int estoque;
    
    public Produtos() {
    }

    public Produtos(ProdutosPK produtosPK) {
        this.produtosPK = produtosPK;
    }

    public Produtos(ProdutosPK produtosPK, String nome, String receita, double valorcobrado, double valorreal) {
        this.produtosPK = produtosPK;
        this.nome = nome;
        this.receita = receita;
        this.valorcobrado = valorcobrado;
        this.valorreal = valorreal;
    }

    public Produtos(int idprodutos, int idlaboratorio, int idtipo) {
        this.produtosPK = new ProdutosPK(idprodutos, idlaboratorio, idtipo);
    }

    public ProdutosPK getProdutosPK() {
        return produtosPK;
    }

    public void setProdutosPK(ProdutosPK produtosPK) {
        this.produtosPK = produtosPK;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

    public double getValorcobrado() {
        return valorcobrado;
    }

    public void setValorcobrado(double valorcobrado) {
        this.valorcobrado = valorcobrado;
    }

    public double getValorreal() {
        return valorreal;
    }

    public void setValorreal(double valorreal) {
        this.valorreal = valorreal;
    }

    public LaboratorioTipo getLaboratoriotipo() {
        return laboratoriotipo;
    }

    public void setLaboratoriotipo(LaboratorioTipo laboratoriotipo) {
        this.laboratoriotipo = laboratoriotipo;
    }

    @XmlTransient
    public Collection<PedidosProdutos> getPedidosprodutosCollection() {
        return pedidosprodutosCollection;
    }

    public void setPedidosprodutosCollection(Collection<PedidosProdutos> pedidosprodutosCollection) {
        this.pedidosprodutosCollection = pedidosprodutosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (produtosPK != null ? produtosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if ((this.produtosPK == null && other.produtosPK != null) || (this.produtosPK != null && !this.produtosPK.equals(other.produtosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Produtos[ produtosPK=" + produtosPK + " ]";
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

}
