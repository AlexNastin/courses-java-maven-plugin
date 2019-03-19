package by.courses.java.plugin.checkup.analysis;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;

import java.util.ArrayList;
import java.util.List;

public class EmptyCatchProcessor extends AbstractProcessor<CtCatch> {

  private static EmptyCatchProcessor instance = new EmptyCatchProcessor();

  private List<CtCatch> emptyCatches = new ArrayList<>();

  private EmptyCatchProcessor() {
  }

  @Override
  public void process(CtCatch element) {
    if (element.getBody().getStatements().size() == 0) {
      emptyCatches.add(element);
    }
  }

  public static EmptyCatchProcessor getInstance() {
    return instance;
  }

  public List<CtCatch> getEmptyCatches() {
    return emptyCatches;
  }
}