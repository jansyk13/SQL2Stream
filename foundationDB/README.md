# FoundationDB SQL Parser

## Overview

The FoundationDB SQL Parser is a complete, production-quality Java parser for
the SQL language. It defines the SQL grammar as implemented by the
[FoundationDB SQL Layer](http://github.com/FoundationDB/sql-layer) but can be
used independently. It is derived from the Apache Derby parser.


## Building From Source

[Maven](http://maven.apache.org) is used to build, test and deploy.

Run tests, build jars and copy to local Maven repository:

```sh
$ mvn install
```

Generate the documentation:

```sh
$ mvn javadoc:javadoc
```

The resulting HTML files are in `target/site/apidocs/`.


## Using From Maven

The SQL Parser is in the standard Maven Central repository. Any Maven based
project can use it directly by adding the appropriate entries to the
`dependencies` section of its `pom.xml` file:

```xml
<dependencies>
  <dependency>
    <groupId>com.foundationdb</groupId>
    <artifactId>fdb-sql-parser</artifactId>
    <version>1.6.1</version>
  </dependency>
</dependencies>
```


## Using From Binaries

Packaged jars can be downloaded directly from the
[Releases page](https://github.com/foundationdb/sql-parser/releases)
and extracted using `tar` or `unzip`.


## Working With The SQL Parser

A simple example:

```java
import com.foundationdb.sql.parser.SQLParser;
import com.foundationdb.sql.parser.StatementNode;

public class ParserHello {
    public static void main(String[] args) throws Exception {
        SQLParser parser = new SQLParser();
        for(String s : args) {
            StatementNode stmt = parser.parseStatement(s);
            stmt.treePrint();
        }
    }
}
```

A new [SQLParser](http://foundationdb.github.io/sql-parser/com/foundationdb/sql/parser/SQLParser.html)
is instantiated and each command line argument is
[parsed](http://foundationdb.github.io/sql-parser/com/foundationdb/sql/parser/SQLParser.html#parseStatement%28java.lang.String%29)
and [printed](http://foundationdb.github.io/sql-parser/com/foundationdb/sql/parser/QueryTreeNode.html#treePrint%28%29)
to standard output. The result is a debug dump of all nodes in the underlying Abstract Syntax Tree.
More advanced usages will generally parse a statement and then pass a custom
[Visitor](http://foundationdb.github.io/sql-parser/com/foundationdb/sql/parser/Visitor.html) to the
[accept()](http://foundationdb.github.io/sql-parser/com/foundationdb/sql/parser/QueryTreeNode.html#accept%28com.foundationdb.sql.parser.Visitor%29) method.

To try the example from the command line, copy the code into a file named
`ParserHello.java`. Then compile and run it, making sure to include the
parser in the `classpath`.

Compile:

```sh
$ javac -cp fdb-sql-parser-1.6.1.jar ParserHello.java
```

Run (output trimmed):

```sh
$ java -cp fdb-sql-parser-1.6.1.jar:. ParserHello "SELECT a FROM b"
com.foundationdb.sql.parser.CursorNode@5889dee2
statementType: SELECT
resultSet:
    com.foundationdb.sql.parser.SelectNode@4387f4d7
    resultColumns:
        [0]:
        com.foundationdb.sql.parser.ResultColumn@5123968
        name: a
        expression:
            com.foundationdb.sql.parser.ColumnReference@6f76dd71
            columnName: a
    fromList:
        [0]:
        com.foundationdb.sql.parser.FromBaseTable@18317b1d
        tableName: b
```


## Contributing

1. Fork
2. Branch
3. Commit
4. Pull Request

Thanks! Please make sure any changes come with new tests.


## Contact

* Community: http://community.foundationdb.com
* IRC: #FoundationDB on irc.freenode.net


## License

Apache License, Version 2.0
Copyright (c) 2012-2015 FoundationDB, LLC
It is free software and may be redistributed under the terms specified
in the LICENSE.xt and NOTICE files.

