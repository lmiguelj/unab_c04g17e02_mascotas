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
import unab.Models.Productos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import unab.Controllers.exceptions.NonexistentEntityException;
import unab.Controllers.exceptions.PreexistingEntityException;
import unab.Models.Especies;

/**
 *
 * @author Luis
 */
public class EspeciesJpaController implements Serializable {

    public EspeciesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especies especies) throws PreexistingEntityException, Exception {
        if (especies.getProductosCollection() == null) {
            especies.setProductosCollection(new ArrayList<Productos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Productos> attachedProductosCollection = new ArrayList<Productos>();
            for (Productos productosCollectionProductosToAttach : especies.getProductosCollection()) {
                productosCollectionProductosToAttach = em.getReference(productosCollectionProductosToAttach.getClass(), productosCollectionProductosToAttach.getIdCodigo());
                attachedProductosCollection.add(productosCollectionProductosToAttach);
            }
            especies.setProductosCollection(attachedProductosCollection);
            em.persist(especies);
            for (Productos productosCollectionProductos : especies.getProductosCollection()) {
                Especies oldIdEspecieOfProductosCollectionProductos = productosCollectionProductos.getIdEspecie();
                productosCollectionProductos.setIdEspecie(especies);
                productosCollectionProductos = em.merge(productosCollectionProductos);
                if (oldIdEspecieOfProductosCollectionProductos != null) {
                    oldIdEspecieOfProductosCollectionProductos.getProductosCollection().remove(productosCollectionProductos);
                    oldIdEspecieOfProductosCollectionProductos = em.merge(oldIdEspecieOfProductosCollectionProductos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEspecies(especies.getIdEspecie()) != null) {
                throw new PreexistingEntityException("Especies " + especies + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especies especies) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especies persistentEspecies = em.find(Especies.class, especies.getIdEspecie());
            Collection<Productos> productosCollectionOld = persistentEspecies.getProductosCollection();
            Collection<Productos> productosCollectionNew = especies.getProductosCollection();
            Collection<Productos> attachedProductosCollectionNew = new ArrayList<Productos>();
            for (Productos productosCollectionNewProductosToAttach : productosCollectionNew) {
                productosCollectionNewProductosToAttach = em.getReference(productosCollectionNewProductosToAttach.getClass(), productosCollectionNewProductosToAttach.getIdCodigo());
                attachedProductosCollectionNew.add(productosCollectionNewProductosToAttach);
            }
            productosCollectionNew = attachedProductosCollectionNew;
            especies.setProductosCollection(productosCollectionNew);
            especies = em.merge(especies);
            for (Productos productosCollectionOldProductos : productosCollectionOld) {
                if (!productosCollectionNew.contains(productosCollectionOldProductos)) {
                    productosCollectionOldProductos.setIdEspecie(null);
                    productosCollectionOldProductos = em.merge(productosCollectionOldProductos);
                }
            }
            for (Productos productosCollectionNewProductos : productosCollectionNew) {
                if (!productosCollectionOld.contains(productosCollectionNewProductos)) {
                    Especies oldIdEspecieOfProductosCollectionNewProductos = productosCollectionNewProductos.getIdEspecie();
                    productosCollectionNewProductos.setIdEspecie(especies);
                    productosCollectionNewProductos = em.merge(productosCollectionNewProductos);
                    if (oldIdEspecieOfProductosCollectionNewProductos != null && !oldIdEspecieOfProductosCollectionNewProductos.equals(especies)) {
                        oldIdEspecieOfProductosCollectionNewProductos.getProductosCollection().remove(productosCollectionNewProductos);
                        oldIdEspecieOfProductosCollectionNewProductos = em.merge(oldIdEspecieOfProductosCollectionNewProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = especies.getIdEspecie();
                if (findEspecies(id) == null) {
                    throw new NonexistentEntityException("The especies with id " + id + " no longer exists.");
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
            Especies especies;
            try {
                especies = em.getReference(Especies.class, id);
                especies.getIdEspecie();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especies with id " + id + " no longer exists.", enfe);
            }
            Collection<Productos> productosCollection = especies.getProductosCollection();
            for (Productos productosCollectionProductos : productosCollection) {
                productosCollectionProductos.setIdEspecie(null);
                productosCollectionProductos = em.merge(productosCollectionProductos);
            }
            em.remove(especies);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especies> findEspeciesEntities() {
        return findEspeciesEntities(true, -1, -1);
    }

    public List<Especies> findEspeciesEntities(int maxResults, int firstResult) {
        return findEspeciesEntities(false, maxResults, firstResult);
    }

    private List<Especies> findEspeciesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especies.class));
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

    public Especies findEspecies(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especies.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspeciesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especies> rt = cq.from(Especies.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
