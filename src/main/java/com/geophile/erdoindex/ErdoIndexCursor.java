package com.geophile.erdoindex;

import com.geophile.erdo.OrderedMap;
import com.geophile.z.Serializer;
import com.geophile.z.index.Cursor;
import com.geophile.z.index.Record;
import com.geophile.z.index.SpatialObjectKey;

import java.io.IOException;

public class ErdoIndexCursor extends Cursor
{
    // Cursor interface

    @Override
    public Record next() throws IOException, InterruptedException
    {
        Record record = new Record();
        ErdoIndexRecord erdoIndexRecord = (ErdoIndexRecord) erdoCursor.next();
        if (erdoIndexRecord == null) {
            record.setEOF();
        } else {
            record.set(erdoIndexRecord.key().spatialObjectKey().z(), erdoIndexRecord.spatialObject());
        }
        return record;
    }

    @Override
    public Record previous() throws IOException, InterruptedException
    {
        Record record = new Record();
        ErdoIndexRecord erdoIndexRecord = (ErdoIndexRecord) erdoCursor.previous();
        if (erdoIndexRecord == null) {
            record.setEOF();
        } else {
            record.set(erdoIndexRecord.key().spatialObjectKey().z(), erdoIndexRecord.spatialObject());
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

    public ErdoIndexCursor(ErdoIndex erdoIndex) throws IOException, InterruptedException
    {
        this.map = erdoIndex.map;
        this.serializer = erdoIndex.serializer;
        this.erdoCursor = newCursor(SpatialObjectKey.keyLowerBound(Long.MIN_VALUE));
    }

    // For use by this class

    private com.geophile.erdo.Cursor newCursor(SpatialObjectKey spatialObjectKey) throws IOException, InterruptedException
    {
        return map.cursor(new ErdoIndexKey(spatialObjectKey));
    }

    // Object state

    private final OrderedMap map;
    private final Serializer serializer;
    private com.geophile.erdo.Cursor erdoCursor;
}
