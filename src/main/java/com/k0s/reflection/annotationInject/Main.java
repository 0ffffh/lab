package com.k0s.reflection.annotationInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


/**- Принимает объект. Поля проаннотиваные аннотацией @Inject
 *  заполняет объектом того класса который находиться в поле аннотации Class clazz().
 * Если поле аннотации содержит ссылку на Void.class.
 * Создает пустой экзепляр класса, базируясь на типе поля (аннотация Ваша, написать самим)
 */

public class Main {
    public static void setAnnotatedFields(Object object) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(Inject.class)){
                Inject inject = field.getAnnotation(Inject.class);

                Class objClazz = inject.clazz();

                if(objClazz.equals(Void.class)){
                    field.setAccessible(true);
//                    Object newObject = field.getDeclaringClass().getDeclaredConstructor().newInstance();
                    Object newObject = field.getType().getDeclaredConstructor().newInstance();
                    field.set(object, newObject);
                } else {
                Object newObject = objClazz.getDeclaredConstructor().newInstance();
                field.setAccessible(true);
                field.set(object, newObject);
                }
            }
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        ClassToInject classToInject = new ClassToInject();

        setAnnotatedFields(classToInject);

        classToInject.printObject(classToInject.o1);
        classToInject.printObject(classToInject.o2);
        classToInject.printObject(classToInject.o3);
        classToInject.printObject(classToInject.o4);

    }
}
