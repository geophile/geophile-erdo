package com.geophile.erdoindex;

import com.geophile.erdo.OrderedMap;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.Cursor;
import com.geophile.z.index.Record;
import com.geophile.z.index.SpatialObjectKey;

import java.io.IOException;

public class ErdoIndexCursor<SPATIAL_OBJECT extends SpatialObject> extends Cursor<SPATIAL_OBJECT>
{
    // Cursor interface

    @Override
    public Record<SPATIAL_OBJECT> next() throws IOException, InterruptedException
    {
        Record<SPATIAL_OBJECT> record = new Record<>();
        ErdoIndexRecord erdoIndexRecord = (ErdoIndexRecord) erdoCursor.next();
        if (erdoIndexRecord == null) {
            record.setEOF();
        } else {
            record.set(erdoIndexRecord.key().spatialObjectKey().z(), (SPATIAL_OBJECT) erdoIndexRecord.spatialObject());
        }
        return record;
    }

    @Override
    public Record<SPATIAL_OBJECT> previous() throws IOException, InterruptedException
    {
        Record<SPATIAL_OBJECT> record = new Record<>();
        ErdoIndexRecord erdoIndexRecord = (ErdoIndexRecord) erdoCursor.previous();
        if (erdoIndexRecord == null) {
            record.setEOF();
        } else {
            record.set(erdoIndexRecord.key().spatialObjectKey().z(), (SPATIAL_OBJECT) erdoIndexRecord.spatialObject());
        }
        return record;
    }

    @Override
    public void goTo(SpatialObjectKey spatialObjectKey) throws IOException, InterruptedException
    {
        erdoCursor.close();
        erdoCursor = newCursor(spatialObjectKey);
    }

    // ErdoIndexCursor interface

    public ErdoIndexCursor(OrderedMap map) throws IOException, InterruptedException
    {
        this.map = map;
        this.erdoCursor = newCursor(SpatialObjectKey.keyLowerBound(Long.MIN_VALUE));
    }

    // For use by this class

    private com.geophile.erdo.Cursor newCursor(SpatialObjectKey spatialObjectKey) throws IOException, InterruptedException
    {
        return map.cursor(new ErdoIndexKey(spatialObjectKey));
    }

    // Object state

    private final OrderedMap map;
    private com.geophile.erdo.Cursor erdoCursor;
}
