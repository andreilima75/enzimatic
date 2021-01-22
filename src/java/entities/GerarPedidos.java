/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author Andrei
 */
public class GerarPedidos implements Serializable{

    private String produtonome;
    private String receita;
    private String opcao;
    private String quantidade;
    private String laboratorionome;
    private String tiponome;
    private String apresentacao;
    private String valorcobrado;

    public String getProdutonome() {
        return produtonome;
    }

    public void setProdutonome(String produtonome) {
        this.produtonome = produtonome;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getLaboratorionome() {
        return laboratorionome;
    }

    public void setLaboratorionome(String laboratorionome) {
        this.laboratorionome = laboratorionome;
    }

    public String getTiponome() {
        return tiponome;
    }

    public void setTiponome(String tiponome) {
        this.tiponome = tiponome;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public String getValorcobrado() {
        return valorcobrado;
    }

    public void setValorcobrado(String valorcobrado) {
        this.valorcobrado = valorcobrado;
    }
    
    

}
