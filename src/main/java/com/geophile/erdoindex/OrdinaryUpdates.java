package com.geophile.erdoindex;

import com.geophile.erdo.DeadlockException;
import com.geophile.erdo.OrderedMap;
import com.geophile.erdo.TransactionRolledBackException;
import com.geophile.z.DuplicateSpatialObjectException;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.SpatialObjectKey;

import java.io.IOException;

class OrdinaryUpdates<SPATIAL_OBJECT extends SpatialObject> extends ErdoIndex<SPATIAL_OBJECT>
{
    // Index interface

    @Override
    public void add(long z, SPATIAL_OBJECT spatialObject) throws IOException, InterruptedException
    {
        SpatialObjectKey key = SpatialObjectKey.key(z, spatialObject.id());
        ErdoIndexRecord record = new ErdoIndexRecord(key, spatialObject);
        try {
            ErdoIndexRecord replaced = (ErdoIndexRecord) map.put(record);
            if (replaced != null) {
                throw new DuplicateSpatialObjectException(replaced.spatialObject());
            }
        } catch (DeadlockException | TransactionRolledBackException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean remove(long z, long soid) throws IOException, InterruptedException
    {
        boolean removed;
        SpatialObjectKey key = SpatialObjectKey.key(z, soid);
        try {
            ErdoIndexRecord removedRecord = (ErdoIndexRecord) map.delete(new ErdoIndexKey(key));
            removed = removedRecord != null;
        } catch (DeadlockException | TransactionRolledBackException e) {
            throw new IOException(e);
        }
        return removed;
    }

    // BlindUpdate interface

    OrdinaryUpdates(OrderedMap map)
    {
        super(map, false);
    }
}
