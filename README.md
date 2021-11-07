# JWebServer
***
## A Java WebServer API

The JWebServer is a Java API that utilizes the integrated sun.httpserver package.
It simplifies the usage of the provided HttpServer.

***
## Features

- [x] Load extensions in form of .class files instead of .html
- [x] An event is being triggered, when accessing contexts.
- [x] Cookies!
- [x] Attach files to a response
- [x] Get a HashMap that contains the given query instead of the plain string, that hasn't been split
- [x] Automatic AccessHandler identification.
- [x] AccessHandler/Contoller Annotation example: ( @AccessContoller("/the/desired/index") )
- [x] Dynamic resource support
- [x] Custom 404 Page
- [x] Improve Cookie-support
- [ ] Add SSL encryption support

## Used APIs

The JWebServer API is also using a number of apis to function properly:

- [JEvent] - An event api fully written in java.
- [lombok] - Automate your logging variables, and much more.
- [org.json] - A reference implementation of a JSON package in Java.
- [HttpServer] - The sun.httpserver provided by default.

## Usage
***
Classify a variable by the type of the "JWebServer" Object
and initialize it with an instanciated object of the class,
with the desired port inbetween the parentheses of the constructor parameters.

#### JWebServer (Object)
```java
//Here's how to create the object. 
//Remember, the port can vary. You can choose it.
JWebServer jWebServer = new JWebServer(9086);

// Register a context:
// the first parameter is a string, which will define the link under which the context is accessible.
// so to access this context, you'd have to open your brower on "http://localhost:9086/this/is/a"
// Once the link is being opened, the handle() method of the AccessHandler defined in the second parameter is triggered:
        jWebServer.registerContext("this/is/a", new TestContext());

// register annotated controllers:
        jWebServer.registerControllers("some.project.pkg");

// Unregister a context:
        jWebServer.unregisterContext("link/to/context");

// start the server:
        jWebServer.start();

// stop the server:
        jWebServer.stop();

// Get the hashmap containinh the registered contexts:
        jWebServer.getHttpContexts();

// Get the hasmap containing the loaded extensions:
        jWebServer.getExtensions();
        
//Set custom 404 page:
        jWebServer.setNotFoundPage(new File("/path/to/404.html"));

// The key to the value of both of the HashMaps mentioned 
// above is same the path for the browser to access the contexts
```

#### Extensions (Object)
You can load extensions with the loadExtensions(); method included in the JWebServer class.
The location of the extensions is being generated on application startup.
The extensions folder is located in the folder of the application file.

Path:
(user.dir/data/www/class/)

```java
// load all extensions located in the path:
        jWebServer.loadExtensions();

// the extension class contains methods for registering the extension, 
// removing and reloading the extension.


```


#### AccessHandler (Object)
The AccessHandler is the essential part, that is accessed in the end.
It includes the code, that is being executed, when calling the website.

```java
// To create an AccessHandler, that you can register in order to execute code,
// you simply have to create a class, that is inheriting from the mentioned class with the "extend" inheritance-keyword.
// Your IDE will prompt you to implement the "handle()" method. Do that.
// Once you have implemented the method, your class should look like in the example.
// The very first line in the handle method must call the method named
// "setHttpExchange" of the super-class, with the parameter shown in the example. otherwhise it won't work, because
// the code that is following up internally, uses the object.

    public class YourCustomHandler extends AccessHandler {
        public void handle(HttpExchange httpExchange) throws IOException {
            setHttpExchange(httpExchange);
            respond(200, "IT WORKED!" + this.getQuery());
        }
    }

//Get the queryMap from the accessed site:
    getQueryMap();

//Example usecase:
    if(getQueryMap().containsKey("token") && getQueryMap().get("token").equals("someAccessToken")){
        respond(200, "Your token is valid. yay. ok. now go on.");
        return;
    } else {
        respond(200, "Your key is invalid..");
    }

//Send a response:
// To send a response, you have to either call the normal respond() method 
// or the fileResponse() method depending on what you want to send.
// The normal respond() method has two parameters, 
// the first is for the http response codes 
// and the second is for the string you want to deliver.
// You can also send html source code through the string and it will be displayed as  
// it's supposed to be.

    respond(200, "<html><h1>This is a header</h1></html>");
    
// Prompts the user to download the file specified.
    fileDownloadResponse(new File(System.getProperty("user.dir") + "/some/file.txt"));
    
// can display the file specified in combination of the setContentType() method.  
    fileResponse(new File(System.getProperty("user.dir") + "/some/image.png"));

// You can also get and set cookies to the response headers

// get cookies by key:
    getCookie("KeyOfCookie");

// set cookie:
    setCookie("KeyOfCookie", "ValueOfCookie");

// remove cookie:
    removeCookie("KeyOfCookie");

// get a list of cookies:
    getCookies();
    
// set content-type of response:
    setContentType(ContentType contentType);
    
    


```


#### ContextAccessedEvent
Listeners of this event are being triggered,
whenever the setHttpHandler method is called.

Look up my project page of the [JEvent] on GitHub to see how to work with it.


***
### Annotations

#### @Register
```
//The @Register class annotation is used to identify classes, that shall be automatically registered,
//when the "registerControllers(String packageNameOfClasses)" has been invoked
//so it's not neccessary anymore to execute the "registerContext(String s, AccessHandler a)" 
//method in the JWebServer class for each class you want to register.

//You must always define the priority of the handler. If the priority is set to high, 
//the already registered module is overwritten and executed instead. 
//If the priority is set to "DEFAULT" or "PRIORITIZED", the defined classes can be overwritten.

This annotation must always come along with the following annotation.

```

#### @AccessController

```
//The @AccessContoller class annotation defines the path 
//under which the registered handler can be accessed.

```

##### Annotated Example

```java
package some.project.pkg;

import com.github.sebyplays.jwebserver.utils.Priority;
import com.github.sebyplays.jwebserver.utils.annotations.AccessController;
import com.github.sebyplays.jwebserver.utils.annotations.Register;

//Define the controllers link
@AccessController(index = "link/of/controller")
//Tell the method to register this class
@Register(priority = Priority.DEFAULT)
public class YourCustomHandler extends AccessHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        setHttpExchange(httpExchange);
        respond(200, "IT WORKED!" + this.getQuery());
    }
}

//So in this example, the webpage can be opened by the browser of your choice with the link 
// http://localhost:8096/link/of/controller
```


## License

MIT

****


[JEvent]: <https://github.com/SebyPlays/JEvent>
[lombok]: <https://projectlombok.org/>
[org.json]: <https://github.com/stleary/JSON-java>
[HttpServer]: <https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html>
