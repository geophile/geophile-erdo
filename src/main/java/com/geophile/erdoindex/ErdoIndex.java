package com.geophile.erdoindex;

import com.geophile.erdo.*;
import com.geophile.z.Index;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.*;

import java.io.IOException;

/**
 * ErdoIndex implementat Geophile's Index abstraction using the Erdo key/value store.
 *
 * @param <SPATIAL_OBJECT> The type of spatial object contained in this index.
 */

public abstract class ErdoIndex<SPATIAL_OBJECT extends SpatialObject> implements Index<SPATIAL_OBJECT>
{
    // Index interface

    @Override
    public final boolean blindUpdates()
    {
        return blindUpdates;
    }

    @Override
    public final ErdoIndexCursor<SPATIAL_OBJECT> cursor(long z) throws IOException, InterruptedException
    {
        ErdoIndexCursor<SPATIAL_OBJECT> cursor = new ErdoIndexCursor<>(map);
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
    public abstract void add(long z, SPATIAL_OBJECT spatialObject) throws IOException, InterruptedException;

    @Override
    public abstract boolean remove(long z, long soid) throws IOException, InterruptedException;

    // ErdoIndex interface

    /**
     * Create an Erdo-based Index that does non-blind updates.
     * @param map The underlying Erdo map.
     * @param <SPATIAL_OBJECT> The type of objects to be stored in the index.
     * @return An Erdo-based index.
     */
    public static <SPATIAL_OBJECT extends SpatialObject> ErdoIndex<SPATIAL_OBJECT> withOrdinaryUpdates(OrderedMap map)
    {
        return new OrdinaryUpdates<>(map);
    }

    /**
     * Create an Erdo-based Index that does blind updates.
     * @param map The underlying Erdo map.
     * @param <SPATIAL_OBJECT> The type of objects to be stored in the index.
     * @return An Erdo-based index.
     */
    public static <SPATIAL_OBJECT extends SpatialObject> ErdoIndex<SPATIAL_OBJECT> withBlindUpdates(OrderedMap map)
    {
        return new BlindUpdates<>(map);
    }

    // For use by subclasses

    protected ErdoIndex(OrderedMap map, boolean blindUpdates)
    {
        this.map = map;
        this.blindUpdates = blindUpdates;
    }

    // Object state

    protected final OrderedMap map;
    private final boolean blindUpdates;
}
