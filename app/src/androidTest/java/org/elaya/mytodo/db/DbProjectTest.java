package org.elaya.mytodo.db;


import android.support.test.runner.AndroidJUnit4;
import org.elaya.mytodo.project.ProjectItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Testing project methods regarding db interaction
 */
@RunWith(AndroidJUnit4.class)

public class DbProjectTest  extends DbTest{

    private static final  String projectName="TestProject";
    private static final  String projectNameOth="TestProjectXXXX";


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
        if(lProject==null){
            fail("Project is null");
        } else {
            assertEquals(projectName,lProject.getProjectName());
            assertEquals(lId,lProject.getId());
            assertEquals(projectName,lProject.toString());
        }
    }

    @Test
    public void testEditProject()
    {
        long lId=ds.addProject(projectName);
        ds.editProject(lId,projectNameOth);
        ProjectItem lProject=ds.getProjectById(lId);
        if(lProject==null){
            fail("Project is null");
        } else {
            assertEquals(projectNameOth,lProject.getProjectName());
            assertEquals(projectNameOth,lProject.toString() );
        }
    }

    @Test
    public void testDeleteProject()
    {
        long lId=ds.addProject(projectName);
        ds.deleteProject(lId);
        ProjectItem lProject=ds.getProjectById(lId);
        assertNull(lProject);
    }

}

