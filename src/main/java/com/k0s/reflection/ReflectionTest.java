
package com.k0s.reflection;


import java.lang.reflect.*;

public class ReflectionTest {



    /**Метод принимает класс и возвращает созданный объект этого класса*/
    public static Object createClassObject(Class className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try {
            return className.getDeclaredConstructor().newInstance();
        }
        catch (Exception e){
            System.out.println("the class object was not created");
            System.out.println(e);
        }
        return null;
    }

    public static Object createClassObject(String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try{
            return  Class.forName(String.valueOf(className)).getDeclaredConstructor().newInstance();
        }
        catch (Exception e){
            System.out.println("the class object was not created");
            System.out.println(e);
        }
        return null;
    }

    /**Метод принимает object и вызывает у него все методы без параметров*/
    public static void getObjectAndInvokeNoparamMethods(Object object) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Метод принимает object и вызывает у него все методы без параметров");
        Method[] declaredMethods = object.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.getParameterCount() == 0){
                System.out.println(method);
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    /**Метод принимает object и выводит на экран все сигнатуры методов в который есть final*/
    public static void getObjectAndPrintAllFinalMethods (Object object){
        System.out.println("Метод принимает object и выводит на экран все сигнатуры методов в который есть final");
        Method[] declaredMethods = object.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            if( Modifier.isFinal(method.getModifiers())){
                System.out.println(method);
            }
        }

    }

    /**Метод принимает Class и выводит все не публичные методы этого класса*/
    public static void getClassAndPrintAllNotPublicMethods(Class clazz){
        System.out.println("Метод принимает Class и выводит все не публичные методы этого класса");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if(!Modifier.isPublic(declaredMethod.getModifiers())){
                System.out.println(declaredMethod);
            }
        }
    }

    /**Метод принимает Class и выводит всех предков класса и все интерфейсы которое класс имплементирует*/
    public static void getClassAndPrintAllSuperclasInterface(Class clazz) {
        System.out.println("Метод принимает Class и выводит всех предков класса и все интерфейсы которое класс имплементирует");
        System.out.println(clazz.getName());
        Class superClass = clazz.getSuperclass();
        Class[] interfaces = clazz.getInterfaces();

        for (Class anInterface : interfaces) {
            System.out.println(anInterface);
        }

        while (superClass != null) {
            System.out.println(superClass);
            superClass = superClass.getSuperclass();
        }
    }

    /**Метод принимает объект и меняет всего его приватные поля на их нулевые значение (null, 0, false etc)+*/
    public static void getObjectAndChangeAllPrivateFields(Object object) throws IllegalAccessException {
        System.out.println("Метод принимает объект и меняет всего его приватные поля на их нулевые значение (null, 0, false etc)+");
        Field[] fields = object.getClass().getDeclaredFields();
        Object o = object;
        for (Field field : fields) {
            if(Modifier.isPrivate(field.getModifiers())){
                field.setAccessible(true);
//                if (Modifier.isFinal(field.getModifiers())){
//                    object = null;
//                }

                if (boolean.class.equals(field.getType())){
                    field.set(object, false);
                    System.out.println("Field " + field.getName() + " " + field.getType() + " set to " + field.get(object));
                } else if (field.getType().isPrimitive()){
                    field.set(object, 0);
                    System.out.println("Field " + field.getName() + " " + field.getType() + " set to " + field.get(object));

                } else {
                    field.set(object, null);
                    System.out.println("Field " + field.getName() + " " + field.getType() + " set to " + field.get(object));

                }
            }
        }
    }






    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {


//       Метод принимает класс и возвращает созданный объект этого класса
//        Object obj = createClassObject(Test.class);
        Object obj = createClassObject(A.class);
//        System.out.println("created object of " + obj.getClass());

//        Метод принимает object и вызывает у него все методы без параметров
        getObjectAndInvokeNoparamMethods(obj);

//        Метод принимает object и выводит на экран все сигнатуры методов в который есть final
        getObjectAndPrintAllFinalMethods(obj);

//        Метод принимает Class и выводит все не публичные методы этого класса
//        getClassAndPrintAllNotPublicMethods(Test.class);
        getClassAndPrintAllNotPublicMethods(A.class);


//        Метод принимает Class и выводит всех предков класса и все интерфейсы которое класс имплементирует
//        getClassAndPrintAllSuperclasInterface(Test.class);
        getClassAndPrintAllSuperclasInterface(A.class);


//        Метод принимает объект и меняет всего его приватные поля на их нулевые значение (null, 0, false etc)+
        getObjectAndChangeAllPrivateFields(obj);



    }


}

class Test{
    private int a = 5;

    public void setA(int a) {
        this.a = a;
    }

    public int getA(){
        return this.a;
    }

    private void print(){
        System.out.println("Print a = " + a);
    }

    private final void hello(){
        System.out.println("Hello " + this.getClass());
    }
}

class A {
    public static final int constant = 10;
    public int value;
    private boolean flag;
    double amount;
    protected String name;

//    private int value;
//    private boolean flag;
//    private double amount;
//    private String name;

    public A() {
    }

    public A(String name) {
        this.name = name;
    }

    private void m() {
        System.out.println("m method run");
    }

    private final void m2() {
        System.out.println("m2 final method run");
    }

    public int publicM() {
        return 0;
    }

    @Override
    public String toString() {
        return "A{" +
                "value=" + value +
                ", flag=" + flag +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                '}';
    }

}



