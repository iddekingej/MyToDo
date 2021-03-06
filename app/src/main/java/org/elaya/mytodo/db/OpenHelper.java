package org.elaya.mytodo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.ActionTypes;

/**
*Creates or updates the database
 * */

class OpenHelper extends SQLiteOpenHelper {
    private final Context context;

    public OpenHelper(Context pContext) {
        super(pContext, "main", null, 1);
        context = pContext;
    }

    private void insertStatus(@NonNull SQLiteDatabase pDb, long pPosition, long pActionType, @StringRes int pDescription) {
        ContentValues lValues = new ContentValues();
        lValues.put("position", pPosition);
        lValues.put("action_type", pActionType);
        lValues.put("description", context.getResources().getString(pDescription));
        lValues.put("active", 1);
        pDb.insert("status", null, lValues);
    }

    private void statusDefaults(@NonNull SQLiteDatabase pDb) {
        insertStatus(pDb, 0, ActionTypes.NOT_STARTED, R.string.at_not_active);
        insertStatus(pDb, 1, ActionTypes.NOT_ACTIVE, R.string.at_not_started);
        insertStatus(pDb, 2, ActionTypes.STARTED, R.string.at_started);
        insertStatus(pDb, 3, ActionTypes.FINISHED, R.string.at_finished);
        insertStatus(pDb, 4, ActionTypes.REMOVED, R.string.at_removed);

    }

    private void createProject(@NonNull SQLiteDatabase pDatabase){
        pDatabase.execSQL("create table projects(_id integer primary key autoincrement,projectname text);");
    }

    private void createTodoItems(@NonNull SQLiteDatabase pDatabase)
    {
        pDatabase.execSQL("" +
                "create table todoitems(" +
                "_id integer primary key autoincrement" +
                ",id_project integer not null" +
                ",id_status  integer"+
                ",title text" +
                ",comment text" +
                ",start_date integer"+
                ",end_date integer"+
                ",constraint fk_todoitems_1 foreign key(id_status) references status(_id)"+
                ",constraint fk_todoitems_2 foreign key(id_project) references projects(_id));");
        pDatabase.execSQL("create index ind_todoitems_1 on todoitems(id_project)");
    }

    private void createStatus(@NonNull  SQLiteDatabase pDatabase){
        pDatabase.execSQL("" +
                "create table status(" +
                "_id integer primary key autoincrement" +
                ",position integer not null"+
                ",action_type integer not null"+
                ",active integer not null"+
                ",description text" +
                ",constraint chk_status_1 check(active in (0,1))"+
                ")");
        statusDefaults(pDatabase);
    }

    private void createFilter(@NonNull SQLiteDatabase pDatabase)
    {
        pDatabase.execSQL(
                "create table filters(" +
                        "_id integer primary key autoincrement" +
                        ",name text" +
                        ",date_filter integer" +
                        ")"
        );
        pDatabase.execSQL(
                "create table filter_status(" +
                        "_id integer primary key autoincrement" +
                        ",id_filter integer not null" +
                        ",id_status integer not null" +
                        ",constraint fk_filter_status_1 foreign key(id_filter) references filters(_id)" +
                        ",constraint fk_filter_status_2 foreign key(id_status) references status(_id) "+
                        ")"
        );
    }


    public void onCreate(@NonNull SQLiteDatabase pDatabase) {
        createProject(pDatabase);
        createStatus(pDatabase);
        createTodoItems(pDatabase);
        createFilter(pDatabase);
    }

    public void onUpgrade(@NonNull SQLiteDatabase pDatabase,int pOldVersion,int pNewVersion) {

    }
}