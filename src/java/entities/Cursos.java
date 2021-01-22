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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrei
 */
@Entity
@Table(name = "cursos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cursos.findAll", query = "SELECT c FROM Cursos c")
    , @NamedQuery(name = "Cursos.findByNome", query = "SELECT c FROM Cursos c WHERE c.nome = :nome")
    , @NamedQuery(name = "Cursos.findByData", query = "SELECT c FROM Cursos c WHERE c.data = :data")
    , @NamedQuery(name = "Cursos.findByObservacao", query = "SELECT c FROM Cursos c WHERE c.observacao = :observacao")
    , @NamedQuery(name = "Cursos.findByVagas", query = "SELECT c FROM Cursos c WHERE c.vagas = :vagas")
    , @NamedQuery(name = "Cursos.findByLocal", query = "SELECT c FROM Cursos c WHERE c.local = :local")
    , @NamedQuery(name = "Cursos.findByValor", query = "SELECT c FROM Cursos c WHERE c.valor = :valor")
    , @NamedQuery(name = "Cursos.findByVagasdisponiveis", query = "SELECT c FROM Cursos c WHERE c.vagasdisponiveis = :vagasdisponiveis")
    , @NamedQuery(name = "Cursos.findByEncerrado", query = "SELECT c FROM Cursos c WHERE c.encerrado = :encerrado")
    , @NamedQuery(name = "Cursos.findByIdcurso", query = "SELECT c FROM Cursos c WHERE c.idcurso = :idcurso")})
public class Cursos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vagas")
    private int vagas;
    @Size(max = 2147483647)
    @Column(name = "local")
    private String local;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private double valor;
    @Column(name = "lucro")
    private double lucro;
    @Column(name = "vagasdisponiveis")
    private Integer vagasdisponiveis;
    @Column(name = "encerrado")
    private Boolean encerrado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcurso")
    private Integer idcurso;

    public Cursos() {
    }

    public Cursos(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public Cursos(Integer idcurso, String nome, Date data, int vagas, double valor) {
        this.idcurso = idcurso;
        this.nome = nome;
        this.data = data;
        this.vagas = vagas;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getVagasdisponiveis() {
        return vagasdisponiveis;
    }

    public void setVagasdisponiveis(Integer vagasdisponiveis) {
        this.vagasdisponiveis = vagasdisponiveis;
    }

    public Boolean getEncerrado() {
        return encerrado;
    }

    public void setEncerrado(Boolean encerrado) {
        this.encerrado = encerrado;
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcurso != null ? idcurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cursos)) {
            return false;
        }
        Cursos other = (Cursos) object;
        if ((this.idcurso == null && other.idcurso != null) || (this.idcurso != null && !this.idcurso.equals(other.idcurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cursos[ idcurso=" + idcurso + " ]";
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

}
