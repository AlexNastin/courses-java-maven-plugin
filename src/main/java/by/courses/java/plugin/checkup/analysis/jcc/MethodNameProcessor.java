package by.courses.java.plugin.checkup.analysis.jcc;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MethodNameProcessor extends AbstractProcessor<CtMethod<?>> {

  private Pattern pattern = Pattern.compile("^[a-z][a-zA-Z0-9]*$");

  private static MethodNameProcessor instance = new MethodNameProcessor();

  private List<CtMethod> nameFailMethods = new ArrayList<>();

  @Override
  public void process(CtMethod<?> element) {
    if (element.getParent(CtClass.class) != null && !pattern.matcher(element.getSimpleName()).find()) {
      nameFailMethods.add(element);
    }
  }

  public List<CtMethod> getFailNameMethods() {
    return nameFailMethods;
  }

  public static MethodNameProcessor getInstance() {
    return instance;
  }

  private MethodNameProcessor() {
  }
}