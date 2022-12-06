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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import unab.Controllers.exceptions.NonexistentEntityException;
import unab.Controllers.exceptions.PreexistingEntityException;
import unab.Models.Clientes;

/**
 *
 * @author Luis
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception {
        if (clientes.getFacturasEncCollection() == null) {
            clientes.setFacturasEncCollection(new ArrayList<FacturasEnc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<FacturasEnc> attachedFacturasEncCollection = new ArrayList<FacturasEnc>();
            for (FacturasEnc facturasEncCollectionFacturasEncToAttach : clientes.getFacturasEncCollection()) {
                facturasEncCollectionFacturasEncToAttach = em.getReference(facturasEncCollectionFacturasEncToAttach.getClass(), facturasEncCollectionFacturasEncToAttach.getNumeroSig());
                attachedFacturasEncCollection.add(facturasEncCollectionFacturasEncToAttach);
            }
            clientes.setFacturasEncCollection(attachedFacturasEncCollection);
            em.persist(clientes);
            for (FacturasEnc facturasEncCollectionFacturasEnc : clientes.getFacturasEncCollection()) {
                Clientes oldIdClienteOfFacturasEncCollectionFacturasEnc = facturasEncCollectionFacturasEnc.getIdCliente();
                facturasEncCollectionFacturasEnc.setIdCliente(clientes);
                facturasEncCollectionFacturasEnc = em.merge(facturasEncCollectionFacturasEnc);
                if (oldIdClienteOfFacturasEncCollectionFacturasEnc != null) {
                    oldIdClienteOfFacturasEncCollectionFacturasEnc.getFacturasEncCollection().remove(facturasEncCollectionFacturasEnc);
                    oldIdClienteOfFacturasEncCollectionFacturasEnc = em.merge(oldIdClienteOfFacturasEncCollectionFacturasEnc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getIdCliente()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getIdCliente());
            Collection<FacturasEnc> facturasEncCollectionOld = persistentClientes.getFacturasEncCollection();
            Collection<FacturasEnc> facturasEncCollectionNew = clientes.getFacturasEncCollection();
            Collection<FacturasEnc> attachedFacturasEncCollectionNew = new ArrayList<FacturasEnc>();
            for (FacturasEnc facturasEncCollectionNewFacturasEncToAttach : facturasEncCollectionNew) {
                facturasEncCollectionNewFacturasEncToAttach = em.getReference(facturasEncCollectionNewFacturasEncToAttach.getClass(), facturasEncCollectionNewFacturasEncToAttach.getNumeroSig());
                attachedFacturasEncCollectionNew.add(facturasEncCollectionNewFacturasEncToAttach);
            }
            facturasEncCollectionNew = attachedFacturasEncCollectionNew;
            clientes.setFacturasEncCollection(facturasEncCollectionNew);
            clientes = em.merge(clientes);
            for (FacturasEnc facturasEncCollectionOldFacturasEnc : facturasEncCollectionOld) {
                if (!facturasEncCollectionNew.contains(facturasEncCollectionOldFacturasEnc)) {
                    facturasEncCollectionOldFacturasEnc.setIdCliente(null);
                    facturasEncCollectionOldFacturasEnc = em.merge(facturasEncCollectionOldFacturasEnc);
                }
            }
            for (FacturasEnc facturasEncCollectionNewFacturasEnc : facturasEncCollectionNew) {
                if (!facturasEncCollectionOld.contains(facturasEncCollectionNewFacturasEnc)) {
                    Clientes oldIdClienteOfFacturasEncCollectionNewFacturasEnc = facturasEncCollectionNewFacturasEnc.getIdCliente();
                    facturasEncCollectionNewFacturasEnc.setIdCliente(clientes);
                    facturasEncCollectionNewFacturasEnc = em.merge(facturasEncCollectionNewFacturasEnc);
                    if (oldIdClienteOfFacturasEncCollectionNewFacturasEnc != null && !oldIdClienteOfFacturasEncCollectionNewFacturasEnc.equals(clientes)) {
                        oldIdClienteOfFacturasEncCollectionNewFacturasEnc.getFacturasEncCollection().remove(facturasEncCollectionNewFacturasEnc);
                        oldIdClienteOfFacturasEncCollectionNewFacturasEnc = em.merge(oldIdClienteOfFacturasEncCollectionNewFacturasEnc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getIdCliente();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            Collection<FacturasEnc> facturasEncCollection = clientes.getFacturasEncCollection();
            for (FacturasEnc facturasEncCollectionFacturasEnc : facturasEncCollection) {
                facturasEncCollectionFacturasEnc.setIdCliente(null);
                facturasEncCollectionFacturasEnc = em.merge(facturasEncCollectionFacturasEnc);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
