# GiftMe
GiftMe is basically a website where you can send gifts to people who have created a registry which is either private or public. Registry is basically a list of items which a user needs. In case of private registry, you can see the registry of that person only if it is shared with you.

This website exploits the benefits of service oriented architecture (SOA). Frontend is created in AngularJS and backend uses Java Servlets and Jersey framework for implementation of services and microservices.

Other features like compression of data, ssl/tls(https) and caching mechanisms are also implemented.Caching is implemented using memcached which is an open source tool.

To successfully run the website you need to download angularjs and start the GiftMe-Master. Then run jaxrs-jerser-rest and jsp-servlet-mvc-restclient in different apache tomcat v8.0s.
