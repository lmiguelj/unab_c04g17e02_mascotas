/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package unab.Controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import unab.Models.FacturasEnc;
import unab.Models.Productos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import unab.Controllers.exceptions.IllegalOrphanException;
import unab.Controllers.exceptions.NonexistentEntityException;
import unab.Controllers.exceptions.PreexistingEntityException;
import unab.Models.FacturasLin;

/**
 *
 * @author Luis
 */
public class FacturasLinJpaController implements Serializable {

    public FacturasLinJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturasLin facturasLin) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturasEnc facturasEnc = facturasLin.getFacturasEnc();
            if (facturasEnc != null) {
                facturasEnc = em.getReference(facturasEnc.getClass(), facturasEnc.getNumeroSig());
                facturasLin.setFacturasEnc(facturasEnc);
            }
            Productos idCodigo = facturasLin.getIdCodigo();
            if (idCodigo != null) {
                idCodigo = em.getReference(idCodigo.getClass(), idCodigo.getIdCodigo());
                facturasLin.setIdCodigo(idCodigo);
            }
            em.persist(facturasLin);
            if (facturasEnc != null) {
                FacturasLin oldFacturasLinOfFacturasEnc = facturasEnc.getFacturasLin();
                if (oldFacturasLinOfFacturasEnc != null) {
                    oldFacturasLinOfFacturasEnc.setFacturasEnc(null);
                    oldFacturasLinOfFacturasEnc = em.merge(oldFacturasLinOfFacturasEnc);
                }
                facturasEnc.setFacturasLin(facturasLin);
                facturasEnc = em.merge(facturasEnc);
            }
            if (idCodigo != null) {
                idCodigo.getFacturasLinCollection().add(facturasLin);
                idCodigo = em.merge(idCodigo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturasLin(facturasLin.getNumeroSig()) != null) {
                throw new PreexistingEntityException("FacturasLin " + facturasLin + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturasLin facturasLin) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturasLin persistentFacturasLin = em.find(FacturasLin.class, facturasLin.getNumeroSig());
            FacturasEnc facturasEncOld = persistentFacturasLin.getFacturasEnc();
            FacturasEnc facturasEncNew = facturasLin.getFacturasEnc();
            Productos idCodigoOld = persistentFacturasLin.getIdCodigo();
            Productos idCodigoNew = facturasLin.getIdCodigo();
            List<String> illegalOrphanMessages = null;
            if (facturasEncOld != null && !facturasEncOld.equals(facturasEncNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain FacturasEnc " + facturasEncOld + " since its facturasLin field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (facturasEncNew != null) {
                facturasEncNew = em.getReference(facturasEncNew.getClass(), facturasEncNew.getNumeroSig());
                facturasLin.setFacturasEnc(facturasEncNew);
            }
            if (idCodigoNew != null) {
                idCodigoNew = em.getReference(idCodigoNew.getClass(), idCodigoNew.getIdCodigo());
                facturasLin.setIdCodigo(idCodigoNew);
            }
            facturasLin = em.merge(facturasLin);
            if (facturasEncNew != null && !facturasEncNew.equals(facturasEncOld)) {
                FacturasLin oldFacturasLinOfFacturasEnc = facturasEncNew.getFacturasLin();
                if (oldFacturasLinOfFacturasEnc != null) {
                    oldFacturasLinOfFacturasEnc.setFacturasEnc(null);
                    oldFacturasLinOfFacturasEnc = em.merge(oldFacturasLinOfFacturasEnc);
                }
                facturasEncNew.setFacturasLin(facturasLin);
                facturasEncNew = em.merge(facturasEncNew);
            }
            if (idCodigoOld != null && !idCodigoOld.equals(idCodigoNew)) {
                idCodigoOld.getFacturasLinCollection().remove(facturasLin);
                idCodigoOld = em.merge(idCodigoOld);
            }
            if (idCodigoNew != null && !idCodigoNew.equals(idCodigoOld)) {
                idCodigoNew.getFacturasLinCollection().add(facturasLin);
                idCodigoNew = em.merge(idCodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturasLin.getNumeroSig();
                if (findFacturasLin(id) == null) {
                    throw new NonexistentEntityException("The facturasLin with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturasLin facturasLin;
            try {
                facturasLin = em.getReference(FacturasLin.class, id);
                facturasLin.getNumeroSig();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturasLin with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            FacturasEnc facturasEncOrphanCheck = facturasLin.getFacturasEnc();
            if (facturasEncOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FacturasLin (" + facturasLin + ") cannot be destroyed since the FacturasEnc " + facturasEncOrphanCheck + " in its facturasEnc field has a non-nullable facturasLin field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Productos idCodigo = facturasLin.getIdCodigo();
            if (idCodigo != null) {
                idCodigo.getFacturasLinCollection().remove(facturasLin);
                idCodigo = em.merge(idCodigo);
            }
            em.remove(facturasLin);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturasLin> findFacturasLinEntities() {
        return findFacturasLinEntities(true, -1, -1);
    }

    public List<FacturasLin> findFacturasLinEntities(int maxResults, int firstResult) {
        return findFacturasLinEntities(false, maxResults, firstResult);
    }

    private List<FacturasLin> findFacturasLinEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturasLin.class));
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

    public FacturasLin findFacturasLin(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturasLin.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturasLinCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturasLin> rt = cq.from(FacturasLin.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
