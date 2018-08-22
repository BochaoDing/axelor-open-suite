package com.axelor.apps.base.test;

import com.axelor.app.AxelorModule;
import com.axelor.apps.base.module.AdminModule;
import com.axelor.apps.base.module.BaseModule;
import com.axelor.apps.base.service.user.UserService;
import com.axelor.apps.base.test.UserServiceTest.MyModule;
import com.axelor.apps.message.module.MessageModule;
import com.axelor.apps.tool.module.ToolModule;
import com.axelor.inject.Beans;
import com.axelor.test.GuiceModules;
import com.axelor.test.GuiceRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GuiceRunner.class)
@GuiceModules({MyModule.class})
public class UserServiceTest {

  static UserService userService;

  public static class MyModule extends AxelorModule {

    @Override
    protected void configure() {
      bind(Beans.class).asEagerSingleton();
      install(new ToolModule());
      install(new MessageModule());
      install(new AdminModule());
      install(new BaseModule());
    }
  }

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    userService = Beans.get(UserService.class);
  }

  @Test
  public void testMatchPasswordPatternUpperLowerDigit() {
    Assert.assertTrue(userService.matchPasswordPattern("Axelor123"));
    Assert.assertTrue(userService.matchPasswordPattern("123Axelor"));
    Assert.assertTrue(userService.matchPasswordPattern("axelor123A"));
  }
  
  @Test
  public void testMatchPasswordPatternUpperLowerSpecial() {
    Assert.assertTrue(userService.matchPasswordPattern("Axelor12"));
    Assert.assertTrue(userService.matchPasswordPattern("123Axelor"));
    Assert.assertTrue(userService.matchPasswordPattern("axelor123A"));
  }
  
  @Test
  public void testMatchPasswordPatternLowerSpecialDigit() {
    Assert.assertTrue(userService.matchPasswordPattern(";axelor12"));
    Assert.assertTrue(userService.matchPasswordPattern("axelor12?"));
    Assert.assertTrue(userService.matchPasswordPattern("axelor123A"));
  }
  
  @Test
  public void testMatchPasswordPatternUpperSpecialDigit() {
    Assert.assertTrue(userService.matchPasswordPattern("AXELOR12!"));
    Assert.assertTrue(userService.matchPasswordPattern("123!AXELOR"));
    Assert.assertTrue(userService.matchPasswordPattern(";XELOR123"));
  }

  @Test
  public void testNotMatchPasswordPattern() {
    Assert.assertFalse(userService.matchPasswordPattern("Xlr1!"));
    Assert.assertFalse(userService.matchPasswordPattern("AxelorAxelor"));
    Assert.assertFalse(userService.matchPasswordPattern("axelor123456"));
  }

}