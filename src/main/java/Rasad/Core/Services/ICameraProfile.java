package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import org.apache.tools.ant.types.resources.selectors.Size;

public interface ICameraProfile
{
	String getToken();

	String getTitle();

	String getUrl();
	void setUrl(String value);

	Size getResolution();
	void setResolution(Size value);

	int getFrameRate();
	void setFrameRate(int value);


	//TODO MRCOMMENT : uncomment after insert folder CameraProviders
	/*TVideoEncodingType getVideoEncodingType();
	void setVideoEncodingType(TVideoEncodingType value);

	TProfileSource getSource();
	void setSource(TProfileSource value);*/
}