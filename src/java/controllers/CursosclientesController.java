package controllers;

import entities.Cursosclientes;
import util.JsfUtil;
import util.JsfUtil.PersistAction;
import facades.CursosclientesFacade;

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

@Named("cursosclientesController")
@SessionScoped
public class CursosclientesController implements Serializable {

    @EJB
    private CursosclientesFacade ejbFacade;
    private List<Cursosclientes> items = null;
    private Cursosclientes selected;

    public CursosclientesController() {
    }

    public Cursosclientes getSelected() {
        return selected;
    }

    public void setSelected(Cursosclientes selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setCursosclientesPK(new entities.CursosclientesPK());
    }

    private CursosclientesFacade getFacade() {
        return ejbFacade;
    }

    public Cursosclientes prepareCreate() {
        selected = new Cursosclientes();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CursosclientesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CursosclientesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CursosclientesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cursosclientes> getItems() {
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

    public Cursosclientes getCursosclientes(entities.CursosclientesPK id) {
        return getFacade().find(id);
    }

    public List<Cursosclientes> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Cursosclientes> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Cursosclientes.class)
    public static class CursosclientesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CursosclientesController controller = (CursosclientesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cursosclientesController");
            return controller.getCursosclientes(getKey(value));
        }

        entities.CursosclientesPK getKey(String value) {
            entities.CursosclientesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.CursosclientesPK();
            key.setIdcurso(Integer.parseInt(values[0]));
            key.setIdclientes(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(entities.CursosclientesPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdcurso());
            sb.append(SEPARATOR);
            sb.append(value.getIdclientes());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cursosclientes) {
                Cursosclientes o = (Cursosclientes) object;
                return getStringKey(o.getCursosclientesPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cursosclientes.class.getName()});
                return null;
            }
        }

    }

}
