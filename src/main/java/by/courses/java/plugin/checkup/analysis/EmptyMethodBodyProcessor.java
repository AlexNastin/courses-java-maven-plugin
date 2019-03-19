package by.courses.java.plugin.checkup.analysis;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import java.util.ArrayList;
import java.util.List;

public class EmptyMethodBodyProcessor extends AbstractProcessor<CtMethod<?>> {

  private static EmptyMethodBodyProcessor instance = new EmptyMethodBodyProcessor();

  private List<CtMethod> emptyMethods = new ArrayList<>();

  private EmptyMethodBodyProcessor() {
  }

  @Override
  public void process(CtMethod<?> element) {
    if (element.getParent(CtClass.class) != null && !element.getModifiers().contains(ModifierKind.ABSTRACT) && element.getBody().getStatements().size() == 0) {
      emptyMethods.add(element);
    }
  }

  public List<CtMethod> getEmptyMethods() {
    return emptyMethods;
  }

  public static EmptyMethodBodyProcessor getInstance() {
    return instance;
  }
}