package org.zeroturnaround.jf;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult2;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@JCStressTest
@Description("The first actor writes to variable b and reads from variable a, " +
  "and the second actor writes to variable a and reads from variable b")
@Outcome(id = "[0, 0]", expect = ACCEPTABLE_INTERESTING,  desc = "should not happen intuitively")
@Outcome(id = "[0, 1]", expect = ACCEPTABLE,  desc = "actor1 ran fully, then actor2")
@Outcome(id = "[1, 0]", expect = ACCEPTABLE,  desc = "actor2 ran fully, then actor1")
@Outcome(id = "[1, 1]", expect = ACCEPTABLE,  desc = "actor1 and actor2 ran in shuffle")
@State
public class JFHW6E1 {

//  volatile
  int a;
//  volatile
  int b;

  @Actor
  public void actor1(IntResult2 result) {
    b = 1;
    result.r1 = a;
  }

  @Actor
  public void actor2(IntResult2 result) {
    a = 1;
    result.r2 = b;
  }

  /*
    Observed state	Occurrence	Expectation	Interpretation
    [0, 1]	30232722	ACCEPTABLE	actor1 ran fully, then actor2
    [1, 0]	34402666	ACCEPTABLE	actor2 ran fully, then actor1
    [1, 1]	120	ACCEPTABLE	actor1 and actor2 ran in shuffle
    [0, 0]	14827122	ACCEPTABLE_INTERESTING	should not happen intuitively

    Classic "visibility" problem where updates from one thread are not visible to the other thread.
    This is due to CPU caching the main memory. Declaring the variables a and b as "volatile" would
    guarantee the visibility for other threads of writes to those variables.
    The [0, 0] combination would not happen in that case.
    */

}
