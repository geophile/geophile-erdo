package com.geophile.erdoindex;

import com.geophile.erdo.Database;
import com.geophile.erdo.OrderedMap;
import com.geophile.z.Index;
import com.geophile.z.space.SpatialIndexTestBase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;

public class ErdoIndexSpatialTest extends SpatialIndexTestBase
{
    @BeforeClass
    public static void beforeClass() throws Exception
    {
        SpatialIndexTestBase.beforeClass();
        deleteRecursively(DB_DIRECTORY);
        database = Database.createDatabase(DB_DIRECTORY);
    }

    @AfterClass
    public static void afterClass() throws IOException, InterruptedException
    {
        database.close();
    }

    @Override
    public Index newIndex() throws IOException, InterruptedException
    {
        ErdoIndexRecordFactory recordFactory = new ErdoIndexRecordFactory(SERIALIZER);
        OrderedMap map = database.createMap(newMapName(), recordFactory);
        return ErdoIndex.withOrdinaryUpdates(map, SERIALIZER);
    }

    @Override
    public void commitTransaction() throws IOException, InterruptedException
    {
        database.commitTransaction();
        database.flush();
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

    private static String newMapName()
    {
        return String.format(MAP_NAME_TEMPLATE, MAP_COUNTER++);
    }

    private static final String MAP_NAME_TEMPLATE = "map_%s";
    private static int MAP_COUNTER = 0;
    private static final File DB_DIRECTORY = new File("/tmp", "erdo-index-spatial-test");
    private static Database database;
}
