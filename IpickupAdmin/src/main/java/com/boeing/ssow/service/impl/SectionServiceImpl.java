package com.boeing.ssow.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boeing.ssow.dao.SectionDao;
import com.boeing.ssow.model.Section;
import com.boeing.ssow.model.SectionTreeXref;
import com.boeing.ssow.model.SectionType;
import com.boeing.ssow.service.SectionService;

@Service
public class SectionServiceImpl implements SectionService {
	
	@Autowired
	private  SectionDao sectionDao;


	public  void save ( Object object ) {
		
		sectionDao.save(object);
	}
	
	
	public Object get ( Class className , String pk ) {
		
		
		return sectionDao.get(className, pk);
	}

	
	public int getNextOrderNumber() {
		return sectionDao.getNextOrderNumber();
	}
	
	public String getNextHeaderNumber() {
		return sectionDao.getNextHeaderNumber();
	}
	
	public List<Section> getChildrenSections(String pk) {

		List<Section> tempList = sectionDao.getChildrenSections(pk);

		Set <Section> sectionsSet = new LinkedHashSet<Section>();
		
		// add non-subsection children sections first:
		Iterator listIter = tempList.iterator();
		while (listIter.hasNext()) {
			Section tempSection = (Section) listIter.next(); 
			if (!tempSection.getSectionType().equalsIgnoreCase(SectionType.SUB_SECTION)) {
				sectionsSet.add(tempSection);
			}
		}

		// add subsection children sections second:
		Iterator listIter2 = tempList.iterator();
		while (listIter2.hasNext()) {
			Section tempSection = (Section) listIter2.next(); 
			if (tempSection.getSectionType().equalsIgnoreCase(SectionType.SUB_SECTION)) {
				sectionsSet.add(tempSection);
			}
		}
		
		List<Section> childList = new ArrayList<Section>(sectionsSet);
		return childList;

	}
	
	public List<SectionTreeXref> getParentXrefs(String pk) {
		return sectionDao.getParentXrefs(pk);
	}

	public List getMainSections() {
		return sectionDao.getMainSections();
	}

	public List<Section> getMainSubSections(String sectionNumber) {
		
		List<Section> list = sectionDao.getMainSubSections(sectionNumber);

		Set <Section> sectionsSet = new LinkedHashSet<Section>();
		Set sectionIdsSet = new HashSet();
		Iterator listIter = list.iterator();
		while (listIter.hasNext()) {
			Section tempSection = (Section) listIter.next(); 
			sectionsSet.add(tempSection);
			sectionIdsSet.add(tempSection.getId());
		}
		
		Set <Section> sectionSet2 = getFullSectionsList(sectionsSet, sectionNumber , sectionIdsSet);
		sectionsSet.addAll(sectionSet2);

		List<Section> fullList = new ArrayList<Section>(sectionsSet);
		return fullList;
	}
	
	
	private  Set<Section> orderChildrenSet(Set<Section> childList, String sectionNumberValue) {
		
		Set <Section> sectionsSet = new LinkedHashSet<Section>();
		
		// add non-subsection children sections first:
		Iterator listIter = childList.iterator();
		while (listIter.hasNext()) {
			Section tempSection = (Section) listIter.next(); 
			if (!tempSection.getSectionType().equalsIgnoreCase(SectionType.SUB_SECTION)) {
				sectionsSet.add(tempSection);
			}
		}

		// add subsection children sections second:
		Iterator listIter2 = childList.iterator();
		while (listIter2.hasNext()) {
			Section tempSection = (Section) listIter2.next(); 
			if (tempSection.getSectionType().equalsIgnoreCase(SectionType.SUB_SECTION)) {
				sectionsSet.add(tempSection);
			}
		}
		
		return sectionsSet;
	}
	
	
	private Set<Section> getFullSectionsList(Set<Section> list, String sectionNumberValue , Set sectionIdsSet) {
		
		boolean sectionNumberUpdated = false;
		Set <Section> newList = new LinkedHashSet();
		
		Set<Section> childList = orderChildrenSet(list, sectionNumberValue);
		Iterator iterator = childList.iterator();
		while (iterator.hasNext())
		{
			Section section = (Section) iterator.next();
			
			// NOTE: START NEW NUMBER GENERATOR 			
			if (section.getSectionType().equals(SectionType.INSTRUCTION_SECTION) || 
					section.getSectionType().equals(SectionType.PARAGRAPH_SECTION) ||
					section.getSectionType().equals(SectionType.TABLE_SECTION))
			{
				// add if section is new to the list
				if (!sectionIdsSet.contains(section.getId())) {
					newList.add(section);	
					sectionIdsSet.add(section.getId());
				}
				
			} else if (section.getSectionType().equals(SectionType.HEADER_SECTION)) {

				// add if section is new to the list
				if (!sectionIdsSet.contains(section)) {
					newList.add(section);	
					sectionIdsSet.add(section.getId());

					// ONLY walk children of parents currently in active state 
				    if (section.getActive() == 1) {
						Set<Section> childSections = section.getChildrenSet();
						
						// Walk children of current Header sections:
						if (childSections.size() > 0) {
							newList.addAll(getFullSectionsList(childSections, sectionNumberValue , sectionIdsSet));
						}
				    }
				}
				


			} else if (section.getSectionType().equals(SectionType.SUB_SECTION)) {
				
			
				
				// TEST FOR DUPLICATES BY SECTION ID:
				if (!sectionIdsSet.contains(section.getId())) {
					String newSectionNumberValue = sectionNumberValue + "." + section.getSectionNumber();
					section.setSectionNumber(newSectionNumberValue);
					newList.add(section);	
					sectionIdsSet.add(section.getId());

				    // ONLY walk children of parents currently in active state 
				    if (section.getActive() == 1) {
						Set<Section> childSections = section.getChildrenSet();
						
						// Walk children of current Header sections:
						if (childSections.size() > 0) {
							newList.addAll(getFullSectionsList(childSections, newSectionNumberValue , sectionIdsSet));
						}
				    }

				}
			}
			
		} // while-loop
		return newList;
		
	}
	
	public List<Section> getSectionsByRuleId( String ruleId) {
		
		return sectionDao.getSectionsByRuleId(ruleId);
	}


}
