// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.org.intersect.sydma.webapp.domain;

import java.lang.String;

privileged aspect ActivityLog_Roo_ToString {
    
    public String ActivityLog.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Activity: ").append(getActivity()).append(", ");
        sb.append("Changes: ").append(getChanges()).append(", ");
        sb.append("Date: ").append(getDate()).append(", ");
        sb.append("DisplayDate: ").append(getDisplayDate()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Principal: ").append(getPrincipal()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
