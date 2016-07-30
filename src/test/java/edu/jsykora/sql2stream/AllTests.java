package edu.jsykora.sql2stream;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({JoinTest.class, OrderByTest.class, SelectTest.class, WhereTest.class, FullTest.class})
public class AllTests {

}
