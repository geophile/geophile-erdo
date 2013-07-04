package com.geophile.erdoindex;

import com.geophile.erdo.Database;
import com.geophile.erdo.OrderedMap;
import com.geophile.z.Index;
import com.geophile.z.index.IndexTestBase;

import java.io.File;
import java.io.IOException;

public class ErdoIndexOrdinaryUpdateTest extends IndexTestBase
{
    @Override
    protected Index newIndex() throws IOException, InterruptedException
    {
        deleteRecursively(DB_DIRECTORY);
        Database db = Database.createDatabase(DB_DIRECTORY);
        OrderedMap map = db.createMap("test", ErdoIndexKey.class, ErdoIndexRecord.class);
        return ErdoIndex.withOrdinaryUpdates(map);
    }

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

    private static final File DB_DIRECTORY = new File("/tmp/erdo-index-test");
}
