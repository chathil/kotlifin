package com.chathil.kotlifin.data.model.session

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object ActiveSessionSerializer : Serializer<ActiveSessionProto> {

    override val defaultValue: ActiveSessionProto
        get() = ActiveSessionProto()

    override suspend fun readFrom(input: InputStream): ActiveSessionProto {
        return ActiveSessionProto.ADAPTER.decode(input)
    }

    override suspend fun writeTo(t: ActiveSessionProto, output: OutputStream) {
        ActiveSessionProto.ADAPTER.encode(output, t)
    }
}
