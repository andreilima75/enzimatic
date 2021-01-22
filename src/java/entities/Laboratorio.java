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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "laboratorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Laboratorio.findAll", query = "SELECT l FROM Laboratorio l")
    , @NamedQuery(name = "Laboratorio.findByNome", query = "SELECT l FROM Laboratorio l WHERE l.nome = :nome")
    , @NamedQuery(name = "Laboratorio.findByIdlaboratorio", query = "SELECT l FROM Laboratorio l WHERE l.idlaboratorio = :idlaboratorio")})
public class Laboratorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome")
    private String nome;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlaboratorio")
    private Integer idlaboratorio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratorio")
    private Collection<LaboratorioTipo> laboratoriotipoCollection;

    public Laboratorio() {
    }

    public Laboratorio(Integer idlaboratorio) {
        this.idlaboratorio = idlaboratorio;
    }

    public Laboratorio(Integer idlaboratorio, String nome) {
        this.idlaboratorio = idlaboratorio;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdlaboratorio() {
        return idlaboratorio;
    }

    public void setIdlaboratorio(Integer idlaboratorio) {
        this.idlaboratorio = idlaboratorio;
    }

    @XmlTransient
    public Collection<LaboratorioTipo> getLaboratoriotipoCollection() {
        return laboratoriotipoCollection;
    }

    public void setLaboratoriotipoCollection(Collection<LaboratorioTipo> laboratoriotipoCollection) {
        this.laboratoriotipoCollection = laboratoriotipoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlaboratorio != null ? idlaboratorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Laboratorio)) {
            return false;
        }
        Laboratorio other = (Laboratorio) object;
        if ((this.idlaboratorio == null && other.idlaboratorio != null) || (this.idlaboratorio != null && !this.idlaboratorio.equals(other.idlaboratorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Laboratorio[ idlaboratorio=" + idlaboratorio + " ]";
    }
    
}
