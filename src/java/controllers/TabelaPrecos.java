package controllers;

import facades.TabelaPrecosFacade;
import entities.GerarPedidos;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("tabelaPrecosController")
@SessionScoped
public class TabelaPrecos implements Serializable {

    @EJB
    private facades.TabelaPrecosFacade ejbFacade;

    private List<GerarPedidos> bioKit = null;
    private List<GerarPedidos> bioMedicamento = null;

    public TabelaPrecos() {
    }

    public void gerarTabelaBiometil() {

        bioKit = getFacade().reportQuery("Biometil", "Kit");
        bioMedicamento = getFacade().reportQuery("Biometil", "Medicamento");

    }

    private TabelaPrecosFacade getFacade() {
        return ejbFacade;
    }

    public List<GerarPedidos> getBioKit() {
        return bioKit;
    }

    public void setBioKit(List<GerarPedidos> bioKit) {
        this.bioKit = bioKit;
    }

    public List<GerarPedidos> getBioMedicamento() {
        return bioMedicamento;
    }

    public void setBioMedicamento(List<GerarPedidos> bioMedicamento) {
        this.bioMedicamento = bioMedicamento;
    }

}
