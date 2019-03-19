package by.courses.java.plugin.checkup.analysis.jcc;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StaticFiledNameProcessor extends AbstractProcessor<CtField> {

  private Pattern pattern = Pattern.compile("^[a-z][a-zA-Z0-9]*$");

  private static StaticFiledNameProcessor instance = new StaticFiledNameProcessor();

  private StaticFiledNameProcessor() {
  }

  private List<CtField> failStaticFiledNames = new ArrayList<>();

  @Override
  public void process(CtField element) {
    if (element.getParent(CtClass.class) != null && element.isStatic() && !element.isFinal() && !pattern.matcher(element.getSimpleName()).find()) {
      failStaticFiledNames.add(element);
    }
  }

  public List<CtField> getFailStaticFiledNames() {
    return failStaticFiledNames;
  }

  public static StaticFiledNameProcessor getInstance() {
    return instance;
  }
}
