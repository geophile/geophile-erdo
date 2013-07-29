package com.geophile.erdoindex;

import com.geophile.erdo.AbstractKey;
import com.geophile.erdo.AbstractRecord;
import com.geophile.erdo.RecordFactory;
import com.geophile.z.Serializer;

public class ErdoIndexRecordFactory extends RecordFactory
{
    // RecordFactory interface

    @Override
    public AbstractKey newKey()
    {
        return new ErdoIndexKey();
    }

    @Override
    public AbstractRecord newRecord()
    {
        return new ErdoIndexRecord(serializer);
    }

    // ErdoIndexRecordFactory interface

    public ErdoIndexRecordFactory(Serializer serializer)
    {
        this.serializer = serializer;
    }

    // Object state

    private final Serializer serializer;
}
