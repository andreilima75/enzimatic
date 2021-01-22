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
public class PedidosFacade extends AbstractFacade<Pedidos> {

    @PersistenceContext(unitName = "enzimatic10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidosFacade() {
        super(Pedidos.class);
    }

    public List<GerarPedidos> reportQuery(String listChecked, String labNome, String tipoNome) {
        Query q = getEntityManager().createNativeQuery("select pr.nome as produtonome, receita, opcao, sum(quantidade) as quantidade, l.nome as laboratorionome, t.nome as tiponome "
                + "from produtos pr, pedidosprodutos pe, laboratorio l, tipo t "
                + "where pr.idprodutos = pe.idprodutos "
                + "and pe.idpedidos in (" + listChecked + ") "
                + "and pr.idlaboratorio = l.idlaboratorio "
                + "and pr.idtipo = t.idtipo "
                + "and l.nome = '" + labNome + "' "
                + "and t.nome = '" + tipoNome + "' "
                + "group by pr.nome, receita, opcao, l.nome, t.nome ");

        return gerarPedidosConverter(q.getResultList());
    }

    public Pedidos reportQuery(String listChecked) {
        Query q = getEntityManager().createNativeQuery("select '2018-04-18' as date, 'true' as aberto, 'true' as pago, "
                + "'1' as idpedidos, '1' as idclientes, '' as observacao,"
                + "sum(valorcobrado) as valorcobrado, sum(valorreal) as valorreal, sum(lucro) as lucro "
                + "from pedidos "
                + "where idpedidos in (" + listChecked + ")");

        return gerarPedidosConverter2(q.getResultList());
    }

    private Pedidos gerarPedidosConverter2(List<Object[]> authors) {

        Pedidos e = new Pedidos();
        for (Object[] a : authors) {
            if (null != a[6]) {
                e.setValorcobrado(Double.parseDouble(a[6].toString()));
                e.setValorreal(Double.parseDouble(a[7].toString()));
                e.setLucro(Double.parseDouble(a[8].toString()));

            }
        }
        return e;
    }

    private List<GerarPedidos> gerarPedidosConverter(List<Object[]> authors) {
        List<GerarPedidos> ocMescla = new ArrayList();
        for (Object[] a : authors) {
            GerarPedidos e = new GerarPedidos();
            e.setProdutonome(a[0].toString());
            e.setReceita(a[1].toString());
            e.setOpcao(a[2].toString());
            e.setQuantidade(a[3].toString());
            e.setLaboratorionome(a[4].toString());
            e.setTiponome(a[5].toString());
            ocMescla.add(e);
        }
        return ocMescla;
    }

    public List<Pedidos> findByAberto(Boolean aberto) {

        return getEntityManager().createNamedQuery("Pedidos.findByAberto", Pedidos.class)
                .setParameter("aberto", aberto).getResultList();
    }

}
