package controllers;

import entities.Cursos;
import entities.Cursosclientes;
import util.JsfUtil;
import util.JsfUtil.PersistAction;
import facades.CursosFacade;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("cursosDetailController")
@SessionScoped
public class CursosDetailController implements Serializable {
    
    @EJB
    private CursosFacade ejbFacade;
    private List<Cursos> items = null;
    private List<Cursosclientes> cursosClientes = null;
    private Cursos selected;
    private Cursos cursos;
    
    public CursosDetailController() {
    }
    
        public void selecionaCursos(Integer i) throws IOException {
        cursos = getFacade().findByIdcurso(i);
        cursosClientes = getFacade().findCCByIdcurso(i);

        FacesContext.getCurrentInstance().getExternalContext().redirect("cursosDetail/CursosDetail.xhtml");
    }
    
    public Cursos getSelected() {
        return selected;
    }
    
    public void setSelected(Cursos selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    private CursosFacade getFacade() {
        return ejbFacade;
    }
    
    public Cursos prepareCreate() {
        selected = new Cursos();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CursosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CursosUpdated"));
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CursosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public List<Cursos> getItems() {
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
                    selected.setVagasdisponiveis(selected.getVagas());
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
    
    public Cursos getCursos(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    public List<Cursos> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
    
    public List<Cursos> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    @FacesConverter(forClass = Cursos.class)
    public static class CursosControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CursosDetailController controller = (CursosDetailController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cursosController");
            return controller.getCursos(getKey(value));
        }
        
        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }
        
        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }
        
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cursos) {
                Cursos o = (Cursos) object;
                return getStringKey(o.getIdcurso());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cursos.class.getName()});
                return null;
            }
        }
        
    }
    
}
