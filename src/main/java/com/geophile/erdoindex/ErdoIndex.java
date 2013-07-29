package com.geophile.erdoindex;

import com.geophile.erdo.OrderedMap;
import com.geophile.z.Index;
import com.geophile.z.Serializer;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.SpatialObjectKey;

import java.io.IOException;

/**
 * ErdoIndex implementat Geophile's Index abstraction using the Erdo key/value store.
 */

public abstract class ErdoIndex implements Index
{
    // Index interface

    @Override
    public final boolean blindUpdates()
    {
        return blindUpdates;
    }

    @Override
    public final ErdoIndexCursor cursor(long z) throws IOException, InterruptedException
    {
        ErdoIndexCursor cursor = new ErdoIndexCursor(this);
        cursor.goTo(SpatialObjectKey.keyLowerBound(z));
        return cursor;
    }

    @Override
    public final SpatialObjectKey key(long z)
    {
        return SpatialObjectKey.keyLowerBound(z);
    }

    @Override
    public final SpatialObjectKey key(long z, long soid)
    {
        return SpatialObjectKey.key(z, soid);
    }

    @Override
    public abstract void add(long z, SpatialObject spatialObject) throws IOException, InterruptedException;

    @Override
    public abstract boolean remove(long z, long soid) throws IOException, InterruptedException;

    // ErdoIndex interface

    /**
     * Create an Erdo-based Index that does non-blind updates.
     * @param map The underlying Erdo map.
     * @return An Erdo-based index.
     */
    public static  ErdoIndex withOrdinaryUpdates(OrderedMap map, Serializer serializer)
    {
        return new OrdinaryUpdates(serializer, map);
    }

    /**
     * Create an Erdo-based Index that does blind updates.
     * @param map The underlying Erdo map.
     * @return An Erdo-based index.
     */
    public static ErdoIndex withBlindUpdates(OrderedMap map, Serializer serializer)
    {
        return new BlindUpdates(serializer, map);
    }

    // For use by subclasses

    protected ErdoIndex(Serializer serializer, OrderedMap map, boolean blindUpdates)
    {
        this.serializer = serializer;
        this.map = map;
        this.blindUpdates = blindUpdates;
    }

    // Object state

    protected Serializer serializer;
    protected final OrderedMap map;
    private final boolean blindUpdates;
}
