package com.geophile.erdoindex;

import com.geophile.erdo.DeadlockException;
import com.geophile.erdo.OrderedMap;
import com.geophile.erdo.TransactionRolledBackException;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.SpatialObjectKey;

import java.io.IOException;

class BlindUpdates extends ErdoIndex
{
    // Index interface

    @Override
    public void add(long z, SpatialObject spatialObject) throws IOException, InterruptedException
    {
        SpatialObjectKey key = SpatialObjectKey.key(z, spatialObject.id());
        ErdoIndexRecord record = new ErdoIndexRecord(key, spatialObject);
        try {
            map.ensurePresent(record);
        } catch (DeadlockException | TransactionRolledBackException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean remove(long z, long soid) throws IOException, InterruptedException
    {
        SpatialObjectKey key = SpatialObjectKey.key(z, soid);
        try {
            map.ensureAbsent(new ErdoIndexKey(key));
        } catch (DeadlockException | TransactionRolledBackException e) {
            throw new IOException(e);
        }
        return false;
    }

    // BlindUpdate interface

    BlindUpdates(OrderedMap map)
    {
        super(map, true);
    }
}
