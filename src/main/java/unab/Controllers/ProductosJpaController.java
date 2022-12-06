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
import unab.Models.Categorias;
import unab.Models.Especies;
import unab.Models.FacturasLin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import unab.Controllers.exceptions.NonexistentEntityException;
import unab.Controllers.exceptions.PreexistingEntityException;
import unab.Models.Productos;

/**
 *
 * @author Luis
 */
public class ProductosJpaController implements Serializable {

    public ProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productos productos) throws PreexistingEntityException, Exception {
        if (productos.getFacturasLinCollection() == null) {
            productos.setFacturasLinCollection(new ArrayList<FacturasLin>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias idCategoria = productos.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                productos.setIdCategoria(idCategoria);
            }
            Especies idEspecie = productos.getIdEspecie();
            if (idEspecie != null) {
                idEspecie = em.getReference(idEspecie.getClass(), idEspecie.getIdEspecie());
                productos.setIdEspecie(idEspecie);
            }
            Collection<FacturasLin> attachedFacturasLinCollection = new ArrayList<FacturasLin>();
            for (FacturasLin facturasLinCollectionFacturasLinToAttach : productos.getFacturasLinCollection()) {
                facturasLinCollectionFacturasLinToAttach = em.getReference(facturasLinCollectionFacturasLinToAttach.getClass(), facturasLinCollectionFacturasLinToAttach.getNumeroSig());
                attachedFacturasLinCollection.add(facturasLinCollectionFacturasLinToAttach);
            }
            productos.setFacturasLinCollection(attachedFacturasLinCollection);
            em.persist(productos);
            if (idCategoria != null) {
                idCategoria.getProductosCollection().add(productos);
                idCategoria = em.merge(idCategoria);
            }
            if (idEspecie != null) {
                idEspecie.getProductosCollection().add(productos);
                idEspecie = em.merge(idEspecie);
            }
            for (FacturasLin facturasLinCollectionFacturasLin : productos.getFacturasLinCollection()) {
                Productos oldIdCodigoOfFacturasLinCollectionFacturasLin = facturasLinCollectionFacturasLin.getIdCodigo();
                facturasLinCollectionFacturasLin.setIdCodigo(productos);
                facturasLinCollectionFacturasLin = em.merge(facturasLinCollectionFacturasLin);
                if (oldIdCodigoOfFacturasLinCollectionFacturasLin != null) {
                    oldIdCodigoOfFacturasLinCollectionFacturasLin.getFacturasLinCollection().remove(facturasLinCollectionFacturasLin);
                    oldIdCodigoOfFacturasLinCollectionFacturasLin = em.merge(oldIdCodigoOfFacturasLinCollectionFacturasLin);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductos(productos.getIdCodigo()) != null) {
                throw new PreexistingEntityException("Productos " + productos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos persistentProductos = em.find(Productos.class, productos.getIdCodigo());
            Categorias idCategoriaOld = persistentProductos.getIdCategoria();
            Categorias idCategoriaNew = productos.getIdCategoria();
            Especies idEspecieOld = persistentProductos.getIdEspecie();
            Especies idEspecieNew = productos.getIdEspecie();
            Collection<FacturasLin> facturasLinCollectionOld = persistentProductos.getFacturasLinCollection();
            Collection<FacturasLin> facturasLinCollectionNew = productos.getFacturasLinCollection();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                productos.setIdCategoria(idCategoriaNew);
            }
            if (idEspecieNew != null) {
                idEspecieNew = em.getReference(idEspecieNew.getClass(), idEspecieNew.getIdEspecie());
                productos.setIdEspecie(idEspecieNew);
            }
            Collection<FacturasLin> attachedFacturasLinCollectionNew = new ArrayList<FacturasLin>();
            for (FacturasLin facturasLinCollectionNewFacturasLinToAttach : facturasLinCollectionNew) {
                facturasLinCollectionNewFacturasLinToAttach = em.getReference(facturasLinCollectionNewFacturasLinToAttach.getClass(), facturasLinCollectionNewFacturasLinToAttach.getNumeroSig());
                attachedFacturasLinCollectionNew.add(facturasLinCollectionNewFacturasLinToAttach);
            }
            facturasLinCollectionNew = attachedFacturasLinCollectionNew;
            productos.setFacturasLinCollection(facturasLinCollectionNew);
            productos = em.merge(productos);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getProductosCollection().remove(productos);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getProductosCollection().add(productos);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idEspecieOld != null && !idEspecieOld.equals(idEspecieNew)) {
                idEspecieOld.getProductosCollection().remove(productos);
                idEspecieOld = em.merge(idEspecieOld);
            }
            if (idEspecieNew != null && !idEspecieNew.equals(idEspecieOld)) {
                idEspecieNew.getProductosCollection().add(productos);
                idEspecieNew = em.merge(idEspecieNew);
            }
            for (FacturasLin facturasLinCollectionOldFacturasLin : facturasLinCollectionOld) {
                if (!facturasLinCollectionNew.contains(facturasLinCollectionOldFacturasLin)) {
                    facturasLinCollectionOldFacturasLin.setIdCodigo(null);
                    facturasLinCollectionOldFacturasLin = em.merge(facturasLinCollectionOldFacturasLin);
                }
            }
            for (FacturasLin facturasLinCollectionNewFacturasLin : facturasLinCollectionNew) {
                if (!facturasLinCollectionOld.contains(facturasLinCollectionNewFacturasLin)) {
                    Productos oldIdCodigoOfFacturasLinCollectionNewFacturasLin = facturasLinCollectionNewFacturasLin.getIdCodigo();
                    facturasLinCollectionNewFacturasLin.setIdCodigo(productos);
                    facturasLinCollectionNewFacturasLin = em.merge(facturasLinCollectionNewFacturasLin);
                    if (oldIdCodigoOfFacturasLinCollectionNewFacturasLin != null && !oldIdCodigoOfFacturasLinCollectionNewFacturasLin.equals(productos)) {
                        oldIdCodigoOfFacturasLinCollectionNewFacturasLin.getFacturasLinCollection().remove(facturasLinCollectionNewFacturasLin);
                        oldIdCodigoOfFacturasLinCollectionNewFacturasLin = em.merge(oldIdCodigoOfFacturasLinCollectionNewFacturasLin);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getIdCodigo();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
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
            Productos productos;
            try {
                productos = em.getReference(Productos.class, id);
                productos.getIdCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
            }
            Categorias idCategoria = productos.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getProductosCollection().remove(productos);
                idCategoria = em.merge(idCategoria);
            }
            Especies idEspecie = productos.getIdEspecie();
            if (idEspecie != null) {
                idEspecie.getProductosCollection().remove(productos);
                idEspecie = em.merge(idEspecie);
            }
            Collection<FacturasLin> facturasLinCollection = productos.getFacturasLinCollection();
            for (FacturasLin facturasLinCollectionFacturasLin : facturasLinCollection) {
                facturasLinCollectionFacturasLin.setIdCodigo(null);
                facturasLinCollectionFacturasLin = em.merge(facturasLinCollectionFacturasLin);
            }
            em.remove(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
