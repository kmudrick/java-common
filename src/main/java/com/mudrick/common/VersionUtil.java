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
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class VersionUtil {
  
  private static final Pattern DOT_PATTERN = Pattern.compile("\\.");
  private static final Pattern NON_DIGIT_PATTERN = Pattern.compile("\\D");

  /**
   * Compares two version strings.  This only considers numeric components to
   * a version - all non-digit components (besides the delimiter - period) will
   * be ignored.  Trailing zero components/pieces will be ignore - 
   * i.e. 3.1.0 is equivalent to 3.1, but 3.10 is greater than 3.1 
   * 
   * @param version1 First version string to compare
   * @param version2 Second version string to compare
   * 
   * @return negative if version1 < version2, 
   *         zero if version1 == version2, 
   *         positive if version1 > version2
   */
  public static int compare(String version1, String version2) {
    return cmp(normalize(version1), normalize(version2));
  }
  
  public static List<Integer> normalize(String version) {
    String[] pieces = DOT_PATTERN.split(StringUtils.trimToEmpty(version));
    List<Integer> parts = new ArrayList<Integer>();
    for (int i=0; i<pieces.length; i++) {
      parts.add(i, NumberUtils.toInt(NON_DIGIT_PATTERN.matcher(pieces[i]).replaceAll("")));
    }
    ListIterator<Integer> iter = parts.listIterator(parts.size());
    while (iter.hasPrevious()) {
      Integer value = iter.previous();
      if (value == 0) {
        iter.remove();
      }
      else {
        break;
      }
    }
    return parts;
  }
  
  // The return value is negative if x < y, zero if x == y and strictly positive if x > y
  // Same idea as Python's cmp builtin
  public static int cmp(List<Integer> x, List<Integer> y) {
    int size = Math.min(x.size(), y.size());
    for (int i=0; i<size; i++) {
      if (x.get(i) == y.get(i)) {
        continue;
      }
      return x.get(i) < y.get(i) ? -1 : 1;
    }
    if (x.size() == y.size()) {
      return 0;
    }
    return x.size() < y.size() ? -1 : 1;
  }

  public static void main(String[] args) {
    printCompare(null, "3.0");
    printCompare("", "3.0");
    printCompare(" ", "3.0");
    printCompare("1", "1");
    printCompare("2", "1");
    printCompare("1", "2");
    printCompare("1.0", "1");
    printCompare("1.0.0", "1");
    printCompare("1.0.1", "1");
    printCompare("3.1", "3.1.0");
    printCompare("3.1.0", "3.10");
    printCompare("3.1", "3.10");
    printCompare("3.1", "3.1.1");
    printCompare("3.1", "3.0.0.2");
    printCompare("3.1", "3.1.0.0.0.0.0.0");
    printCompare("3.1", "3.1.a");
    printCompare("3.1", "3.1a");
  }
  
  private static void printCompare(String version1, String version2) {
    System.out.println("Comparing " + version1 + " vs " + version2 + " => " 
                       + compare(version1, version2));
  }

}
