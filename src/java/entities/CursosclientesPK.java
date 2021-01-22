/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Andrei
 */
@Embeddable
public class CursosclientesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idcurso")
    private int idcurso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idclientes")
    private int idclientes;

    public CursosclientesPK() {
    }

    public CursosclientesPK(int idcurso, int idclientes) {
        this.idcurso = idcurso;
        this.idclientes = idclientes;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    public int getIdclientes() {
        return idclientes;
    }

    public void setIdclientes(int idclientes) {
        this.idclientes = idclientes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcurso;
        hash += (int) idclientes;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CursosclientesPK)) {
            return false;
        }
        CursosclientesPK other = (CursosclientesPK) object;
        if (this.idcurso != other.idcurso) {
            return false;
        }
        if (this.idclientes != other.idclientes) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CursosclientesPK[ idcurso=" + idcurso + ", idclientes=" + idclientes + " ]";
    }
    
}
