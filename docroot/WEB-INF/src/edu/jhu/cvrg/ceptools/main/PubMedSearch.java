/*Copyright 2013 Johns Hopkins University Institute for Computational Medicine

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
/**
* @author Shallon Brown 2014
* 
*/




package edu.jhu.cvrg.ceptools.main;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;
import org.apache.solr.common.util.NamedList;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PortalUtil;
import edu.jhu.cvrg.ceptools.controller.FileDownloadController;
import edu.jhu.cvrg.ceptools.controller.FileUploadController;

import edu.jhu.cvrg.ceptools.model.FileStorer;
import edu.jhu.cvrg.ceptools.model.Publication;
import edu.jhu.cvrg.ceptools.utility.ZipDirectory;




@ManagedBean(name="pubMedSearch")
@SessionScoped

public class PubMedSearch implements Serializable{

	  
	
	 private List<Publication> publications;
	 private List<Publication> results;
	 private List<Publication> checklist;
	 
	    private String pSearcher;
	    
	    private String searcher;
	    private String url;
	    private String base;
	    private boolean redostep3;
	    private boolean redostep5;
	    private boolean redostep6;
	    private String redostep3msg;
	    private String redostep5msg;
	    private String redostep6msg;
	    
	    
	    public int counter;
	    private int recurpmid;
	    private String buttonvalue;
	    
	    private  String userauthor;  
		 
	    private String usertitle;
	    private String jv,jn,ji,jd,jm,jy,jsp,authorfull, doi, epday, epmonth, epyear, epubsum, epubsum2;
	    private String userpmid; 
	    private int selectedindex;
	    private Publication selectedPub;
	    private FileUploadController fchooser;
	    private FileDownloadController fdownload;
	    private FileStorer selecteddownloadfile;
	    private String selecteddownloadfiletype;
	    private String selecteddownloadfilename;
	    private String pfigure, ppanel, pdescription;
	    
	    private boolean confirmed;
	    
	    
	    
	    private List<File> files;
	    private List<String> filenames;
	    private List<FileStorer> allfiles;
	    private FileStorer selectedfile;
	    private int selectedpubpmid; //specifically for filechooser for storage
	    private ZipDirectory zip;
	    private int index;

	    private int step;
	    private String searchchoice;
	    private List<Integer> pmidlist;
	    
	    
	    
	    
	    
	    
	    public void setRedostep3msg(String m)
	    {
	    	redostep3msg = m;
	    }
	    
	    public String getRedostep3msg()
	    {
	    	return redostep3msg;
	    }
	    
	    public void setRedostep5msg(String m)
	    {
	    	redostep5msg = m;
	    }
	    
	    public String getRedostep5msg()
	    {
	    	return redostep5msg;
	    }
	    
	    public void setRedostep6msg(String m)
	    {
	    	redostep6msg = m;
	    }
	    
	    public String getRedostep6msg()
	    {
	    	return redostep6msg;
	    }
	    
	    public List<Publication> getResults()
	    {
	    	return results;
	    }
	    
	    public void setResults(List<Publication> re)
	    {
	    	results = re;
	    }
	    
	    public List<Publication> getChecklist()
	    {
	    	return checklist;
	    }
	    
	    public void setChecklist(List<Publication> re)
	    {
	    	checklist = re;
	    }
	    
	    
	    public int getIndex()
        {
        	return index;
        }
        
        public void setIndex(int i)
        {
        	this.index = i;
        }
        
	    public List<FileStorer> getAllfiles()
	    {
	    	return allfiles;
	    }
	    
	    public void setAllfiles(List<FileStorer> allfiles)
	    {
	    	this.allfiles = allfiles;
	    }
	    
	    public Publication getSelectedPub()
	    {
	    	return selectedPub;
	    }
	    
	    public void setSelectedPub(Publication spub)
	    {
	    	this.selectedPub = spub;
	    }
	    
	    public List<Publication> getPublications()
	    {
	    	return publications;
	    }
	    
	    public void setPublications(List<Publication> publist)
	    {
	    	this.publications = publist;
	    }
	    
	    
	    public  String getUserauthor()
        {
        	return userauthor;
        }
        
        public  String getUsertitle()
        {
        	return usertitle;
        }
        public  String getUserpmid()
        {
        	return userpmid;
        }
        
        public  void setUserauthor(String u)
        {
        	userauthor = u;
        }
        
        public  void setUsertitle(String u)
        {
        	usertitle = u;
        }
        
        public  void setUserpmid(String u)
        {
        	userpmid = u;
        }
	   
        public String setButtonvalue ()
        {
        	return buttonvalue;
        }
        
        public void getButtonvalue(String b)
        {
        	this.buttonvalue = b;
        }
        
        public void setStep(int currstep)
 	   {
 		   step = currstep;
 	   }
 	   
 	   public int getStep()
 	   {
 		   return step;
 	   }
 	   
 	   
 	   public void setSearchchoice(String currsearch)
 	   {
 		   searchchoice = currsearch;
 		   
 	   }
 	
 	   public String getSearchchoice()
 	   {
 		   return searchchoice;
 	   }
 	   
 	  public void setFilestring()
 	 {
 	 	for(File currfile:this.files)
 	 	{
 	 		filenames.add(currfile.getName());
 	 	}
 	 }


 	 public void setFilenames(List<String> filenames)
 	 {
 	 	  this.filenames = filenames;
 	 }

 	 public List<String> getFilenames()
 	 {
 	 	  return filenames;
 	 }
 	   
 	  public void setSelecteddownloadfile(FileStorer thefile)
	   {
		   this.selecteddownloadfile = thefile;
	   }
	   
	   public FileStorer getSelecteddownloadfile()
	   {
		   return selecteddownloadfile;
	   }
	   
	   public void setFdownload(FileDownloadController fdownload)
	   {
		   this.fdownload = fdownload;
	   }
	   
	   
	   public FileDownloadController getFdownload()
	   {
		   return fdownload;
	   }
	   
	   public void setSelectedfile(FileStorer thefile)
	   {
		   selectedfile = thefile;
	   }
	   
	   public FileStorer getSelectedfile()
	   {
		   return selectedfile;
	   }
	   
	   public FileUploadController getFchooser()
	    {
	    	return fchooser;
	    }
	    
	    public void setFchooser( FileUploadController fchooser)
	    {
	    	this.fchooser =  fchooser;
	    }
	   
	      
	    public String editPSearcher(String pSearcher)
	    {
	    	this.pSearcher = pSearcher;
	    	return pSearcher;
	    }
	    
	    public void setPSearcher(String pSearcher)
	    {
	    	this.pSearcher = pSearcher;
	    }
	 
	    public String getPSearcher()
	    {
	    	return pSearcher;
	    }
	    
	    public String getSearcher()
	    {
	    	return searcher;
	    }
   
	    public void setSearcher(String searcher)
	    {
	    	this.searcher = searcher;
	    }
	    
	    public void save(ActionEvent actionEvent) {  
	        //Persist user  
	          
	        FacesMessage msg = new FacesMessage("Successful", "Welcome " );  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }  

	    
	    private static Logger logger = Logger.getLogger(PubMedSearch.class.getName());  
	  
	   
	    
	   public PubMedSearch()
	    {
		   step = 1;
	    	 counter = 0;
	    	 searcher = "title";
	    	 buttonvalue = "";
	    	 userauthor = usertitle = userpmid = url = searcher = pSearcher = "";
	    	 base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
	    	 publications = new ArrayList<Publication>();
	    	 results = new ArrayList<Publication>();
	    	 checklist = new ArrayList<Publication>();		 
	    	 selectedindex = -1;
	    	 selectedPub = new Publication();
	    	 fchooser = new FileUploadController();
	    	 files = new ArrayList<File>();
	    	 filenames = new ArrayList<String>();
	    	 allfiles = new ArrayList<FileStorer>();
	    	 selectedfile = null;
	    	 zip = new ZipDirectory();
	    	 fdownload = new FileDownloadController();
	    	 selecteddownloadfile = null;
	    	 index = 0;
	    	 jv=jn=ji=jd=jm=jy=jsp=authorfull=doi=epday=epmonth=epyear =epubsum = epubsum2 = "";
	    	 selecteddownloadfiletype = selecteddownloadfilename = "";
	    	 searchchoice="";
	    
	    	 searchchoice = "searchtitle";  
	    	 pmidlist = new ArrayList<Integer>();
	    	 ppanel = pfigure = pdescription = "";
	    	 confirmed = false;
	    	 redostep3 = redostep5 = redostep6= false;
	    	 redostep3msg =  redostep5msg =  redostep6msg = "";
	    	 
	    }
	   
	   
	   public void configDisplay ()
	   {
		 
		   String tmpdis = "";
		   
		   for(FileStorer currfile: allfiles)
		   {
			  
			   if(currfile.getFigure().length() > 0 && currfile.getPanel().length()>0)
			   {
				   tmpdis = "Figure " + currfile.getFigure() + ", Panel " + currfile.getPanel();
				   currfile.setFigpandisplay(tmpdis); 
			   }
			   else if (currfile.getFigure().length() > 0 && currfile.getPanel().length()<1)
			   {
				   tmpdis = "Figure " + currfile.getFigure(); 
				   currfile.setFigpandisplay(tmpdis);  
			   }
			   else 
			   {
				   currfile.setFigpandisplay("");  
			   }
			   
			   
		   }
		   
		   
		   
	   }
	   

public void Clean()
{
	 step = 1;
 	 counter = 0;
 	 searcher = "title";
 	 buttonvalue = "";
 	 userauthor = usertitle = userpmid = url = searcher = pSearcher = "";
 	 base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
 	 publications = new ArrayList<Publication>();
 	 results = new ArrayList<Publication>();
 	 checklist = new ArrayList<Publication>();		 
 	 selectedindex = -1;
 	 selectedPub = new Publication();
 	 fchooser = new FileUploadController();
 	 files = new ArrayList<File>();
 	 filenames = new ArrayList<String>();
 	 allfiles = new ArrayList<FileStorer>();
 	 selectedfile = null;
 	 zip = new ZipDirectory();
 	 fdownload = new FileDownloadController();
 	 selecteddownloadfile = null;
 	 index = 0;
 	 jv=jn=ji=jd=jm=jy=jsp=doi=epday=epmonth=epyear =epubsum = epubsum2 = "";
 	 selecteddownloadfiletype = selecteddownloadfilename = "";
  	searchchoice = "searchtitle";  

 	 ppanel = pfigure = pdescription = "";
	confirmed = false;
	
	 redostep3 = redostep5 = redostep6= false;
	 redostep3msg =  redostep5msg =  redostep6msg = "";
}


public void Clean2()
{

 	 counter = 0;
 	 searcher = "title";
 	 buttonvalue = "";
 	 userauthor = userpmid = url = searcher = pSearcher = "";
 	 base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
 	 publications = new ArrayList<Publication>();
 	 results = new ArrayList<Publication>();
 	 checklist = new ArrayList<Publication>();		 
 	 selectedindex = -1;
 	 selectedPub = new Publication();
 	 fchooser = new FileUploadController();
 	 files = new ArrayList<File>();
 	 filenames = new ArrayList<String>();
 	 allfiles = new ArrayList<FileStorer>();
 	 selectedfile = null;
 	 zip = new ZipDirectory();
 	 fdownload = new FileDownloadController();
 	 selecteddownloadfile = null;
 	 index = 0;
 	 jv=jn=ji=jd=jm=jy=jsp=doi=epday=epmonth=epyear =epubsum = epubsum2 = "";
 	 selecteddownloadfiletype = selecteddownloadfilename = "";
  	searchchoice = "searchtitle";  

 	 ppanel = pfigure = pdescription = "";
	 confirmed = false;
	
	 redostep3 = redostep5 = redostep6= false;
	 redostep3msg =  redostep5msg =  redostep6msg = "";
}
	   
	   
	   public void moveStep(int nextstep)
		{
		   int previousstep = step;
		   
		   if (redostep3 == true && nextstep == 3 && previousstep !=4)
			{
				if(selectedPub != null)
				{
					
				step = 4;
				try{
					LiferayFacesContext portletFacesContext = LiferayFacesContext.getInstance();
					portletFacesContext.getExternalContext().redirect("shallon_upload");
					return;
				}
				catch (Exception ex)
				{
					logger.info(ex);
				}
				
				}
				else
				{
					step = 3;
					redostep3msg = "Please select a single citation from the list to proceed.";
				}
			}
		   if (redostep5 == true && nextstep == 5 && redostep6 == false)
			{
				if(!fchooser.getAllFiles().isEmpty())
				{
					
					if(nextstep == 6)
					{
				step = 6;
					}
					else if (nextstep == 5)
					{
						step = 5;
					}
				getStoredFiles();
				redostep6 = true;
				try{
					LiferayFacesContext portletFacesContext = LiferayFacesContext.getInstance();
					portletFacesContext.getExternalContext().redirect("shallon_upload");
					return;
				}
				catch (Exception ex)
				{
					logger.info(ex);
				}
				
				}
				else
				{
					step = 5;
					redostep5msg = "Please upload at least one file to the publication to proceed.";
					try{
						LiferayFacesContext portletFacesContext = LiferayFacesContext.getInstance();
						portletFacesContext.getExternalContext().redirect("shallon_upload");
						return;
					}
					catch (Exception ex)
					{
						logger.info(ex);
					}
				}
			}
		

		   
		
		   if(nextstep < step)
		   {
			   
			   
			   if(nextstep == 1)
			   {
				 
				   Clean();
					step=1;
				   
			   }
			   
			   
			   if(nextstep == 3)
			   {
				   
				   step = 3;
				   
			   }
			   
			   if(nextstep == 4)
			   {
				   
				   step = 4;
			   }
			   
			   if(nextstep == 5)
			   {
				 step = 5;  
				   
			   }
			   
			   
			   
			 
			   
			   
			   
			   
			   
		   }
		   else
		   {
		   
		   
		   

		   
		   step = nextstep;
		   
		
	
		
				if (step == 3)
				{
					
					if(redostep3 == false)
					{
						 if (searchchoice == "searchauthor" || searchchoice=="searchtitle" || searchchoice=="searchpmid")
						{
							
							  try{
						    	   processUrl(); 
						    	   syncResults();
						    	   }
						    	   catch(Exception ex)
						    	   {
						    		   
						    	   }
						}
					}
					redostep3 = true;
					
				}
				
				
				else if(step == 4)
				{
					step = 5;
					setFchooservar();
				}
				else if (step==5)
			{
				
		    	redostep5 = true;
		    	getStoredFiles();
		    	step = 6;
			}
				else if(step==6)
			{
				
					
			                validateDesc();
						
							if(confirmed==true)
							{
								step = 7;
								configDisplay();
								sendtoSolr();
							}
							else
							{
								step = 6;
							}
					
					redostep6 = true;
			}
				else if(step==7)
			{
				 configDisplay ();
				 sendtoSolr();
			}
				else if(step==8)
			{
				Clean();
				step=1;
			}
			
				else if(step==0)
			{ 
			   getStoredFiles();
			   configDisplay ();
		
			}
				else if(step==4)
			{   
			
				
				if(selectedPub.getExists())
				{
					step=99;
				}
			}
			
		   }
		  

			
			try{
				LiferayFacesContext portletFacesContext = LiferayFacesContext.getInstance();
				portletFacesContext.getExternalContext().redirect("shallon_upload");
				return;
			}
			catch (Exception ex)
			{
				logger.info(ex);
			}
		}
	   
	   public void onEdit(RowEditEvent event) {  
	        FacesMessage msg = new FacesMessage("File Edited", ((FileStorer) event.getObject()).getFilename());  
	  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	        
	    }  
	      
	    public void onCancel(RowEditEvent event) {  
	        FacesMessage msg = new FacesMessage("File Edited", ((FileStorer) event.getObject()).getFilename());  
	  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	        
	    }  
	    
	   public void validateDesc()
	    {
		  
		 
	       boolean verify = true;
		   
		   for(FileStorer currfile: allfiles)
		   { 
			
			   //Validate Figure
			   
			   
			   
			   //Validate Panel
			   if(currfile.getFigure().length() < 1 && currfile.getPanel().length()>=1)
			   {
				   
				   currfile.setMessage("You must enter a figure number to correspond to the panel entered.");
				   
				  verify= false;
			   }
			   
			   
			   //Validate Description
			   
			   else if(currfile.getFigure().length() < 1 && currfile.getPanel().length()<1 && currfile.getDescription().length()<1)
			   {
				  
				   currfile.setMessage("You must enter a description if there is no Figure or Panel number entered.");
				   
				   verify= false;
			   }
			   
			   else
			   {
				   
				  
				   currfile.setMessage("");
				
			   }
		
			   
			   
			   
			   
			   
	      }
		   
		   if(verify == true)
		   {
			   confirmed = true;
			  
			   
		   }
		   
		   
		   
	    }
	   
	   

	   
	   public void syncResults()
	   {
		   int counter = 0;
		   
		   for(Publication currpub : publications)
		   {
			  
			   recurpmid = currpub.getPmid();
			   pmidlist.add(recurpmid);
			   

		   }
		    
		   SearchSolrList(pmidlist);
		   
		   
	   }
	   
	   
	   public void SearchSolr(int mypmid)
		 {
		   
		   
		  int currpmid = mypmid;
		  
		 
			 
			 
			 try
			 {
				
				 CoreAdminRequest adminRequest = new CoreAdminRequest();
				 adminRequest.setAction(CoreAdminAction.RELOAD);
				 CoreAdminResponse adminResponse = adminRequest.process(new HttpSolrServer("http://localhost:8983/solr"));
				 NamedList<NamedList<Object>> coreStatus = adminResponse.getCoreStatus();
				 
				 
				 
				 SolrServer solr = new HttpSolrServer ("http://localhost:8983/solr");
				
				 
				 
				 String query;
				
				
				
				
				 query = "pmid:" + currpmid;
				 
				
				 SolrQuery theq = new SolrQuery();
				 theq.setQuery(query);
				 
			
				 QueryResponse response = new QueryResponse();
				 
				 
				 
				 response = solr.query(theq);
				 
				 
				 SolrDocumentList list = response.getResults();
			
				
				 
				
				 
				 int docnum = 1;
				 for(SolrDocument doc: list)
				 {
					Publication currlist = new Publication();
					currlist.setTitle(doc.getFieldValue("ptitle").toString());
					currlist.setAbstract(doc.getFieldValue("abstract").toString());
					currlist.setPmid(Integer.valueOf(doc.getFieldValue("pmid").toString()));
					
				
				
					results.add(currlist);
					docnum++;
				 }
				 
				 
				 
			
			  
			 }
			 catch (Exception ex)
			 {
				 logger.info(ex);
				 StringWriter stack = new StringWriter();
				ex.printStackTrace(new PrintWriter(stack));
				 
				
			 }
		 }
	   
	   
	   public void addInfo(ActionEvent actionEvent) {  
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sample info message", "PrimeFaces rocks!"));  
	   
            if(!usertitle.isEmpty())
            {
            	moveStep(3);
            }
	   
	   
	   }  
	   
	   public void SearchSolrList(List<Integer> mypmids)
		 {
		   
		   String querylist = "pmid:(";
		   
		  for(Integer currint: mypmids)
		  {
			  querylist += currint + " OR ";
			
		  }
		  
		  if(querylist.length()>5)
		  {
			 querylist = querylist.substring(0, querylist.length()-4);
		     querylist +=")";
		  }
		  
		  
		  
		 
		 
			 
			 
			 try
			 {
				
				 CoreAdminRequest adminRequest = new CoreAdminRequest();
				 adminRequest.setAction(CoreAdminAction.RELOAD);
				 CoreAdminResponse adminResponse = adminRequest.process(new HttpSolrServer("http://localhost:8983/solr"));
				 NamedList<NamedList<Object>> coreStatus = adminResponse.getCoreStatus();
				 
				 
				 
				 SolrServer solr = new HttpSolrServer ("http://localhost:8983/solr");
				
				
				 
			
				 SolrQuery theq = new SolrQuery();
				 theq.setQuery(querylist);
				 
			
				 QueryResponse response = new QueryResponse();
				 
				 
				
				 response = solr.query(theq);
				 
				
				 SolrDocumentList list = response.getResults();
				
				
				 
				
				 
				 int docnum = 1;
				 for(SolrDocument doc: list)
				 {
					Publication currlist = new Publication();
					currlist.setTitle(doc.getFieldValue("ptitle").toString());
					currlist.setAbstract(doc.getFieldValue("abstract").toString());
					currlist.setPmid(Integer.valueOf(doc.getFieldValue("pmid").toString()));
					
					results.add(currlist);
					docnum++;
				 }
				 
				 int currcounter = 0;
				 
				 for(Publication solrmatch: results)
				 {
					 currcounter = 0;
					 for(Publication publistrecord: publications)
					 {
						 if(solrmatch.getPmid() == publistrecord.getPmid())
						 {
							
							 publications.get(currcounter).setExists(true);
							 
						 }
						 currcounter++;
					 }
					
				 }
				 
				 
			
			  
			 }
			 catch (Exception ex)
			 {
				 logger.info(ex);
				 StringWriter stack = new StringWriter();
				 ex.printStackTrace(new PrintWriter(stack));
				
				
			 }
		 }   
	   
	   
	   
	
	   
	   public void downloadPrep(FileStorer currfile)
	    {

		   String filename = currfile.getFilename();
			if(selecteddownloadfile != null){
				
				String filetype = filename.substring(filename.length()-4, filename.length()-1);
				currfile.setFiletype(filetype);
			
				
				try
				{
				fdownload.downloadFile(currfile);
				}
				catch (Exception ex)
				{
					
				}
				
				}
				
	    }
	   
	   
	   
	   
	   public void UpdateSolr () throws Exception
	   {
		   String str = PropsUtil.get("data_store");
		   File maindir = new File(str);
		   File[] mdcontents = maindir.listFiles();
		   List<Integer> allpubs = new ArrayList<Integer> ();
		   SAXReader reader = new SAXReader();
	    	SAXReader reader2 = new SAXReader();
		     Document document;
	        
	         OutputFormat format = OutputFormat.createPrettyPrint();
	      
	         XMLWriter writer = new XMLWriter( System.out, format );
	         
	         String mytitle, myabstract, myyear, myfullname = "";
	         Element journalname, journalvolume, journalissue, journalyear, journalmonth, journalday, journalpagestart, epubmonth, epubday, epubyear;
	         int mypmid;
	         List<String> mylauthors = new ArrayList<String>();
	         List<String> myfauthors = new ArrayList<String>();
	         List<String> myfnames = new ArrayList<String>();
	        
		   
		   for(File currfile: mdcontents)
		   {
			  if(currfile.isDirectory())
			  {
				  String currname = currfile.getName();
				  int currpmid = Integer.valueOf(currname);
				  allpubs.add(currpmid);
			  }
			   
			   
		   }
		
		   
		   for(int currint: allpubs)
		   {
		   String input = String.valueOf(currint);
		 
	    
	        
	    
		   String url = base + "efetch.fcgi?db=pubmed&id="+input+"&retmode=xml&rettype=abstract";
		
		   document = reader.read(url);
    	   
           List firstnames = document.selectNodes( "//ForeName" );
           List lastnames = document.selectNodes( "//LastName" );

          mytitle = document.selectSingleNode("//ArticleTitle").getText();
          myyear =  document.selectSingleNode("//PubDate/Year").getText();
          myabstract = document.selectSingleNode("//AbstractText").getText();
          journalname =(Element) document.selectSingleNode(".//Journal/Title");
            journalyear =(Element) document.selectSingleNode(".//PubDate/Year"); 
            journalmonth =(Element) document.selectSingleNode(".//PubDate/Month"); 
            journalday =(Element) document.selectSingleNode(".//PubMedPubDate[@PubStatus='epublish']/Day | .//DateCreated/Day");
            
            epubday = (Element) document.selectSingleNode(".//PubMedPubDate[@PubStatus='aheadofprint']/Day"); 
            epubmonth = (Element) document.selectSingleNode(".//PubMedPubDate[@PubStatus='aheadofprint']/Month");
            epubyear =(Element) document.selectSingleNode(".//PubMedPubDate[@PubStatus='aheadofprint']/Year");
            
            
            journalvolume =(Element) document.selectSingleNode(".//Journal/Volume"); 
            journalissue =(Element) document.selectSingleNode(".//Journal/Issue"); 
            journalpagestart =(Element) document.selectSingleNode(".//MedlinePgn");
          mypmid = Integer.valueOf(document.selectSingleNode(".//PMID").getText());
          
          
            jn = journalname.getText();
            jv = journalvolume.getText();
            ji = journalissue.getText();
            jm = journalmonth.getText();
            jy = journalyear.getText();
            jsp =journalpagestart.getText();
            jd = journalday.getText();
            epday = epubday.getText();
            epmonth = epubmonth.getText();
            epyear = epubyear.getText();
            
          
          mypmid = currint;

            Iterator fiter=firstnames.iterator();
            Iterator liter=lastnames.iterator();

            //System.out.println("The Authors: ");
            
             while(fiter.hasNext()){
             Element currelement= (Element) fiter.next();
             myfauthors.add(currelement.getText());
             Element currelement2 = (Element) liter.next();
            mylauthors.add(currelement2.getText());
            
            myfullname = currelement.getText() + " " + currelement2.getText();
            myfnames.add(myfullname);
            
            	if(fiter.hasNext())
            		{
            		
            		authorfull = authorfull + myfullname + ", ";
            		}
             }
           

		   
		   
		   try{
		   SolrInputDocument metadoc = new SolrInputDocument();
	    	 
	    	 metadoc.addField("pmid", currint);
	    	 metadoc.addField("pid", UUID.randomUUID());
	    	 metadoc.addField("journalvolume", jv);
	    	 metadoc.addField("journalissue", ji);
	    	 metadoc.addField("journalmonth", jm);
	    	 metadoc.addField("journalyear", jy);
	    	 metadoc.addField("journalday", jd);
	    	 metadoc.addField("journalname", jn);
	    	 metadoc.addField("journalpage", jsp);
	    	 metadoc.addField("author_fullname_list", authorfull);
	    	 
	    	    metadoc.addField("abstract", myabstract);
	    	   metadoc.addField("publicationdate_year", myyear);
	    	  
	    	   metadoc.addField("ptitle", mytitle );  
	    	   
	    	  for(int i=0; i<myfauthors.size(); i++) 
	    	  {
	    	   metadoc.addField("author_firstname",myfauthors.get(i));
	    	    metadoc.addField("author_lastname",mylauthors.get(i)); 
	    	  }
	    	    
	    	  HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr");
	    	 server.add(metadoc);
	    	    server.commit();
	    	}
	    	catch(Exception ex)
	    	{
	    		logger.info(ex);
	    	}
		   }
	   }
	    
	  	      
	   public boolean checkSubmit()
	   {
		   if((this.getUserauthor().length() > 0)|| (this.getUsertitle().length() > 0 )|| (this.getUserpmid().length() > 0))
		   {
			   return true; 
		   }
		   else
		   {
			   return false;
		   }
	   }
	      
	    public String onFlowProcess(FlowEvent event) {  
	       
	        
	        
	       if(event.getOldStep().equalsIgnoreCase("searchselect") )
	       {
			     
	    	   
	    	   if(searcher.equalsIgnoreCase("author"))
			      {  
			    	  
			    	return "searchauthor";  
			    	
			    	
			      }
			      else if(searcher.equalsIgnoreCase("title"))
			      {
			    	  return "searchtitle";  
			      }
			      else if(searcher.equalsIgnoreCase("pmid"))
			      {
			    	  return "searchpmid";  
			      }
			      else
			      {
			    	  return "searchpmid";
			      }
	       }
	       if((event.getOldStep().equalsIgnoreCase("searchauthor") ||event.getOldStep().equalsIgnoreCase("searchtitle") || event.getOldStep().equalsIgnoreCase("searchpmid"))
	    		    && ! checkSubmit()){
	    	   Clean();
	    	   return "searchselect";
	       }
	      
	       if(event.getOldStep().equalsIgnoreCase("searchauthor") && checkSubmit())
	       {
	    	   try{
	    	   processUrl(); 
	    	   }
	    	   catch(Exception ex)
	    	   {
	    		   logger.info(ex);
	    	   }
	    	   return "resultsdisplay";
	       }
	       if(event.getOldStep().equalsIgnoreCase("searchtitle") && checkSubmit())
	       {
	    	
	   
	    	  
	    
	    	   try{
		    	   processUrl(); 
		    	   }
		    	   catch(Exception ex)
		    	   {
		    		   
		    	   }
	    	   return "resultsdisplay";
	       }
	       if(event.getOldStep().equalsIgnoreCase("searchpmid") && checkSubmit())
	       {
	    	   try{
		    	   processUrl(); 
		    	   }
		    	   catch(Exception ex)
		    	   {
		    		   
		    	   }
	    	   return "resultsdisplay";
	       }
	       if(event.getOldStep().equalsIgnoreCase("resultsdisplay"))
	       {
	    	   testPub();
	    	
	    	   return "confirm";
	       }
	       if(event.getOldStep().equalsIgnoreCase("confirm"))
	       {
	    	   setFchooservar();
	    	   sendtoSolr();
	    	   
	    	   return "upload";
	       }
	       if(event.getOldStep().equalsIgnoreCase("upload"))
	       {
	    	   
	    	   getStoredFiles();
	    	   return "display";
	       }
	       else
	       {
	    	   Clean();
	    	   return "searchselect";
	       }
	      
	    	    
	    
	    
}
	    
public List<File> getFiles()
{
	return files;
}
	    
public void setFiles(List<File> files)
{
	this.files = files;
}
	    
	    public void setFchooservar()
	    {
	    	selectedpubpmid = this.selectedPub.getPmid();
	    	fchooser.setPmid(selectedpubpmid);
	        
	    }
	    
	    public void testPub()
	    {
	    	Publication thispub = this.selectedPub;
	
	    	
	    	
	    }
	    
	    public void setSelectedindex(int si)
	    {
	    	this.selectedindex = si;
	    }
	    
	    public Publication identifyRecord()
	    {
	    	return publications.get(this.selectedindex);
	    }
	    
	    
	    
	    

	    public void getStoredFiles()
	    {
	    	
	    	
	    	
	    	String currlocation = PropsUtil.get("data_store") + this.selectedPub.getPmid() + "/";
	    	String zipfilelocation = currlocation+ this.selectedPub.getPmid()+".zip";
	    	File foldertozip = new File(currlocation);
	    	 
	    
	    	
	    
	    	File folder = new File(currlocation);
	    	
	    
	    
	    	
	    	for(File currfile: folder.listFiles())
	    	{
	    		
               String absolutePath = currfile.getAbsolutePath();
	    		FileStorer currfilestore = new FileStorer();
	    		currfilestore.setFilename(currfile.getName());
	    		currfilestore.setFilelocation(currfile.getPath());
	    		
	    		
	    		if(currfile.length()/1000 < 1)
	    		{
	    		currfilestore.setFilesize(1);
	    		}
	    		else
	    		{
	    	     currfilestore.setFilesize(currfile.length()/1000);
	    		}
	    		
	    		
	    		
	    		currfilestore.setFiletype(FilenameUtils.getExtension(currfile.getName()));
	    		currfilestore.setLocalfilestore( absolutePath.substring(0,absolutePath.lastIndexOf(File.separator)));
	    		
	    		
	    		
	    		allfiles.add(currfilestore);
	    		
	    		files.add(currfile);
	    		filenames.add(currfile.getName());
	    		
	    	}
	    	
	    	
	    	selectedPub.setFiles(files);
	    	selectedPub.setFilenames(filenames);
	    	
	   	 if(step != 0)
	   	 {
	    	try{
	    	zip.zipFiles(foldertozip); 
	    	
    
	    	}
	    	catch (Exception ex)
	    	{
	    		logger.info(ex);
	    		
	    	}
	   	 }

	      
	    }
	    
	    
	    
	    
	    public void sendtoSolr()
	    {
	    	List<String> filesanddata = new ArrayList<String>();
	    	
	    	String allstrings = "";
	    	
	    	for(FileStorer currfile: allfiles)
	    	{
	    		
	    		
	    		String cfilesize = "filesize:" + String.valueOf(currfile.getFilesize());
	    		String cfiledescription = ",filedescription:" + currfile.getDescription();
	    		String cfilefigure = ",filefigure:" + currfile.getFigure();
	    		String cfilepanel = ",filepanel:" + currfile.getPanel();
	    		String cfilename = ",filename:" + currfile.getFilename();
	    		
	    		
	    		allstrings = cfilesize + cfiledescription + cfilefigure + cfilepanel + cfilename;
	    		
	    		filesanddata.add(allstrings);
	    		
	    		
	    	}
	    	
	    	 
	    	try
	    	{
	    
	    	 SolrInputDocument metadoc = new SolrInputDocument();
	    	 
	    	 metadoc.addField("pmid", selectedPub.getPmid());
	    	 metadoc.addField("pid", UUID.randomUUID());
	    
	    	    metadoc.addField("abstract", selectedPub.getAbstract());
	    	   metadoc.addField("publicationdate_year", selectedPub.getYear());
	    	   metadoc.addField("doi", selectedPub.getDoi());
	    		 metadoc.addField("journalvolume", selectedPub.getJournalvolume());
		    	 metadoc.addField("journalissue", selectedPub.getJournalissue());
		    	 metadoc.addField("journalmonth", selectedPub.getJournalmonth());
		    	 metadoc.addField("journalyear", selectedPub.getJournalyear());
		    	 metadoc.addField("journalday", selectedPub.getJournalday());
		    	 metadoc.addField("journalname", selectedPub.getJournalname());
		    	 metadoc.addField("journalpage", selectedPub.getJournalstartpg());
		    	 metadoc.addField("epubday", selectedPub.getEpubday());
		    	 metadoc.addField("epubmonth", selectedPub.getEpubmonth());
		    	 metadoc.addField("epubyear", selectedPub.getEpubyear());
		    	 metadoc.addField("author_fullname_list", selectedPub.getAuthorfull());
		    	
	    	  
	    	   metadoc.addField("ptitle", selectedPub.getTitle() );  
	    	   
	    	  for(int i=0; i<selectedPub.getFauthors().size(); i++) 
	    	  {
	    	     metadoc.addField("author_firstname",selectedPub.getFauthors().get(i));
	    	     metadoc.addField("author_lastname",selectedPub.getLauthors().get(i)); 
	    	  }
	    	  
	    	  for(String currstring: filesanddata)
	    	  {
	    		  metadoc.addField("pfileinfo",currstring);
	    	  }
	    	    
	    	    HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr");
	    	    server.add(metadoc);
	    	    server.commit();
	    	}
	    	catch(Exception ex)
	    	{
	    		
	    	}
	    }

	    
public int getCounter()
{
	return this.counter;
}



public void downloadRawFiles(FileStorer currfile){


	
	selecteddownloadfile = currfile;
	
	
    if(selecteddownloadfile != null){
    	 
           downloadInit();
    }
    //return "success";
}

public void downloadInit()
{
	
	
	//Gather the content type and store
	selecteddownloadfilename = selecteddownloadfile.getFilename();
	
	
	
	String currtype = FilenameUtils.getExtension(selecteddownloadfile.getFilename());
	
	

	
     selecteddownloadfile.setFiletype(currtype);
	
	downloadFile(selecteddownloadfilename, selecteddownloadfiletype);
	
	//Begin the download process
}




public void downloadZipOnly()
{
	
	
	selecteddownloadfile = new FileStorer();
	
	String fileloc = PropsUtil.get("data_store") + selectedpubpmid;
	String filen = selectedpubpmid + ".zip";
	
    selecteddownloadfile.setFilelocation(fileloc);
	selecteddownloadfile.setFilename(filen);
	selecteddownloadfile.setIndex(0);
	selecteddownloadfile.setLocalfilestore(fileloc);
	selecteddownloadfile.setFiletype("zip");
	
	
	downloadFile(filen,"zip");
}



public void downloadFile(String filename, String filetype){
         
         String contentType = "application/zip";
 
         if(filetype.equals("abf")){
                 contentType = "text/abf";
         }
     
       
    
         
  FacesContext facesContext = (FacesContext) FacesContext.getCurrentInstance();
  ExternalContext externalContext = facesContext.getExternalContext();
  PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
  HttpServletResponse response = PortalUtil.getHttpServletResponse(portletResponse);

  
  File file = new File(selecteddownloadfile.getLocalfilestore(), filename);
  BufferedInputStream input = null;
  BufferedOutputStream output = null;

 
  try {
  input = new BufferedInputStream(new FileInputStream(file), 10240);
 
  response.reset();
  response.setHeader("Content-Type", contentType);
  response.setHeader("Content-Length", String.valueOf(file.length()));
  response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
  response.flushBuffer();
  output = new BufferedOutputStream(response.getOutputStream(), 10240);

  byte[] buffer = new byte[10240];
  int length;
  while ((length = input.read(buffer)) > 0) {
  output.write(buffer, 0, length);
  }
 
  output.flush();
  } catch (FileNotFoundException e) {
                 e.printStackTrace();
         } catch (IOException e) {
                 e.printStackTrace();
         } finally {
  try {
                         output.close();
  input.close();
                 } catch (IOException e) {
                         e.printStackTrace();
                 }
  }
 
  facesContext.responseComplete();

 
}


public void FileSave()
{
	
	String currpmid  = String.valueOf(selectedPub.getPmid());
	String currlocation = PropsUtil.get("data_store")  + currpmid;
	ArrayList<org.primefaces.model.UploadedFile> thefiles = new ArrayList<org.primefaces.model.UploadedFile>();
	
	 File thedir = new File(currlocation);
	 
	if(!thedir.exists())
	{
	 thedir.mkdirs();
	}
	
	//Now add files to it
	 try
	   {
	thefiles = fchooser.getAllFiles();
	OutputStream out = new FileOutputStream(thedir);
	for(org.primefaces.model.UploadedFile currfile: thefiles)
	{
	   String fullfilelocation = currlocation + currfile.getFileName();
	   File myfile = new File(fullfilelocation);
	   
	 
	 InputStream in = currfile.getInputstream();
	 
	 
	  
	     out = new FileOutputStream(myfile);
	    org.apache.commons.io.IOUtils.copy(in, out);
	     
	 
	}
	
	
	
	}
	catch(Exception ex)
	{
		logger.info(ex);
	}
	
}





public void initialProcess() throws Exception
{
	

	String input = "";
	
	if(this.getUsertitle().length() > 0)
	{
		
	 input = this.getUsertitle();	
	}
	else if(this.getUserauthor().length() > 0)
	{
		input = this.getUserauthor();
	}
	else if(this.getUserpmid().length() > 0)
	{
		input = this.getUserpmid();
	}
	
	  if (searcher.equalsIgnoreCase("title") )
	    {
		  
      
	          input = input.replaceAll("\\s", "+");
	        if( input.charAt(input.length()-1) == ((char) '+')   )
	        {
	           input = input.substring(0, input.length()-1);
	        }
	        
	        url = base + "esearch.fcgi?db=pubmed&term="+input+"&retmax=200&tool=ceptools";
	    }  
	    else if (searcher.equalsIgnoreCase("pmid") )
	    {
	       
	    
	        
	         input = input.replaceAll("\\s", "+");
	        if( input.charAt(input.length()-1) == ((char) '+')   )
	        {
	           input = input.substring(0, input.length()-1);
	        }
	        
	        url = base + "efetch.fcgi?db=pubmed&id="+input+"&retmode=xml&rettype=abstract";
	    }
	    else if (searcher.equalsIgnoreCase("author") )
	    {
	        
	        
	     
	        
	       input = input.replaceAll("\\s", "+");
	        if( input.charAt(input.length()-1) == ((char) '+')   )
	        {
	           input = input.substring(0, input.length()-1);
	        }
	        
	        url = base + "esearch.fcgi?db=pubmed&term="+input+"&retmax=200&tool=ceptools";
	    }
	    

	   
	    
}



	    
	    public void processUrl() throws Exception
	    {
	    	searcher = "title";
	    	initialProcess();
	    	//selectedPub = new PubList();
	    	
	    	
	    	SAXReader reader = new SAXReader();
	    	SAXReader reader2 = new SAXReader();
		     Document document;
	        
	         OutputFormat format = OutputFormat.createPrettyPrint();
	      
	         XMLWriter writer = new XMLWriter( System.out, format );
	         
	         String mytitle, myabstract, myyear, myfullname;
	         Element journalname, journalyear, journalmonth, journalday, journalvolume, journalissue, journalpagestart, epubday, epubmonth, epubyear, pubdoi;
	         int mypmid;
	         
	         
	         List<String> mylauthors = new ArrayList<String>();
	         List<String> myfauthors = new ArrayList<String>();
	         List<String> myfnames = new ArrayList<String>();
	         
	         
	         
	        
	       
	         
	         //PubMed

	          if (searcher.equalsIgnoreCase("title"))
	           {
	        	  
	        	
	        	 int counter = 1;
	        	  document = reader.read(url);
	                   List idlist = document.selectNodes( "//IdList/Id" );
	                   Iterator iditer = idlist.iterator();
	                   String pubmedlist = "";
	                   int x = 0;
	                   this.counter = 0;
	               

	            
	                   while(iditer.hasNext())
	                   {
	                       Element pelement= (Element) iditer.next();
	                       if(x==0)
	                       {
	                       pubmedlist += pelement.getText() ;
	                       x+=1;
	                       }
	                       else
	                       {
	                           pubmedlist += "," + pelement.getText() ; 
	                       }
	                   }
	                   String msg = "";
	                   msg += pubmedlist;
	             
	                 url = base + "efetch.fcgi?db=pubmed&id="+pubmedlist+"&retmax=200&retmode=xml&rettype=abstract";   
	                 Document pubdoc = reader2.read(url);   
	            

	  	           List<Node> thelist = pubdoc.selectNodes("//PubmedArticle| //PubmedBookArticle");
	  	           
	      
	  	          
	  	           List currlist;
	  	           Element abstractnode, titlenode, yearsnode, pmidnode;
	  	           List firstnamenode;
	  	           List lastnamenode;
	  	          
	  	           
	  	           int n= 1;
	  	           int currindex = 0;
	  	           
	  	          for (Node currnode: thelist)
	  	          {
	  	           mylauthors = new ArrayList<String>();
	  		        myfauthors = new ArrayList<String>();
	  		       myfnames = new ArrayList<String>();
	  	        	  epubsum = epubsum2 = authorfull =  "";
	  	
	  	              titlenode= (Element) currnode.selectSingleNode(".//ArticleTitle | .//BookTitle");
	  	              yearsnode= (Element) currnode.selectSingleNode(".//PubDate/Year | .//DateCompleted/Year | .//DateCreated/Year");
	  	              journalname =(Element) currnode.selectSingleNode(".//Journal/Title");
	  	              journalyear =(Element) currnode.selectSingleNode(".//PubDate/Year"); 
	  	              journalmonth =(Element) currnode.selectSingleNode(".//PubDate/Month"); 
	  	          
	  	              journalday =(Element) currnode.selectSingleNode(".//PubDate/Day"); 
	  	              journalvolume =(Element) currnode.selectSingleNode(".//JournalIssue/Volume"); 
	  	              journalissue =(Element) currnode.selectSingleNode(".//JournalIssue/Issue"); 
	  	              journalpagestart =(Element) currnode.selectSingleNode(".//Pagination/MedlinePgn");
	  	         
	  	            
	  	          epubday = (Element) document.selectSingleNode(".//PubMedPubDate[@PubStatus='aheadofprint']/Day  | .//PubMedPubDate[@PubStatus='epublish']/Day "); 
	              epubmonth = (Element) document.selectSingleNode(".//PubMedPubDate[@PubStatus='aheadofprint']/Month | .//PubMedPubDate[@PubStatus='epublish']/Month");
	              epubyear =(Element) document.selectSingleNode(".//PubMedPubDate[@PubStatus='aheadofprint']/Year | .//PubMedPubDate[@PubStatus='epublish']/Year");
	              
	              
	              pubdoi  =(Element) currnode.selectSingleNode(".//ArticleId[@IdType='doi']"); 
	              
	              
	              
	              
	  	              firstnamenode= currnode.selectNodes(".//ForeName");
	  	              lastnamenode=  currnode.selectNodes(".//LastName");
	  	              abstractnode = (Element) currnode.selectSingleNode(".//Abstract/AbstractText[1]");
	  	              pmidnode = (Element) currnode.selectSingleNode(".//PMID");
	  	              myfnames = new ArrayList<String>();
	  	              Iterator fiter = firstnamenode.iterator();
	  	              Iterator liter = lastnamenode.iterator();
	  	           
	  	              if(journalname !=null)
	  	              {
	  	            jn = journalname.getText();
	  	              }
	  	              if(journalvolume!=null)
	  	              {
		              jv = journalvolume.getText();
	  	              }
	  	            if(journalissue!=null)
	  	            {
		              ji = journalissue.getText();
	  	            }
	  	          if(journalmonth!=null)
	  	          {
		              jm = journalmonth.getText();
	  	          }
	  	        if(journalyear!=null)
	  	        {
		              jy = journalyear.getText();
	  	        }
	  	        	if(journalpagestart!=null)
	  	        	{
		              jsp =journalpagestart.getText();
	  	        		}
	  	        	if(journalday != null)
	  	        	{
		              jd = journalday.getText();
	  	        	}
	  	        	if(epubday != null)
	  	        	{
	  	          epday = epubday.getText();
	  	        	}
	  	        	if(epubmonth != null)
	  	        	{
	              epmonth = epubmonth.getText();
	  	        	}
	  	        	if(epubyear != null)
	  	        	{
	              epyear = epubyear.getText();
	  	        	}
	  	        	if (pubdoi != null)
	  	        	{
	  	        		
	  	        		doi = "doi: " + pubdoi.getText();
	  	        	}
	  	        	
	  	        	if(jv.length()>0)
	  	        	{
	  	        		epubsum2 +=  jv ;
	  	        	}
	  	        	
	  	        	if(ji.length()>0)
	  	        	{
	  	        		epubsum2 += "("+ ji + ")"+ ":";
	  	        	}
	  	        	
	  	        	if(jsp.length()>0)
	  	        	{
	  	        		epubsum2 += jsp + ".";
	  	        	}
	  	        	
	  	        	
	  	        	
		     
		              
		              
		              if( epmonth.length()<1 && epyear.length()<1  && epday.length()<1)
	  	              {
	  	            	epubsum = "[Epub ahead of print]"; 
	  	              }
		              else if(epyear.length()>0)
		              {
		            	epubsum = "Epub "  + epyear + " " + epmonth + " " + epday;
		              }
		              else
		              {
		            	epubsum = "";
		              }
	  	  
	  	              mytitle = titlenode.getText();
	  	         
	  	          
	  	    
	  	              myyear = yearsnode.getText();
	  	         
	  	            	
	  	          
	  	           
	  	              mypmid = Integer.valueOf(pmidnode.getText());
	  	           
	  	            	
	  	      
	  	              
	  	         
	  	                
	  	                while(fiter.hasNext())
	  	                {
	  	                 Element fname =  (Element) fiter.next();   
	  	                 Element lname =  (Element) liter.next();  
	  	                 
	  	                 myfauthors.add(fname.getText());
	  	               mylauthors.add(lname.getText());
	  	               
	  	             myfullname = fname.getText() + " " + lname.getText();
		                myfnames.add(myfullname);
		                
		                if(fiter.hasNext())
	            		{
	            
	            		authorfull = authorfull + myfullname + ", ";
	            		}
		                else
		                {
		                	authorfull = authorfull + myfullname ;
		                }
	  	                 
	  	                }
	  	                
	  	              
	  	              if(abstractnode != null)
	  	              {   
	  	            	myabstract = abstractnode.getText();
	  	              }
	  	              else
	  	              {
	  	            	  myabstract = "NO ABSTRACT FOUND.";
	  	              }    
	  	              
	  	           
	  	             
	  	              
	  	            publications.add(new Publication(mytitle, myabstract, myyear, myfauthors, mylauthors, myfnames, jv, jn,jy, jm, jd, jsp, ji, epday, epmonth, epyear, doi, epubsum, epubsum2, authorfull, mypmid, counter));
	  	           
	  	           
	  	           counter++;
	  	              n++;
	  	              currindex++;
	  	          }  
	  	         
	  	       
	             
	           }
	
	         
	
   
	  String currtitle, currabstract, currafull, curryear = "";
	  
	  for(Publication mypub: publications)
	  {
		  currtitle = mypub.getTitle();
		  currabstract = mypub.getAbstract();
		  curryear = mypub.getYear();
		  currafull = mypub.getAuthorfull();
		  
		 
		  

		 
		  
	  }

	         
	    
	      
	    
	         
	    }   
	    
	    
}
	  

