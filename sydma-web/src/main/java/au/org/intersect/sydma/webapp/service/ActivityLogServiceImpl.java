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

package au.org.intersect.sydma.webapp.service;

import java.security.Principal;
import java.util.Date;

import org.springframework.stereotype.Service;

import au.org.intersect.sydma.webapp.domain.AccessLevel;
import au.org.intersect.sydma.webapp.domain.Activity;
import au.org.intersect.sydma.webapp.domain.ActivityLog;
import au.org.intersect.sydma.webapp.domain.ResearchGroup;
import au.org.intersect.sydma.webapp.domain.User;
import au.org.intersect.sydma.webapp.permission.path.Path;

/**
 * Service for logging activities
 *
 * @version $Rev: 29 $
 */
@Service
public class ActivityLogServiceImpl implements ActivityLogService
{
    @Override
    public void log(Activity activity, AccessLevel accessLevel, Path path, User user, Principal principal)
    {
        ActivityLog log = new ActivityLog();

        Date date = new Date();
        log.setDate(date);
        log.setActivity(activity);

        // Added/Deleted <accessLevel> for <user> over <path>.
        String changes = activity.getMessage() + " " + accessLevel.getName() + " for " + user.getUsername() + " over "
                + path.getDisplayName();
        log.setChanges(changes);

        ResearchGroup group = ResearchGroup.findResearchGroup(path.getGroupId());
        log.setResearchGroup(group);
        User principalUser = User.findUsersByUsernameEquals(principal.getName()).getSingleResult();
        log.setPrincipal(principalUser);
        log.persist();
    }
}
