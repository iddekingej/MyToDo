package org.elaya.mytodo.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

/**
 * Base class for a DataSource tests
 */

public abstract class DbTest {
    DataSource ds;

    @Before
    public void setupDS()
    {
        Context lContext= InstrumentationRegistry.getTargetContext();
        ds=DataSource.makeSource(lContext);
    }
}
