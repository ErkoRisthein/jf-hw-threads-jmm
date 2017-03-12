package org.zeroturnaround.jf;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult2;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@JCStressTest
@Description("Both actors increase the value of i by 1")
@Outcome(id = "[1, 1]", expect = ACCEPTABLE_INTERESTING,  desc = "actor1 and actor2 ran in shuffle")
@Outcome(id = "[1, 2]", expect = ACCEPTABLE,  desc = "actor1 ran fully, then actor2")
@Outcome(id = "[2, 1]", expect = ACCEPTABLE,  desc = "actor2 ran fully, then actor1")
@State
public class JFHW6E3 {

  int i;

  @Actor
  public void actor1(IntResult2 result) {
    i++;
    result.r1 = i;

  }

  @Actor
  public void actor2(IntResult2 result) {
    i++;
    result.r2 = i;
  }

  /*
    Observed state	Occurrence	Expectation	Interpretation
    [1, 1]	11374935	ACCEPTABLE_INTERESTING	actor1 and actor2 ran in shuffle
    [1, 2]	24209720	ACCEPTABLE	actor1 ran fully, then actor2
    [2, 1]	25187985	ACCEPTABLE	actor2 ran fully, then actor1

    The "++" operator is not atomic and is actually done in 2 operations:
    1. read the current value of i
    2. increment the value and assign it to i
    */

}
