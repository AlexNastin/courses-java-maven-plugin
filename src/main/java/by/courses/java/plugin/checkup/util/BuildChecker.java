package by.courses.java.plugin.checkup.util;

import spoon.processing.AbstractProcessor;

import java.util.Arrays;
import java.util.List;

public final class BuildChecker {

  private List<AbstractProcessor> processors;

  public BuildChecker(AbstractProcessor<?>... processors) {
    this.processors = Arrays.asList(processors);
  }

  public boolean checkBuild() {
    boolean isCheck = true;
    for (AbstractProcessor processor : processors) {
      isCheck &= ProcessorHandler.handleProcessor(processor);
    }
    return isCheck;
  }
}