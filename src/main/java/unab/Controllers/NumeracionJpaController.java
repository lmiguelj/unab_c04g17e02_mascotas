/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unab.Controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import unab.Controllers.exceptions.NonexistentEntityException;
import unab.Controllers.exceptions.PreexistingEntityException;
import unab.Models.Numeracion;

/**
 *
 * @author Luis
 */
public class NumeracionJpaController implements Serializable {

    public NumeracionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Numeracion numeracion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(numeracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNumeracion(numeracion.getNumeroSig()) != null) {
                throw new PreexistingEntityException("Numeracion " + numeracion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Numeracion numeracion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            numeracion = em.merge(numeracion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = numeracion.getNumeroSig();
                if (findNumeracion(id) == null) {
                    throw new NonexistentEntityException("The numeracion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Numeracion numeracion;
            try {
                numeracion = em.getReference(Numeracion.class, id);
                numeracion.getNumeroSig();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The numeracion with id " + id + " no longer exists.", enfe);
            }
            em.remove(numeracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Numeracion> findNumeracionEntities() {
        return findNumeracionEntities(true, -1, -1);
    }

    public List<Numeracion> findNumeracionEntities(int maxResults, int firstResult) {
        return findNumeracionEntities(false, maxResults, firstResult);
    }

    private List<Numeracion> findNumeracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Numeracion.class));
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

    public Numeracion findNumeracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Numeracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getNumeracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Numeracion> rt = cq.from(Numeracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
