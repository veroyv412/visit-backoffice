package com.cdrossi

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.springframework.web.multipart.MultipartFile;

class FileUploadService {
	
	boolean transactional = true
	
    def String uploadFile(MultipartFile file, String name, String destinationDirectory) 
	{
		def servletContext = ServletContextHolder.servletContext
		def storagePath = servletContext.getRealPath(destinationDirectory)
		
		def storagePathDirectory = new File(storagePath)
		if ( !storagePathDirectory.exists() )
		{
			storagePathDirectory.mkdirs()
		}
		
		if ( !file.isEmpty() )
		{
			file.transferTo(new File("${storagePath}/${name}"))
			return "${storagePath}/${name}"
		}
		else
		{
			return null
		}	
    }
}
