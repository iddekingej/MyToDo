package org.elaya.mytodo.tools;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.elaya.mytodo.db.DataSource;
import org.elaya.mytodo.R;

/**
 * The Base activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected DataSource ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResource());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ds=DataSource.makeSource(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        getMenuInflater().inflate(getMenuResource(), pMenu);
        return true;
    }

    protected abstract int getContentResource();
    protected abstract int getMenuResource();

    private void back()
    {
        setResult(RESULT_CANCELED);
        finish();

    }

    @Nullable
    protected String getHelpName()
    {
        return null;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        if (pItem.getItemId() == R.id.help) {
            String lHelp=getHelpName();
            if(lHelp!=null) {
                Helpers.openHelp(this, lHelp);
            }
            return true;
        } else if(pItem.getItemId() == R.id.back){
            back();
        }
        return super.onOptionsItemSelected(pItem);

    }
}
