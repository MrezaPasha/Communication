package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;
import java.nio.file.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

///#region PlaybackController
//public class PlaybackController : ApiController
//{
//    //*********************************************
//    //Note : Do not Declare a method with parameter name "recordFileID" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    //*********************************************
//    public TWebApiFileTransferServer Server
//    {
//        get
//        {
//            return (TWebApiFileTransferServer)this.Configuration.Properties["Server"];
//        }
//    }

//    //private string GetRecordFilePath(Int64 IDrecordFile)
//    //{

//    //}


//    public HttpResponseMessage Get(int recordFileID)
//    {
//        var response = new HttpResponseMessage();
//        try
//        {

//            //FileInfo F = new FileInfo(GetRecordFilePath(recordFileID));
//            FileInfo F = new FileInfo(Server.ConvertFileIDToPath(recordFileID));
//            ContentInfo contentInfo = GetContentInfoFromRequest(this.Request, F.Length);
//            FileStream FileS = File.Open(F.FullName, FileMode.Open, FileAccess.Read, FileShare.Read);
//            var stream = new PartialReadFileStream(FileS, contentInfo.From, contentInfo.To);
//            response.Content = new StreamContent(stream);
//            SetResponseHeaders(response, contentInfo, F.Length);
//        }
//        catch (Exception ex)
//        {
//            TLogManager.Error(ex);
//            response.StatusCode = HttpStatusCode.NotFound;
//        }
//        return response;
//    }
//    //[MethodAttributeExceptionHandling]
//    //public HttpResponseMessage GetHandlerException()
//    //{
//    //    throw new
//    //        BusinessLayerException("4001",
//    //        "Your account is in negative. please recharge");
//    //}
//    private ContentInfo GetContentInfoFromRequest(HttpRequestMessage request, long entityLength)
//    {
//        var result = new ContentInfo
//        {
//            From = 0,
//            To = entityLength - 1,
//            IsPartial = false,
//            Length = entityLength
//        };
//        RangeHeaderValue rangeHeader = request.Headers.Range;
//        if (rangeHeader != null && rangeHeader.Ranges.Count != 0)
//        {
//            //we support only one range
//            if (rangeHeader.Ranges.Count > 1)
//            {
//                //we probably return other status code here
//                throw new HttpResponseException(HttpStatusCode.RequestedRangeNotSatisfiable);
//            }
//            RangeItemHeaderValue range = rangeHeader.Ranges.First();
//            if (range.From.HasValue && range.From < 0 || range.To.HasValue && range.To > entityLength - 1)
//            {
//                throw new HttpResponseException(HttpStatusCode.RequestedRangeNotSatisfiable);
//            }

//            result.To = range.To ?? entityLength - 1;
//            var from = range.From ?? 0;
//            result.From = from < result.To ? from : result.To;
//            result.IsPartial = true;
//            result.Length = entityLength;
//            if (range.From.HasValue && range.To.HasValue)
//            {
//                result.Length = range.To.Value - range.From.Value + 1;
//            }
//            else if (range.From.HasValue)
//            {
//                result.Length = entityLength - range.From.Value + 1;
//            }
//            else if (range.To.HasValue)
//            {
//                result.Length = range.To.Value + 1;
//            }
//        }

//        return result;
//    }
//    private void SetResponseHeaders(HttpResponseMessage response, ContentInfo contentInfo, long fileLength)
//    {
//        response.Headers.AcceptRanges.Add("bytes");
//        response.StatusCode = contentInfo.IsPartial ? HttpStatusCode.PartialContent : HttpStatusCode.OK;
//        //response.Content.Headers.ContentDisposition = new ContentDispositionHeaderValue("attachment");
//        //response.Content.Headers.ContentDisposition.FileName = Path.GetFileName(fileName);
//        //response.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
//        response.Content.Headers.ContentType = new MediaTypeHeaderValue("video/mp4");

//        response.Content.Headers.ContentLength = contentInfo.Length;
//        if (contentInfo.IsPartial)
//        {
//            response.Content.Headers.ContentRange
//                = new ContentRangeHeaderValue(contentInfo.From, contentInfo.To, fileLength);
//        }
//    }

//    [HttpGet]
//    //[ActionName("Test")]
//    public string Test()
//    {
//        return "Test Completed by mehdi nazari !!!";
//    }
//}
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region MapController
public class MapController extends ApiController
{
	//*********************************************
	//Note : Do not Declare a method with parameter name "int z,int x,int y" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//*********************************************
	public final TWebApiFileTransferServer getServer()
	{
		return (TWebApiFileTransferServer)this.Configuration.Properties["Server"];
	}
	public final HttpResponseMessage Get(int z, int x, int y)
	{
		HttpResponseMessage response = new HttpResponseMessage();
		try
		{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
			FileStream F = LocalMapTileLoader.GetTileImage(z, x, y);
			ContentInfo contentInfo = GetContentInfoFromRequest(this.Request, F.Length);
			PartialReadFileStream stream = new PartialReadFileStream(F, contentInfo.From, contentInfo.To);
			response.Content = new StreamContent(stream);
			SetResponseHeaders(response, contentInfo, F.Length);
		}
		catch (RuntimeException ex)
		{
			TLogManager.Error(ex);
			response.StatusCode = HttpStatusCode.NotFound;
		}
		return response;
	}

	private ContentInfo GetContentInfoFromRequest(HttpRequestMessage request, long entityLength)
	{
		ContentInfo result = new ContentInfo();
		result.From = 0;
		result.To = entityLength - 1;
		result.IsPartial = false;
		result.Length = entityLength;
		RangeHeaderValue rangeHeader = request.Headers.Range;
		if (rangeHeader != null && rangeHeader.Ranges.Count != 0)
		{
			//we support only one range
			if (rangeHeader.Ranges.Count > 1)
			{
				//we probably return other status code here
				throw new HttpResponseException(HttpStatusCode.RequestedRangeNotSatisfiable);
			}
			RangeItemHeaderValue range = rangeHeader.Ranges.First();
			if (range.From.HasValue && range.From < 0 || range.To.HasValue && range.To > entityLength - 1)
			{
				throw new HttpResponseException(HttpStatusCode.RequestedRangeNotSatisfiable);
			}

			result.To = (range.To != null) ? range.To : entityLength - 1;
			int from = (range.From != null) ? range.From : 0;
			result.From = from < result.To ? from : result.To;
			result.IsPartial = true;
			result.Length = entityLength;
			if (range.From.HasValue && range.To.HasValue)
			{
				result.Length = range.To.Value - range.From.Value + 1;
			}
			else if (range.From.HasValue)
			{
				result.Length = entityLength - range.From.Value + 1;
			}
			else if (range.To.HasValue)
			{
				result.Length = range.To.Value + 1;
			}
		}

		return result;
	}
	private void SetResponseHeaders(HttpResponseMessage response, ContentInfo contentInfo, long fileLength)
	{
		response.Headers.AcceptRanges.Add("bytes");
		response.StatusCode = contentInfo.IsPartial ? HttpStatusCode.PartialContent : HttpStatusCode.OK;
		//response.Content.Headers.ContentDisposition = new ContentDispositionHeaderValue("attachment");
		//response.Content.Headers.ContentDisposition.FileName = Path.GetFileName(fileName);
		//response.Content.Headers.ContentType = new MediaTypeHeaderValue("application/octet-stream");
		response.Content.Headers.ContentType = new MediaTypeHeaderValue("image/png");

		response.Content.Headers.ContentLength = contentInfo.Length;
		if (contentInfo.IsPartial)
		{
			response.Content.Headers.ContentRange = new ContentRangeHeaderValue(contentInfo.From, contentInfo.To, fileLength);
		}
	}



	public static class LocalMapTileLoader
	{


		private static tangible.Func3Param<Integer, Integer, Integer, String> getUri;
		private static String uriFormat = "C:\\ProgramData\\MapControl\\TileCache\\{z}\\{x}\\{y}.png";
		public static String getUriFormat()
		{
			return uriFormat;
		}
		public static void setUriFormat(String value)
		{
			uriFormat = value;
			getUri = (int arg1, int arg2, int arg3) -> GetBasicUri(arg1, arg2, arg3);

		}


//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
		public static FileStream GetTileImage(int z, int x, int y)
		{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
			FileStream ret = null;
			String path = GetUri(x, y, z);

			if (!tangible.StringHelper.isNullOrEmpty(path))
			{
				ret = CreateImage(path);
			}
			return ret;
		}

		public static String GetUri(int x, int y, int z)
		{
			return getUri != null ? getUri.invoke(x, y, z) : null;
		}
		private static String GetBasicUri(int x, int y, int zoomLevel)
		{
			return getUriFormat().replace("{x}", String.valueOf(x)).replace("{y}", String.valueOf(y)).replace("{z}", String.valueOf(zoomLevel));
		}
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
		private static FileStream CreateImage(String path)
		{
			FileInputStream stream = null;

			if ((new File(path)).isFile())
			{
				try
				{
					stream = new FileInputStream(path);

				}
				catch (RuntimeException ex)
				{
					Trace.TraceWarning("Creating tile image failed: {0}", ex.getMessage());
				}
			}
			else
			{

			}

			return stream;
		}


	}
}