package com.geophile.erdoindex;

import com.geophile.erdo.AbstractRecord;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.SpatialObjectKey;

import java.nio.ByteBuffer;

public class ErdoIndexRecord extends AbstractRecord<ErdoIndexKey>
{
    // AbstractRecord interface

    @Override
    public void readFrom(ByteBuffer buffer)
    {
        spatialObject.readFrom(buffer);
    }

    @Override
    public void writeTo(ByteBuffer buffer)
    {
        spatialObject.writeTo(buffer);
    }

    @Override
    public ErdoIndexRecord copy()
    {
        return new ErdoIndexRecord(key().spatialObjectKey(), spatialObject);
    }

    // ErdoIndexRecord

    public SpatialObject spatialObject()
    {
        return spatialObject;
    }

    public ErdoIndexRecord(SpatialObjectKey key, SpatialObject spatialObject)
    {
        super(new ErdoIndexKey(key));
        this.spatialObject = spatialObject;
    }

    // Object state

    private final SpatialObject spatialObject;
}
