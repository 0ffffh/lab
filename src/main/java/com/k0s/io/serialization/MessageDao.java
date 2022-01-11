package com.k0s.io.serialization;

import java.io.IOException;
import java.io.InputStream;

public interface MessageDao {
    void save(Message message) throws IOException;

    Message load(InputStream inputStream) throws IOException, ClassNotFoundException;
}