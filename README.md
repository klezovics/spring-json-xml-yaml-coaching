# spring-json-xml-yaml-coaching

This repository covers the key subset of functionality
described here: https://www.baeldung.com/jackson

OK, so let's transform between objects and json ...

O -> J -> serialisation. Converting DS in RAM to text
J -> O -> deserialisation. Converting text to DS in RAM

OK, so let's take a closer look at JSON syntax

1. Simplest possible JSON
{}

2. Let's add a field in here

{
  "name": "Arthur"
}

3. Let's add another field

{
  "name": "Arthur",
  "age": 31,
}

4. Hmm ... what kinds of data can we store in json
We can have
- strings
- numbers
- booleans
- nulls
- arrays
- objects

{
  "name": "Arthur",
  "age": 31,
  "isProgrammer": true,
  "enemies": null,
  "likes": ["ice-cream", "cats"],
  "education": {
     "school": "Riga secondary school #40",
     "university": "MIPT"
  }
}


So, OK, let's think about how can we transform this into a java object.

jsonKeys -> field names
jsonKeyValues -> field values

In java this object can be something like

class Person {

  String name;
  int age;
  boolean isProgrammer;
  List<String> likes;
  Education education;

  static class Education {
     String school;
     String university;
  }
}

And OK, this kind of completes our base case ...
This is like a nice clean example of how we can do a 1-to-1 mapping between
JSON and a Java object. Note, that you can freely convert between the two.

OK, now let's think about how we might want to change this standard
behaviour during serialisation and deserialisation.

== Serealisation use cases ==
1. Just write out the full object
2. Do not serialize some secret fields 
  2.1 Always @JsonIgnore
  2.2 Based on some condition - filters, views
  2.3 Do not deserealize nulls
3. Control serialisation process
  3.1 Custom serealizer
  3.2 @JsonValue
4. Flatten objects
  4.1 Flatten a map
  4.2 Flatten a nested object
5. Control property order
6. Generate classes from JsonSchema
7. Control date format during deserealisation
8. Merge objects to allow for overrides

== Deserialisation use cases ==
1. Rename property values or configure multiple property names.
2. Ignore unknown properties
3. How to deserialise an object into a map
4. Use a specific constructor during deserialisaiton
5. Use property value injections.
6. Do some transformations on the data during deserialisation

== Both == 
1. How to make a field serealizable, but not deserealizable