package controllers;

import entities.Produtos;
import facades.ProdutosFacade;
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

@Named("produtosController")
@SessionScoped
public class ProdutosController implements Serializable {

    @EJB
    private facades.ProdutosFacade ejbFacade;
    private List<Produtos> items = null;
    private Produtos selected;

    public ProdutosController() {
    }

    public Produtos getSelected() {
        return selected;
    }

    public void setSelected(Produtos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getProdutosPK().setIdtipo(selected.getLaboratoriotipo().getLaboratoriotipoPK().getIdtipo());
        selected.getProdutosPK().setIdlaboratorio(selected.getLaboratoriotipo().getLaboratoriotipoPK().getIdlaboratorio());
    }

    protected void initializeEmbeddableKey() {
        selected.setProdutosPK(new entities.ProdutosPK());
    }

    private ProdutosFacade getFacade() {
        return ejbFacade;
    }

    public Produtos prepareCreate() {
        selected = new Produtos();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProdutosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProdutosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProdutosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Produtos> getItems() {
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

    public Produtos getProdutos(entities.ProdutosPK id) {
        return getFacade().find(id);
    }

    public List<Produtos> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Produtos> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Produtos.class)
    public static class ProdutosControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProdutosController controller = (ProdutosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "produtosController");
            return controller.getProdutos(getKey(value));
        }

        entities.ProdutosPK getKey(String value) {
            entities.ProdutosPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.ProdutosPK();
            key.setIdprodutos(Integer.parseInt(values[0]));
            key.setIdlaboratorio(Integer.parseInt(values[1]));
            key.setIdtipo(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(entities.ProdutosPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdprodutos());
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
            if (object instanceof Produtos) {
                Produtos o = (Produtos) object;
                return getStringKey(o.getProdutosPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Produtos.class.getName()});
                return null;
            }
        }

    }

}
