package edu.jhu.cvrg.ceptools.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;





import org.apache.log4j.Logger;

import edu.jhu.cvrg.ceptools.model.FileStorer;

@ManagedBean(name="zipDirectory")
@ViewScoped

public class ZipDirectory implements Serializable{
	
	  private static Logger logger = Logger.getLogger(ZipDirectory.class.getName());
	  private static int loopcounter;
	  private int pmid;

	public ZipDirectory()
	{
		loopcounter = 0;
	}
	
	public int getLoopcounter()
	{
		return loopcounter;
	}
	
	public void setLoopcounter(int s)
	{
	loopcounter = s;	
	}
	
	public void setPmid(int i)
	{
		pmid = i;
	}
	
	public int getPmid()
	{
		return pmid;
	}
	
	
	public void Zipfiles (String foldertocompress, String zfile) throws IOException
	{
		byte[] buffer = new byte[1024];
		
		
		String zipFile = zfile;
		String srcDir = foldertocompress;
		InputStream in = null;
		String pmidfile = pmid + ".zip";
		
		try {
			
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
		
			

			File dir = new File(srcDir);

			File[] files = dir.listFiles();

			for (int i = 0; i < files.length; i++) {
				
				if(!files[i].getName().equals(pmidfile))
				{
				
				FileInputStream fis = new FileInputStream(files[i]);

				// begin writing a new ZIP entry, positions the stream to the start of the entry data
				zos.putNextEntry(new ZipEntry(files[i].getName()));
				
				int length;

				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}

				zos.closeEntry();

				// close the InputStream
				fis.close();
				}
			}

			// close the ZipOutputStream
			zos.close();
			
		} finally {
	        if( null != in) {
	            try 
	            {
	                in.close();
	            } catch(IOException ex) {
	                // log or fail if you like
	            	 ex.printStackTrace();
	            }
	        }
	        
		}
}

	
		
		

}
