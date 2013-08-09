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
        return super.toString() + '/' + key.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof ErdoIndexKey)) {
            return false;
        } else if (super.equals(obj)) {
            ErdoIndexKey that = (ErdoIndexKey) obj;
            return this.key.equals(that.key);
        } else {
            return false;
        }
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
        int c = super.compareTo(that);
        if (c == 0) {
            c = key.compareTo(((ErdoIndexKey)that).key);
        }
        return c;
    }


    // AbstractKey interface

    @Override
    public void readFrom(ByteBuffer buffer) throws BufferUnderflowException
    {
        super.readFrom(buffer);
        long z = buffer.getLong();
        long soid = buffer.getLong();
        key = SpatialObjectKey.key(z, soid);
    }

    @Override
    public void writeTo(ByteBuffer buffer) throws BufferOverflowException
    {
        super.writeTo(buffer);
        buffer.putLong(key.z());
        buffer.putLong(key.soid());
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

    public ErdoIndexKey()
    {
        return;
    }

    // Object state

    private SpatialObjectKey key;
}
