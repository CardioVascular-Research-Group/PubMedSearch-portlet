package edu.jhu.cvrg.ceptools.controller;



import java.io.Serializable;


import java.io.InputStream;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;  
import org.primefaces.model.StreamedContent;  


import edu.jhu.cvrg.ceptools.model.FileStorer;  


  
@ManagedBean(name="fileDownloadController")
@ViewScoped


  
public class FileDownloadController implements Serializable{  
  
    private StreamedContent file;  
    private String filelocation;
    private String filename;
    private String filetype;
    private static final long serialVersionUID = 1L;
    

    
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