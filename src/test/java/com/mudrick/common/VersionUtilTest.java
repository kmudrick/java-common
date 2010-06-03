package com.mudrick.common;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VersionUtilTest {
  
  @Test
  public void testIt() {
    assertTrue(VersionUtil.compare(null, "3.0") < 0);
    assertTrue(VersionUtil.compare("", "3.0") < 0);
    assertTrue(VersionUtil.compare(" ", "3.0") < 0);
    assertTrue(VersionUtil.compare("1", "1") == 0);
    assertTrue(VersionUtil.compare("2", "1") > 0);
    assertTrue(VersionUtil.compare("1", "2") < 0);
    VersionUtil.compare("1.0", "1");
    VersionUtil.compare("1.0.0", "1");
    VersionUtil.compare("1.0.1", "1");
    VersionUtil.compare("3.1", "3.1.0");
    VersionUtil.compare("3.1.0", "3.10");
    VersionUtil.compare("3.1", "3.10");
    VersionUtil.compare("3.1", "3.1.1");
    VersionUtil.compare("3.1", "3.0.0.2");
    VersionUtil.compare("3.1", "3.1.0.0.0.0.0.0");
    VersionUtil.compare("3.1", "3.1.a");
    VersionUtil.compare("3.1", "3.1a");
  }
  
}
