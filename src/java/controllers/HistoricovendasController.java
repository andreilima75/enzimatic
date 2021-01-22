package controllers;

import entities.Historicovendas;
import controllers.util.JsfUtil;
import controllers.util.JsfUtil.PersistAction;
import entities.LaboratorioTipo;
import entities.Produtos;
import facades.HistoricovendasFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

@Named("historicovendasController")
@SessionScoped
public class HistoricovendasController implements Serializable {

    @EJB
    private facades.HistoricovendasFacade ejbFacade;
    private List<Historicovendas> items = null;
    private Historicovendas selected;
    private LaboratorioTipo laboratoriotipo;
    private SelectItem[] vendasProdutos = null;

    public HistoricovendasController() {
    }

    public Historicovendas getSelected() {
        return selected;
    }

    public void setSelected(Historicovendas selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getHistoricovendasPK().setIdtipo(selected.getProdutos().getProdutosPK().getIdtipo());
        selected.getHistoricovendasPK().setIdprodutos(selected.getProdutos().getProdutosPK().getIdprodutos());
        selected.getHistoricovendasPK().setIdlaboratorio(selected.getProdutos().getProdutosPK().getIdlaboratorio());
    }

    protected void initializeEmbeddableKey() {
        selected.setHistoricovendasPK(new entities.HistoricovendasPK());
    }

    private HistoricovendasFacade getFacade() {
        return ejbFacade;
    }

    public Historicovendas prepareCreate() {
        selected = new Historicovendas();
        initializeEmbeddableKey();
        this.selected.setData(new Date());
        return selected;
    }

    public void create() {
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("HistoricovendasCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        this.updateProdutos(this.selected.getProdutos().getEstoque() - this.selected.getQuantidade());
    }

    public void update() {
        
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("HistoricovendasUpdated"));
    }

    public void destroy() {
        this.updateProdutos(this.selected.getProdutos().getEstoque() + this.selected.getQuantidade());
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("HistoricovendasDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Historicovendas> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
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
        }
    }

    public Historicovendas getHistoricovendas(entities.HistoricovendasPK id) {
        return getFacade().find(id);
    }

    public List<Historicovendas> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Historicovendas> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Historicovendas.class)
    public static class HistoricovendasControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HistoricovendasController controller = (HistoricovendasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "historicovendasController");
            return controller.getHistoricovendas(getKey(value));
        }

        entities.HistoricovendasPK getKey(String value) {
            entities.HistoricovendasPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.HistoricovendasPK();
            key.setIdprodutos(Integer.parseInt(values[0]));
            key.setIdhistoricovendas(Integer.parseInt(values[1]));
            key.setIdlaboratorio(Integer.parseInt(values[2]));
            key.setIdtipo(Integer.parseInt(values[3]));
            return key;
        }

        String getStringKey(entities.HistoricovendasPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdprodutos());
            sb.append(SEPARATOR);
            sb.append(value.getIdhistoricovendas());
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
            if (object instanceof Historicovendas) {
                Historicovendas o = (Historicovendas) object;
                return getStringKey(o.getHistoricovendasPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Historicovendas.class.getName()});
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

    public SelectItem[] getVendasProdutos() {
        return vendasProdutos;
    }

    public void setVendasProdutos(SelectItem[] vendasProdutos) {
        this.vendasProdutos = vendasProdutos;
    }

    public void onLaboratorioTipoChange() {

        vendasProdutos = HistoricovendasController.getSelectItems(getFacade().findByTipoAndLaboratorio(this.laboratoriotipo.getLaboratorio().getIdlaboratorio(),
                this.laboratoriotipo.getTipo().getIdtipo()), true);
        this.selected.setProdutos(null);
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

    public void onProdutosChange() {

        this.selected.setValorcobrado(this.selected.getProdutos().getValorcobrado());
        this.selected.setValorreal(this.selected.getProdutos().getValorreal());
        this.onQuantidadeChange();
    }

    public void onQuantidadeChange() {
        if (this.selected.getQuantidade() > this.selected.getProdutos().getEstoque()) {
            this.selected.setQuantidade(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Quantidade não pode ser superior ao estoque!"));
        } else {
            this.selected.setLucro((this.selected.getProdutos().getValorcobrado() - this.selected.getProdutos().getValorreal()) * this.selected.getQuantidade());
        }

    }

    public void updateProdutos(Integer estoque) {
       
        getFacade().updateProdutos(this.selected.getProdutos().getProdutosPK().getIdprodutos(), this.selected.getProdutos().getProdutosPK().getIdtipo(), this.selected.getProdutos().getProdutosPK().getIdlaboratorio(), estoque);

    }

}
