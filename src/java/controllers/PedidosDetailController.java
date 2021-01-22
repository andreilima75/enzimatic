package controllers;

import entities.GerarPedidos;
import entities.LaboratorioTipo;
import entities.Pedidos;
import entities.PedidosProdutos;
import entities.Produtos;
import facades.PedidosProdutosFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import util.Correios;
import util.JsfUtil;

@Named("pedidosDetailController")
@SessionScoped
public class PedidosDetailController implements Serializable {

    @EJB
    private facades.PedidosProdutosFacade ejbFacade;
    private Pedidos pedido;
    private List<PedidosProdutos> items;
    private PedidosProdutos selected;
    private LaboratorioTipo laboratoriotipo;
    private String receita = null;
    private String valorFrete = null;
    private String valorTotal = null;
    private SelectItem[] pedidosProdutos = null;
    private List<GerarPedidos> ocMescla = null;
    private List<GerarPedidos> ocKit = null;
    private List<GerarPedidos> ocMedicamento = null;
    private List<GerarPedidos> bioKit = null;
    private List<GerarPedidos> bioMedicamento = null;

    @Inject
    private PedidosController pedidosController;

    public PedidosDetailController() {
    }

    private PedidosProdutosFacade getFacade() {
        return ejbFacade;
    }

    public void selecionaPedidos(Integer i) throws IOException {
        pedido = getFacade().findPedidoByIdPedidos(i);
        items = getFacade().findByIdPedidos(i);

        FacesContext.getCurrentInstance().getExternalContext().redirect("pedidosDetail/PedidoDetail.xhtml");
    }

    public List<PedidosProdutos> getItems() {
        if (items == null) {
            items = getFacade().findByIdPedidos(pedido.getIdpedidos());
        }
        return items;
    }

    public void setItems(List<PedidosProdutos> items) {
        this.items = items;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }

    public PedidosProdutos getSelected() {
        return selected;
    }

    public void setSelected(PedidosProdutos selected) {
        this.selected = selected;
    }

    public LaboratorioTipo getLaboratoriotipo() {
        return laboratoriotipo;
    }

    public void setLaboratoriotipo(LaboratorioTipo laboratoriotipo) {
        this.laboratoriotipo = laboratoriotipo;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

    public PedidosProdutos prepareCreate() {
        selected = new PedidosProdutos();
        initializeEmbeddableKey();
        return selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPedidosprodutosPK().setIdtipo(selected.getProdutos().getProdutosPK().getIdtipo());
        selected.getPedidosprodutosPK().setIdprodutos(selected.getProdutos().getProdutosPK().getIdprodutos());
        selected.getPedidosprodutosPK().setIdlaboratorio(selected.getProdutos().getProdutosPK().getIdlaboratorio());
        selected.getPedidosprodutosPK().setIdpedidos(pedido.getIdpedidos());
    }

    protected void initializeEmbeddableKey() {
        selected.setPedidosprodutosPK(new entities.PedidosProdutosPK());
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();

            selected.setValorreal(selected.getProdutos().getValorreal() * selected.getQuantidade());
            selected.setValorcobrado(selected.getProdutos().getValorcobrado() * selected.getQuantidade());
            selected.setLucro(selected.getValorcobrado() - selected.getValorreal());
            selected.setPedidos(pedido);
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
            calculateValues();
            pedidosController.setItems(null);
        }
    }

    private void calculateValues() {
        List<PedidosProdutos> pedidosProdutosByIdPedidos = getFacade().findByIdPedidos(selected.pedidosprodutosPK.getIdpedidos());
        Double valorCobrado = 0.0;
        Double valorReal = 0.0;

        for (PedidosProdutos x : pedidosProdutosByIdPedidos) {
            valorCobrado = valorCobrado + x.getValorcobrado();
            valorReal = valorReal + x.getValorreal();
        }

        getFacade().updatePedidos(selected.pedidosprodutosPK.getIdpedidos(), valorCobrado, valorReal, valorCobrado - valorReal);
        pedido = getFacade().findPedidoByIdPedidos(selected.pedidosprodutosPK.getIdpedidos());
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PedidosprodutosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PedidosprodutosUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PedidosprodutosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public static SelectItem[] getSelectItems(List<Produtos> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Produtos x : entities) {
            items[i++] = new SelectItem(x, x.getNome() + " - " + x.getOpcao());
        }
        return items;
    }

    public SelectItem[] getPedidosProdutos() {
        return pedidosProdutos;
    }

    public void setPedidosProdutos(SelectItem[] pedidosProdutos) {
        this.pedidosProdutos = pedidosProdutos;
    }

    public void onLaboratorioTipoChange() {

        pedidosProdutos = PedidosProdutosController.getSelectItems(getFacade().findByTipoAndLaboratorio(this.laboratoriotipo.getLaboratorio().getIdlaboratorio(),
                this.laboratoriotipo.getTipo().getIdtipo()), true);
        this.selected.setProdutos(null);
        receita = null;

    }

    public void onLoadEdit() {
        laboratoriotipo = getFacade().findLaboratorioTipo(this.selected.getPedidosprodutosPK().getIdlaboratorio(),
                this.selected.pedidosprodutosPK.getIdtipo());
        pedidosProdutos = PedidosProdutosController.getSelectItems(getFacade().findByTipoAndLaboratorio(this.selected.getPedidosprodutosPK().getIdlaboratorio(),
                this.selected.pedidosprodutosPK.getIdtipo()), true);
        receita = this.selected.getProdutos().getReceita();

    }

    public void onProdutosChange() {

        receita = this.selected.getProdutos().getReceita();
    }

    public void gerarPedido() {

        String listChecked = pedido.getIdpedidos().toString();

        ocMescla = getFacade().reportQuery(listChecked, "Octalab", "Mescla");
        ocKit = getFacade().reportQuery(listChecked, "Octalab", "Kit");
        ocMedicamento = getFacade().reportQuery(listChecked, "Octalab", "Medicamento");
        bioKit = getFacade().reportQuery(listChecked, "Biometil", "Kit");
        bioMedicamento = getFacade().reportQuery(listChecked, "Biometil", "Medicamento");
        valorTotal = "";
        Correios co = new Correios();
        Double frete = 0.0;
        try {
            valorFrete = co.execute("91120350", pedido.getIdclientes().getCep());
            frete = Double.parseDouble(valorFrete.replace(",", "."));
            frete = frete + 6;
            frete = co.round(frete, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //if (frete > 140) {
         //   frete = 140.0;
      //  }
        if (frete < 10) {
            frete = 130.0;
        }
        valorFrete = frete.toString();

        Double vt = frete + pedido.getValorcobrado();
        valorTotal = vt.toString();

    }

    public List<GerarPedidos> getOcMescla() {
        return ocMescla;
    }

    public void setOcMescla(List<GerarPedidos> ocMescla) {
        this.ocMescla = ocMescla;
    }

    public List<GerarPedidos> getOcKit() {
        return ocKit;
    }

    public void setOcKit(List<GerarPedidos> ocKit) {
        this.ocKit = ocKit;
    }

    public List<GerarPedidos> getOcMedicamento() {
        return ocMedicamento;
    }

    public void setOcMedicamento(List<GerarPedidos> ocMedicamento) {
        this.ocMedicamento = ocMedicamento;
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

    public String getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(String valorFrete) {
        this.valorFrete = valorFrete;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

}
