// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.MasterVocabulary;
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

privileged aspect MasterVocabulary_Roo_Entity {
    
    declare @type: MasterVocabulary: @Entity;
    
    @PersistenceContext(unitName = "sydmaPU")
    transient EntityManager MasterVocabulary.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long MasterVocabulary.id;
    
    @Version
    @Column(name = "version")
    private Integer MasterVocabulary.version;
    
    public Long MasterVocabulary.getId() {
        return this.id;
    }
    
    public void MasterVocabulary.setId(Long id) {
        this.id = id;
    }
    
    public Integer MasterVocabulary.getVersion() {
        return this.version;
    }
    
    public void MasterVocabulary.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional("sydmaPU")
    public void MasterVocabulary.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional("sydmaPU")
    public void MasterVocabulary.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            MasterVocabulary attached = MasterVocabulary.findMasterVocabulary(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional("sydmaPU")
    public void MasterVocabulary.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional("sydmaPU")
    public void MasterVocabulary.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional("sydmaPU")
    public MasterVocabulary MasterVocabulary.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        MasterVocabulary merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager MasterVocabulary.entityManager() {
        EntityManager em = new MasterVocabulary().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long MasterVocabulary.countMasterVocabularys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM MasterVocabulary o", Long.class).getSingleResult();
    }
    
    public static List<MasterVocabulary> MasterVocabulary.findAllMasterVocabularys() {
        return entityManager().createQuery("SELECT o FROM MasterVocabulary o", MasterVocabulary.class).getResultList();
    }
    
    public static MasterVocabulary MasterVocabulary.findMasterVocabulary(Long id) {
        if (id == null) return null;
        return entityManager().find(MasterVocabulary.class, id);
    }
    
    public static List<MasterVocabulary> MasterVocabulary.findMasterVocabularyEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM MasterVocabulary o", MasterVocabulary.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
