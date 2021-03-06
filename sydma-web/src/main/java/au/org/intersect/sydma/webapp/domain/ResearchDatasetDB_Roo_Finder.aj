// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.ResearchDataset;
import au.org.intersect.sydma.webapp.domain.ResearchDatasetDB;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect ResearchDatasetDB_Roo_Finder {
    
    public static TypedQuery<ResearchDatasetDB> ResearchDatasetDB.findResearchDatasetDBsByResearchDataset(ResearchDataset researchDataset) {
        if (researchDataset == null) throw new IllegalArgumentException("The researchDataset argument is required");
        EntityManager em = ResearchDatasetDB.entityManager();
        TypedQuery<ResearchDatasetDB> q = em.createQuery("SELECT o FROM ResearchDatasetDB AS o WHERE o.researchDataset = :researchDataset", ResearchDatasetDB.class);
        q.setParameter("researchDataset", researchDataset);
        return q;
    }
    
}
