/** Copyright (c) 2011, Intersect, Australia
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Intersect, Intersect's partners, nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package au.org.intersect.sydma.webapp.domain;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * PermissionDto assigned to a user for a research group object
 * 
 * @version $Rev: 29 $
 */
@RooJavaBean
@RooToString(excludeFields = {"user"})
@RooEntity(persistenceUnit = "sydmaPU", finders = {"findPermissionEntrysByPathEqualsAndUser",
        "findPermissionEntrysByPathLikeAndUser", "findPermissionEntrysByUser", "findPermissionEntrysByPathLike",
        "findPermissionEntrysByPathEquals"})
// TODO CHECKSTYLE-OFF: MagicNumber
// TODO CHECKSTYLE-OFF: ParameterAssignmentCheck
// TODO CHECKSTYLE-OFF: MultipleStringLiteralsCheck
public class PermissionEntry
{
    @ManyToOne
    @NotNull
    private User user;

    @Size(max = 20000)
    @NotNull
    @NotEmpty(message = "Location is a required field")
    private String path;

    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    public static TypedQuery<PermissionEntry> findPermissionEntrysByPathLike(String path)
    {
        if (path == null || path.length() == 0)
        {
            throw new IllegalArgumentException("The path argument is required");
        }
        path = path.replace('*', '%');

        if (path.charAt(path.length() - 1) != '%')
        {
            path = path + "%";
        }
        EntityManager em = PermissionEntry.entityManager();
        TypedQuery<PermissionEntry> q = em.createQuery(
                "SELECT o FROM PermissionEntry AS o WHERE LOWER(o.path) LIKE LOWER(:path)", PermissionEntry.class);
        q.setParameter("path", path);
        return q;
    }

    public static TypedQuery<PermissionEntry> findChildPathForUser(String path, User user)
    {
        if (path == null || path.length() == 0)
        {
            throw new IllegalArgumentException("The path argument is required");
        }
        path = path.replace('*', '%');

        if (path.charAt(path.length() - 1) != '%')
        {
            path = path + "_%";
        }
        EntityManager em = PermissionEntry.entityManager();
        TypedQuery<PermissionEntry> q = em.createQuery(
                "SELECT o FROM PermissionEntry AS o WHERE LOWER(o.path) LIKE LOWER(:path) AND o.user = :user",
                PermissionEntry.class);
        q.setParameter("path", path);
        q.setParameter("user", user);
        return q;
    }

    public static TypedQuery<PermissionEntry> findChildPath(String path)
    {
        if (path == null || path.length() == 0)
        {
            throw new IllegalArgumentException("The path argument is required");
        }
        path = path.replace('*', '%');

        if (path.charAt(path.length() - 1) != '%')
        {
            path = path + "_%";
        }
        EntityManager em = PermissionEntry.entityManager();
        TypedQuery<PermissionEntry> q = em.createQuery(
                "SELECT o FROM PermissionEntry AS o WHERE LOWER(o.path) LIKE LOWER(:path)", PermissionEntry.class);
        q.setParameter("path", path);
        return q;
    }

    public static TypedQuery<PermissionEntry> findParentPathForUser(String path, User user)
    {
        if (path == null || path.length() == 0)
        {
            throw new IllegalArgumentException("The path argument is required");
        }
        path = path.replace('*', '%');

        if (path.charAt(path.length() - 1) != '%')
        {
            path = path.substring(0, path.length() - 2) + "%";
        }
        EntityManager em = PermissionEntry.entityManager();
        TypedQuery<PermissionEntry> q = em.createQuery("SELECT o FROM PermissionEntry AS o "
                + "WHERE LOWER(:path) LIKE CONCAT(LOWER(o.path), '_%') AND o.user = :user", PermissionEntry.class);
        q.setParameter("path", path);
        q.setParameter("user", user);
        return q;
    }
}
