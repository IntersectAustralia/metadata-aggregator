// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.ResearchDatabaseQuery;
import au.org.intersect.sydma.webapp.domain.ResearchDatasetDB;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect ResearchDatabaseQuery_Roo_Finder {
    
    public static TypedQuery<ResearchDatabaseQuery> ResearchDatabaseQuery.findResearchDatabaseQuerysByNameEquals(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = ResearchDatabaseQuery.entityManager();
        TypedQuery<ResearchDatabaseQuery> q = em.createQuery("SELECT o FROM ResearchDatabaseQuery AS o WHERE o.name = :name", ResearchDatabaseQuery.class);
        q.setParameter("name", name);
        return q;
    }
    
    public static TypedQuery<ResearchDatabaseQuery> ResearchDatabaseQuery.findResearchDatabaseQuerysByResearchDatasetDB(ResearchDatasetDB researchDatasetDB) {
        if (researchDatasetDB == null) throw new IllegalArgumentException("The researchDatasetDB argument is required");
        EntityManager em = ResearchDatabaseQuery.entityManager();
        TypedQuery<ResearchDatabaseQuery> q = em.createQuery("SELECT o FROM ResearchDatabaseQuery AS o WHERE o.researchDatasetDB = :researchDatasetDB", ResearchDatabaseQuery.class);
        q.setParameter("researchDatasetDB", researchDatasetDB);
        return q;
    }
    
}
