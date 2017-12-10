# Problem of loading Spring context parts in different ClassLoaders

This project illustrates Spring problem with context loading from parts with different classloaders. With Google Guice it's not a problem. That's why you can't add plugins functionality to your Spring project so easy and must use huge, complex OSGi framework. Looks like it because Spring tries to do all in single classloader, that created AnnotationConfigApplicationContext.

I added workaround for this problem.