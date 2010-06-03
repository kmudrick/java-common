// Copyright 2009-2010 severally by the contributors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
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
