package Rasad.Core.Drawing;

import Rasad.Core.*;

public class TGifReader implements IFrameSource
{
	private BitmapImage _Bitmap;
	public final void Load(String fileName)
	{
		_Bitmap = new BitmapImage();
		_Bitmap.BeginInit();
		_Bitmap.UriSource = new Uri(fileName);
		_Bitmap.CacheOption = BitmapCacheOption.OnLoad;
		_Bitmap.EndInit();
		Decode();
	}

	public final void Load(BitmapImage bitmap)
	{
		_Bitmap = bitmap;
		Decode();
	}

	private void Decode()
	{
		BitmapDecoder decoder;
		if (_Bitmap.StreamSource != null)
		{
			decoder = BitmapDecoder.Create(_Bitmap.StreamSource, BitmapCreateOptions.DelayCreation, BitmapCacheOption.OnLoad);
		}
		else if (_Bitmap.UriSource != null)
		{
			decoder = BitmapDecoder.Create(_Bitmap.UriSource, BitmapCreateOptions.DelayCreation, BitmapCacheOption.OnLoad);
		}
		else
		{
			return;
		}

		setFrames(decoder.Frames.ToList());
	}

	private java.lang.Iterable<BitmapSource> Frames;
	public final java.lang.Iterable<BitmapSource> getFrames()
	{
		return Frames;
	}
	private void setFrames(java.lang.Iterable<BitmapSource> value)
	{
		Frames = value;
	}
}