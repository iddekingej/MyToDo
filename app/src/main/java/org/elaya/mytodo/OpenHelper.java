package org.elaya.mytodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
*Creates or updates the database
 * */

class OpenHelper extends SQLiteOpenHelper {

    public OpenHelper(Context pContext){
        super(pContext,"main",null,1);
    }

    private void createStatus(SQLiteDatabase pDatabase){
        pDatabase.execSQL("" +
                "create table status(" +
                "_id integer primary key autoincrement" +
                ",position integer not null"+
                ",action_type integer not null"+
                ",description text" +
                ")");
    }
    private void createTodoItems(SQLiteDatabase pDatabase)
    {
        pDatabase.execSQL("" +
                "create table todoitems(" +
                "_id integer primary key autoincrement" +
                ",id_project integer not null" +
                ",id_status  integer"+
                ",title text" +
                ",comment text" +
                ",constraint fk_todoitems_1 foreign key(id_status) references status(_id)"+
                ",constraint fk_todoitems_2 foreign key(id_project) references projects(_id));");
        pDatabase.execSQL("create index ind_todoitems_1 on todoitems(id_project)");
    }
    public void onCreate(SQLiteDatabase pDatabase) {

        pDatabase.execSQL("create table projects(_id integer primary key autoincrement,projectname text);");
        createStatus(pDatabase);
        createTodoItems(pDatabase);
    }
    public void onUpgrade(SQLiteDatabase pDatabase,int p_oldversion,int p_newVersion){


    }
}