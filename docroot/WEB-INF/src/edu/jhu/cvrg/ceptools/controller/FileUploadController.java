package edu.jhu.cvrg.ceptools.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.liferay.portal.kernel.util.PropsUtil;


import edu.jhu.cvrg.ceptools.model.FileStorer;

@ManagedBean(name="fileUploadController")
@ViewScoped

public class FileUploadController implements Serializable{

 
	
	private UploadedFile file;
    private ArrayList<UploadedFile> allfiles;
    private int pmid;
    private static Logger logger = Logger.getLogger(FileUploadController.class.getName());
    private ArrayList<FileStorer> filesondrive;
    private static final long serialVersionUID = 2L;
    
    public FileUploadController()
    {
    	allfiles = new ArrayList<UploadedFile> ();
    	filesondrive = new ArrayList<FileStorer> ();
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
    
    private void RetrieveFiles() {
		 

    	String currlocation = PropsUtil.get("data_store2") + this.pmid + "/";
    	File folder = new File(currlocation);
    	
    	
    	if(folder.exists())
    	{
    	
    		for(File currfile: folder.listFiles())
    		{
    	
    			String absolutePath = currfile.getAbsolutePath();
    			FileStorer currfilestore = new FileStorer();
    			currfilestore.setFilename(currfile.getName());
    			currfilestore.setFilelocation(currlocation);
    			currfilestore.setFiletype(FilenameUtils.getExtension(currfile.getName()));
    			currfilestore.setLocalfilestore( absolutePath.substring(0,absolutePath.lastIndexOf(File.separator)));	
    			filesondrive.add(currfilestore);
    			
    		}
    	}
    	
    	
    }
    public boolean checkUploads()
    {
    	
    	boolean filecheck = false;
    	
    	for(FileStorer currfile: filesondrive)
    	{
    		if(currfile.getFilename().equals(file.getFileName()))
    		{
    			filecheck = true;
    		}
    	}

		    	if(filecheck == true)
		    	{
		    		return true;
		    	}
		    	else
		    	{
		    		return false; 		
		    	}
    }
    
    
    public void FileSave()
    {
    	
    	String currpmid  = String.valueOf(pmid);
    	String currlocation = PropsUtil.get("data_store2")+ currpmid+"/";
    	ArrayList<org.primefaces.model.UploadedFile> thefiles = new ArrayList<org.primefaces.model.UploadedFile>();
    	File thedir = new File(currlocation);

    	if(!thedir.exists())
    	{
 
    	 thedir.mkdirs();
   
    	}
    
    	
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
  
    		this.setFile(event.getFile());
    	
    		if(filesondrive.isEmpty())
    		{
    			RetrieveFiles();	
    		}
    		else
    		{
    			filesondrive = new ArrayList<FileStorer> ();
    			RetrieveFiles();
    		}
    	
    	    if(!checkUploads())
    	    {
    	    	this.allfiles.add(event.getFile());
    	    	FileSave();
    	    	FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    	    }
    	    else
    	    {
    	    	FacesMessage msg = new FacesMessage("You can not upload a file with the same name as a file already stored.", file.getFileName() + " was not saved. Please change the name and try again.");
    	    	FacesContext.getCurrentInstance().addMessage(null, msg);
    	    }
    	
    	

    	}
    
    }
    

    
    
}