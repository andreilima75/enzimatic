/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andrei
 */
@Entity
@Table(name = "laboratoriotipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Laboratoriotipo.findAll", query = "SELECT l FROM LaboratorioTipo l")
    , @NamedQuery(name = "Laboratoriotipo.findByIdlaboratorio", query = "SELECT l FROM LaboratorioTipo l WHERE l.laboratoriotipoPK.idlaboratorio = :idlaboratorio")
    , @NamedQuery(name = "Laboratoriotipo.findByIdtipo", query = "SELECT l FROM LaboratorioTipo l WHERE l.laboratoriotipoPK.idtipo = :idtipo")
    , @NamedQuery(name = "Laboratoriotipo.findByObservacao", query = "SELECT l FROM LaboratorioTipo l WHERE l.observacao = :observacao")})
public class LaboratorioTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LaboratorioTipoPK laboratoriotipoPK;
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratoriotipo")
    private Collection<Produtos> produtosCollection;
    @JoinColumn(name = "idlaboratorio", referencedColumnName = "idlaboratorio", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Laboratorio laboratorio;
    @JoinColumn(name = "idtipo", referencedColumnName = "idtipo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tipo tipo;

    public LaboratorioTipo() {
    }

    public LaboratorioTipo(LaboratorioTipoPK laboratoriotipoPK) {
        this.laboratoriotipoPK = laboratoriotipoPK;
    }

    public LaboratorioTipo(int idlaboratorio, int idtipo) {
        this.laboratoriotipoPK = new LaboratorioTipoPK(idlaboratorio, idtipo);
    }

    public LaboratorioTipoPK getLaboratoriotipoPK() {
        return laboratoriotipoPK;
    }

    public void setLaboratoriotipoPK(LaboratorioTipoPK laboratoriotipoPK) {
        this.laboratoriotipoPK = laboratoriotipoPK;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @XmlTransient
    public Collection<Produtos> getProdutosCollection() {
        return produtosCollection;
    }

    public void setProdutosCollection(Collection<Produtos> produtosCollection) {
        this.produtosCollection = produtosCollection;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (laboratoriotipoPK != null ? laboratoriotipoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LaboratorioTipo)) {
            return false;
        }
        LaboratorioTipo other = (LaboratorioTipo) object;
        if ((this.laboratoriotipoPK == null && other.laboratoriotipoPK != null) || (this.laboratoriotipoPK != null && !this.laboratoriotipoPK.equals(other.laboratoriotipoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Laboratoriotipo[ laboratoriotipoPK=" + laboratoriotipoPK + " ]";
    }
    
}
