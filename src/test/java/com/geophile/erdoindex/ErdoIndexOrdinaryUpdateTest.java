package com.geophile.erdoindex;

import com.geophile.erdo.OrderedMap;
import com.geophile.z.Index;

import java.io.IOException;

public class ErdoIndexOrdinaryUpdateTest extends ErdoIndexTestBase
{
    @Override
    protected Index newIndex() throws IOException, InterruptedException
    {
        OrderedMap map = database.createMap("test", new ErdoIndexRecordFactory(SERIALIZER));
        return ErdoIndex.withOrdinaryUpdates(map, SERIALIZER);
    }
}
