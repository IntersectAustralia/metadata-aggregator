// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import au.org.intersect.sydma.webapp.domain.RdsRequest;
import au.org.intersect.sydma.webapp.domain.RdsRequestStatus;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect RdsRequest_Roo_Finder {
    
    public static TypedQuery<RdsRequest> RdsRequest.findRdsRequestsByNameEquals(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = RdsRequest.entityManager();
        TypedQuery<RdsRequest> q = em.createQuery("SELECT o FROM RdsRequest AS o WHERE o.name = :name", RdsRequest.class);
        q.setParameter("name", name);
        return q;
    }
    
    public static TypedQuery<RdsRequest> RdsRequest.findRdsRequestsByNameEqualsAndRequestStatus(String name, RdsRequestStatus requestStatus) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        if (requestStatus == null) throw new IllegalArgumentException("The requestStatus argument is required");
        EntityManager em = RdsRequest.entityManager();
        TypedQuery<RdsRequest> q = em.createQuery("SELECT o FROM RdsRequest AS o WHERE o.name = :name  AND o.requestStatus = :requestStatus", RdsRequest.class);
        q.setParameter("name", name);
        q.setParameter("requestStatus", requestStatus);
        return q;
    }
    
    public static TypedQuery<RdsRequest> RdsRequest.findRdsRequestsByRequestStatus(RdsRequestStatus requestStatus) {
        if (requestStatus == null) throw new IllegalArgumentException("The requestStatus argument is required");
        EntityManager em = RdsRequest.entityManager();
        TypedQuery<RdsRequest> q = em.createQuery("SELECT o FROM RdsRequest AS o WHERE o.requestStatus = :requestStatus", RdsRequest.class);
        q.setParameter("requestStatus", requestStatus);
        return q;
    }
    
    public static TypedQuery<RdsRequest> RdsRequest.findRdsRequestByResearchGroupEquals(ResearchGroup researchGroup) {
        if (researchGroup == null) throw new IllegalArgumentException("The researchGroup argument is required");
        EntityManager em = RdsRequest.entityManager();
        TypedQuery<RdsRequest> q = em.createQuery("SELECT o FROM RdsRequest AS o WHERE o.researchGroup = :researchGroup", RdsRequest.class);
        q.setParameter("researchGroup", researchGroup);
        return q;
    }
    
}
