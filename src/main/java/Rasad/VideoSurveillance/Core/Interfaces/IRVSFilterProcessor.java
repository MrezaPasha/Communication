package Rasad.VideoSurveillance.Core.Interfaces;

import Rasad.VideoSurveillance.Core.*;

public interface IRVSFilterProcessor
{
	//Size FilteredResolution { get; }
	Bitmap ProcessFrame(int width, int height, IntPtr scan0, int stride);
}