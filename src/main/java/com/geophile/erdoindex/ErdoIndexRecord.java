package com.geophile.erdoindex;

import com.geophile.erdo.AbstractRecord;
import com.geophile.z.Serializer;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.SpatialObjectKey;

import java.nio.ByteBuffer;

public class ErdoIndexRecord extends AbstractRecord<ErdoIndexKey>
{
    // AbstractRecord interface

    @Override
    public void readFrom(ByteBuffer buffer)
    {
        super.readFrom(buffer);
        spatialObject = serializer.deserialize(buffer);
    }

    @Override
    public void writeTo(ByteBuffer buffer)
    {
        super.writeTo(buffer);
        serializer.serialize(spatialObject, buffer);
    }

    @Override
    public ErdoIndexRecord copy()
    {
        return new ErdoIndexRecord(serializer, key().spatialObjectKey(), spatialObject);
    }

    // ErdoIndexRecord

    public SpatialObject spatialObject()
    {
        return spatialObject;
    }

    public ErdoIndexRecord(Serializer serializer, SpatialObjectKey key, SpatialObject spatialObject)
    {
        super(new ErdoIndexKey(key));
        this.serializer = serializer;
        this.spatialObject = spatialObject;
    }

    public ErdoIndexRecord(Serializer serializer)
    {
        this.serializer = serializer;
    }

    // Object state

    private final Serializer serializer;
    private SpatialObject spatialObject;
}
