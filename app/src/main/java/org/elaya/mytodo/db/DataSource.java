package org.elaya.mytodo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import org.elaya.mytodo.filter.FilterFilterSelection;
import org.elaya.mytodo.filter.FilterItem;
import org.elaya.mytodo.filter.FilterManager;
import org.elaya.mytodo.filter.FilterSelection;
import org.elaya.mytodo.project.ProjectItem;
import org.elaya.mytodo.status.StatusItem;
import org.elaya.mytodo.todo.TodoItem;
import org.elaya.mytodo.tools.ActionTypes;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Interface to SqlLite database
 */

public final class DataSource {
    private static  DataSource source;
    private SQLiteDatabase db;


    private DataSource(Context pContext){
        open(pContext);
    }


    private String[] stringArrayFromLong(long pId)
    {
        return new String[]{Long.toString(pId)};
    }


    /**
     * Delete record from table by ID
     * @param pTable  Table name
     * @param pId     Pk value (must have _id pk)
     */

    private void deleteById(String pTable,long pId)
    {
        db.delete(pTable,"_id=?", stringArrayFromLong(pId));
    }

    /**
     * Update values by id (must have a PK named _id)
     *
     * @param pTable   Table names
     * @param pValues  Values (key are column names)
     * @param pId      Pk ID  record that must be updated
     */

    private void updateById(String pTable,ContentValues pValues,long pId)
    {
        db.update(pTable,pValues,"_id=?",new String[]{String.valueOf(pId)});
    }

    /**
     * DataSource is a singleton, this is the factory for creating the DataSource
     *
     * @param pContext  Application activity
     * @return          Created data source
     */
    public static DataSource makeSource(Context pContext)
    {
        if(source == null){
            source=new DataSource(pContext);
        }
        return source;
    }

     private void open(Context pContext)
    {
        OpenHelper lHelper=new OpenHelper(pContext);
        db = lHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=on");
    }

    @Nullable
    public ProjectItem getProjectById(long pId)
    {
        Cursor lProjectCursor=db.rawQuery("select _id,projectname from projects where _id=?",new String[]{Long.toString(pId)});
        lProjectCursor.moveToFirst();
        ProjectItem lProjectItem=null;
        if(!lProjectCursor.isAfterLast()){
            lProjectItem=new ProjectItem(lProjectCursor);

        }
        lProjectCursor.close();
        return lProjectItem;
    }


    public int fillProjectAdapter(@NonNull ArrayAdapter<ProjectItem> pProjectAdapter, long pId)
    {
        Cursor lProjectCursor=db.rawQuery("select _id,projectname from projects order by projectName",null);
        lProjectCursor.moveToFirst();
        int lCnt=0;
        int lReturn=0;
        while(!lProjectCursor.isAfterLast()){
            ProjectItem lProjectItem=new ProjectItem(lProjectCursor);
            pProjectAdapter.add(lProjectItem);
            lProjectCursor.moveToNext();
            if(lProjectItem.getId()==pId){
                lReturn=lCnt;
            }
            lCnt++;
        }
        lProjectCursor.close();
        return lReturn;
    }

    /**
     * Get query hat retrieves all projects
     *
     * @return Initialized cursor that retrieves all projects
     */
    public Cursor getProjectCursor()
    {
        Cursor lProjectCursor=db.rawQuery("" +
                "select p._id" +
                ",      p.projectname " +
                ",      sum(case when action_type in (0,1,4) then 1 else 0 end) num_not_active "+
                ",      sum(case when action_type =2  then 1 else 0 end) num_active "+
                ",      sum(case when action_type =3  then 1 else 0 end) num_finished "+
                "from      projects p " +
                "left join todoitems t on (p._id=t.id_project) " +
                "left join status s on (t.id_status=s._id) "+
                "group by p._id,p.projectname "+
                "order by p._id desc " ,null);
        lProjectCursor.moveToFirst();
        return lProjectCursor;
    }

    public boolean projectHasTodo(@NonNull  ProjectItem pProject)
    {
        Cursor lHasToDoCursor=db.rawQuery("select 1 as dm where exists(select 1 from todoitems where id_project=?)",new String[]{String.valueOf(pProject.getId())});
        lHasToDoCursor.moveToFirst();
        boolean lHas=!lHasToDoCursor.isAfterLast();
        lHasToDoCursor.close();
        return lHas;
    }

    public String getStatusTextById(long pId)
    {
            Cursor lStatusCursor=db.rawQuery("select description from status where _id=?",new String[]{String.valueOf(pId)});
            lStatusCursor.moveToFirst();
            String lDescription;
            if(!lStatusCursor.isAfterLast()){
                lDescription=lStatusCursor.getString(0);
            } else {
                lDescription="";
            }
            lStatusCursor.close();
            return lDescription;
    }

    public long getNumberOfTodo(long pIdProject)
    {
        Cursor lNumCursor=db.rawQuery("select count(1) num from todoitems where id_project=?",new String[]{String.valueOf(pIdProject)});
        lNumCursor.moveToFirst();
        long lNum=lNumCursor.getLong(0);
        lNumCursor.close();
        return lNum;
    }

    /**
     * List all to do items belonging to a project
     *
     * @param pIdProject Project ID. The to do's of this project are retrieved
     * @return           Cursor that retrieves all to do belonging to a project
     */
    public Cursor getTodoCursor(long pIdProject,boolean pNot)
    {
        long lUnix=new DateTime().getMillis();
        FilterSelection  lFilter= FilterManager.getCurrentFilter();
        String lCondition="";
        if(lFilter != null){
            lCondition=lFilter.getCondition();
            if(!lCondition.isEmpty()) {
                if (pNot) {
                    lCondition = "not " + lCondition;
                }
                lCondition = "and " + lCondition;
            }
        }

        Cursor lTodoCursor=db.rawQuery("" +
                "select t._id " +
                ",      t.id_project" +
                ",      t.id_status" +
                ",      t.title "+
                ",      t.comment" +
                ",      t.start_date "+
                ",      t.end_date "+
                ",      s.description statusdesc " +
                ",      case when (s.action_type = ?) then 1 else 0 end isfinished "+
                "from todoitems t " +
                "left join status s on (t.id_status = s._id) " +
                "join projects p on (t.id_project = p._id)"+
                "where t.id_project=? " +
                lCondition+
                "order by " +
                "   case " +
                "   when end_date <?  and not action_type in (3,4) then 1" +
                "   when start_date < ?  and not action_type in (3,4) then 2"+
                "   when action_type=2 then 3 " +
                "   when action_type in (0,1,4) then 4 " +
                "   else 5 end" +
                ", t._id desc",new String[]{
                    String.valueOf(ActionTypes.FINISHED),
                    String.valueOf(pIdProject),
                    String.valueOf(lUnix),
                    String.valueOf(lUnix)
            });
        lTodoCursor.moveToFirst();
        return lTodoCursor;
    }



    /**
     * Add project to database
     *
     * @param pProjectName  Name/description of project
     */
    public long addProject(String pProjectName){
        ContentValues lValues=new ContentValues();
        lValues.put( ProjectItem.F_PROJECTNAME,pProjectName);
        return db.insert(ProjectItem.F_TABLE_NAME,null,lValues);
    }

    /**
     * Update project data with id=pId into database.
     *
     * @param pId           Id of project
     * @param pProjectName  New project name
     */
    public void editProject(long pId,String pProjectName)
    {
        ContentValues lValues = new ContentValues();
        lValues.put(ProjectItem.F_PROJECTNAME,pProjectName);

        updateById(ProjectItem.F_TABLE_NAME,lValues,pId);
    }

    /**
     * Add new to do item to database
     *
     * @param pIdProject   This to do item belongs to project with this id
     * @param pIdStatus    To Do item status id
     * @param pTitle       Title of to do
     * @param pComment     To do Comment
     */

    public void addTodo(long pIdProject,long pIdStatus,String pTitle,String pComment,Long pStartDate,Long pEndDate)
    {
        ContentValues lValues=new ContentValues();
        lValues.put(TodoItem.F_ID_PROJECT,pIdProject);
        lValues.put(TodoItem.F_ID_STATUS,pIdStatus);
        lValues.put(TodoItem.F_TITLE,pTitle);
        lValues.put(TodoItem.F_COMMENT,pComment);
        lValues.put(TodoItem.F_START_DATE,pStartDate);
        lValues.put(TodoItem.F_END_DATE,pEndDate);
        db.insert(TodoItem.TABLE_NAME,null,lValues);
    }

    public void updateToDo(long pId,long pIdProject,long pIdStatus,String pTitle,String pComment,Long pStartDate,Long pEndDate)
    {
        ContentValues lValues = new ContentValues();
        lValues.put(TodoItem.F_ID_PROJECT,pIdProject);
        lValues.put(TodoItem.F_ID_STATUS,pIdStatus);
        lValues.put(TodoItem.F_TITLE,pTitle);
        lValues.put(TodoItem.F_COMMENT,pComment);
        lValues.put(TodoItem.F_START_DATE,pStartDate);
        lValues.put(TodoItem.F_END_DATE,pEndDate);
        updateById(TodoItem.TABLE_NAME,lValues,pId);
    }

    public void deleteToDo(long pId)
    {
        deleteById("todoitems",pId);
    }

    public long addStatus(long pPosition,long pActionType,String pDescription,boolean pActive)
    {
        ContentValues lValues = new ContentValues();

        lValues.put(StatusItem.F_POSITION,pPosition);
        lValues.put(StatusItem.F_ACTION_TYPE,pActionType);
        lValues.put(StatusItem.F_DESCRIPTION,pDescription);
        lValues.put(StatusItem.F_ACTIVE,pActive?1:0);
        return db.insert(StatusItem.TABLE_NAME,null,lValues);
    }

    public void updateStatus(long pId,long pPosition,long pActionType,String pDescription,boolean pActive)
    {
        ContentValues lValues = new ContentValues();
        lValues.put(StatusItem.F_POSITION,pPosition);
        lValues.put(StatusItem.F_ACTION_TYPE,pActionType);
        lValues.put(StatusItem.F_DESCRIPTION,pDescription);
        lValues.put(StatusItem.F_ACTIVE,pActive?1:0);
        updateById(StatusItem.TABLE_NAME,lValues,pId);
    }

    public boolean statusIsUsed(long pId)
    {
        Cursor lExistsCursor=db.rawQuery("select 1 as dummy where exists(select 1 from todoitems where id_status=?)",new String[]{Long.toString(pId)});
        lExistsCursor.moveToFirst();
        boolean lExists=!lExistsCursor.isAfterLast();
        lExistsCursor.close();
        return lExists;
    }

    public void deleteStatus(long pId)
    {
        deleteById(StatusItem.TABLE_NAME,pId);
    }

    public Cursor getStatusCursor()
    {
        Cursor lStatusCursor=db.rawQuery("select * from status order by position",null);
        lStatusCursor.moveToFirst();
        return lStatusCursor;
    }

    public Cursor getActiveStatusCursor(Long pIdCurrent)
    {
        String  lParams[]=null;
        String lQry;
        if(pIdCurrent != null){
            lParams=new String[]{String.valueOf(pIdCurrent)};
            lQry="select * from status where active=1 or _id=? order by position";
        } else {
            lQry="select * from status where active=1 order by position";
        }
        Cursor lStatusCursor=db.rawQuery(lQry,lParams);
        lStatusCursor.moveToFirst();
        return lStatusCursor;
    }

    public void deleteProject(long pId)
    {
        deleteById("projects",pId);
    }


    @NonNull
    public Set<Long> getStatusSetFilter(long pIdFilter)
    {
        Set<Long> lStatusSet=new HashSet<>();
        Cursor lStatusCursor=db.rawQuery("select id_status from filter_status where id_filter=?",new String[]{String.valueOf(pIdFilter)});
        lStatusCursor.moveToFirst();

        while(!lStatusCursor.isAfterLast()){
            lStatusSet.add(lStatusCursor.getLong(0));
            lStatusCursor.moveToNext();
        }
        lStatusCursor.close();
        return lStatusSet;
    }



    public Cursor getFilterCursor()
    {
        Cursor lCursor=db.rawQuery("select * from filters order by lower(name)",null);
        lCursor.moveToFirst();
        return lCursor;
    }

    private void insertFilterStatus(long pIdFilter,List<Long> pStatus){
        ContentValues lStatusValues = new ContentValues();
        lStatusValues.put("id_filter", pIdFilter);
        for(long lStatus:pStatus){
            lStatusValues.put("id_status", lStatus);
            db.insert("filter_status", null, lStatusValues);
        }
    }

    public void addFilter(String pName,long pDateFilter,List<Long> pStatus)
    {
        db.beginTransaction();
        try {
            ContentValues lValues = new ContentValues();
            lValues.put(FilterItem.F_NAME, pName);
            lValues.put(FilterItem.F_DATE_FILTER, pDateFilter);
            long lId = db.insert(FilterItem.F_TABLE_NAME, null, lValues);
            insertFilterStatus(lId,pStatus);
            db.setTransactionSuccessful();
            db.endTransaction();
        }catch(Exception e){
            db.endTransaction();
            throw e;
        }
    }

    public void editFilter(long pIdFilter,String pName,long pDateFilter,List<Long> pStatus)
    {
        db.beginTransaction();
        try {
            ContentValues lValues=new ContentValues();
            lValues.put(FilterItem.F_NAME,pName);
            lValues.put(FilterItem.F_DATE_FILTER,pDateFilter);
            updateById(FilterItem.F_TABLE_NAME,lValues,pIdFilter);

            db.delete("filter_status","id_filter=?", stringArrayFromLong(pIdFilter));
            insertFilterStatus(pIdFilter,pStatus);
            db.setTransactionSuccessful();
            db.endTransaction();
        }catch(Exception e){
            db.endTransaction();
            throw e;
        }

    }

    public void deleteFilter(long pIdFilter)
    {
        db.delete("filter_status","id_filter=?", stringArrayFromLong(pIdFilter));
        deleteById(FilterItem.F_TABLE_NAME,pIdFilter);
    }

    public void fillFilterSelection(List<FilterSelection> pSelectionList)
    {
        FilterItem lItem;
        Cursor lCursor=db.rawQuery("select * from filters order by name",null);
        lCursor.moveToFirst();
        while(!lCursor.isAfterLast()){
            lItem=new FilterItem(lCursor);
            pSelectionList.add(new FilterFilterSelection(lItem));
            lCursor.moveToNext();
        }
        lCursor.close();
    }

}
