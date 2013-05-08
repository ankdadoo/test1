package com.boeing.ssow.persistence.event;

import static com.boeing.ssow.model.Auditable.CREATED_BY_PROPERTY;
import static com.boeing.ssow.model.Auditable.CREATED_DATE_PROPERTY;
import static com.boeing.ssow.persistence.event.ListenerUtil.determineIndex;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

import com.boeing.ssow.model.Auditable;

/**
 * Updates the fields of @{Link Auditable} classes with audit information.  lastUpdated*
 * should be handled by another Hibernate listener
 *
 * In order to configure this in your hibernate config you would do the following
 * <code>
 *  <event type="pre-insert">
 *      <listener class="com.boeing.epic.common.persistence.event.AuditablePreInsertListener"/>
 *  </event>
 * </code>
 *
 * @version $Revision: 1.3 $
 */
public class BoeingAuditablePreInsertListener implements PreInsertEventListener {

   private static final long serialVersionUID = -5314426017133541643L;

   protected Log log = LogFactory.getLog(getClass());

	public boolean onPreInsert(PreInsertEvent preInsertEvent) {
		if (preInsertEvent.getEntity() instanceof Auditable) {

            EntityPersister persister = preInsertEvent.getPersister();
			int byIndex = determineIndex(CREATED_BY_PROPERTY, persister);
			int dateIndex = determineIndex(CREATED_DATE_PROPERTY, persister);

			if (log.isDebugEnabled()) {
				log.debug("Index for " + CREATED_BY_PROPERTY + " = " + byIndex);
				log.debug("Index for " + CREATED_DATE_PROPERTY + " = " + dateIndex);
			}

			Object[] state = preInsertEvent.getState();
			Auditable auditable = (Auditable) preInsertEvent.getEntity();

			if (byIndex > -1) {
//				EpicUser auth = null;
//				if (SecurityContextHolder.getContext() != null &&
//						SecurityContextHolder.getContext().getAuthentication() != null) {
//					auth = (EpicUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//				}
//				if (auth != null) {
//					auditable.setCreatedBy(auth.getUsername());
//					state[byIndex] = auth.getUsername();
//				} else {
//					// should not happen at least from the web side
//					// may happen in batch jobs, or if people use SQL
//					auditable.setCreatedBy(EpicUser.EPIC_APP_BEMS_ID);
//					state[byIndex] = EpicUser.EPIC_APP_BEMS_ID;
//				}
			}

			if (dateIndex > -1) {
				auditable.setCreatedDate(new Date());
				state[dateIndex] = auditable.getCreatedDate();
			}

			if (log.isDebugEnabled()) {
				log.debug("Set created information for " + auditable.getClass().getSimpleName() + ":" + preInsertEvent.getId());
				log.debug("CreatedBy = " + auditable.getCreatedBy());
				log.debug("CreatedDate = " + auditable.getCreatedDate());
			}
		}
		return false;
	}
}
