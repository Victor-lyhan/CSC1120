# Week 1 - Week1.Week3.Week4.HW - Explanation

## Overview
* An enum in Java is a special type of class that represents a fixed set of constants 
* Color enum defines an enum that represents several ANSI escape codes for colors

## Enum Declaration

```
public enum Color {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");
```
* Each constant (RESET, BLACK, RED, etc.) is an instance of the Color enum. When you declare **RESET("\u001B[0m")**, you’re assigning an ANSI escape code string ("\u001B[0m") to the RESET enum constant.

## Private Field

```
private final String ansi;
```
* The private field ansi stores the ANSI escape code associared with each color
* Noticed it's a final? 
  * It's because its value cannot be changed after it's initialized as you are not supposed to change the color of the ansi
  * You will just create a new **Color.RED** when new colors are needed after **Color.RESET**

## Constructor

```
Color(String ansi) {
    this.ansi = ansi;
}
```
* Each enum constant calls this constructor when it is created
* assigns the input (RED, BLUE, etc.) to the private field ansi
  * When you use **Color.RED**, you are actually referring to the constant - predefined instances
  * When the **Color.RED** constant is defined, **Color(String ansi)** is called with the string **"\u001B[31m"** as the argument.
  * The constructor takes the ANSI escape code string (**not RED**) and stores it in the ansi field.
 
## toString() Method

```
@Override
public String toString() {
    return ansi;
}
```
* Returns the **ansi** private field for color coding strings and outputs
* **@Override** is used to indicate that this method is overriding a method from a superclass
  * Here, the method is defined in Object class (the superclass of all Java classes)
