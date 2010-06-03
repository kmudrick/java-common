package com.mudrick.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.lang.reflect.Modifier;

public class ReflectionUtil {

  private ReflectionUtil() {
    // Private constructor
  }
  
  public static Set<Field> getAllFields(Class<?> clazz) {
    return getAllFields(clazz, null);
  }
  
  public static Set<Field> getAllFields(Class<?> clazz, Class<?> stopClass) {
    Set<Field> fields = new HashSet<Field>();
    populateAllFields(fields, clazz, stopClass);
    return fields;
  }
  
  public static Set<Field> getAllAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation) {
    Set<Field> fields = new HashSet<Field>();
    populateAllAnnotatedFields(fields, clazz, annotation);
    return fields;
  }
  
  public static <P, C extends P> void copyFromParent(C child, Object parent) {
    List<Field> fields = new ArrayList<Field>();
    populateAllFields(fields, parent.getClass(), null);
    for (Field field : fields) {
      if (Modifier.isFinal(field.getModifiers())) {
        continue;
      }
      field.setAccessible(true);
      try {
        field.set(child, field.get(parent));
      } catch (IllegalArgumentException e) {
        throw new RuntimeException("Could not copy property", e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Could not copy property", e);
      }
    }
  }
  
  private static void populateAllFields(Collection<Field> fields, Class<?> clazz, Class<?> stopClass) {
    fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
    if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(stopClass)) {
      populateAllFields(fields, clazz.getSuperclass(), stopClass);
    }
  }
  
  private static void populateAllAnnotatedFields(Collection<Field> fields, Class<?> clazz, Class<? extends Annotation> annotation) {
    for (Field field : clazz.getDeclaredFields()) {
      if (field.isAnnotationPresent(annotation)) {
        fields.add(field);
      }
    }
    if (clazz.getSuperclass() != null) {
      populateAllAnnotatedFields(fields, clazz.getSuperclass(), annotation);
    }
  }
}
