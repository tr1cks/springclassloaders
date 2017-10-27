This project illustrates Spring problem with context loading from parts with different classloaders. Google Guice definitely can do it. Looks like Spring tries to do it in single classloader, that creates AnnotationConfigApplicationContext.

Added workaround for this problem.