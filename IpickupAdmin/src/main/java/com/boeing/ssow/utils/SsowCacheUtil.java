package com.boeing.ssow.utils;



import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;



import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * Object Cache Utility.
 * 
 */
@Component
public class SsowCacheUtil {

   // The cache.
   private static GeneralCacheAdministrator cache = new GeneralCacheAdministrator();

   /** The default amount of time to cache (in seconds); set to 8 hours. */
   public final static int CACHE_SECONDS = (60 * 60 * 8 * 30);

   private final static String CONTACT_KEY = "Contact";

 //  private static Logger log = Logger.getLogger(BclCacheUtil.class);

   /** The short amount of time to cache (in seconds); set to 30 minutes. */
   public final static int SHORT_CACHE_SECONDS = (60 * 60 * 8 );

   /**
    * Flushes the specified object from the cache.
    * 
    * @param key
    *           the object's key
    */
   public static void flush(String key) {
      cache.flushEntry(key);
    //  log.debug("[flush] Flushed " + key);
   }

   /**
    * Flushes the entire cache.
    */
   public static void flushAll() {
      cache.flushAll();
   //   log.debug("[flushAll] Flushed the entire cache.");
   }



 
   public static String getRulesListKey() {
	      return "RulesList";
 }
   

   
   public static String getStatusesListKey() {
	      return "StatusesList";
 }
   
   
   public static String getDocumentTypesListKey() {
	      return "DocumentTypesList";
 }
   public static String getCheckListKey() {
	      return "CheckList";
   }

   public static String getSdrlListKey() {
	      return "Sdrl";
}

   
   public static String getAcronymsListKey() {
	      return "Acronyms";
}

   
   public static String getDictionaryListKey() {
	      return "Dictionary";
}

   
   
   public static String getCheckListBasicQKey() {
	      return "CheckListBasicQ";
   }
   
   
   public static String getCheckListAdvancedQKey() {
	      return "CheckListAdvancedQ";
   }

   public static String getAllBuyerSitesKey() {
      return "AllBuyerSites";
      
   }
   
   public static String getAllSourceSystemsKey() {
      return "AllSourceSystems";
      
   }
   /** Returns the key to retrieve the Collection of all String systems from the cache. */
   public static String getAllSystemsKey() {
      return "AllSystems";
   }
   
   public static String getRfqDocumentKey( String Id , String key ) {
      return "RfqDocument" + Id + key;
   }
   
  

   
   
   




  


 
   
   
   /** Returns the key to retrieve the Collection of lower-case String convertible file extensions from the cache. */
   public static String getConvertibleExtensionsKey() {
      return "ConvertibleExtensions";
   }

   /** Returns the key to retrieve the Map of Long Document Type ID to smefsModel.pojo.DocumentType objects from the cache. */
   public static String getDocumentTypeIdToPojoMapKey() {
      return "DocumentTypeIdToPojoMap";
   }

   /** Returns the key to retrieve the Map of LongStringto DocumentType objects from the cache. */
   public static String getDocumentTypeMapKey() {
      return "DocumentTypeMap";
   }

   /** Returns the key to retrieve the Map of String Type Name to DocumentType objects from the cache. */
   public static String getDocumentTypeNameMapKey() {
      return "DocumentTypeNameMap";
   }

   /** Returns the key to retrieve the Map of String Document Type Name to DocumentType POJO objects from the cache. */
   public static String getDocumentTypeNameToPojoMapKey() {
      return "DocumentTypeNameToPojoMap";
   }

   /** Returns the key to retrieve the Collection of all DocumentType objects from the cache. */
   public static String getDocumentTypesKey() {
      return "DocumentTypes";
   }

   /**
    * @return the Key to retrieve the Collection of smefsModel.pojo.DocumentType objects where Select is Allowed.
    */
   public static String getDocumentTypesSelectIsAllowedKey() {
      return "DocumentTypesSelectIsAllowed";
   }

   /** Returns the key to retrieve the Map of String extension to smefsModel.pojo.FileType objects from the cache. */
   public static String getExtensionToFileTypePojoMapKey() {
      return "ExtensionToFileTypePojoMap";
   }

   /** Returns the key to retrieve the Collection of lower-case String faxable file extensions from the cache. */
   public static String getFaxableExtensionsKey() {
      return "FaxableExtensions";
   }

   /** Returns the key to retrieve the String fax XML template from the cache. */
   public static String getFaxXmlTemplateKey() {
      return "FaxXmlTemplate";
   }


   /**
    * Retrieves an object from the cache using the default time-to-live. The default time-to-live is {@link #CACHE_SECONDS}.
    * 
    * @param key
    *           the object's key
    * @return the object from the cache
    * @throws NeedsRefreshException
    *            if the cache needs to be refreshed
    */
   public static Object getFromCache(String key) throws NeedsRefreshException {
      return getFromCache(key, CACHE_SECONDS);
   }

   /**
    * Retrieves an object from the cache using the specified time-to-live.
    * 
    * @param key
    *           the object's key
    * @param seconds
    *           the time-to-live in seconds
    * @return the object from the cache
    * @throws NeedsRefreshException
    *            if the cache needs to be refreshed
    */
   public static Object getFromCache(String key, int seconds) throws NeedsRefreshException {

      // Get the object from the cache.
      if (key.equals("BoeingUsernull"))
         return null;
      
     
      Object obj = null ;
      try {
         cache.cancelUpdate(key);
         obj = cache.getFromCache(key, seconds);
      } catch (NeedsRefreshException e) {
        
      
         throw e;
      }
 
      return obj;
   }

   /**
    * @return the Key to retrieve the Collection of smefsModel.pojo.ProcurementType objects where Select is Allowed.
    */
   public static String getProcurementTypesSelectIsAllowedKey() {
      return "ProcurementTypesSelectIsAllowed";
   }

   /** Returns the key to retrieve the Collection of selectable DocumentType objects from the cache. */
   public static String getSelectableDocumentTypesKey() {
      return "SelectableDocumentTypes";
   }

   /** Returns the key to retrieve the Collection of selectable ProcurementType objects from the cache. */
   public static String getSelectableProcurementTypesKey() {
      return "SelectableProcurementTypes";
   }

   /** Returns the key to retrieve the Collection of lower-case supported String file extensions from the cache. */
   public static String getSupportedExtensionsKey() {
      return "SupportedExtensions";
   }

   /** Returns the key to retrieve the Map of String USER id to BoeingUser obj. */
   public static String getUserIdToBoeingUserMapKey() {
      return "UserIdToBoeingUserMap";
   }

   /**
    * Puts the object in the cache.
    * 
    * @param key
    *           the object's key
    * @param obj
    *           the object to put in the cache
    */
   public static void putInCache(String key, Object obj) {

      boolean updated = false;
      try {
         cache.putInCache(key, obj);
      //   log.debug("[putInCache] Set " + key);
         updated = true;

         // This step is essential: cancel the update if something went wrong.
      } finally {
         if (!updated) {
            cache.cancelUpdate(key);
        //    log.debug("[putInCache] Cancel the update for " + key);
         }
      }
   }
   
   public static void removeFromCache( String key ) {
      
      cache.flushEntry(key);
   }
}
