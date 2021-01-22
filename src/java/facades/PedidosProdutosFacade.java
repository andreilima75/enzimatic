/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.GerarPedidos;
import entities.LaboratorioTipo;
import entities.Pedidos;
import entities.PedidosProdutos;
import entities.Produtos;
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
public class PedidosProdutosFacade extends AbstractFacade<PedidosProdutos> {

    @PersistenceContext(unitName = "enzimatic10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidosProdutosFacade() {
        super(PedidosProdutos.class);
    }

    public List<Produtos> findByTipoAndLaboratorio(Integer laboratorio, Integer tipo) {

        return getEntityManager().createNamedQuery("Pedidosprodutos.findByTipoAndLaboratorio", Produtos.class)
                .setParameter("idlaboratorio", laboratorio).setParameter("idtipo", tipo).getResultList();

    }

    public List<PedidosProdutos> findByIdPedidos(Integer idPedidos) {

        return getEntityManager().createNamedQuery("Pedidosprodutos.findByIdpedidos", PedidosProdutos.class)
                .setParameter("idpedidos", idPedidos).getResultList();

    }

    public Pedidos findPedidoByIdPedidos(Integer idPedidos) {

        return getEntityManager().createNamedQuery("Pedidosprodutos.findPedidoByIdpedidos", Pedidos.class)
                .setParameter("idpedidos", idPedidos).getSingleResult();

    }

    public void updatePedidos(Integer idPedidos, double valorcobrado, double valorreal, double lucro) {

        getEntityManager().createNamedQuery("Pedidosprodutos.updatePedidos", Pedidos.class)
                .setParameter("lucro", lucro).setParameter("valorreal", valorreal).setParameter("valorcobrado", valorcobrado).setParameter("idpedidos", idPedidos).executeUpdate();

    }

    public LaboratorioTipo findLaboratorioTipo(Integer laboratorio, Integer tipo) {

        return getEntityManager().createNamedQuery("Pedidosprodutos.findLaboratorioTipo", LaboratorioTipo.class)
                .setParameter("idlaboratorio", laboratorio).setParameter("idtipo", tipo).getSingleResult();

    }

    public List<PedidosProdutos> findPedidosOpened(Boolean aberto) {

        return getEntityManager().createNamedQuery("Pedidosprodutos.findPedidosOpened", PedidosProdutos.class)
                .setParameter("aberto", aberto).getResultList();

    }

    public List<GerarPedidos> reportQuery(String listChecked, String labNome, String tipoNome) {
        Query q = getEntityManager().createNativeQuery("select pr.nome as produtonome, receita, opcao, sum(quantidade) as quantidade, l.nome as laboratorionome, t.nome as tiponome, apresentacao "
                + "from produtos pr, pedidosprodutos pe, laboratorio l, tipo t "
                + "where pr.idprodutos = pe.idprodutos "
                + "and pe.idpedidos in (" + listChecked + ") "
                + "and pr.idlaboratorio = l.idlaboratorio "
                + "and pr.idtipo = t.idtipo "
                + "and l.nome = '" + labNome + "' "
                + "and t.nome = '" + tipoNome + "' "
                + "group by pr.nome, receita, opcao, l.nome, t.nome, apresentacao ");

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
            e.setQuantidade(a[3].toString());
            e.setLaboratorionome(a[4].toString());
            e.setTiponome(a[5].toString());
            if (a[6] != null) {
                e.setApresentacao(a[6].toString());
            } else {
                e.setApresentacao("");
            }
            ocMescla.add(e);
        }
        return ocMescla;
    }
}
