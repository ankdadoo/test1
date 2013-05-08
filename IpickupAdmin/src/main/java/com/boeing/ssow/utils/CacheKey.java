package com.boeing.ssow.utils;



import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This object represents a key to a cached object.
 *
 * @author Rebecca Hughes
 * $Id: CacheKey.java,v 1.4 2005/12/20 16:57:27 rhughes Exp $
 */
public class CacheKey {

   private Collection dependentKeys = null;
   private String description = null;
   private Long id = null;
   private String key = null;
   private String suffixName = null;
   private String suffixValue = null;

   /**
    * Constructs a new key to a cached object.
    * @param id the unique id
    * @param description the description
    * @param key the key
    */
   public CacheKey(Long id, String description, String key) {

      this.description = description;
      this.id = id;
      this.key = key;
   }

   /**
    * Constructs a new key to a cached object, including dependent keys.
    * @param id the unique id
    * @param description the description
    * @param key the key
    * @param dependentKeys the collection of String dependent keys
    */
   public CacheKey(
      Long id, String description, String key, Collection dependentKeys) {

      this(id, description, key);
      this.dependentKeys = dependentKeys;
   }

   /**
    * Constructs a new key to a cached object, including the suffix name.
    * @param id the unique id
    * @param description the description
    * @param key the key
    * @param suffixName the name of the key's suffix (such as "User Id" or "Role Id")
    */
   public CacheKey(Long id, String description, String key, String suffixName) {

      this(id, description, key);
      this.suffixName = suffixName;
   }

   // Getter methods.

   /** Returns the collection of String dependent keys. */
   public Collection getDependentKeys() {
      if (null == dependentKeys)
         return Collections.EMPTY_LIST;
      return dependentKeys;
   }

   /** Returns the description. */
   public String getDescription() { return description; }

   /** Returns the unique id. */
   public Long getId() { return id; }

   /** Returns the key. */
   public String getKey() { return key; }

   /** Returns true if the key requires a suffix (such as "User Id" or "Role Id"). */
   public boolean getRequiresSuffix() {
      return (!(StringUtils.isEmpty(suffixName)));
   }

   /** Returns the name of the key's suffix (such as "User Id" or "Role Id"). */
   public String getSuffixName() { return suffixName; }

   /**
    * Returns the value of the key's suffix.
    * <p>For example, if the suffix name is "User Id", the suffix value might be <code>1462246</code>.
    */
   public String getSuffixValue() { return suffixValue; }

   // Setter methods.

   /**
    * Sets the value of the key's suffix.
    * <p>For example, if the suffix name is "User Id", the suffix value might be <code>1462246</code>.
    * @param suffixValue the suffix value
    */
   public void setSuffixValue(String suffixValue) {
      this.suffixValue = suffixValue;
   }

   /**
    * Returns a String representation of this object.
    */
   public String toString() {
      return new ToStringBuilder(this).
         append("dependentKeys", dependentKeys).
         append("description", description).
         append("id", id).
         append("key", key).
         append("suffixName", suffixName).
         append("suffixValue", suffixValue).
         toString();
   }
}

