package org.zeroturnaround.jf;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.BooleanResult2;

import java.util.BitSet;

import static org.openjdk.jcstress.annotations.Expect.*;

@JCStressTest
@Description("The first actor sets the first bit to true, " +
  "and the second actor sets the second bit to true")
@Outcome(id = "[false, false]", expect = FORBIDDEN,  desc = "should not happen")
@Outcome(id = "[false, true]", expect = ACCEPTABLE_INTERESTING,  desc = "second actor overwrote the first actor's write: atomicity failure")
@Outcome(id = "[true, false]", expect = ACCEPTABLE_INTERESTING,  desc = "first actor overwrote the second actor's write: atomicity failure")
@Outcome(id = "[true, true]", expect = ACCEPTABLE,  desc = "one actor executed fully and then the other one")
@State
public class JFHW6E2 {

  BitSet bitSet = new BitSet();

  @Actor
  public void actor1() {
    bitSet.set(0);
  }

  @Actor
  public void actor2() {
    bitSet.set(1);
  }

  //  If you need to observe and transform the state after all the actors have run, use an @Arbiter method

  @Arbiter
  public void arbiter(BooleanResult2 r) {
    r.r1 = bitSet.get(0);
    r.r2 = bitSet.get(1);
  }

  /*
    Observed state	Occurrence	Expectation	Interpretation
    [false, false]	0	FORBIDDEN	should not happen
    [false, true]	243097	ACCEPTABLE_INTERESTING	second actor overwrote the first actor's write: atomicity failure
    [true, false]	239799	ACCEPTABLE_INTERESTING	first actor overwrote the second actor's write: atomicity failure
    [true, true]	53342184	ACCEPTABLE	one actor executed fully and then the other one

    BitSet is not thread safe without external synchronization. The set() method calls are not atomic, i.e. they could
    overwrite the previous method call's assignment because they use multiple CPU instructions
    (as an example, it could be something like:
      1. read the current value from the memory address
      2. increment it
      3. assign the new value back to the memory address)
    */

}
