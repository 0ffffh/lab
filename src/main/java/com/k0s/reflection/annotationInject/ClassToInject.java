package com.k0s.reflection.annotationInject;

public class ClassToInject {
    @Inject(clazz = A.class)
    public Object o1;

    @Inject(clazz = B.class)
    public Object o2;

    @Inject
    public Object o3;

    @Inject
    public C o4;

    public void printObject(Object o) {
        System.out.println(o.getClass());
    }

}

class A {}
class B {}
class C {}

