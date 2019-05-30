package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;
import java.nio.file.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region StreamFilesController

public class StreamFilesController extends ApiController
{
	public final TWebApiFileTransferServer getServer()
	{
		return (TWebApiFileTransferServer)this.Configuration.Properties["Server"];
	}

	/** 
	 Download file
	 
	 @param fileName FileName value
	 @return File stream response
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [HttpGet] public HttpResponseMessage DownloadFile(string fileName)
	public final HttpResponseMessage DownloadFile(String fileName)
	{
		HttpResponseMessage response = Request.CreateResponse();
		try
		{

			File fileInfo = new File(HttpUtility.HtmlDecode(fileName));

			if (!fileInfo.exists())
			{
				response = Request.CreateResponse(HttpStatusCode.NotFound, fileName);
			}
			else
			{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
				Stream Data = new FileInputStream(fileInfo.getPath());

				response.Headers.AcceptRanges.Add("bytes");
				response.StatusCode = HttpStatusCode.OK;
				response.Content = new StreamContent(Data);
				response.Content.Headers.ContentDisposition = new ContentDispositionHeaderValue("attachment");
				response.Content.Headers.ContentDisposition.FileName = (new File(fileInfo.getPath())).getName();
				response.Content.Headers.ContentDisposition.Size = fileInfo.length();

				response.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
				response.Content.Headers.ContentLength = fileInfo.length();
			}
		}
		catch (RuntimeException exception)
		{
			response = Request.CreateErrorResponse(HttpStatusCode.InternalServerError, exception);
		}
		return response;
	}

	// POST api/files
	public final void UploadFile()
	{
		if (!(new File(getServer().getServerRootFolder())).isDirectory())
		{
			(new File(getServer().getServerRootFolder())).mkdirs();
		}
		// Check if the request contains multipart/form-data.
		if (!Request.Content.IsMimeMultipartContent())
		{
			throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
		}
		MultipartFormDataStreamProvider provider = new MultipartFormDataStreamProvider(getServer().getServerRootFolder());

		// Read the form data and return an async data.
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var result = Request.Content.ReadAsMultipartAsync(provider);
		result.Wait();

		for (MultipartFileData item : result.Result.FileData)
		{
			getServer().OnFileRecieved(item);
		}
	}


	//[ActionName("Test")]
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [HttpGet] public string Test()
	public final String Test()
	{
		return "Test Completed by mehdi nazari !!!";
	}
}