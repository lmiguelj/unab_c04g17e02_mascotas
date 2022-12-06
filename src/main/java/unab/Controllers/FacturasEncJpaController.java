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
import unab.Models.Clientes;
import unab.Models.FacturasLin;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import unab.Controllers.exceptions.IllegalOrphanException;
import unab.Controllers.exceptions.NonexistentEntityException;
import unab.Controllers.exceptions.PreexistingEntityException;
import unab.Models.FacturasEnc;

/**
 *
 * @author Luis
 */
public class FacturasEncJpaController implements Serializable {

    public FacturasEncJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturasEnc facturasEnc) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        FacturasLin facturasLinOrphanCheck = facturasEnc.getFacturasLin();
        if (facturasLinOrphanCheck != null) {
            FacturasEnc oldFacturasEncOfFacturasLin = facturasLinOrphanCheck.getFacturasEnc();
            if (oldFacturasEncOfFacturasLin != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The FacturasLin " + facturasLinOrphanCheck + " already has an item of type FacturasEnc whose facturasLin column cannot be null. Please make another selection for the facturasLin field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idCliente = facturasEnc.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                facturasEnc.setIdCliente(idCliente);
            }
            FacturasLin facturasLin = facturasEnc.getFacturasLin();
            if (facturasLin != null) {
                facturasLin = em.getReference(facturasLin.getClass(), facturasLin.getNumeroSig());
                facturasEnc.setFacturasLin(facturasLin);
            }
            em.persist(facturasEnc);
            if (idCliente != null) {
                idCliente.getFacturasEncCollection().add(facturasEnc);
                idCliente = em.merge(idCliente);
            }
            if (facturasLin != null) {
                facturasLin.setFacturasEnc(facturasEnc);
                facturasLin = em.merge(facturasLin);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacturasEnc(facturasEnc.getNumeroSig()) != null) {
                throw new PreexistingEntityException("FacturasEnc " + facturasEnc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturasEnc facturasEnc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturasEnc persistentFacturasEnc = em.find(FacturasEnc.class, facturasEnc.getNumeroSig());
            Clientes idClienteOld = persistentFacturasEnc.getIdCliente();
            Clientes idClienteNew = facturasEnc.getIdCliente();
            FacturasLin facturasLinOld = persistentFacturasEnc.getFacturasLin();
            FacturasLin facturasLinNew = facturasEnc.getFacturasLin();
            List<String> illegalOrphanMessages = null;
            if (facturasLinNew != null && !facturasLinNew.equals(facturasLinOld)) {
                FacturasEnc oldFacturasEncOfFacturasLin = facturasLinNew.getFacturasEnc();
                if (oldFacturasEncOfFacturasLin != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The FacturasLin " + facturasLinNew + " already has an item of type FacturasEnc whose facturasLin column cannot be null. Please make another selection for the facturasLin field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                facturasEnc.setIdCliente(idClienteNew);
            }
            if (facturasLinNew != null) {
                facturasLinNew = em.getReference(facturasLinNew.getClass(), facturasLinNew.getNumeroSig());
                facturasEnc.setFacturasLin(facturasLinNew);
            }
            facturasEnc = em.merge(facturasEnc);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getFacturasEncCollection().remove(facturasEnc);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getFacturasEncCollection().add(facturasEnc);
                idClienteNew = em.merge(idClienteNew);
            }
            if (facturasLinOld != null && !facturasLinOld.equals(facturasLinNew)) {
                facturasLinOld.setFacturasEnc(null);
                facturasLinOld = em.merge(facturasLinOld);
            }
            if (facturasLinNew != null && !facturasLinNew.equals(facturasLinOld)) {
                facturasLinNew.setFacturasEnc(facturasEnc);
                facturasLinNew = em.merge(facturasLinNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturasEnc.getNumeroSig();
                if (findFacturasEnc(id) == null) {
                    throw new NonexistentEntityException("The facturasEnc with id " + id + " no longer exists.");
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
            FacturasEnc facturasEnc;
            try {
                facturasEnc = em.getReference(FacturasEnc.class, id);
                facturasEnc.getNumeroSig();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturasEnc with id " + id + " no longer exists.", enfe);
            }
            Clientes idCliente = facturasEnc.getIdCliente();
            if (idCliente != null) {
                idCliente.getFacturasEncCollection().remove(facturasEnc);
                idCliente = em.merge(idCliente);
            }
            FacturasLin facturasLin = facturasEnc.getFacturasLin();
            if (facturasLin != null) {
                facturasLin.setFacturasEnc(null);
                facturasLin = em.merge(facturasLin);
            }
            em.remove(facturasEnc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturasEnc> findFacturasEncEntities() {
        return findFacturasEncEntities(true, -1, -1);
    }

    public List<FacturasEnc> findFacturasEncEntities(int maxResults, int firstResult) {
        return findFacturasEncEntities(false, maxResults, firstResult);
    }

    private List<FacturasEnc> findFacturasEncEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturasEnc.class));
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

    public FacturasEnc findFacturasEnc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturasEnc.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturasEncCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturasEnc> rt = cq.from(FacturasEnc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
