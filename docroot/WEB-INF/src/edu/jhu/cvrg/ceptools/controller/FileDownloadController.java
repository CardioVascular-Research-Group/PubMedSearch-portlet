package edu.jhu.cvrg.ceptools.controller;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;


import java.io.InputStream;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;  
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;  
import javax.servlet.http.HttpServletResponse;
  
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;  
import org.primefaces.model.StreamedContent;  


import com.liferay.compat.portal.util.PortalUtil;

import edu.jhu.cvrg.ceptools.main.PubMedSearch;
import edu.jhu.cvrg.ceptools.model.FileStorer;



import java.io.InputStream;  
import javax.faces.context.FacesContext;  
import javax.servlet.ServletContext;  
  
import org.primefaces.model.DefaultStreamedContent;  
import org.primefaces.model.StreamedContent;  


  
@ManagedBean(name="fileDownloadController")
@ViewScoped


  
public class FileDownloadController implements Serializable{  
  
    private StreamedContent file;  
    private String filelocation;
    private String filename;
    private String filetype;
    
    private static Logger logger = Logger.getLogger(PubMedSearch.class.getName());  
    
    public FileDownloadController() {  
    	filename = "";
    	filelocation = "";
    	filetype = "";
    }  
    
    public void downloadFile (FileStorer myfstore)
    {
    	this.filelocation = myfstore.getFilelocation();
   
        InputStream stream = this.getClass().getResourceAsStream(this.filelocation);  
        file = new DefaultStreamedContent(stream, myfstore.getFiletype(), myfstore.getFilename());  
    }
    
  
    public StreamedContent getFile() {  
        return file;  
    }    
    
    public void setFile(StreamedContent file) {
        this.file = file;
    }
    
    public String getFilename() {  
        return filename;  
    }    
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getFilelocation() {  
        return filelocation;  
    }    
    
    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }
    
    public String getFiletype() {  
        return filetype;  
    }    
    
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
}  