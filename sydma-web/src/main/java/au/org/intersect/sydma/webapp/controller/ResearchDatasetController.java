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

package au.org.intersect.sydma.webapp.controller;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import au.org.intersect.sydma.webapp.controller.propertyeditor.DateTimePropertyEditor;
import au.org.intersect.sydma.webapp.controller.propertyeditor.ResearchSubjectCodePropertyEditor;
import au.org.intersect.sydma.webapp.domain.Building;
import au.org.intersect.sydma.webapp.domain.PublicAccessRight;
import au.org.intersect.sydma.webapp.domain.Publication;
import au.org.intersect.sydma.webapp.domain.ResearchDataset;
import au.org.intersect.sydma.webapp.domain.ResearchGroup;
import au.org.intersect.sydma.webapp.domain.ResearchProject;
import au.org.intersect.sydma.webapp.domain.ResearchSubjectCode;
import au.org.intersect.sydma.webapp.domain.User;
import au.org.intersect.sydma.webapp.exception.NoneUniqueNameException;
import au.org.intersect.sydma.webapp.permission.PermissionType;
import au.org.intersect.sydma.webapp.security.SecurityContextFacade;
import au.org.intersect.sydma.webapp.service.DatasetReadyToPublishMailService;
import au.org.intersect.sydma.webapp.service.PermissionService;
import au.org.intersect.sydma.webapp.service.ResearchDatasetService;
import au.org.intersect.sydma.webapp.util.Breadcrumb;
import au.org.intersect.sydma.webapp.util.RifCsWriter;
import au.org.intersect.sydma.webapp.util.TokenInputHelper;
import au.org.intersect.sydma.webapp.util.UrlHelper;
import au.org.intersect.sydma.webapp.util.UrlHelperImpl;
import au.org.intersect.sydma.webapp.validator.TemporalDataValidator;

/**
 * Research Dataset Controller.
 */
// TODO CHECKSTYLE-OFF: ClassFanOutComplexityCheck
@RequestMapping("/researchdataset/**")
@Controller
public class ResearchDatasetController
{
    private static final Logger LOG = LoggerFactory.getLogger(ResearchDatasetController.class);

    private static final String BUILDINGS_MODEL_ATTRIBUTE = "buildings";
    private static final String PUBLIC_ACCESS_RIGHT_OPTIONS_ATTRIBUTE = "publicAccessRightOptions";
    private static final String REDIRECT_TO_HOME = "redirect:/";
    private static final String REDIRECT_TO_NEW = "researchdataset/new";
    private static final String REDIRECT_TO_VIEW = "researchdataset/view";
    private static final String REDIRECT_TO_EDIT = "researchdataset/edit";
    private static final String CONFIRM_UNADVERTISE = "confirmUnadvertise";
    private static final String RESEARCH_GROUP_REQUEST_MODEL = "researchGroup";
    private static final String RESEARCH_PROJECT_REQUEST_MODEL = "researchProject";
    private static final String RESEARCH_DATASET_REQUEST_MODEL = "researchDataset";
    private static final String BREADCRUMBS = "breadcrumbs";

    private static List<Breadcrumb> breadcrumbsForCreating = new ArrayList<Breadcrumb>();
    private static List<Breadcrumb> breadcrumbsForEditing = new ArrayList<Breadcrumb>();
    private static List<Breadcrumb> breadcrumbsForPublicizing = new ArrayList<Breadcrumb>();
    private static List<Breadcrumb> breadcrumbsForViewing = new ArrayList<Breadcrumb>();

    static
    {
        breadcrumbsForCreating.add(Breadcrumb.getHome());
        breadcrumbsForCreating.add(new Breadcrumb("sections.dataset.create.title"));
        breadcrumbsForEditing.add(Breadcrumb.getHome());
        breadcrumbsForEditing.add(new Breadcrumb("sections.dataset.edit.title"));
        breadcrumbsForPublicizing.add(Breadcrumb.getHome());
        breadcrumbsForPublicizing.add(new Breadcrumb("sections.dataset.advertise.title"));
        breadcrumbsForViewing.add(Breadcrumb.getHome());
        breadcrumbsForViewing.add(new Breadcrumb("sections.dataset.view.title"));
    }

    @Autowired
    private ResearchDatasetService researchDatasetService;

    @Autowired
    private RifCsWriter rifCsWriter;

    @Autowired
    private DatasetReadyToPublishMailService mailService;

    @Autowired
    private SecurityContextFacade securityContextFacade;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private TemporalDataValidator temporalDataValidator;

    @Autowired
    private TokenInputHelper tokenInputHelper;
    
    @Autowired
	private UrlHelper urlHelper;

    /**
     * For ResearchDataset we always initialize them with an empty ResearchProject first, workaround for the NotNull
     * validation constraint when we only want to retrieve and set the real one later
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ModelAttribute("researchDataset")
    public ResearchDataset initResearchProject()
    {
        ResearchDataset emptyModel = new ResearchDataset();
        ResearchProject researchProject = new ResearchProject();
        researchProject.associateWithResearchDataset(emptyModel);
        emptyModel.setPublications(new AutoPopulatingList(Publication.class));
        return emptyModel;
    }

    @RequestMapping(value = "/new/{id}", method = RequestMethod.GET)
    public String newResearchDataset(@PathVariable("id") Long projectId, final Model model, Principal principal)
    {
        return permissionService.canProject(PermissionType.CREATE_DATASET, projectId, principal,
                new PermissionService.ResearchProjectAction()
                {
                    @Override
                    public String act(ResearchProject project, User user)
                    {
                        model.addAttribute(RESEARCH_PROJECT_REQUEST_MODEL, project);
                        ResearchDataset dataset = new ResearchDataset();
                        dataset.setSubjectCode(project.getSubjectCode());
                        dataset.setSubjectCode2(project.getSubjectCode2());
                        dataset.setSubjectCode3(project.getSubjectCode3());

                        model.addAttribute(BREADCRUMBS, breadcrumbsForCreating);
                        model.addAttribute(RESEARCH_DATASET_REQUEST_MODEL, dataset);
                        model.addAttribute(BUILDINGS_MODEL_ATTRIBUTE, Building.findAllBuildings());
                        return REDIRECT_TO_NEW;
                    }
                });
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createResearchDataset(@RequestParam("projectId") final Long projectId,
            @RequestParam("vocabulary") final String keywords, @Valid final ResearchDataset researchDataset,
            final BindingResult result, final Model model, Principal principal)
    {

        temporalDataValidator.validateResearchDatasetTemporalData(researchDataset, "researchDataset", result);
        return permissionService.canProject(PermissionType.CREATE_DATASET, projectId, principal,
                new PermissionService.ResearchProjectAction()
                {
                    @Override
                    public String act(ResearchProject project, User user)
                    {
                        if (!result.hasErrors())
                        {
                            try
                            {
                                researchDatasetService.createDataset(projectId, researchDataset, keywords);
                                return REDIRECT_TO_HOME;
                            }
                            catch (NoneUniqueNameException noneUniqueException)
                            {
                                String[] nameErrorCode = {""};
                                String[] nameErrorArg = {""};
                                FieldError nameError = new FieldError(RESEARCH_DATASET_REQUEST_MODEL, "name",
                                        researchDataset.getName(), true, nameErrorCode, nameErrorArg,
                                        "Dataset already exists under this project");
                                result.addError(nameError);
                            }
                        }
                        // going back to view with errors, add model
                        model.addAttribute("vocabulary",
                                tokenInputHelper.buildJsonOnValidationError(keywords, project.getResearchGroup()));
                        model.addAttribute(BREADCRUMBS, breadcrumbsForCreating);
                        model.addAttribute(RESEARCH_PROJECT_REQUEST_MODEL, project);
                        model.addAttribute(BUILDINGS_MODEL_ATTRIBUTE, Building.findAllBuildings());
                        return REDIRECT_TO_NEW;
                    }
                });
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewResearchDataset(@PathVariable("id") Long datasetId, final Model model, Principal principal)
    {
        return permissionService.canViewDataset(datasetId, principal, new PermissionService.ResearchDatasetAction()
        {
            @Override
            public String act(ResearchDataset dataset, User user)
            {
                model.addAttribute(BREADCRUMBS, breadcrumbsForViewing);
                model.addAttribute(RESEARCH_DATASET_REQUEST_MODEL, dataset);
                if (!(dataset.getDateFrom() == null))
                {
                    model.addAttribute("dateFrom", dataset.getDateFrom().toDate());
                }
                if (!(dataset.getDateTo() == null))
                {
                    model.addAttribute("dateTo", dataset.getDateTo().toDate());
                }

                return REDIRECT_TO_VIEW;
            }
        });

    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editResearchDataset(@PathVariable("id") Long datasetId, final Model model,
            java.security.Principal principal)
    {
        return permissionService.canDataset(PermissionType.EDIT_DATASET, datasetId, principal,
                new PermissionService.ResearchDatasetAction()
                {
                    @Override
                    public String act(ResearchDataset dataset, User user)
                    {
                        model.addAttribute("vocabulary", tokenInputHelper.appendJson(dataset.getKeywords()));
                        model.addAttribute("groupId", dataset.getResearchProject().getResearchGroup().getId());
                        model.addAttribute(BREADCRUMBS, breadcrumbsForEditing);
                        model.addAttribute(RESEARCH_DATASET_REQUEST_MODEL, dataset);
                        model.addAttribute(BUILDINGS_MODEL_ATTRIBUTE, Building.findAllBuildings());
                        return REDIRECT_TO_EDIT;
                    }
                });

    }

    @RequestMapping(value = "/editResearchDataset", method = RequestMethod.PUT)
    public String editResearchDataset(@Valid final ResearchDataset researchDataset, final BindingResult result,
            final Model model, Principal principal, @RequestParam("vocabulary") final String keywords)
    {
        temporalDataValidator.validateResearchDatasetTemporalData(researchDataset, "researchDataset", result);
        return permissionService.canDataset(PermissionType.EDIT_DATASET, researchDataset.getId(), principal,
                new PermissionService.ResearchDatasetAction()
                {
                    @Override
                    public String act(ResearchDataset dataset, User user)
                    {
                        if (!dataset.getVersion().equals(researchDataset.getVersion()))
                        {
                            ResearchGroup group = dataset.getResearchProject().getResearchGroup();
                            model.addAttribute("vocabulary",
                                    tokenInputHelper.buildJsonOnValidationError(keywords, group));
                            model.addAttribute("groupId", dataset.getResearchProject().getResearchGroup().getId());
                            model.addAttribute(BREADCRUMBS, breadcrumbsForEditing);
                            model.addAttribute(RESEARCH_DATASET_REQUEST_MODEL, researchDataset);
                            model.addAttribute(BUILDINGS_MODEL_ATTRIBUTE, Building.findAllBuildings());
                            model.addAttribute("version_error", true);
                            return REDIRECT_TO_EDIT;
                        }

                        if (!result.hasErrors())
                        {
                            try
                            {
                                researchDatasetService.editDataset(researchDataset, keywords);
                                return REDIRECT_TO_HOME;
                            }
                            catch (NoneUniqueNameException noneUniqueException)
                            {
                                // Find its own id, there should only be one
                                String[] nameErrorCode = {""};
                                String[] nameErrorArg = {""};
                                FieldError nameError = new FieldError(RESEARCH_DATASET_REQUEST_MODEL, "name",
                                        researchDataset.getName(), true, nameErrorCode, nameErrorArg,
                                        "Dataset already exists under this project");
                                result.addError(nameError);
                            }
                        }
                        // going back to view with errors, add model
                        ResearchGroup group = dataset.getResearchProject().getResearchGroup();
                        model.addAttribute("vocabulary", tokenInputHelper.buildJsonOnValidationError(keywords, group));
                        model.addAttribute("groupId", dataset.getResearchProject().getResearchGroup().getId());
                        model.addAttribute(BREADCRUMBS, breadcrumbsForEditing);
                        model.addAttribute(RESEARCH_DATASET_REQUEST_MODEL, researchDataset);
                        model.addAttribute(BUILDINGS_MODEL_ATTRIBUTE, Building.findAllBuildings());
                        return REDIRECT_TO_EDIT;
                    }
                });
    }

    @RequestMapping(value = "/publish/{id}", method = RequestMethod.GET)
    public String confirmPublishResearchDataset(@PathVariable("id") Long datasetId, Model model, Principal principal)
    {
        ResearchDataset researchDataset = ResearchDataset.findResearchDataset(datasetId);
        ResearchProject researchProject = researchDataset.getResearchProject();
        ResearchGroup researchGroup = researchProject.getResearchGroup();
        User user = User.findUsersByUsernameEquals(principal.getName()).getSingleResult();
        if (researchDataset.isAdvertised())
        {
            return REDIRECT_TO_HOME;
        }
        else
        {
            // Check permissions to each set and determine if editing is avaliable at the project/group level
            model.addAttribute("canEditGroup",
                    permissionService.hasEditingAccessPermissionForGroup(user, researchGroup));
            model.addAttribute("canEditProject",
                    permissionService.hasEditingAccessPermissionForProject(user, researchProject));

            model.addAttribute(BREADCRUMBS, breadcrumbsForPublicizing);
            model.addAttribute(RESEARCH_DATASET_REQUEST_MODEL, researchDataset);
            model.addAttribute(RESEARCH_PROJECT_REQUEST_MODEL, researchProject);
            model.addAttribute(RESEARCH_GROUP_REQUEST_MODEL, researchGroup);
            model.addAttribute(PUBLIC_ACCESS_RIGHT_OPTIONS_ATTRIBUTE, PublicAccessRight.findAllPublicAccessRights());
            return "researchdataset/publish";
        }
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String publishResearchDataset(@Valid ResearchDataset incomingResearchDataset, BindingResult result,
            Model model, @RequestParam("id") Long datasetId,
            @RequestParam(value = "submit", required = false) String submit, HttpServletRequest request)
    {
        if ("Advertise".equals(submit))
        {
            ResearchDataset researchDataset = ResearchDataset.findResearchDataset(datasetId);
            String username = securityContextFacade.getAuthorizedUsername();

            boolean advertiseOccurred = researchDataset.advertise(username,
                    incomingResearchDataset.getPublicAccessRight(), rifCsWriter, mailService,
                    urlHelper.getCurrentBaseUrl(request));
            if (advertiseOccurred)
            {
                researchDataset.merge();
            }

        }
        return REDIRECT_TO_HOME;
    }

    @RequestMapping(value = "/rejectAdvertising/{id}", method = RequestMethod.GET)
    public String rejectAdvertisingResearchDataset(@PathVariable("id") Long datasetId, Model model)
    {
        ResearchDataset researchDataset = ResearchDataset.findResearchDataset(datasetId);
        String username = securityContextFacade.getAuthorizedUsername();
        boolean rejectOccurred = researchDataset.rejectAdvertise(username);
        if (rejectOccurred)
        {
            researchDataset.merge();
        }

        return REDIRECT_TO_HOME;
    }

    @RequestMapping(value = "/unadvertise", method = RequestMethod.POST)
    public String unadvertiseResearchDataset(@RequestParam("datasetId") Long datasetId,
            @RequestParam("submit") String submit, Model model)
    {
        if ("Confirm".equals(submit))
        {
            ResearchDataset researchDataset = ResearchDataset.findResearchDataset(datasetId);
            String username = securityContextFacade.getAuthorizedUsername();

            boolean unadvertiseOccurred = researchDataset.unadvertise(username, rifCsWriter);

            if (unadvertiseOccurred)
            {
                researchDataset.merge();
            }
        }
        return REDIRECT_TO_HOME;
    }

    @RequestMapping(value = "/unadvertise/{id}", method = RequestMethod.GET)
    public String confirmUnadvertiseResearchDataset(@PathVariable("id") Long datasetId, Model model)
    {
        ResearchDataset researchDataset = ResearchDataset.findResearchDataset(datasetId);
        model.addAttribute("researchDataset", researchDataset);

        return CONFIRM_UNADVERTISE;
    }

    @RequestMapping
    public String index()
    {
        return "researchdataset/index";
    }

    @InitBinder
    public void initDataBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(ResearchSubjectCode.class, new ResearchSubjectCodePropertyEditor());
        binder.registerCustomEditor(DateTime.class, new DateTimePropertyEditor());
        binder.registerCustomEditor(PublicAccessRight.class, new PropertyEditorSupport()
        {
            public String getAsText()
            {
                PublicAccessRight value = (PublicAccessRight) getValue();
                if (value == null)
                {
                    return "";
                }
                return value.getId().toString();
            }

            public void setAsText(String text) throws IllegalArgumentException
            {
                PublicAccessRight publicAccessRight = PublicAccessRight.findPublicAccessRight(Long.parseLong(text));
                setValue(publicAccessRight);
            }
        });
    }
}
