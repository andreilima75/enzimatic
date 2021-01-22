/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrei
 */
@Entity
@Table(name = "cursosclientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cursosclientes.findAll", query = "SELECT c FROM Cursosclientes c")
    , @NamedQuery(name = "Cursosclientes.findByIdcurso", query = "SELECT c FROM Cursosclientes c WHERE c.cursosclientesPK.idcurso = :idcurso")
    , @NamedQuery(name = "Cursosclientes.findByIdclientes", query = "SELECT c FROM Cursosclientes c WHERE c.cursosclientesPK.idclientes = :idclientes")
    , @NamedQuery(name = "Cursosclientes.findByPago", query = "SELECT c FROM Cursosclientes c WHERE c.pago = :pago")
    , @NamedQuery(name = "Cursosclientes.findByObservacao", query = "SELECT c FROM Cursosclientes c WHERE c.observacao = :observacao")})
public class Cursosclientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CursosclientesPK cursosclientesPK;
    @Column(name = "pago")
    private Boolean pago;
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;

    public Cursosclientes() {
    }

    public Cursosclientes(CursosclientesPK cursosclientesPK) {
        this.cursosclientesPK = cursosclientesPK;
    }

    public Cursosclientes(int idcurso, int idclientes) {
        this.cursosclientesPK = new CursosclientesPK(idcurso, idclientes);
    }

    public CursosclientesPK getCursosclientesPK() {
        return cursosclientesPK;
    }

    public void setCursosclientesPK(CursosclientesPK cursosclientesPK) {
        this.cursosclientesPK = cursosclientesPK;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cursosclientesPK != null ? cursosclientesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cursosclientes)) {
            return false;
        }
        Cursosclientes other = (Cursosclientes) object;
        if ((this.cursosclientesPK == null && other.cursosclientesPK != null) || (this.cursosclientesPK != null && !this.cursosclientesPK.equals(other.cursosclientesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cursosclientes[ cursosclientesPK=" + cursosclientesPK + " ]";
    }
    
}
