/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Pedidos;
import entities.GerarPedidos;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Andrei
 */
@Stateless
public class TabelaPrecosFacade extends AbstractFacade<Pedidos> {

    @PersistenceContext(unitName = "enzimatic10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TabelaPrecosFacade() {
        super(Pedidos.class);
    }

    public List<GerarPedidos> reportQuery(String labNome, String tipoNome) {
        Query q = getEntityManager().createNativeQuery("select pr.nome as produtonome, receita, opcao, apresentacao, pr.valorcobrado "
                + "from produtos pr, laboratorio l, tipo t "
                + "where pr.idlaboratorio = l.idlaboratorio "
                + "and pr.idtipo = t.idtipo "
                + "and l.nome = '" + labNome + "' "
                + "and t.nome = '" + tipoNome + "' ");

        System.out.println(q.toString());
        
        return gerarPedidosConverter(q.getResultList());
    }

    private List<GerarPedidos> gerarPedidosConverter(List<Object[]> authors) {
        List<GerarPedidos> ocMescla = new ArrayList();
        for (Object[] a : authors) {
            GerarPedidos e = new GerarPedidos();
            e.setProdutonome(a[0].toString());
            e.setReceita(a[1].toString());
            e.setOpcao(a[2].toString());
            e.setApresentacao(a[3].toString());
            e.setValorcobrado(a[4].toString().replace('.', ','));

            ocMescla.add(e);
        }
        return ocMescla;
    }

}
