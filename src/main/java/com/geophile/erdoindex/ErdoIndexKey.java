package com.geophile.erdoindex;

import com.geophile.erdo.AbstractKey;
import com.geophile.z.index.SpatialObjectKey;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class ErdoIndexKey extends AbstractKey
{
    // Object interface

    @Override
    public String toString()
    {
        return key.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof ErdoIndexKey && key.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return key.hashCode();
    }


    // Comparable interface

    @Override
    public int compareTo(AbstractKey that)
    {
        return key.compareTo(((ErdoIndexKey)that).key);
    }


    // AbstractKey interface

    @Override
    public void readFrom(ByteBuffer byteBuffer) throws BufferUnderflowException
    {
        long z = byteBuffer.getLong();
        long soid = byteBuffer.getLong();
        key = SpatialObjectKey.key(z, soid);
    }

    @Override
    public void writeTo(ByteBuffer byteBuffer) throws BufferOverflowException
    {
        byteBuffer.putLong(key.z());
        byteBuffer.putLong(key.soid());
    }

    @Override
    public int estimatedSizeBytes()
    {
        return 16;
    }

    @Override
    public AbstractKey copy()
    {
        return new ErdoIndexKey(key);
    }

    // ErdoIndexKey interface

    public SpatialObjectKey spatialObjectKey()
    {
        return key;
    }

    public ErdoIndexKey(SpatialObjectKey key)
    {
        this.key = key;
    }

    // Object state

    private SpatialObjectKey key;
}
