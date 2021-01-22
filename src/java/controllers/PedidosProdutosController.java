package controllers;

import entities.LaboratorioTipo;
import entities.PedidosProdutos;
import facades.PedidosProdutosFacade;
import entities.Produtos;
import util.JsfUtil;
import util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

@Named("pedidosprodutosController")
@SessionScoped
public class PedidosProdutosController implements Serializable {

    @EJB
    private facades.PedidosProdutosFacade ejbFacade;
    private List<PedidosProdutos> items = null;
    private PedidosProdutos selected;
    private LaboratorioTipo laboratoriotipo;
    private SelectItem[] pedidosProdutos = null;
    private String receita = null;

    public PedidosProdutosController() {
    }

    public PedidosProdutos getSelected() {
        return selected;
    }

    public void setSelected(PedidosProdutos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPedidosprodutosPK().setIdtipo(selected.getProdutos().getProdutosPK().getIdtipo());
        selected.getPedidosprodutosPK().setIdprodutos(selected.getProdutos().getProdutosPK().getIdprodutos());
        selected.getPedidosprodutosPK().setIdlaboratorio(selected.getProdutos().getProdutosPK().getIdlaboratorio());
        selected.getPedidosprodutosPK().setIdpedidos(selected.getPedidos().getIdpedidos());
    }

    protected void initializeEmbeddableKey() {
        selected.setPedidosprodutosPK(new entities.PedidosProdutosPK());
    }

    private PedidosProdutosFacade getFacade() {
        return ejbFacade;
    }

    public PedidosProdutos prepareCreate() {
        selected = new PedidosProdutos();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PedidosprodutosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PedidosprodutosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PedidosprodutosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PedidosProdutos> getItems() {
        if (items == null) {
            //items = getFacade().findAll();
            items = getFacade().findPedidosOpened(Boolean.TRUE);
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            
            selected.setValorreal(selected.getProdutos().getValorreal() * selected.getQuantidade());
            selected.setValorcobrado(selected.getProdutos().getValorcobrado() * selected.getQuantidade());
            selected.setLucro(selected.getValorcobrado() - selected.getValorreal());
            try {
                if (persistAction != PersistAction.DELETE) {
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

    }

    public PedidosProdutos getPedidosprodutos(entities.PedidosProdutosPK id) {
        return getFacade().find(id);
    }

    public List<PedidosProdutos> getItemsAvailableSelectMany() {
        return getFacade().findPedidosOpened(Boolean.TRUE);
    }

    public List<PedidosProdutos> getItemsAvailableSelectOne() {
        return getFacade().findPedidosOpened(Boolean.TRUE);
    }

    @FacesConverter(forClass = PedidosProdutos.class)
    public static class PedidosprodutosControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PedidosProdutosController controller = (PedidosProdutosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pedidosprodutosController");
            return controller.getPedidosprodutos(getKey(value));
        }

        entities.PedidosProdutosPK getKey(String value) {
            entities.PedidosProdutosPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.PedidosProdutosPK();
            key.setIdprodutos(Integer.parseInt(values[0]));
            key.setIdpedidos(Integer.parseInt(values[1]));
            key.setIdlaboratorio(Integer.parseInt(values[2]));
            key.setIdtipo(Integer.parseInt(values[3]));
            return key;
        }

        String getStringKey(entities.PedidosProdutosPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdprodutos());
            sb.append(SEPARATOR);
            sb.append(value.getIdpedidos());
            sb.append(SEPARATOR);
            sb.append(value.getIdlaboratorio());
            sb.append(SEPARATOR);
            sb.append(value.getIdtipo());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PedidosProdutos) {
                PedidosProdutos o = (PedidosProdutos) object;
                return getStringKey(o.getPedidosprodutosPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PedidosProdutos.class.getName()});
                return null;
            }
        }

    }

    public LaboratorioTipo getLaboratoriotipo() {
        return laboratoriotipo;
    }

    public void setLaboratoriotipo(LaboratorioTipo laboratoriotipo) {
        this.laboratoriotipo = laboratoriotipo;
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

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        this.receita = receita;
    }

}
