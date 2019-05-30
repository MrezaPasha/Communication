package main.java.Rasad._core;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class GenericProtobuf<T> {

    public <T> T getObject(byte[] serialized, Class c) throws Exception {
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(c);
        T objectParsed = (T) schema.newMessage();
        ProtostuffIOUtil.mergeFrom(serialized, objectParsed, schema);
        return objectParsed;

    }

    public byte[] getByte(T object) {

        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(object.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        final byte[] protostuff;
        try {
            protostuff = ProtostuffIOUtil.toByteArray((T) object, schema, buffer);
        } finally {
            buffer.clear();
        }
        return protostuff;

    }
}
