package Rasad.Core.Media;

import Rasad.Core.*;

public class TSoundPlayerMultiple
{
	private TSoundPlayerMultiple(int playCount, tangible.Action0Param complete)
	{
		this.setPlayCount(playCount);
		this.setComplete(() -> complete.invoke());
		this._Player = new TSoundPlayer();
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TSoundPlayerMultiple(int playCount, Action complete, byte[] stream, string fileExtention)
	public TSoundPlayerMultiple(int playCount, tangible.Action0Param complete, byte[] stream, String fileExtention)
	{
		this(playCount, complete);
		_Player.OpenStream(stream, fileExtention);

	}
	public TSoundPlayerMultiple(int playCount, tangible.Action0Param complete, String fileName)
	{
		_Player.OpenFile(fileName);

	}

	private TSoundPlayer _Player;
	private void _Player_PlaybackStateChanged(Object sender, tangible.EventArgs e)
	{
		if (_Player.getState() == NAudio.Wave.PlaybackState.Stopped)
		{
			if (getPlayCount() > 0)
			{
				Play();
			}
			else
			{
				Stop();
			}
		}
	}

	public final void Play()
	{
		_Player.PlaybackStateChanged.removeListener("_Player_PlaybackStateChanged");

		setPlayCount(getPlayCount() - 1);
		if (_Player.getState() != NAudio.Wave.PlaybackState.Stopped)
		{
			_Player.Stop();
		}
		_Player.PlaybackStateChanged.addListener("_Player_PlaybackStateChanged", (Object sender, tangible.EventArgs e) -> _Player_PlaybackStateChanged(sender, e));
		_Player.Play();
	}
	private int PlayCount;
	public final int getPlayCount()
	{
		return PlayCount;
	}
	private void setPlayCount(int value)
	{
		PlayCount = value;
	}
	private tangible.Action0Param Complete;
	public final tangible.Action0Param getComplete()
	{
		return Complete;
	}
	public final void setComplete(tangible.Action0Param value)
	{
		Complete = () -> value.invoke();
	}
	public final void Stop()
	{
		if (_Player != null)
		{
			_Player.PlaybackStateChanged.removeListener("_Player_PlaybackStateChanged");
			_Player.close();
			_Player = null;
		}

		if (getComplete() != null)
		{
			Complete();
			setComplete(() -> null.invoke());
		}
	}

	public final boolean getIsPlaying()
	{
		return _Player != null && _Player.getState() == NAudio.Wave.PlaybackState.Playing;
	}
}