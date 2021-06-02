LP5 - RMQ using Hybrid One and Fischer-Heun 

Developers:
 - Dhara Patel (dxp190051)
 - Umar Khalid (uxk150630)
 - Reetish Chand (rxg190006)
 - Rohan Vannala (rxv190003)

The zip file contains the following java files:
   - RMQStructure.java
   - RMQSparseTable.java
   - RMQIndexSparseTable.java
   - RMQHrbridOne.java
   - RMQFischerHeun.java
   - RMQDriver.javaa
   - Timer.java

Use the following commands in command line to compile the program:
   - cd into directory containing the package (one level above the package directory,
      rxg190006)
   - javac dxp190006/*.java

Once compiled, to run the SkipListDriver or RedBlackTreeDriver code you can do either of the following:

   java -Xmx36g <package name>/RMQDriver.java <choice> <percent for range size> <n>

   Ex: java -Xmx36g dxp190051/RMQDriver.java 5 20 100000000

Note: - Choice = 4 for Hybrid One and 5 for Fischer Heun

The Report.pdf file contains the report for the second part of this assignment. The report highlights the comparison between Hybrid One approach and Fischer Heun algorithm.




