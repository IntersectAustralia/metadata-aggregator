// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.RdsRequest;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect RdsRequest_Roo_Entity {
    
    declare @type: RdsRequest: @Entity;
    
    @PersistenceContext(unitName = "sydmaPU")
    transient EntityManager RdsRequest.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long RdsRequest.id;
    
    @Version
    @Column(name = "version")
    private Integer RdsRequest.version;
    
    public Long RdsRequest.getId() {
        return this.id;
    }
    
    public void RdsRequest.setId(Long id) {
        this.id = id;
    }
    
    public Integer RdsRequest.getVersion() {
        return this.version;
    }
    
    public void RdsRequest.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional("sydmaPU")
    public void RdsRequest.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("sydmaPU")
    public void RdsRequest.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            RdsRequest attached = RdsRequest.findRdsRequest(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("sydmaPU")
    public void RdsRequest.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("sydmaPU")
    public void RdsRequest.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("sydmaPU")
    public RdsRequest RdsRequest.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RdsRequest merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager RdsRequest.entityManager() {
        EntityManager em = new RdsRequest().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long RdsRequest.countRdsRequests() {
        return entityManager().createQuery("SELECT COUNT(o) FROM RdsRequest o", Long.class).getSingleResult();
    }
    
    public static List<RdsRequest> RdsRequest.findAllRdsRequests() {
        return entityManager().createQuery("SELECT o FROM RdsRequest o", RdsRequest.class).getResultList();
    }
    
    public static RdsRequest RdsRequest.findRdsRequest(Long id) {
        if (id == null) return null;
        return entityManager().find(RdsRequest.class, id);
    }
    
    public static List<RdsRequest> RdsRequest.findRdsRequestEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM RdsRequest o", RdsRequest.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
