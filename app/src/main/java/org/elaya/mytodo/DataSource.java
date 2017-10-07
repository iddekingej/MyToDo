package org.elaya.mytodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Interface to SqlLite database
 */

final class DataSource {
    private static  DataSource source;
    private SQLiteDatabase db;


    private DataSource(Context pContext){
        open(pContext);
    }


    /**
     * Delete record from table by ID
     * @param pTable  Table name
     * @param pId     Pk value (must have _id pk)
     */

    private void deleteById(String pTable,long pId)
    {
        db.delete(pTable,"_id=?",new String[]{Long.toString(pId)});
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

    public static DataSource getSource()
    {
        return source;
    }

    private void open(Context pContext)
    {
        OpenHelper lHelper=new OpenHelper(pContext);
        db = lHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=on");
    }

    public void close()
    {
        db.close();
    }

    public ProjectItem getProjectById(long pId)
    {
        Cursor lProjectCursor=db.rawQuery("select projectname from projects where _id=?",new String[]{Long.toString(pId)});
        lProjectCursor.moveToFirst();
        ProjectItem lProjectItem=null;
        if(!lProjectCursor.isAfterLast()){
            lProjectItem=new ProjectItem(pId,lProjectCursor.getString(0));

        }
        lProjectCursor.close();
        return lProjectItem;
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

    public boolean projectHasTodo(ProjectItem pProject)
    {
        Cursor lHasToDoCursor=db.rawQuery("select 1 as dm where exists(select 1 from todoitems where id_project=?)",new String[]{String.valueOf(pProject.getId())});
        lHasToDoCursor.moveToFirst();
        boolean lHas=!lHasToDoCursor.isAfterLast();
        lHasToDoCursor.close();
        return lHas;
    }


    /**
     * List all to do items belonging to a project
     *
     * @param pIdProject Project ID. The to do's of this project are retrieved
     * @return           Cursor that retrieves all to do belonging to a project
     */
    public Cursor getTodoCursor(long pIdProject)
    {
        Cursor lTodoCursor=db.rawQuery("" +
                "select t._id " +
                ",      t.id_project" +
                ",      t.id_status" +
                ",      t.title "+
                ",      t.comment" +
                ",      t.start_date "+
                ",      t.end_date "+
                ",      s.description statusdesc " +
                ",      case when s.action_type==? then 1 else 0 end isfinished "+
                "from todoitems t " +
                "left join status s on (t.id_status = s._id) " +
                "where id_project=? " +
                "order by " +
                "   case " +
                "   when action_type=2 then 1 " +
                "   when action_type in (0,1,4) then 2 " +
                "   else 3 end" +
                ", t._id desc",new String[]{Long.toString(ActionTypes.FINISHED),Long.toString(pIdProject)});
        lTodoCursor.moveToFirst();
        return lTodoCursor;
    }

    /**
     * Add project to database
     *
     * @param pProjectName  Naam/description of project
     */
    public long addProject(String pProjectName){
        ContentValues lValues=new ContentValues();
        lValues.put("projectname",pProjectName);
        return db.insert("projects",null,lValues);
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
        lValues.put("projectname",pProjectName);
        updateById("projects",lValues,pId);
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
        lValues.put("id_project",pIdProject);
        lValues.put("id_status",pIdStatus);
        lValues.put("title",pTitle);
        lValues.put("comment",pComment);
        if(pStartDate != null){
            lValues.put("start_date",pStartDate);
        }
        if(pEndDate != null){
            lValues.put("end_date",pEndDate);
        }
        db.insert("todoitems",null,lValues);
    }

    public void updateToDo(long pId,long pIdProject,long pIdStatus,String pTitle,String pComment,Long pStartDate,Long pEndDate)
    {
        ContentValues lValues = new ContentValues();
        lValues.put("id_project",pIdProject);
        lValues.put("id_status",pIdStatus);
        lValues.put("title",pTitle);
        lValues.put("comment",pComment);
        if(pStartDate != null){
            lValues.put("start_date",pStartDate);
        }
        if(pEndDate != null){
            lValues.put("end_date",pEndDate);
        }
        updateById("todoitems",lValues,pId);
    }

    public void deleteToDo(long pId)
    {
        deleteById("todoitems",pId);
    }

    public void addStatus(long pPosition,long pActionType,String pDescription,boolean pActive)
    {
        ContentValues lValues = new ContentValues();
        lValues.put("position",pPosition);
        lValues.put("action_type",pActionType);
        lValues.put("description",pDescription);
        lValues.put("active",pActive?1:0);
        db.insert("status",null,lValues);
    }

    public void updateStatus(long pId,long pPosition,long pActionType,String pDescription,boolean pActive)
    {
        ContentValues lValues = new ContentValues();
        lValues.put("position",pPosition);
        lValues.put("action_type",pActionType);
        lValues.put("description",pDescription);
        lValues.put("active",pActive?1:0);
        updateById("status",lValues,pId);
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
        deleteById("status",pId);
    }

    public Cursor getStatusCursor()
    {
        Cursor lStatusCursor=db.rawQuery("select * from status order by position",null);
        lStatusCursor.moveToFirst();
        return lStatusCursor;
    }

    public Cursor getActiveStatusCursor(long pIdCurrent)
    {
        Cursor lStatusCursor=db.rawQuery("select * from status where active=1 or _id=? order by position",new String[]{String.valueOf(pIdCurrent)});
        lStatusCursor.moveToFirst();
        return lStatusCursor;
    }

    public void deleteProject(long pId)
    {
        deleteById("projects",pId);
    }
}
