package com.github.sebyplays.jwebserver.testHandler;

import com.github.sebyplays.jwebserver.AccessHandler;
import com.github.sebyplays.jwebserver.utils.Priority;
import com.github.sebyplays.jwebserver.utils.annotations.AccessController;
import com.github.sebyplays.jwebserver.utils.annotations.Register;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

@AccessController(index = "test")
@Register(priority = Priority.DEFAULT)
public class BasicFunctionalityTest extends AccessHandler {
    @Override
    public void handle(HttpExchange httpExchange){
        if(setHttpExchange(httpExchange)) return;
        respond(200, "It's working so far!");
    }
}
