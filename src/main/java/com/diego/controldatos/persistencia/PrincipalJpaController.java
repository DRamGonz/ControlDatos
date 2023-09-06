
package com.diego.controldatos.persistencia;

import com.diego.controldatos.logica.Principal;
import com.diego.controldatos.persistencia.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Diego Ramos.
 */
public class PrincipalJpaController implements Serializable {

    public PrincipalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
     //Es lo único que hay que crear en esta cuenta para que llame al constructor.
    public PrincipalJpaController() {
        emf = Persistence.createEntityManagerFactory("ControlDatosJPAPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Principal principal) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(principal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Principal principal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            principal = em.merge(principal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = principal.getId();
                if (findPrincipal(id) == null) {
                    throw new NonexistentEntityException("The principal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Principal principal;
            try {
                principal = em.getReference(Principal.class, id);
                principal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The principal with id " + id + " no longer exists.", enfe);
            }
            em.remove(principal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Principal> findPrincipalEntities() {
        return findPrincipalEntities(true, -1, -1);
    }

    public List<Principal> findPrincipalEntities(int maxResults, int firstResult) {
        return findPrincipalEntities(false, maxResults, firstResult);
    }

    private List<Principal> findPrincipalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Principal.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Principal findPrincipal(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Principal.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrincipalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Principal> rt = cq.from(Principal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    Principal findDatos(int idDatos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
