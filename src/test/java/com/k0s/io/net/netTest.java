package com.k0s.io.net;

import com.k0s.io.net.iostream.Client;
import com.k0s.io.net.iostream.Server;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class netTest {

    @Before
    public void listen() {
        Server server = new Server();
        server.listen();


    }

    @Test
    void main() {
        Client client = new Client();
        String msg = "Hello world";
        assertEquals("echo: " + msg, client.sendMessage(msg));
    }
}