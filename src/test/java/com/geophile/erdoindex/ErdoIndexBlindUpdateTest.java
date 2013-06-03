package com.geophile.erdoindex;

import com.geophile.erdo.Database;
import com.geophile.erdo.OrderedMap;
import com.geophile.z.Index;
import com.geophile.z.index.IndexTestBase;
import com.geophile.z.index.TestSpatialObject;

import java.io.File;
import java.io.IOException;

public class ErdoIndexBlindUpdateTest extends IndexTestBase
{
    @Override
    protected Index<TestSpatialObject> newIndex() throws IOException, InterruptedException
    {
        deleteRecursively(DB_DIRECTORY);
        Database db = Database.createDatabase(DB_DIRECTORY);
        OrderedMap map = db.createMap("test", ErdoIndexKey.class, ErdoIndexRecord.class);
        return ErdoIndex.withBlindUpdates(map);
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