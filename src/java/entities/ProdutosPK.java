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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Andrei
 */
@Embeddable
public class ProdutosPK implements Serializable {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodutos")
    private int idprodutos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idlaboratorio")
    private int idlaboratorio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtipo")
    private int idtipo;

    public ProdutosPK() {
    }

    public ProdutosPK(int idprodutos, int idlaboratorio, int idtipo) {
        this.idprodutos = idprodutos;
        this.idlaboratorio = idlaboratorio;
        this.idtipo = idtipo;
    }

    public int getIdprodutos() {
        return idprodutos;
    }

    public void setIdprodutos(int idprodutos) {
        this.idprodutos = idprodutos;
    }

    public int getIdlaboratorio() {
        return idlaboratorio;
    }

    public void setIdlaboratorio(int idlaboratorio) {
        this.idlaboratorio = idlaboratorio;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idprodutos;
        hash += (int) idlaboratorio;
        hash += (int) idtipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdutosPK)) {
            return false;
        }
        ProdutosPK other = (ProdutosPK) object;
        if (this.idprodutos != other.idprodutos) {
            return false;
        }
        if (this.idlaboratorio != other.idlaboratorio) {
            return false;
        }
        if (this.idtipo != other.idtipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ProdutosPK[ idprodutos=" + idprodutos + ", idlaboratorio=" + idlaboratorio + ", idtipo=" + idtipo + " ]";
    }

}
