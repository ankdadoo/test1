package com.boeing.ssow.persistence.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.persister.entity.EntityPersister;

/**
 * Used by Hibernate event listeners to determine the index of a property
 *
 * @author os666b
 */
public class ListenerUtil {
    private static Log log = LogFactory.getLog(ListenerUtil.class);

    public static int determineIndex(String propertyName, String[] propertyNames) {

        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyNames[i].equals(propertyName)) {
                if (log.isDebugEnabled()) {
                    log.debug(propertyName + " index is " + i);
                }
                return i;
            }
        }

        log.warn("Did not find an index for " + propertyName);
        log.warn("returning - 1");
        return -1;
    }

    public static int determineIndex(String propertyName, EntityPersister persister) {
        return determineIndex(propertyName, persister.getPropertyNames());
    }
}
