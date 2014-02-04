<uploader:uploader 
	id="uploader" 
	url="${[controller:'usuario', action:'subirAsigancionMedicosFile']}" multiple="false"
	>
	
	<uploader:onComplete>
	    if ( !responseJSON.error )
	    {
	    	filenameObj = document.getElementById('filename');
		    filenameObj.value = responseJSON.filename;
	    }
	</uploader:onComplete>
</uploader:uploader>