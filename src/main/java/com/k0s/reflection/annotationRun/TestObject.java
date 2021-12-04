package com.k0s.reflection.annotationRun;

public class TestObject {

    public void m1() {
        System.out.println("method1");
    }

    @Run
    public void m2() {
        System.out.println("method2");
    }

    public void m3() {
        System.out.println("method3");
    }

    @Run
    public void m4() {
        System.out.println("method4");
    }

    @Run
    public void m5() {
        System.out.println("method5");
    }

}
