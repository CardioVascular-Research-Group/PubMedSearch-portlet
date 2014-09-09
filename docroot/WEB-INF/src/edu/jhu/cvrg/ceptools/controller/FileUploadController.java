package edu.jhu.cvrg.ceptools.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import com.liferay.portal.kernel.util.PropsUtil;

import edu.jhu.cvrg.ceptools.model.FileStorer;

@ManagedBean(name="fileUploadController")
@ViewScoped

public class FileUploadController implements Serializable{

 
	
	private UploadedFile file;
    private ArrayList<UploadedFile> allfiles;
    private ArrayList<UploadedFile> newestfiles;
    private int pmid;
    private static Logger logger = Logger.getLogger(FileUploadController.class.getName());
    private ArrayList<FileStorer> filesondrive;
    private static final long serialVersionUID = 2L;
    
    public FileUploadController()
    {
    	allfiles = new ArrayList<UploadedFile> ();
    	filesondrive = new ArrayList<FileStorer> ();
    	pmid = 0;
    	newestfiles = new ArrayList<UploadedFile> ();
    	
    }
    
    public void setFilesondrive(ArrayList<FileStorer> g)
    {
    	filesondrive = g;
    	
    }
    
    public ArrayList<FileStorer> getFilesondrive()
    {
    	return filesondrive;
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
    
    public void setAllfiles(ArrayList<UploadedFile> allfiles)
    {
    	this.allfiles = allfiles;
    }
    
    public ArrayList<UploadedFile> getAllfiles()
    {
    	return allfiles;
    }

    public void setNewestfiles(ArrayList<UploadedFile> f)
    {
    	this.newestfiles = f;
    }
    
    public ArrayList<UploadedFile> getNewestfiles()
    {
    	return newestfiles;
    }
    
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    
    public void RetrieveFiles() {
		 

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
    public void checkUploads()
    {
    	
    	 Iterator<UploadedFile> myitr = newestfiles.iterator();
    	
    	
    	 while(myitr.hasNext())
    	 {
    		 
    		 UploadedFile curruploadedfile  =	 myitr.next();
		    	for(FileStorer currfile: filesondrive)
		    	{
		    		if(currfile.getFilename().equals(curruploadedfile.getFileName()))
		    		{
		    			myitr.remove();
		    			FacesMessage msg = new FacesMessage("You can not upload a file with the same name as a file already stored.", curruploadedfile.getFileName() + " was not saved. Please change the name and try again.");
		    	    	FacesContext.getCurrentInstance().addMessage(null, msg);
		    		}
		    	}
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
    		 thefiles = newestfiles;
    		 
    	    
    		 	for(org.primefaces.model.UploadedFile currfile: thefiles)
    		 	{ 
    	
    		 		String currfilename = currfile.getFileName();
    		 		String fullfilelocation = currlocation + currfile.getFileName();
    		 		File myfile = new File(fullfilelocation);
    		 		OutputStream out = new FileOutputStream(myfile);
    		 		InputStream in = currfile.getInputstream();
    	 
    		 		org.apache.commons.io.IOUtils.copy(in, out);
	    
    		 		out.close();
    		 		FacesMessage msg = new FacesMessage("Successful", currfilename + " has been uploaded successfully.");
    				FacesContext.getCurrentInstance().addMessage(null, msg);
    		 	}
    	
    	
    	}
    	catch(Exception ex)
    	{
    		logger.error("Error: ",ex);
    	}
    	
    	
    }
    


    
    public void handleClearErr(ActionEvent event) {
    	if(event!=null)
    	{
    		 RequestContext.getCurrentInstance().execute("jQuery(\"div.fileupload-content tr.ui-state-error\").remove();");
    		
    	}
    }
    

    
    
}