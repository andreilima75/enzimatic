/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrei Lima
 */
@Entity
@Table(name = "historicovendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historicovendas.findAll", query = "SELECT h FROM Historicovendas h")
    , @NamedQuery(name = "Historicovendas.findByIdprodutos", query = "SELECT h FROM Historicovendas h WHERE h.historicovendasPK.idprodutos = :idprodutos")
    , @NamedQuery(name = "Historicovendas.findByIdhistoricovendas", query = "SELECT h FROM Historicovendas h WHERE h.historicovendasPK.idhistoricovendas = :idhistoricovendas")
    , @NamedQuery(name = "Historicovendas.findByQuantidade", query = "SELECT h FROM Historicovendas h WHERE h.quantidade = :quantidade")
    , @NamedQuery(name = "Historicovendas.findByIdlaboratorio", query = "SELECT h FROM Historicovendas h WHERE h.historicovendasPK.idlaboratorio = :idlaboratorio")
    , @NamedQuery(name = "Historicovendas.findByIdtipo", query = "SELECT h FROM Historicovendas h WHERE h.historicovendasPK.idtipo = :idtipo")
    , @NamedQuery(name = "Historicovendas.findByValorcobrado", query = "SELECT h FROM Historicovendas h WHERE h.valorcobrado = :valorcobrado")
    , @NamedQuery(name = "Historicovendas.findByValorreal", query = "SELECT h FROM Historicovendas h WHERE h.valorreal = :valorreal")
    , @NamedQuery(name = "Historicovendas.findByLucro", query = "SELECT h FROM Historicovendas h WHERE h.lucro = :lucro")
    , @NamedQuery(name = "Historicovendas.findByTipoAndLaboratorio", query = "SELECT p FROM Produtos p WHERE p.produtosPK.idlaboratorio = :idlaboratorio and p.produtosPK.idtipo = :idtipo")
    , @NamedQuery(name = "Historicovendas.updateProdutos", query = "update Produtos set estoque = :estoque where produtosPK.idprodutos = :idprodutos and produtosPK.idlaboratorio = :idlaboratorio and produtosPK.idtipo = :idtipo")     
    , @NamedQuery(name = "Historicovendas.findByData", query = "SELECT h FROM Historicovendas h WHERE h.data = :data")})
public class Historicovendas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoricovendasPK historicovendasPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantidade")
    private int quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorcobrado")
    private Double valorcobrado;
    @Column(name = "valorreal")
    private Double valorreal;
    @Column(name = "lucro")
    private Double lucro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumns({
        @JoinColumn(name = "idprodutos", referencedColumnName = "idprodutos", insertable = false, updatable = false)
        , @JoinColumn(name = "idlaboratorio", referencedColumnName = "idlaboratorio", insertable = false, updatable = false)
        , @JoinColumn(name = "idtipo", referencedColumnName = "idtipo", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Produtos produtos;

    public Historicovendas() {
    }

    public Historicovendas(HistoricovendasPK historicovendasPK) {
        this.historicovendasPK = historicovendasPK;
    }

    public Historicovendas(HistoricovendasPK historicovendasPK, int quantidade, Date data) {
        this.historicovendasPK = historicovendasPK;
        this.quantidade = quantidade;
        this.data = data;
    }

    public Historicovendas(int idprodutos, int idhistoricovendas, int idlaboratorio, int idtipo) {
        this.historicovendasPK = new HistoricovendasPK(idprodutos, idhistoricovendas, idlaboratorio, idtipo);
    }

    public HistoricovendasPK getHistoricovendasPK() {
        return historicovendasPK;
    }

    public void setHistoricovendasPK(HistoricovendasPK historicovendasPK) {
        this.historicovendasPK = historicovendasPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historicovendasPK != null ? historicovendasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historicovendas)) {
            return false;
        }
        Historicovendas other = (Historicovendas) object;
        if ((this.historicovendasPK == null && other.historicovendasPK != null) || (this.historicovendasPK != null && !this.historicovendasPK.equals(other.historicovendasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Historicovendas[ historicovendasPK=" + historicovendasPK + " ]";
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

}
