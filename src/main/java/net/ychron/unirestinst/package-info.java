/**
 * Non static implementation of UnirestInst library {@linkplain http:/unirest.io/java}
 * <p>
 * UnirestInst rest client is a fantastic little library but there is one small simplification
 * that did not work well with some of my projects: UnirestInst is a singleton with a single
 * instance for the whole JVM.
 * <p>
 * This tiny package implements the {@link net.ychron.unirestinst.http.UnirestInst} variation,
 * that makes it possible to get multiple instances of UnirestInst per JVM and configure
 * each one separately.
 * <p>
 * The changes done are falling into three categories:
 * <br>
 * a. Addition of instance members for the the three static objects: UnirestInst, Options and HttpClientHelper  
 * b. A lot of constructor changes to pass the instance members 
 * c. Conversion of static methods to instance methods
 * d. Replication of classes from the request body package to solve visibility problems
 * <p>
 * At the momemnt of wriing this, looks like the best option is to release this as a
 * completely separate library that depends on the original UnirestInst.
 * Also there was a decision to rename UnirestInst to UnirestInst to avoid confusion
 * 
 * @author Yiannis Chronakis <jchronakis@gmail.com>
 * 
 */
package net.ychron.unirestinst;
