package controllers;

import entities.LaboratorioTipo;
import facades.LaboratorioTipoFacade;
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

@Named("laboratoriotipoController")
@SessionScoped
public class LaboratorioTipoController implements Serializable {

    @EJB
    private facades.LaboratorioTipoFacade ejbFacade;
    private List<LaboratorioTipo> items = null;
    private LaboratorioTipo selected;

    public LaboratorioTipoController() {
    }

    public LaboratorioTipo getSelected() {
        return selected;
    }

    public void setSelected(LaboratorioTipo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getLaboratoriotipoPK().setIdlaboratorio(selected.getLaboratorio().getIdlaboratorio());
        selected.getLaboratoriotipoPK().setIdtipo(selected.getTipo().getIdtipo());
    }

    protected void initializeEmbeddableKey() {
        selected.setLaboratoriotipoPK(new entities.LaboratorioTipoPK());
    }

    private LaboratorioTipoFacade getFacade() {
        return ejbFacade;
    }

    public LaboratorioTipo prepareCreate() {
        selected = new LaboratorioTipo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LaboratoriotipoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LaboratoriotipoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LaboratoriotipoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LaboratorioTipo> getItems() {
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

    public LaboratorioTipo getLaboratoriotipo(entities.LaboratorioTipoPK id) {
        return getFacade().find(id);
    }

    public List<LaboratorioTipo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public SelectItem[] getItemsAvailableSelectOne() {

        List<LaboratorioTipo> laboratoriotipo;
        laboratoriotipo = getFacade().findAll();

        return LaboratorioTipoController.getSelectItems(laboratoriotipo);
    }

    @FacesConverter(forClass = LaboratorioTipo.class)
    public static class LaboratoriotipoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LaboratorioTipoController controller = (LaboratorioTipoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "laboratoriotipoController");
            return controller.getLaboratoriotipo(getKey(value));
        }

        entities.LaboratorioTipoPK getKey(String value) {
            entities.LaboratorioTipoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.LaboratorioTipoPK();
            key.setIdlaboratorio(Integer.parseInt(values[0]));
            key.setIdtipo(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(entities.LaboratorioTipoPK value) {
            StringBuilder sb = new StringBuilder();
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
            if (object instanceof LaboratorioTipo) {
                LaboratorioTipo o = (LaboratorioTipo) object;
                return getStringKey(o.getLaboratoriotipoPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LaboratorioTipo.class.getName()});
                return null;
            }
        }

    }

    public static SelectItem[] getSelectItems(List<LaboratorioTipo> entities) {
        int size = entities.size() + 1;
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        items[0] = new SelectItem("", "---");
        i++;
        for (LaboratorioTipo x : entities) {
            items[i++] = new SelectItem(x, x.getLaboratorio().getNome() + " - " + x.getTipo().getNome());
        }
        return items;
    }
}
