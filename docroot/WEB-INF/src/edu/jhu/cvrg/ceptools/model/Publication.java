package edu.jhu.cvrg.ceptools.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;







public class Publication implements Serializable{
		  
        
        private List<String> fauthors;
	    private List<String> lauthors;
	    private List<String> fullnames;
	    private List<FileStorer> fstorefiles; 
	    private String title;
	    private String theabstract;
	    private String year;
	    private String journalname;
	    private String journalvolume;
	    private String journalissue;
	    private String journalyear;
	    private String journalday;
	    private String   journalmonth;
	    private String journalstartpg;
	    private String epubday;
	    private String epubmonth;
	    private String epubyear;
	    private String epubsum, epubsum2;
	    private String doi;
	    private String authorfull;
	    private int pmid;
	    private int index;
	    private boolean exists;
	    private boolean completed;

	    private List<File> files;
	    private List<String> filenames;
	    private static final long serialVersionUID = 5L;
	   

        
        public Publication()
        {
        	
        	fauthors = new ArrayList<String>();
        	lauthors = new ArrayList<String>();
        	fullnames = new ArrayList<String>();
        	files = new ArrayList<File>();
        	filenames = new ArrayList<String>();
        	fstorefiles = new ArrayList<FileStorer>();
        	title = "";
        	theabstract = "";
        	year = "";
        	epubmonth = epubyear = epubday = doi = epubsum = epubsum2 = authorfull = "";
        	journalvolume = journalname = journalissue = journalyear = journalday = journalmonth = journalstartpg="";
        	
        	index = -1;
        	pmid = 0;
        	exists = completed = false;
        }

        
        public Publication(String t, String a, String y, List<String> fa, List<String> la, List<String> fn, String jv, String jn, String jy, String jm, String jd, String jsp, String ji, String epday, String epmonth, String epyear, String doi,String epubsum, String epubsum2, String afull, int pmid, int in)
        {
        	this.theabstract = a;
        	this.title = t;
        	this.year = y;
        	this.fauthors = fa;
        	this.lauthors = la;
        	this.fullnames = fn;
        	this.index = in;
        	this.pmid = pmid;
        	this.journalvolume = jv;
        	this.journalday = jd;
        	this.journalissue = ji;
        	this.journalmonth = jm;
        	this.journalname = jn;
        	this.journalstartpg = jsp;
        	this.journalyear = jy;
        	this.epubday = epday;
        	this.epubmonth = epmonth;
        	this.epubyear = epyear;
        	this.doi = doi;
        	this.epubsum = epubsum;
        	this.epubsum2 = epubsum2;
        	this.authorfull = afull;
        	
        	
        }
        
        public List<FileStorer> getFstorefiles()
        {
        	return fstorefiles;
        }
        
        public void setFstorefiles(List<FileStorer> f)
        {
        	fstorefiles = f;
        }
        
        public void setAuthorfull(String a)
        {
        	authorfull = a;
        }
        
        public String getAuthorfull()
        {
        	return authorfull;
        }
  
        public int getIndex()
        {
        	return index;
        }
        
        public void setIndex(int i)
        {
        	this.index = i;
        }
        
        public void setCompleted(boolean s)
        {
        	completed = s;
        }
        
        public boolean getCompleted()
        {
        	return completed;
        }
        
        public void setExists(boolean s)
        {
        	exists = s;
        }
        
        public boolean getExists()
        {
        	return exists;
        }
        
      public void setFullnames(List<String> fullnames)
      {
    	  this.fullnames = fullnames;
      }
      
      public List<String> getFullnames()
      {
    	  return fullnames;
      }
        
	   public void setYear(String year )
	   {
		   this.year = year;
	   }
	   
	   public void setTitle(String title)
	   {
		   this.title = title;
	   }
	   
	   public void setAbstract(String theabstract )
	   {
		   this.theabstract = theabstract;
	   }
	   
	   public void setLauthors(List<String> lauthors )
	   {
		   this.lauthors = lauthors;
	   }
	   
	   public void setFauthors (List<String> fauthors )
	   {
		   this.fauthors  = fauthors;
	   }
	   
	   
	   public String getAbstract()
	   {
		  return theabstract; 
	   }
	   
	   public int getPmid()
	   {
		   return pmid;
	   }
	   
	   public void setPmid(int pmid)
	   {
		   this.pmid = pmid;
	   }
	
	   
	   public String getTitle()
	   {
		   return title;
	   }
	   
	   public String getYear()
	   {
		   return year;
	   }
	   
	   public List<String> getLauthors ()
	   {
		  return lauthors; 
	   }
    
      public List<String> getFauthors ()
	   {
		  return fauthors; 
	   }
	   

      public void setFilenames(List<String> filenames )
	   {
		   this.filenames = filenames;
	   }
      public void setFiles(List<File> files)
	   {
		   this.files = files;
	   }
      public List<String> getFilenames( )
	   {
		   return filenames;
	   }
      public List<File> getFiles( )
	   {
		   return files;
	   }
	   
      public String getJournalname ()
      {
    	  return journalname;
      }
      
      public String getJournalissue ()
      {
    	  return journalissue;  
      }
      public String getJournalday ()
      {
    	  return journalday;
      }
      public String getJournalmonth ()
      {
    	  return journalmonth;
      }
      public String getJournalyear ()
      {
    	  return journalyear;
      }
      public String getJournalstartpg ()
      {
    	  return journalstartpg;
      }
      public String getJournalvolume ()
      {
    	  return journalvolume;
      }
      
      public void setJournalname (String jn)
      {
    	  this.journalname = jn;
      }
      
      public void setJournalvolume (String jv)
      {
    	  this.journalvolume = jv;
      }
      
      public void setJournalissue (String ji)
      {
    	  this.journalissue = ji;
      }
      
      public void setJournalyear (String jy)
      {
    	  this.journalyear = jy;
      }
      
      public void setJournalmonth (String jm)
      {
    	  this.journalmonth = jm;
      }
      
      public void setJournalday (String jd)
      {
    	  this.journalday = jd;
      }
      
      public void setJournalstartpg (String jp)
      {
    	  this.journalstartpg = jp;
      }
	   
	   public void setEpubmonth (String epmonth)
	   {
		   this.epubmonth = epmonth;
	   }
	   
	   public void setEpubyear (String epyear)
	   {
		   this.epubyear = epyear;
	   }
	   
	   
	   public void setEpubday (String epday)
	   {
		  this.epubday = epday; 
	   }
	   
	   
	   public void setDoi (String doi)
	   {
		   this.doi = doi;
	   }
	   
	   
	   public String getEpubday ()
	   {
		 return epubday;  
	   }
	   
	   public String getEpubmonth ()
	   {
		  return epubmonth; 
	   }
	   
	   public String getEpubyear ()
	   {
		   return epubyear;
	   }
	   
	   public String getDoi ()
	   {
		 return doi;  
	   }
	   
	   public String getEpubsum ()
	   {
		   return epubsum;
	   }
	   
	   public void setEpubsum (String ep)
	   {
		   this.epubsum = ep;
	   }
	   
	   public String getEpubsum2 ()
	   {
		   return epubsum2;
	   }
	   
	   public void setEpubsum2 (String ep)
	   {
		   this.epubsum2 = ep;
	   }
	   
        

}