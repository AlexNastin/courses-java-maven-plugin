package by.courses.java.plugin.checkup.analysis.jcc;

import by.courses.java.plugin.checkup.util.SpoonFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spoon.reflect.declaration.CtNamedElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConstantFieldNameProcessorTest {

  private SpoonFacade spoonFacade = new SpoonFacade();

  private ConstantFieldNameProcessor constantFieldNameProcessor = ConstantFieldNameProcessor.getInstance();

  private final String PATH = "src/test/java/com/netcracker/courses/plugin/checkup/analysis/constanttest";

  private List<String> checkFailConstantNames = new ArrayList<>();

  @BeforeEach
  public void init() {
    spoonFacade.start(Collections.singletonList(PATH), constantFieldNameProcessor);

    checkFailConstantNames.add("mPublic");
    checkFailConstantNames.add("mProtected");
    checkFailConstantNames.add("mPackage");
    checkFailConstantNames.add("mPrivate");
    checkFailConstantNames.add("_public");
    checkFailConstantNames.add("_protected");
    checkFailConstantNames.add("_package");
    checkFailConstantNames.add("_private");
    checkFailConstantNames.add("data");
    checkFailConstantNames.add("data");
    checkFailConstantNames.add("badConstant");
    checkFailConstantNames.add("BAD__NAME");
  }

  @Test
  public void test() {
    List<String> failConstantNames = constantFieldNameProcessor.getFailConstantNames().stream().map(CtNamedElement::getSimpleName).collect(Collectors.toList());
    assertNotNull(failConstantNames);
    assertArrayEquals(checkFailConstantNames.toArray(), failConstantNames.toArray());
  }
}