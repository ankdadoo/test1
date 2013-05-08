package com.boeing.ssow.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.boeing.ssow.model.ProgramFile;
import com.boeing.ssow.model.SectionType;
import com.boeing.ssow.model.SectionW;
import com.boeing.ssow.model.Ssow;
import com.boeing.ssow.model.User;

@Component
public class SsowWordUtil {

	
	public static  File  processWordDocument(ProgramFile programFile , Ssow ssow , ArrayList sectionList ) throws ZipException, IOException, SAXException, ParserConfigurationException , Exception{
		ArrayList returnlist = getWordSectionsList();
		String filename="test";
		 return createDoc(sectionList,filename ,programFile , ssow);
	}
	
	private static File createDoc(ArrayList returnlist, String filename , ProgramFile programFile , Ssow ssow) {
		String[] tmp;
		
		//System.out.println("**** list size " + returnlist.size());
		
		String doc =null,br="<w:p><w:r></w:r></w:p>",input=null;
		try {
			for(int i=0;i<returnlist.size();i++){
				
				String sectionText = ((SectionW)returnlist.get(i)).getSectionText();
				sectionText = StringEscapeUtils.escapeXml(sectionText);
				//	String result = yourString.replaceAll("[-+.^:,]","");
					//String alphaOnly = input.replaceAll("[^a-zA-Z]+","");
				//	 sectionText = sectionText.replaceAll("[-+.^:,'-_]","");
				//sectionText = sectionText.replaceAll("[^a-zA-Z 0-9&&]+","");
				//System.out.println("**** section id " +  ((SectionW)returnlist.get(i)).getSectionNumber() + "Section Text   " + sectionText );
				//System.out.println("** section text " + sectionText);
				
				input=markUp(sectionText);
				
		//		System.out.println("**** input " + input);
				if(((SectionW)returnlist.get(i)).getSectionType().equals("H")){
					//System.out.println("Header");
					doc+="<w:p><w:pPr><w:pStyle w:val=\"Heading2\"/></w:pPr><w:r><w:rPr><w:sz w:val=\"28\"/></w:rPr><w:t>"+((SectionW)returnlist.get(i)).getSectionNumber()+" "+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("I")){
					//System.out.println("Instruction");
					doc+="<w:p><w:r><w:t>"+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("P")){
					//System.out.println("Paragraph");
					doc+="<w:p><w:r><w:t>"+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("S")){
					//System.out.println("Sub Section");
					doc+="<w:p><w:pPr><w:pStyle w:val=\"Heading3\"/></w:pPr><w:r><w:t>"+((SectionW)returnlist.get(i)).getSectionNumber()+" "+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("TH")){
					//System.out.println("Table Header");
					doc+="<w:tbl><w:tblPr><w:tblW w:w=\"5000\" w:type=\"pct\"/><w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:left w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:bottom w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:right w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideH w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideV w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/></w:tblBorders></w:tblPr>";
					tmp=input.split("\\|\\|");
					doc+="<w:tr>";
					for(int j=0;j<tmp.length;j++){
						doc+="<w:tc><w:p><w:r><w:rPr><w:b/></w:rPr><w:t>"+tmp[j]+"</w:t></w:r></w:p></w:tc>";
					}
					doc+="</w:tr>";
					i++;
					while((i<returnlist.size())&&(((SectionW)returnlist.get(i)).getSectionType().equals("TR"))){
						//System.out.println("Table Row");
						String sectionText2 = ((SectionW)returnlist.get(i)).getSectionText();
						sectionText2 = StringEscapeUtils.escapeXml(sectionText2);
						input=markUp(sectionText2);  //needs to collect new data for each row
						tmp=input.split("\\|\\|");
						doc+="<w:tr>";
						for(int j=0;j<tmp.length;j++){
							doc+="<w:tc><w:p><w:r><w:t>"+tmp[j]+"</w:t></w:r></w:p></w:tc>";
						}
						doc+="</w:tr>";
						i++;
					}
					i--;
					doc+="</w:tbl>";
					doc+=br;
					//System.out.println("Table End");
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("T")){
					//System.out.println("Table");
					doc+="<w:tbl><w:tblPr><w:tblW w:w=\"5000\" w:type=\"pct\"/><w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:left w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:bottom w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:right w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideH w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideV w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/></w:tblBorders></w:tblPr>";
					while((i<returnlist.size())&&(((SectionW)returnlist.get(i)).getSectionType().equals("T"))){
						//System.out.println("Table Row");
						String sectionText3 = ((SectionW)returnlist.get(i)).getSectionText();
						sectionText3 = StringEscapeUtils.escapeXml(sectionText3);
						input=markUp(sectionText3);  //needs to collect new data for each row
						tmp=input.split("\\|\\|");
						doc+="<w:tr>";
						for(int j=0;j<tmp.length;j++){
							doc+="<w:tc><w:p><w:r><w:t>"+tmp[j]+"</w:t></w:r></w:p></w:tc>";
						}
						doc+="</w:tr>";
						i++;
					}
					i--;
					doc+="</w:tbl>";
					doc+=br;
					//System.out.println("Table End");
				}else{
				//	System.out.println("Unmatched Type: "+((SectionW)returnlist.get(i)).getSectionType());
				}
			}
			
			
			return saveFile(doc,filename , programFile , ssow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//System.out.println("**** exception happened " + e.getMessage());
		}
		
		return null;
		
	}
	
	
	/*
	
	private static File createDoc(ArrayList returnlist, String filename , ProgramFile programFile , Ssow ssow) {
		String[] tmp;
		
		System.out.println("**** list size " + returnlist.size());
		String doc =null,br="<w:p><w:r></w:r></w:p>",input=null;
		try {
			for(int i=0;i<returnlist.size();i++){
				
				
				input=markUp(((SectionW)returnlist.get(i)).getSectionText());
				
				System.out.println("**** input " + input);
				if(((SectionW)returnlist.get(i)).getSectionType().equals("H")){
					//System.out.println("Header");
					doc+="<w:p><w:pPr><w:pStyle w:val=\"Heading2\"/></w:pPr><w:r><w:rPr><w:sz w:val=\"28\"/></w:rPr><w:t>"+((SectionW)returnlist.get(i)).getSectionNumber()+" "+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("I")){
					//System.out.println("Instruction");
					doc+="<w:p><w:r><w:t>"+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("P")){
					//System.out.println("Paragraph");
					doc+="<w:p><w:r><w:t>"+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("S")){
					//System.out.println("Sub Section");
					doc+="<w:p><w:pPr><w:pStyle w:val=\"Heading3\"/></w:pPr><w:r><w:t>"+((SectionW)returnlist.get(i)).getSectionNumber()+" "+input+"</w:t></w:r></w:p>";
					doc+=br;
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("TH")){
					//System.out.println("Table Header");
					doc+="<w:tbl><w:tblPr><w:tblW w:w=\"5000\" w:type=\"pct\"/><w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:left w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:bottom w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:right w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideH w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideV w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/></w:tblBorders></w:tblPr>";
					tmp=input.split("\\|");
					doc+="<w:tr>";
					for(int j=0;j<tmp.length;j++){
						doc+="<w:tc><w:p><w:r><w:rPr><w:b/></w:rPr><w:t>"+tmp[j]+"</w:t></w:r></w:p></w:tc>";
					}
					doc+="</w:tr>";
					i++;
					while((i<returnlist.size())&&(((SectionW)returnlist.get(i)).getSectionType().equals("TR"))){
						//System.out.println("Table Row");
						input=markUp(((SectionW)returnlist.get(i)).getSectionText());  //needs to collect new data for each row
						tmp=input.split("\\|");
						doc+="<w:tr>";
						for(int j=0;j<tmp.length;j++){
							doc+="<w:tc><w:p><w:r><w:t>"+tmp[j]+"</w:t></w:r></w:p></w:tc>";
						}
						doc+="</w:tr>";
						i++;
					}
					i--;
					doc+="</w:tbl>";
					doc+=br;
					//System.out.println("Table End");
				}else if(((SectionW)returnlist.get(i)).getSectionType().equals("T")){
					System.out.println("Table");
					doc+="<w:tbl><w:tblPr><w:tblW w:w=\"5000\" w:type=\"pct\"/><w:tblBorders><w:top w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:left w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:bottom w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:right w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideH w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/><w:insideV w:val=\"single\" w:sz=\"4\" wx:bdrwidth=\"10\" w:space=\"0\" w:color=\"auto\"/></w:tblBorders></w:tblPr>";
					while((i<returnlist.size())&&(((SectionW)returnlist.get(i)).getSectionType().equals("T"))){
						//System.out.println("Table Row");
						input=markUp(((SectionW)returnlist.get(i)).getSectionText());  //needs to collect new data for each row
						tmp=input.split("\\|");
						doc+="<w:tr>";
						for(int j=0;j<tmp.length;j++){
							doc+="<w:tc><w:p><w:r><w:t>"+tmp[j]+"</w:t></w:r></w:p></w:tc>";
						}
						doc+="</w:tr>";
						i++;
					}
					i--;
					doc+="</w:tbl>";
					doc+=br;
					//System.out.println("Table End");
				}else{
					System.out.println("Unmatched Type: "+((SectionW)returnlist.get(i)).getSectionType());
				}
			}
			
			
			return saveFile(doc,filename , programFile , ssow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("**** exception happened " + e.getMessage());
		}
		
		return null;
		
	} */
	private static String markUp(String input){
		
	//	if ( input.contains("<red>") ) {
			
		//	System.out.println("***** red text " + input);
		//}
		
		
		
		input=input.replace("&lt;red&gt;","</w:t></w:r><w:r><w:rPr><w:color w:val=\"FF0000\"/></w:rPr><w:t>");
		input=input.replace("&lt;/red&gt;","</w:t></w:r><w:r><w:t>");
		input=input.replace("&lt;b&gt;","</w:t></w:r><w:r><w:rPr><w:b/></w:rPr><w:t>");
		input=input.replace("&lt;/b&gt;","</w:t></w:r><w:r><w:t>");
		input=input.replace("&lt;i&gt;","</w:t></w:r><w:r><w:rPr><w:i/></w:rPr><w:t>");
		input=input.replace("&lt;/i&gt;","</w:t></w:r><w:r><w:t>");
		input=input.replace("&lt;u&gt;","</w:t></w:r><w:r><w:rPr><w:u w:val=\"single\"/></w:rPr><w:t>");
		input=input.replace("&lt;/u&gt;","</w:t></w:r><w:r><w:t>");
		
//		input=input.replace("<red>","</w:t></w:r><w:r><w:rPr><w:color w:val=\"FF0000\"/></w:rPr><w:t>");
//		input=input.replace("</red>","</w:t></w:r><w:r><w:t>");
//		input=input.replace("<b>","</w:t></w:r><w:r><w:rPr><w:b/></w:rPr><w:t>");
//		input=input.replace("</b>","</w:t></w:r><w:r><w:t>");
//		input=input.replace("<i>","</w:t></w:r><w:r><w:rPr><w:i/></w:rPr><w:t>");
//		input=input.replace("</i>","</w:t></w:r><w:r><w:t>");
//		input=input.replace("<u>","</w:t></w:r><w:r><w:rPr><w:u w:val=\"single\"/></w:rPr><w:t>");
//		input=input.replace("</u>","</w:t></w:r><w:r><w:t>");
		
	//	if ( input.contains("<red>") ) {
			
	//		System.out.println("*****after  red text " + input);
	//	}
		return input;
	}
	
	private static File  saveFile(String fileContents, String fileName , ProgramFile programFile , Ssow ssow) throws ZipException, IOException, SAXException, ParserConfigurationException , Exception{
		//String xmlTemplate = Utils.readFile("resources\\~Project XXX SSOW Template BA Format.xml");
		String xmlTemplate;
		
		//System.out.println("*** locaiton of input file " + programFile.getInputTemplateLocation());
		  
		File inputFile = new File(programFile.getInputTemplateLocation());
		
		if ( inputFile == null || !inputFile.exists()) {
			
		//	System.out.println("*** ERROR -- Input file at location " +  programFile.getInputTemplateLocation() + " does not exist ");
			
		}
		FileInputStream stream = new FileInputStream(inputFile);
		  
		//FileInputStream stream = new FileInputStream(new File("c:\\~Project XXX SSOW Template BA Format.xml"));
		//		FileInputStream stream = new FileInputStream(tempFile);
				
				
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    xmlTemplate=Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		
		String tmp=replacePh(xmlTemplate,"<w:listPr><w:ilvl w:val=\"1\"/><w:ilfo w:val=\"1\"/></w:listPr>", ""); //Remove the heading # from h2 & h3
		tmp=replacePh(tmp,"<w:listPr><w:ilvl w:val=\"2\"/><w:ilfo w:val=\"1\"/></w:listPr>", "");
		
		tmp=replacePh(tmp,"<w:p wsp:rsidR=\"004C697D\" wsp:rsidRPr=\"0031571C\" wsp:rsidRDefault=\"00184238\" wsp:rsidP=\"0031571C\"><w:pPr><w:jc w:val=\"center\"/></w:pPr><aml:annotation aml:id=\"1\" w:type=\"Word.Bookmark.Start\" w:name=\"Paste_To\"/><w:proofErr w:type=\"spellStart\"/><w:proofErr w:type=\"gramStart\"/><w:r wsp:rsidRPr=\"0031571C\"><w:t>phPaste</w:t></w:r><w:proofErr w:type=\"spellEnd\"/><w:proofErr w:type=\"gramEnd\"/></w:p>",fileContents);
		//System.out.println(" creating a new doc");
		
		
		// now lets work to create output file 
		
		String outFileName = inputFile.getParentFile().getAbsolutePath() + "\\" + ssow.getSsowNumber() + "_" + System.currentTimeMillis() + ".doc";
		
		
		///System.out.println("**** out file name " + outFileName);
		//File fileObj = new File("c:\\"+fileName+".doc");
		File fileObj = new File(outFileName);
	//	File fileObj = new File("c:\\Ankur.doc");
        OutputStream outputStream = new FileOutputStream(fileObj);
        
        //System.out.println(" load buffer");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
        
        //System.out.println(" write to file");
        writer.println(tmp);
        writer.close();
        
        
        ssow.setFileLocation(outFileName);
        
        return fileObj;
       
	}
	 private static String replacePh(String base, String placeHolder, String value) {
	        if(!base.contains(placeHolder)) {
	         //   System.out.println("### WARN: couldn't find the place holder: " + placeHolder);
	            return base;
	        }        
	        return base.replace(placeHolder, value);
	    }
	
	private static ArrayList getWordSectionsList () {
        
        
        ArrayList returnList  = new ArrayList();
        
        
        //createSection(sectionNumber, text, type)
     /*   createSection("1"  ,     "Scope" , SectionType.HEADER_SECTION , returnList);
        createSection("" , "<red>This</red> <b>Supplier</b> <i>Statement</i> <u>of Work (SSOW)</u> defines tasks that shall be performed by the Supplier, under contract to The Boeing Company, hereinafter known as Boeing, as authorized by, and subject to the terms and conditions of the Boeing Purchase Contract (PC).  The Supplier shall design, develop, flight qualify, and produce an XXX for the purpose of technical maturation and risk reduction in anticipation of Customer needs for future unmanned, intelligence, surveillance, reconnaissance, and strike aerial systems, including, but not limited to the United States Navy (USN), Unmanned Carrier Launched Airborne Surveillance and Strike System (UCLASS) program." , SectionType.PARAGRAPH_SECTION , returnList) ;
        createSection("" , "Work to be performed hereunder by the Supplier shall consist of providing personnel, materials, services, facilities, logistic support, data and management required to design, develop, fabricate, test, document, deliver on schedule, and support the equipment and/or computer programs defined by the Procurement Specification (PS) and Supplier Data Requirements Catalog (SDRC) identified in the PC. In this context, the term “support” refers to the Supplier's effort to assist Boeing with the integration, verification, and usage of the Supplier's delivered product in the system." , SectionType.PARAGRAPH_SECTION , returnList);
        createSection("" , "Throughout this document, Supplier Data Requirements List (SDRL) data items are noted by referencing specific Supplier Data Sheets (SDS), as defined in Supplier Data Requirements Catalog (SDRC), UISRSTK1SMSDL0001.  The SDRL data items referenced in this SSOW are summarized in Appendix A herein.  Each SDS provides additional definition to the content, format, and digital delivery of a required data item." , SectionType.PARAGRAPH_SECTION , returnList);
        createSection("" , "Throughout this SSOW, the term “Supplier” shall be interpreted to include Sub-tier Suppliers, as applicable." , SectionType.PARAGRAPH_SECTION , returnList);  
        createSection("" , "Where paragraphs of this document or any of its referenced documents are cited, the citation shall be understood to include all subparagraphs under the cited paragraphs, unless otherwise noted." , SectionType.PARAGRAPH_SECTION , returnList);
        createSection("1.1"  , "Product/Service Description" , SectionType.SUB_SECTION, returnList);
        createSection("" , "The Supplier shall perform the tasks detailed in Section 3 of this SSOW to design, develop and/or provide the product specified in the PS, as referenced in Section 2, herein. The Supplier shall, to the maximum extent possible, use non developmental items (NDIs) for the UCLASS program, while meeting program requirements. For purposes of this contract, NDI is defined in Federal Acquisition Regulation (FAR) Part 2, and includes Commercial and Government Off-the-Shelf (COTS/GOTS) products, or any previously developed product in use by a federal, state, or local agency of the United States or a foreign government. A COTS item is any product which was developed independently by industry to meet market demand and includes newly developed items that do not yet have a market history. Hereinafter, these items will be referred to as NDIs and items that do not meet this definition will be referred to as developmental items (DIs). For DIs, the Supplier shall be responsible for all activities required to design, develop, fabricate, verify, and document the delivered items as defined in this SSOW, the PS, and the SDRL." , SectionType.PARAGRAPH_SECTION , returnList);
        createSection("1.2" ,      "Period of Performance" , SectionType.SUB_SECTION , returnList);
        createSection("" , "The period of performance shall be in accordance with the applicable Purchase Contract (PC)." , SectionType.PARAGRAPH_SECTION , returnList) ;
        createSection("" , "The period of performance for the UCLASS program is anticipated to start." , SectionType.PARAGRAPH_SECTION , returnList);
        createSection("1.3" ,      "Responsibility in Subcontracting" , SectionType.SUB_SECTION , returnList);
        createSection("" , "In the event the Supplier subcontracts any portion of its design, manufacture, test, services or data development, the Supplier's subcontracted effort is not exempt from the provisions of this document, the PC and the PS.  The Supplier shall include in subcontractor PCs all necessary elements to ensure complete conformance with these requirements." , SectionType.PARAGRAPH_SECTION , returnList);
        createSection("" , "The Supplier shall be solely responsible for the performance and quality of the total requirements for items that are subcontracted or purchased." , SectionType.PARAGRAPH_SECTION , returnList);

        
        createSection("2" ,      "Applicable Documents" , SectionType.HEADER_SECTION , returnList);
        createSection("" , "The documents listed hereunder form a part of this SSOW to the extent invoked by specific reference in other paragraphs of this SSOW.  If a document is referenced without indicating any specific paragraphs as being applicable, then the document is applicable in its entirety.  Where a specific issue of the document is provided in Section 2, no other issue shall be used without the prior, written approval of the Boeing Procurement Agent.  When documents are referenced herein, a short form citing only the basic number of the document is used and revision letters, amendment indicators, notices, supplements and dates are generally omitted.  If a document is invoked by reference in the text of this SSOW, but not listed in Section 2, it is applicable.  The existence of this situation should be called to the attention of the Boeing Procurement Agent.  The applicable issue of subsidiary documents shall be per the revisions listed in Section 2.1 through Section 2.3 of this document." , SectionType.PARAGRAPH_SECTION , returnList);

        
        
        createSection("3" ,      "Requirements" , SectionType.HEADER_SECTION , returnList );
        createSection("3.1" ,     "General Requirements" , SectionType.SUB_SECTION , returnList);
        createSection("" , "The Supplier shall be responsible for satisfying all requirements of the Boeing PC, PS, this SSOW and all other applicable requirements.  In the event that the Supplier fails to meet these requirements, the Supplier shall be responsible for all redesign, rework, failure analysis, retesting, and other associated efforts required to bring all equipment, delivered or otherwise, up to the requirements specified.  The Supplier shall provide design, test, and production facilities required to meet the test and delivery schedules specified in the PC." , SectionType.PARAGRAPH_SECTION , returnList); 
        createSection("3.2"   ,  "Design and Development" , SectionType.SUB_SECTION, returnList);
        createSection("3.2.1"  ,   "Systems Engineering (SE)" , SectionType.SUB_SECTION , returnList);
        createSection("" , "The Supplier shall use standard Systems Engineering (SE) processes as outlined in the following paragraphs." , SectionType.PARAGRAPH_SECTION , returnList);  
        createSection("3.2.1.1" ,      "Requirements Development" , SectionType.SUB_SECTION , returnList);
        createSection("" , "The Supplier shall collaborate with Boeing to ensure: 1) that there is an understanding of the requirements, and 2) that appropriate management focus and techniques are employed to ensure compliance (e.g., risk, issue, opportunity, affordability, schedule needs or constraints, and technology readiness)." , SectionType.PARAGRAPH_SECTION , returnList);
        createSection("3.2.1.2",     "Requirements Management and Requirements Metrics" , SectionType.SUB_SECTION , returnList);
        createSection("3.2.1.2.1" ,      "Requirements Traceability" , SectionType.SUB_SECTION , returnList);
        createSection("" , "The Supplier shall execute, document and provide an initial traceability product (bi-directional) between a functional requirement and its associated function in the functional architecture, no later than Supplier’s Systems Requirements Review (SRR).  Supplier derived requirements and associated verifications shall be jointly reviewed with Boeing at the Supplier’s System Requirements Review (SRR)." , SectionType.PARAGRAPH_SECTION , returnList);   

        

        
        
        
        createSection("4" ,      "Definitions" , SectionType.HEADER_SECTION , returnList);
        createSection("" , "Term   |Definition" , SectionType.TABLE_HEADER_SECTION , returnList);
        createSection("" , "Boeing |The Boeing Company" , SectionType.TABLE_ROW_SECTION , returnList);
        createSection("" , "Supplier      |The designer/manufacturer of the equipment/product" , SectionType.TABLE_ROW_SECTION , returnList);
        createSection("" , "Pre-Production Equipment    |Pre-production equipment is equipment in a configuration suitable for installation. This equipment is completely representative of the production equipment to follow, and is entirely suitable for testing and demonstration to determine if the production equipment will meet the requirements of this specification. This equipment uses the same parts intended for use in production equipment; however, the peculiar parts developed for the equipment may be manufactured using development tooling and methods. Pre-production equipment may be used for flight demonstrations, bench tests, spares, first article tests, engineering development tests, and any other usage as Boeing may determine." , SectionType.TABLE_ROW_SECTION , returnList);

                     
                     
        createSection("5" ,      "Acronyms" , SectionType.HEADER_SECTION , returnList);
        createSection("" , "Acronym |Definition" , SectionType.TABLE_HEADER_SECTION , returnList);
        createSection("" , "ATP    |Acceptance Test Procedure" , SectionType.TABLE_ROW_SECTION , returnList);
        createSection("" , "BIT    |Built-In Test" , SectionType.TABLE_ROW_SECTION , returnList);
        createSection("" , "BEST   |Boeing Enterprise Supplier Tool" , SectionType.TABLE_ROW_SECTION , returnList);
        createSection("" , "BQMS   |Boeing Quality Management System" , SectionType.TABLE_ROW_SECTION , returnList);
        createSection("" , "CBT    |Computer Based Training" , SectionType.TABLE_ROW_SECTION , returnList);
        createSection("" , "CCDR   |Contract Cost Data Reporting" , SectionType.TABLE_ROW_SECTION , returnList);
                     
*/
        
        return returnList;
 }

	private static void createSection ( String sectionNumber , String text , String type , ArrayList returnList  ) {
        
        
        SectionW sectionW = new SectionW();
        sectionW.setSectionNumber(sectionNumber);
        sectionW.setSectionText(text);
        sectionW.setSectionType(type);
        
        
        returnList.add(sectionW);
        //return sectionW;
        
        
 }
}
