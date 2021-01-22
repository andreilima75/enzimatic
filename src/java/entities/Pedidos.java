/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andrei
 */
@Entity
@Table(name = "pedidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedidos.findAll", query = "SELECT p FROM Pedidos p")
    , @NamedQuery(name = "Pedidos.findByData", query = "SELECT p FROM Pedidos p WHERE p.data = :data")
    , @NamedQuery(name = "Pedidos.findByAberto", query = "SELECT p FROM Pedidos p WHERE p.aberto = :aberto order by p.data desc")
    , @NamedQuery(name = "Pedidos.findByPago", query = "SELECT p FROM Pedidos p WHERE p.pago = :pago")
    , @NamedQuery(name = "Pedidos.findByIdpedidos", query = "SELECT p FROM Pedidos p WHERE p.idpedidos = :idpedidos")
    , @NamedQuery(name = "Pedidos.findByObservacao", query = "SELECT p FROM Pedidos p WHERE p.observacao = :observacao")
    , @NamedQuery(name = "Pedidos.findByValorcobrado", query = "SELECT p FROM Pedidos p WHERE p.valorcobrado = :valorcobrado")
    , @NamedQuery(name = "Pedidos.findByValorreal", query = "SELECT p FROM Pedidos p WHERE p.valorreal = :valorreal")
    , @NamedQuery(name = "Pedidos.findByLucro", query = "SELECT p FROM Pedidos p WHERE p.lucro = :lucro")})
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aberto")
    private boolean aberto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pago")
    private boolean pago;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpedidos")
    private Integer idpedidos;
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorcobrado")
    private Double valorcobrado;
    @Column(name = "valorreal")
    private Double valorreal;
    @Column(name = "lucro")
    private Double lucro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidos")
    private Collection<PedidosProdutos> pedidosprodutosCollection;
    @JoinColumn(name = "idclientes", referencedColumnName = "idclientes")
    @ManyToOne(optional = false)
    private Clientes idclientes;

    public Pedidos() {
    }

    public Pedidos(Integer idpedidos) {
        this.idpedidos = idpedidos;
    }

    public Pedidos(Integer idpedidos, Date data, boolean aberto, boolean pago) {
        this.idpedidos = idpedidos;
        this.data = data;
        this.aberto = aberto;
        this.pago = pago;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean getAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Integer getIdpedidos() {
        return idpedidos;
    }

    public void setIdpedidos(Integer idpedidos) {
        this.idpedidos = idpedidos;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    @XmlTransient
    public Collection<PedidosProdutos> getPedidosprodutosCollection() {
        return pedidosprodutosCollection;
    }

    public void setPedidosprodutosCollection(Collection<PedidosProdutos> pedidosprodutosCollection) {
        this.pedidosprodutosCollection = pedidosprodutosCollection;
    }

    public Clientes getIdclientes() {
        return idclientes;
    }

    public void setIdclientes(Clientes idclientes) {
        this.idclientes = idclientes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpedidos != null ? idpedidos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedidos)) {
            return false;
        }
        Pedidos other = (Pedidos) object;
        if ((this.idpedidos == null && other.idpedidos != null) || (this.idpedidos != null && !this.idpedidos.equals(other.idpedidos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pedidos[ idpedidos=" + idpedidos + " ]";
    }
    
}
