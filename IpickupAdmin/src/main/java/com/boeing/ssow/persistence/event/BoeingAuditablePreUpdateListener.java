package com.boeing.ssow.persistence.event;

import static com.boeing.ssow.model.Auditable.LAST_MODIFIED_BY_PROPERTY;
import static com.boeing.ssow.model.Auditable.LAST_MODIFIED_DATE_PROPERTY;
import static com.boeing.ssow.persistence.event.ListenerUtil.determineIndex;
import static org.apache.commons.logging.LogFactory.getLog;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

import com.boeing.ssow.model.Auditable;

/**
 * Updates the lastUpdated* fields of @{Link Auditable} classes with audit information.  created* fields
 * should be handled by another Hibernate listener
 * In order to configure this in your hibernate config you would do the following
 * <code>
 *  <event type="pre-update">
 *       <listener class="com.boeing.epic.common.persistence.event.AuditablePreUpdateListener"/>
 *  </event>
 * </code>
 *
 * @version $Revision: 1.3 $
 */
public class BoeingAuditablePreUpdateListener implements PreUpdateEventListener {

   private static final long serialVersionUID = 4151194537333068286L;

   protected Log log = getLog(getClass());

	public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
		if (preUpdateEvent.getEntity() instanceof Auditable) {
            EntityPersister persister = preUpdateEvent.getPersister();
			int byIndex = determineIndex(Auditable.LAST_MODIFIED_BY_PROPERTY, persister);
			int dateIndex = determineIndex(Auditable.LAST_MODIFIED_DATE_PROPERTY, persister);

			if (log.isDebugEnabled()) {
				log.debug("Index for " + Auditable.LAST_MODIFIED_BY_PROPERTY + " = " + byIndex);
				log.debug("Index for " + Auditable.LAST_MODIFIED_DATE_PROPERTY + " = " + dateIndex);
			}

			Object[] state = preUpdateEvent.getState();
			Auditable auditable = (Auditable) preUpdateEvent.getEntity();

			if (byIndex > -1) {
//				EpicUser auth = null; 
//				if (SecurityContextHolder.getContext() != null &&
//						SecurityContextHolder.getContext().getAuthentication() != null) {
//					auth = (EpicUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//				}
//				if (auth != null) {
//					auditable.setLastModifiedBy(auth.getUsername());
//					state[byIndex] = auth.getUsername();
//				} else {
//					// should not happen at least from the web side
//					// may happen in batch, or if people use SQL
//					auditable.setLastModifiedBy(EpicUser.EPIC_APP_BEMS_ID);
//					state[byIndex] = EpicUser.EPIC_APP_BEMS_ID;
//				}
			}

			if (dateIndex > -1) {
				auditable.setLastModifiedDate(new Date());
				state[dateIndex] = auditable.getLastModifiedDate();
			}

			if (log.isDebugEnabled()) {
				log.debug("Set lastUpdated information for " + auditable.getClass().getSimpleName() + ":" + preUpdateEvent.getId());
				log.debug(LAST_MODIFIED_BY_PROPERTY + " = " + auditable.getLastModifiedBy());
				log.debug(LAST_MODIFIED_DATE_PROPERTY + " = " + auditable.getLastModifiedDate());
			}
		}
		return false;
	}
}
