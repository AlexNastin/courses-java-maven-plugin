package by.courses.java.plugin.checkup.analysis.jcc;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtField;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ConstantFieldNameProcessor extends AbstractProcessor<CtField> {

  private Pattern pattern = Pattern.compile("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$");

  private String ignoredNameSerialVersionUID = "serialVersionUID";

  private String ignoredNameSerialPersistentFields = "serialPersistentFields";

  private List<CtField> failConstantNames = new ArrayList<>();

  private static ConstantFieldNameProcessor instance = new ConstantFieldNameProcessor();

  private ConstantFieldNameProcessor() {
  }

  @Override
  public void process(CtField element) {
    if (element.isStatic() && element.isFinal() && !ignoredNameSerialVersionUID.equals(element.getSimpleName()) && !ignoredNameSerialPersistentFields.equals(element.getSimpleName()) && !pattern.matcher(element.getSimpleName()).find()) {
      failConstantNames.add(element);
    }
  }

  public List<CtField> getFailConstantNames() {
    return failConstantNames;
  }

  public static ConstantFieldNameProcessor getInstance() {
    return instance;
  }
}