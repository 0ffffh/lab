package com.k0s.reflection.annotationRun;

import java.lang.reflect.Method;

public class Main {
/**- Принимает объект и вызывает методы проанотированные аннотацией @Run (аннотация Ваша, написать самим)*/

    public static void runAnnotatedMethods (Object object){
        Method[] methods = object.getClass().getMethods();

        for (Method method : methods) {
            Run runAnnotation = method.getAnnotation(Run.class);
            if (runAnnotation != null) {
                try {
                    method.invoke(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        TestObject testObject = new TestObject();
        runAnnotatedMethods(testObject);

    }
}
