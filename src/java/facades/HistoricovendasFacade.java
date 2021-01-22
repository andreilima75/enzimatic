/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Historicovendas;
import entities.Produtos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andrei Lima
 */
@Stateless
public class HistoricovendasFacade extends AbstractFacade<Historicovendas> {

    @PersistenceContext(unitName = "enzimatic10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoricovendasFacade() {
        super(Historicovendas.class);
    }

    public List<Produtos> findByTipoAndLaboratorio(Integer laboratorio, Integer tipo) {

        return getEntityManager().createNamedQuery("Historicovendas.findByTipoAndLaboratorio", Produtos.class)
                .setParameter("idlaboratorio", laboratorio).setParameter("idtipo", tipo).getResultList();

    }

    public void updateProdutos(Integer idProdutos, Integer idTipo, Integer idLaboratorio, Integer estoque) {

        getEntityManager().createNamedQuery("Historicovendas.updateProdutos", Produtos.class)
                .setParameter("idprodutos", idProdutos).setParameter("idtipo", idTipo).setParameter("idlaboratorio", idLaboratorio).setParameter("estoque", estoque).executeUpdate();

    }
}
