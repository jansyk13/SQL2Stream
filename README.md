# SQL2Stream

Bachelor thesis at University of Economics in Prague led by Ing. Rudolf Pecinovský, CSc (Opponent: Ing. Vladimír Oraný). Thesis has been succesfully defended in 2015. Full text in Czech can be found as pdf file in repository.

### Title
Data processing library using SQL queries
### Abstract
Eighth version of programming language Java brought broad variety of changes, which strongly incline to declarative style of programming. In my thesis I have focused on using these changes in declarative ways of data processing on Java platform. I have decided on using Structured Query Language due to its declarativity Topic of my thesis was to create a library, which could interpret SQL queries. For interpretation, my library uses Streams and many other concepts which allow functional style of programming in Java. The most significant benefit of this library was giving alternative to programmers, who are looking for ways, how process data in application layer rather than on persistent layer.
### Key words
SQL, Streams, Java 8, declarative programming, interpret

## Tutorial and examples of usage
For my library I decided to take advantage of tree structure when dealing with tuples. I have defined several classes and interfaces for that and used composite design pattern. This library supports joins, reststrictions and ordering.

#### Input
SQL querry as input into library is supplies in String form. Iterables are provided in `InputContainer<T>`, constructor has two arguments, `Class<T>` and `Iterable<T>`. User can give up to four containers as input.

#### Output
As an output of main method `interpret` my library offers two possibilities. Either return `ResultIterator<BaseElement<T>>`, where iterator supplies programmer with tuples as degenerate tree or if user provided a mapping function `ResultIterator<T>`, where T is result from the function.

#### Possible syntax of SQL querry
```
SELECT [*|<attribute>]*?
FROM [<collection_name>|<class_name> <alias>] 
[JOIN <collection_name>|<class_name> <alias> ON <alias>.<attritbute><operator><alias><attribute>] *? (max 3)
WHERE <condition> [(AND|OR) <condition>]*?
ORDER BY <attribute> [ASC|DESC]*?
```

### Examples
```
String sql = "SELECT * FROM list1 t";
SQL2Stream<TestingModel> test = new SQL2Stream<>(sql,
new InputContainer<>(list1, TestingModel.class));
ResultSupplier<BaseElement<TestingModel>> result = test.interpret();

String sql = "SELECT t.name FROM list1 t WHERE t.number>2";
SQL2Stream<TestingModel> test = new SQL2Stream<>(sql,
new InputContainer<>(list1, TestingModel.class));
ResultSupplier<BaseElement<String>> result = test.interpret();

String sql = "SELECT * FROM list WHERE number='1' AND name='test' OR
name='test2'";
```
