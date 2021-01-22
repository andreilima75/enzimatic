/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Cursos;
import entities.Cursosclientes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andrei
 */
@Stateless
public class CursosFacade extends AbstractFacade<Cursos> {

    @PersistenceContext(unitName = "enzimatic10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursosFacade() {
        super(Cursos.class);
    }

    public Cursos findByIdcurso(Integer idcurso) {

        return getEntityManager().createNamedQuery("Cursos.findByIdcurso", Cursos.class)
                .setParameter("idcurso", idcurso).getSingleResult();

    }

    public List<Cursosclientes> findCCByIdcurso(Integer idcurso) {

        return getEntityManager().createNamedQuery("Cursosclientes.findByIdcurso", Cursosclientes.class)
                .setParameter("idcurso", idcurso).getResultList();

    }
}
