Java Fundamentals - 2017, Tallinn. Homework 6 - Threads and Java Memory Model
===========

This homework compiles for me on Java 8.

```
java -version
java version "1.8.0_91"
Java(TM) SE Runtime Environment (build 1.8.0_91-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.91-b14, mixed mode)
```

Build the executable jar:
```
mvnw clean install
```

Run the jcstress test, change the pattern given as the *-t* argument to specify which tests to execute.
The command below will execute all tests that start with JFHW7.
```
java -Xbootclasspath/a:./ext-lib/jcstress-core.jar:./ext-lib/jopt-simple.jar:./target/homework6-0.0.1-SNAPSHOT.jar org.openjdk.jcstress.Main -t=".*JFHW6"
```

or the following one if you're on Windows (I think, because I don't have a Windows machine nearby, in case it still doesn't work, check the [documentation](https://docs.oracle.com/cd/E15289_01/doc.40/e15062/optionx.htm#i1021218):
```
java -Xbootclasspath/a:./ext-lib/jcstress-core.jar;./ext-lib/jopt-simple.jar;./target/homework6-0.0.1-SNAPSHOT.jar org.openjdk.jcstress.Main -t=".*JFHW6"
```


**NB. this complicated way of running is because jcstress master is set to require Java 9 minimum now. So we are using a bit older build.


Check the results in the "results" dir.
Copy the relevant portion of the HTML result table into the comment in the source file of the test and explain the details of why did it happen this way.

Check the JFHW6E1.java for more details on what code is necessary for the tests.

Add the tests the homework requires you to, execute them, experiment with it. Have fun :)
I mean since the harness tells you what results were achieved it's hard to make mistakes here.
 If you need inspiration check out the existing tests in the [jcstress repo](http://hg.openjdk.java.net/code-tools/jcstress/).


Good luck!


Consult the original readme on how to submit the homework. I just basically need your .java files and your student number. But submit it properly,
then the probability of me missing the emails is smaller.

Cheers,
Oleg!


Here are the descriptions of the tests you have to create:

JFHW6E1.java
-------
```
Shared memory: int a; int b

Thread 1: b = 1; x = a;
Thread 2: a = 1; y = b;
```

**Question**: what values of x, y can be observed at the end?

Devise the test, run it, explain the outcome in the source file!!

JFHW6E2.java
--------
```
Shared memory: an instance of java.util.BitSet

Thread 1: sets 0th bit of the BitSet to true
Thread 2: sets 1st bit of the BitSet to true
```

**Question**: what values of 0th and 1st bits in the bitset can be observed at the end?  
Devise the test, run it, explain the outcome in the source file!!

JFHW6E3.java
--------

Come up with a description of the test that shows non-trivial executions.
If a result of the test shows interesting reorderings, the properties of the volatiles, or something you think is worth showing.

Create a test and explain it.

Good luck!


Submitting your assignment
--------------------------

For your convenience, we have set up the Maven project to ZIP up all files in your project folder so it is easy for you to attach it to an e-mail and send it our way. All you need to do is to execute the following command in your project folder:

```
./mvnw clean deploy
```

It will ask you for your full name, Student Book Number (also known as *matrikli number*) and a comment (optional).

Example:

```bash
./mvnw clean deploy

#...skipping building, testing and packaging output from Maven...

[INFO] --- maven-antrun-plugin:1.8:run (package homework ZIP) @ homework4 ---
[INFO] Executing tasks

main:
Your full name (e.g. John Smith):
Jane Smith
Your Student Book Number (matrikli number, e.g. B12345):
B12345
Comment:
      [zip] Building zip: /Users/jane/workspace/jf-skeleton/target/jf-homework6-B12345.zip
   [delete] Deleting: /Users/jane/workspace/jf-skeleton/homework.properties
[INFO] Executed tasks
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 20.041 s
[INFO] Finished at: 2017-02-03T11:35:11+02:00
[INFO] Final Memory: 21M/283M
[INFO] ------------------------------------------------------------------------
```

After Maven has finished, you can find the generated ZIP file in *target* folder with name such as
*jf-homework6-B12345.zip* (it contains your Student Book Number/matrikli number and homework number).

The only thing left to do now is to send the ZIP file as an attachment to an e-mail with subject **"Homework 6 - *your Student Code/maktrikli number*"** to *jf@zeroturnaround.com*.
