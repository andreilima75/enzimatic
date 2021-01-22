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
public class PedidosProdutosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idprodutos")
    private int idprodutos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpedidos")
    private int idpedidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idlaboratorio")
    private int idlaboratorio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtipo")
    private int idtipo;

    public PedidosProdutosPK() {
    }

    public PedidosProdutosPK(int idprodutos, int idpedidos, int idlaboratorio, int idtipo) {
        this.idprodutos = idprodutos;
        this.idpedidos = idpedidos;
        this.idlaboratorio = idlaboratorio;
        this.idtipo = idtipo;
    }

    public int getIdprodutos() {
        return idprodutos;
    }

    public void setIdprodutos(int idprodutos) {
        this.idprodutos = idprodutos;
    }

    public int getIdpedidos() {
        return idpedidos;
    }

    public void setIdpedidos(int idpedidos) {
        this.idpedidos = idpedidos;
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
        hash += (int) idpedidos;
        hash += (int) idlaboratorio;
        hash += (int) idtipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidosProdutosPK)) {
            return false;
        }
        PedidosProdutosPK other = (PedidosProdutosPK) object;
        if (this.idprodutos != other.idprodutos) {
            return false;
        }
        if (this.idpedidos != other.idpedidos) {
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
        return "entities.PedidosprodutosPK[ idprodutos=" + idprodutos + ", idpedidos=" + idpedidos + ", idlaboratorio=" + idlaboratorio + ", idtipo=" + idtipo + " ]";
    }
    
}
