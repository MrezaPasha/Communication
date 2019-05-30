package main.java.Rasad.Core.Services.NotificationService.Recording;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyCameraStreamOperation : TMessageBase
public class TNotifyCameraStreamOperation extends TMessageBase
{
	private TNotifyCameraStreamOperation()
	{
	}

	public TNotifyCameraStreamOperation(short cameraID, CameraStreamOperation operation)
	{
		super(cameraID);
		this.setCameraID(cameraID);
		this.setOperation(operation);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public short CameraID {get;private set;}
	private short CameraID;
	public final short getCameraID()
	{
		return CameraID;
	}
	private void setCameraID(short value)
	{
		CameraID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public CameraStreamOperation Operation {get;private set;}
	private CameraStreamOperation Operation = CameraStreamOperation.values()[0];
	public final CameraStreamOperation getOperation()
	{
		return Operation;
	}
	private void setOperation(CameraStreamOperation value)
	{
		Operation = value;
	}

	@Override
	public String toString()
	{
		return String.format("TNotifyCameraStreamOperation, CameraID=%1$s, Operation=%2$s", this.getCameraID(), this.getOperation().toString());
	}
}