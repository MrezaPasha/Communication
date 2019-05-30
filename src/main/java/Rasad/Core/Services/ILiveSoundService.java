package main.java.Rasad.Core.Services;

public interface ILiveSoundService
{
	void StartPlaySound(IPlayerHost playerHost);
	void StopPlaySound(IPlayerHost playerHost);

	boolean IsPlayingSound(TPlayerHost playerHost);
	void StopAll();

	void StartSendSound(ICameraInformation camera);
	void StopSendSound(ICameraInformation camera);
}