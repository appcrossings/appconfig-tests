package com.appcrossings.configtest;

import org.springframework.beans.factory.annotation.Value;

public class SampleClass {


  public String someValue1;


  public String someValue2;


  public String someValue4;


  public String bonus1;

  public String someOtherValue;

  public String getSomeValue4() {
    return someValue4;
  }

  public String getBonus1() {
    return bonus1;
  }

  @Value("${bonus.1.property:none}")
  public void setBonus1(String bonus1) {
    this.bonus1 = bonus1;
  }

  @Value("${property.4.name:none}")
  public void setSomeValue4(String someValue4) {
    this.someValue4 = someValue4;
  }

  public String getSomeOtherValue() {
    return someOtherValue;
  }


  public String getSomeValue1() {
    return someValue1;
  }

  @Value("${property.1.name:none}")
  public void setSomeValue1(String someValue1) {
    this.someValue1 = someValue1;
  }


  public String getSomeValue2() {
    return someValue2;
  }

  @Value("${property.2.name:none}")
  public void setSomeValue2(String someValue2) {
    this.someValue2 = someValue2;
  }


  public void setSomeOtherValue(String someOtherValue) {
    this.someOtherValue = someOtherValue;
  }


}
