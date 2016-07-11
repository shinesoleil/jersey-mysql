package com.thoughtworks;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FirstTest {
  @Test
  public void should_be_one() {
    assertThat(1, is(1));
  }
}
