package com.geophile.erdoindex;

import com.geophile.erdo.Database;
import com.geophile.erdo.OrderedMap;
import com.geophile.z.Index;
import com.geophile.z.Serializer;
import com.geophile.z.space.SpatialIndexTestBase;
import com.geophile.z.spatialobject.d2.Box;
import com.geophile.z.spatialobject.d2.Point;

import java.io.File;
import java.io.IOException;

public class ErdoIndexSpatialTest extends SpatialIndexTestBase
{
    @Override
    public Index newIndex() throws IOException, InterruptedException
    {
        ErdoIndexRecordFactory recordFactory = new ErdoIndexRecordFactory(SERIALIZER);
        Database db = Database.createDatabase(DB_DIRECTORY);
        OrderedMap map = db.createMap(MAP_NAME, recordFactory);
        return ErdoIndex.withBlindUpdates(map, serializer());
    }

    private static Serializer serializer()
    {
        Serializer serializer = Serializer.newSerializer();
        serializer.register(1, Point.class);
        serializer.register(2, Box.class);
        return serializer;
    }

    private static final String MAP_NAME = "erdo-index-spatial-test";
    private static final File DB_DIRECTORY = new File("/tmp", MAP_NAME);
}
