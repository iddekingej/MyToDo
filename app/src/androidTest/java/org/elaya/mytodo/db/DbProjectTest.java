package org.elaya.mytodo.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.elaya.mytodo.db.DataSource;
import org.elaya.mytodo.project.ProjectItem;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

/**
 * Created by jeroen on 11/10/17.
 */
@RunWith(AndroidJUnit4.class)

public class DbProjectTest {

    static final String projectName="TestProject";
    static final String projectNameOth="TestProjectXXXX";

    DataSource ds;
    Context context;
    long IdProject;
    @Before
    public void setupDS()
    {
        context= InstrumentationRegistry.getTargetContext();
        ds=DataSource.makeSource(context);
    }

    @Test
    public void testAddProject()
    {
        long lId=ds.addProject(projectName);
        assertTrue(lId>=0);
    }

    @Test
    public void  testAddGetProject()
    {
        long lId=ds.addProject(projectName);
        ProjectItem lProject=ds.getProjectById(lId);
        assertEquals(lProject.getProjectName(),projectName);
        assertEquals(lProject.getId(),lId);
        assertEquals(lProject.toString(),projectName);
    }

    @Test
    public void testEditProject()
    {
        long lId=ds.addProject(projectName);
        ds.editProject(lId,projectNameOth);
        ProjectItem lProject=ds.getProjectById(lId);
        assertEquals(lProject.getProjectName(),projectNameOth);
        assertEquals(lProject.toString(),projectNameOth);
    }

}

