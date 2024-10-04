package com.example.bpmsenterprise.components.documents.facade.impl;

import com.example.bpmsenterprise.components.authentication.entity.*;
import com.example.bpmsenterprise.components.authentication.interfaces.*;
import com.example.bpmsenterprise.components.documents.DTO.*;
import com.example.bpmsenterprise.components.documents.facade.*;
import com.example.bpmsenterprise.components.documents.interfaces.*;
import com.example.bpmsenterprise.components.userData.entity.*;
import com.example.bpmsenterprise.components.userData.entity.views.*;
import com.example.bpmsenterprise.components.userData.interfaces.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class DocumentsFacadeImpl implements IDocumentsFacade {

    private final ICompanyControl companyControl;
    private final IDocumentsControl documentsControl;

    private final IProjectControl projectControl;

    private final UserData userData;


    public DocumentsFacadeImpl(ICompanyControl companyControl, IDocumentsControl documentsControl, IProjectControl projectControl, UserData userData) {
        this.companyControl = companyControl;
        this.documentsControl = documentsControl;
        this.projectControl = projectControl;
        this.userData = userData;
    }

    @Override
    public List<DocumentInfoDTO> fetchDocumentsFromAllRecourses(String companyName, String type) {

        User user = userData.getCurrentUser();

        Company company = companyControl.companyData(companyName);

        List<ViewProject> projects = projectControl.getAllProjects(companyName);

        return documentsControl.fetch(company, projects, user, type);

    }
}
