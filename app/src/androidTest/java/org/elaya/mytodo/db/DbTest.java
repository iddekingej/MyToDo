package org.elaya.mytodo.db;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

/**
 * Created by jeroen on 11/10/17.
 */

public abstract class DbTest {
    protected Context context;
    protected DataSource ds;

    @Before
    public void setupDS()
    {
        context= InstrumentationRegistry.getTargetContext();
        ds=DataSource.makeSource(context);
    }
}
