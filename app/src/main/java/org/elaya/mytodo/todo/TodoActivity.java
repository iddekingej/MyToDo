package org.elaya.mytodo.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.elaya.mytodo.filter.FilterActivity;
import org.elaya.mytodo.filter.FilterConditionSelection;
import org.elaya.mytodo.filter.FilterManager;
import org.elaya.mytodo.filter.FilterSelection;
import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.project.ProjectItem;
import org.elaya.mytodo.R;

import java.util.ArrayList;

public class TodoActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final int ACT_NEW_TODO=100;
    private static final int ACT_SHOW_TODO=101;
    private static final int ACT_FILTER=102;
    public static  final int RES_DELETE_TODO=RESULT_FIRST_USER+1000;

    private long id;
    private TodoListAdapter adapter;
    @Nullable
    private ProjectItem projectItem;
    private boolean notFilter;
    private TextView numTodoFilterElement;
    private LinearLayout headerElement;
    private ArrayAdapter<FilterSelection> filterListAdapter;
    private Spinner todoFilterElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent lIntent=getIntent();
        id=lIntent.getLongExtra("_id",0);
        projectItem=ds.getProjectById(id);

        headerElement=findViewById(R.id.header);

        TextView projectName= findViewById(R.id.projectName);
        ListView todoList= findViewById(R.id.todoList);

        todoFilterElement = findViewById(R.id.todoFilter);
        ArrayList<FilterSelection> lList=getFilterList();
        filterListAdapter =new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,lList);
        todoFilterElement.setAdapter(filterListAdapter);
        todoFilterElement.setSelection(FilterManager.getSelected(lList));
        todoFilterElement.setOnItemSelectedListener(this);

        Spinner filterType=findViewById(R.id.filterType);
        filterType.setOnItemSelectedListener(this);

        if(projectItem != null) {
            projectName.setText(projectItem.getProjectName());
        }

        adapter=new TodoListAdapter(this,ds.getTodoCursor(id,false));
        todoList.setAdapter(adapter);
        todoList.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> pParent, @NonNull View pView, int pPosition, long pId){
                openShowToDo(pView);
            }
        });
        todoList.setEmptyView(findViewById(R.id.noItemsMessage));

        numTodoFilterElement =findViewById(R.id.numTodoFilter);
        notFilter=false;

        setNumNotInFilter();
        showHeader();
    }

    private ArrayList<FilterSelection> getFilterList()
    {
        ArrayList<FilterSelection> lList=new ArrayList<>();
        lList.add(new FilterConditionSelection("All","",false));
        ds.fillFilterSelection(lList);
        return lList;
    }

    private void setNumNotInFilter()
    {
        int lNum=ds.getNumberOfTodo(id);

        int lFiltered=adapter.getCount();

        String lTitle=getResources().getQuantityString(R.plurals.ft_num_found,lFiltered,lFiltered,lNum-lFiltered);
        numTodoFilterElement.setText(lTitle);
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_todo;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_todo;
    }

    @Override
    protected String getHelpName(){
        return "todo";
    }

    private void showHeader()
    {
        headerElement.setVisibility(adapter.getCount()==0?View.GONE:View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        if(R.id.add_todo==pItem.getItemId()){
            newToDo();
        } else {
            return super.onOptionsItemSelected(pItem);
        }

        return true;
    }
    private void warning(String pWarning) {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(this);
        lBuilder.setTitle(R.string.failure_title);
        lBuilder.setMessage(pWarning);
        lBuilder.setPositiveButton("Ok", null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

    private void newToDo()
    {
        Intent lIntent= new Intent(this,EditToDoActivity.class);
        lIntent.putExtra("id_project",id);
        startActivityForResult(lIntent,ACT_NEW_TODO);
    }

    private void openShowToDo(@NonNull View pItem){
        TodoItem lItem=(TodoItem)pItem.getTag();
        Intent lIntent= new Intent(this,ShowTodoActivity.class);
        lIntent.putExtra(ShowTodoActivity.P_ID,lItem.getId());
        lIntent.putExtra(ShowTodoActivity.P_ID_PROJECT,id);
        lIntent.putExtra(ShowTodoActivity.P_ID_STATUS,lItem.getIdStatus());
        lIntent.putExtra(ShowTodoActivity.P_TITLE,lItem.getTitle());
        lIntent.putExtra(ShowTodoActivity.P_COMMENT,lItem.getComment());
        if(lItem.getStartDate() != null){
            lIntent.putExtra(ShowTodoActivity.P_START_DATE,lItem.getStartDate());
        }
        if(lItem.getEndDate() != null){
            lIntent.putExtra(ShowTodoActivity.P_END_DATE,lItem.getEndDate());
        }
        startActivityForResult(lIntent,ACT_SHOW_TODO);
    }

    private void insertTodo(@NonNull  Intent pData)
    {
        long lIdProject=pData.getLongExtra("id_project",-1);
        long lIdStatus=pData.getLongExtra("id_status",-1);
        String lTitle=pData.getStringExtra("title");
        String lComment=pData.getStringExtra("comment");
        Long lStartDate=null;
        if(pData.hasExtra("startDate")){
            lStartDate=pData.getLongExtra("startDate",-1);
        }
        Long lEndDate=null;
        if(pData.hasExtra("endDate")){
            lEndDate=pData.getLongExtra("endDate",-1);
        }
        try {
            ds.addTodo(lIdProject,lIdStatus, lTitle, lComment,lStartDate,lEndDate);
        }catch(Exception e){
            warning(e.getMessage());
        }
        refreshList();
    }


    private void deleteTodo(@NonNull Intent pData)
    {
        long lId=pData.getLongExtra("_id",-1);
        ds.deleteToDo(lId);
        refreshList();
    }
    protected void onActivityResult(int pRequestCode,int pResultCode,@NonNull Intent pData) {
        try {
            if(pRequestCode==ACT_FILTER){
                ArrayList<FilterSelection> lList=getFilterList();
                filterListAdapter.clear();
                filterListAdapter.addAll(getFilterList());
                filterListAdapter.notifyDataSetChanged();
                todoFilterElement.setSelection(FilterManager.getSelected(lList));
                refreshListFilter();
            } else {
                switch (pResultCode) {

                    case RES_DELETE_TODO:
                        deleteTodo(pData);
                        break;

                    case RESULT_OK:
                        switch (pRequestCode) {
                            case ACT_NEW_TODO:
                                insertTodo(pData);
                                break;

                            case ACT_SHOW_TODO:
                                refreshList();
                                break;
                            default:
                                super.onActivityResult(pRequestCode,pResultCode,pData);


                        }
                        break;
                    default:
                        super.onActivityResult(pRequestCode,pResultCode,pData);
                }
            }
        }catch(Exception e){
            warning(e.getMessage());
        }
    }

    public void openFilter(@SuppressWarnings("UnusedParameters") View pView)
    {
        Intent lIntent=new Intent(this,FilterActivity.class);
        lIntent.putExtra("projectId",id);
        startActivityForResult(lIntent,ACT_FILTER);
    }


    private void refreshListFilter()
    {
        projectItem=ds.getProjectById(id);
        refreshList();
    }

    /**
     * Refresh the to do list
     */

    private void refreshList()
    {


        adapter.getCursor().close();
        adapter.swapCursor(ds.getTodoCursor(id,notFilter));
        adapter.notifyDataSetChanged();

        setNumNotInFilter();
        showHeader();
    }

    /**
     * On the top of the screen there is a spinner for selecting a filter. When a filter is selected
     * this method is called
     *
     * @param pAdapterView  Adapter of the spinner
     * @param pView The view within the adapter view that was clicked
     * @param pPos  Position selected
     * @param pId   The row id that was selected
     */
    @Override
    public void onItemSelected(@NonNull AdapterView<?> pAdapterView, View pView, int pPos, long pId) {
        if(projectItem != null) {
            if (pAdapterView.getId() == R.id.todoFilter) {
                FilterSelection lSelection = (FilterSelection) pAdapterView.getItemAtPosition(pPos);
                FilterManager.setCurrentFilter(lSelection);
                refreshList();
            } else if(pAdapterView.getId()== R.id.filterType){
                notFilter=pPos ==1;
                refreshList();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //When nothing is selected , nothing should be done..
    }
}
