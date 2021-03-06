package com.geophile.erdoindex;

import com.geophile.erdo.AbstractRecord;
import com.geophile.erdo.DeadlockException;
import com.geophile.erdo.OrderedMap;
import com.geophile.erdo.TransactionRolledBackException;
import com.geophile.z.DuplicateSpatialObjectException;
import com.geophile.z.Serializer;
import com.geophile.z.SpatialObject;
import com.geophile.z.index.SpatialObjectKey;

import java.io.IOException;

class OrdinaryUpdates extends ErdoIndex
{
    // Index interface

    @Override
    public void add(long z, SpatialObject spatialObject) throws IOException, InterruptedException
    {
        SpatialObjectKey key = SpatialObjectKey.key(z, spatialObject.id());
        ErdoIndexRecord record = new ErdoIndexRecord(serializer, key, spatialObject);
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

    OrdinaryUpdates(Serializer serializer, OrderedMap map)
    {
        super(serializer, map, false);
    }
}
