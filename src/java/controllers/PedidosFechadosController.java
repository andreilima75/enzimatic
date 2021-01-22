package controllers;

import entities.Pedidos;
import facades.PedidosFacade;
import entities.GerarPedidos;
import util.Comparators;
import util.JsfUtil;
import util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

@Named("pedidosFechadosController")
@SessionScoped
public class PedidosFechadosController implements Serializable {

    @EJB
    private facades.PedidosFacade ejbFacade;
    private List<Pedidos> items = null;
    private Pedidos selected;
    private List<Integer> checked = new ArrayList();
    private List<GerarPedidos> ocMescla = null;
    private List<GerarPedidos> ocKit = null;
    private List<GerarPedidos> ocMedicamento = null;
    private List<GerarPedidos> bioKit = null;
    private List<GerarPedidos> bioMedicamento = null;

    public PedidosFechadosController() {
    }

    public void gerarPedido() {

        String listChecked = null;

        for (Integer x : checked) {
            if (listChecked == null) {
                listChecked = x.toString();
            } else {
                listChecked = listChecked + "," + x.toString();
            }
        }

        ocMescla = getFacade().reportQuery(listChecked, "Octalab", "Mescla");
        ocKit = getFacade().reportQuery(listChecked, "Octalab", "Kit");
        ocMedicamento = getFacade().reportQuery(listChecked, "Octalab", "Medicamento");
        bioKit = getFacade().reportQuery(listChecked, "Biometil", "Kit");
        bioMedicamento = getFacade().reportQuery(listChecked, "Biometil", "Medicamento");
        checked = new ArrayList();
    }

    public void selectBooleanView(Integer id) {
        if (checked.contains(id)) {
            checked.remove(id);
        } else {
            checked.add(id);
        }
        System.out.println(checked.toString());
    }

    public Pedidos getSelected() {
        return selected;
    }

    public void setSelected(Pedidos selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PedidosFacade getFacade() {
        return ejbFacade;
    }

    public Pedidos prepareCreate() {
        selected = new Pedidos();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PedidosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PedidosUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PedidosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pedidos> getItems() {
        if (items == null) {
            items = getFacade().findByAberto(Boolean.FALSE);
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

    public Pedidos getPedidos(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Pedidos> getItemsAvailableSelectMany() {
        return getFacade().findByAberto(Boolean.FALSE);
    }

    public SelectItem[] getItemsAvailableSelectOne() {

        List<Pedidos> pedidosList = getFacade().findByAberto(Boolean.FALSE);
        Collections.sort(pedidosList, Comparators.PEDIDOSID);

        return PedidosFechadosController.getSelectItems(pedidosList);
    }

    @FacesConverter(forClass = Pedidos.class)
    public static class PedidosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PedidosFechadosController controller = (PedidosFechadosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pedidosFechadosController");
            return controller.getPedidos(getKey(value));
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
            if (object instanceof Pedidos) {
                Pedidos o = (Pedidos) object;
                return getStringKey(o.getIdpedidos());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pedidos.class.getName()});
                return null;
            }
        }

    }

    public static SelectItem[] getSelectItems(List<Pedidos> entities) {
        int size = entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

        for (Pedidos x : entities) {
            items[i++] = new SelectItem(x, dt1.format(x.getData()) + " - " + x.getIdclientes().getNome());
        }
        return items;
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

}
