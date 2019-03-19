package by.courses.java.plugin.checkup.analysis.jcc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodNamePatternTest {

  private Pattern pattern = Pattern.compile("^[a-z][a-zA-Z0-9]*$");

  private List<String> failMethodsName;

  private List<String> methodsName;

  @BeforeEach
  public void setUp() {
    methodsName = new ArrayList<>();
    failMethodsName = new ArrayList<>();

    methodsName.add("InputMethodNameEqualClassName");
    methodsName.add("PRIVATEInputMethodNameEqualClassName");
    methodsName.add("Method");
  }

  @Test
  public void checkNames() {
    for (String methodName : methodsName) {
      if (!pattern.matcher(methodName).find()) {
        failMethodsName.add(methodName);
      }
    }
    assertEquals(3, failMethodsName.size());
  }
}