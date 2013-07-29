package com.geophile.erdoindex;

import com.geophile.erdo.Database;
import com.geophile.z.index.IndexTestBase;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

public abstract class ErdoIndexTestBase extends IndexTestBase
{
    @Before
    public void before() throws IOException, InterruptedException
    {
        deleteRecursively(DB_DIRECTORY);
        database = Database.createDatabase(DB_DIRECTORY);
    }


    @Override
    protected void commit()
    {
        try {
            database.commitTransaction();
            database.consolidateAll();
        } catch (IOException | InterruptedException e) {
            fail();
        }
    }

    @Override
    protected void shutdown() throws IOException, InterruptedException
    {
        database.close();
    }

    private static final File DB_DIRECTORY = new File("/tmp/erdo-index-test");
    private static void deleteRecursively(File root)
    {
        if (root.isDirectory()) {
            for (File file : root.listFiles()) {
                deleteRecursively(file);
            }
        } else {
            root.delete();
        }
    }


    protected Database database;
}
