package by.courses.java.plugin.checkup.analysis.jcc;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LocalFiledNameProcessor extends AbstractProcessor<CtField<?>> {

  private Pattern pattern = Pattern.compile("^[a-z][a-zA-Z0-9]*$");

  private static LocalFiledNameProcessor instance = new LocalFiledNameProcessor();

  private LocalFiledNameProcessor() {
  }

  private List<CtField> failFiledNames = new ArrayList<>();

  @Override
  public void process(CtField<?> element) {
    if (element.getParent(CtClass.class) != null && !element.isFinal() && !pattern.matcher(element.getSimpleName()).find()) {
      failFiledNames.add(element);
    }
  }

  public List<CtField> getFailFiledNames() {
    return failFiledNames;
  }

  public static LocalFiledNameProcessor getInstance() {
    return instance;
  }
}