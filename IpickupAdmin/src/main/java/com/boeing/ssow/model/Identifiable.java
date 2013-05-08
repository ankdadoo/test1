package com.boeing.ssow.model;

import java.io.Serializable;

/**
 * Class for Objects that return an Id field.
 * The rules for the Id field are that the Id should be unique for a given implementation class.
 * Subclasses and implementations may provide a globally unique id (unique regardless of the class)
 */
public interface Identifiable {

  public Serializable getId();

}
