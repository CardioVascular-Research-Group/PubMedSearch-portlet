package edu.jhu.cvrg.ceptools.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


import com.liferay.portal.kernel.util.PropsUtil;


import edu.jhu.cvrg.ceptools.main.PubMedSearch;

@ManagedBean(name="fileUploadController")
@ViewScoped

public class FileUploadController implements Serializable{

    /**
	 * 
	 */
	
	private UploadedFile file;
    private ArrayList<UploadedFile> allfiles;
    private int pmid;
    private static Logger logger = Logger.getLogger(PubMedSearch.class.getName());
    
    public FileUploadController()
    {
    	allfiles = new ArrayList<UploadedFile> ();
    	pmid = 0;
    	
    }

    public UploadedFile getFile() {
        return file;
    }
    
    public int getPmid()
    {
    	return pmid;
    }
    
    public void setPmid(int pmid)
    {
    	this.pmid = pmid;
    }
    
    public void setAllFiles(ArrayList<UploadedFile> allfiles)
    {
    	this.allfiles = allfiles;
    }
    
    public ArrayList<UploadedFile> getAllFiles()
    {
    	return allfiles;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        FacesMessage msg = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void FileSave()
    {
    	
    	String currpmid  = String.valueOf(pmid);
    	String currlocation = PropsUtil.get("data_store")+ currpmid+"/";
    	String locallocation =  PropsUtil.get("data_store_local") + currpmid;
    	ArrayList<org.primefaces.model.UploadedFile> thefiles = new ArrayList<org.primefaces.model.UploadedFile>();
    	
    	boolean result;
    	
    	
    	 File thedir = new File(currlocation);
    	
    	 
    	if(!thedir.exists())
    	{
 
    	 thedir.mkdirs();
   
    	}
    	else
    	{
    		
    		
    	}
    	
    	//Now add files to it
    	 try
    	   {
    	thefiles = this.getAllFiles();
    	 
    	for(org.primefaces.model.UploadedFile currfile: thefiles)
    	  { 
    	
    		
    	   String fullfilelocation = currlocation + currfile.getFileName();
    	   File myfile = new File(fullfilelocation);
    	  
    	
    	
    	  
    	   
    	     OutputStream out = new FileOutputStream(myfile);
    	
      	 InputStream in = currfile.getInputstream();
    	 
    	 
   	  
	   
	    org.apache.commons.io.IOUtils.copy(in, out);
	    
    	 
    	  out.close();
    	}
    	

    	
    	}
    	catch(Exception ex)
    	{
    		logger.info(ex);
    	}
    	
    	
    }
    


    public void handleFileUpload(FileUploadEvent event) {
    	if(event!=null)
    	{
    	UploadedFile currentfile = 	event.getFile();
    	
    	this.setFile(event.getFile());
    	this.allfiles.add(event.getFile());
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	 FileSave();
	}
}