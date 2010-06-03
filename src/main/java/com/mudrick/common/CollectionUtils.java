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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtils {

  /**
   * Returns the first key (by whatever iteration order is maintained by
   * the implementation of the given map) who maps to the given value.  
   * Returns null if there are no keys that map to the given value.
   */
  public static <K, V> K findKeyByValue(Map<K, V> map, V value) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      if (entry.getValue().equals(value)) {
        return entry.getKey();
      }
    }
    return null;
  }
  
  public static <T> List<T> createList(T... elements) {
    int presize = elements == null ? 0 : elements.length;
    List<T> list = new ArrayList<T>(presize);
    if (presize > 0) {
      for (T element : elements) {
        list.add(element);
      }
    }
    return list;
  }
  
  public static <T> Set<T> createSet(T... elements) {
    int presize = elements == null ? 0 : elements.length;
    Set<T> set = new HashSet<T>(presize);
    if (presize > 0) {
      for (T element : elements) {
        set.add(element);
      }
    }
    return set;
  }
  
  public static boolean isEmpty(Map<?, ?> map) {
    return (map == null || map.isEmpty());
  }
  
  public static boolean isEmpty(Collection<?> collection) {
    return (collection == null || collection.isEmpty());
  }
  
  /**
   * Truncates a list to match a desired pagination scheme
   *  
   * @param list Total list of elements
   * @param pageID Page number, zero-indexed
   * @param size Number of elements per page
   * 
   * @return A subset of the original total list matching the given page.  
   * <em>Note</em>: the resultant list in most cases is <em>not</em> 
   * going to be serializable.
   */
  public static <T> List<T> truncateList(List<T> list, int pageID, int size) {
    int fromIndex = pageID * size;
    int toIndex = fromIndex + size;
    if (toIndex > list.size()) {
      toIndex = list.size();
    }
    if (fromIndex >= list.size()) {
      return Collections.<T>emptyList();
    }
    return list.subList(fromIndex, toIndex);
  }
  
  public static <T> List<T> safelyTruncate(List<T> list, int size) {
    if (list == null || list.size() == 0) {
      return list;
    }
    int toIndex = Math.min(size, list.size());
    return list.subList(0, toIndex);
  }
  
}
