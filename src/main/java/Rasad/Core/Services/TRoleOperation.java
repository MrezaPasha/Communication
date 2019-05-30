package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.SecurityOperationEnum;

public class TRoleOperation
{
	private TRoleOperation()
	{
	}
	public TRoleOperation(SecurityOperationEnum operation, Short cameraID, Integer viewGroupID)
	{
		this.setOperation(operation);
		this.setCameraID(cameraID);
		this.setViewGroupID(viewGroupID);
	}

	private SecurityOperationEnum Operation = SecurityOperationEnum.values()[0];
	public final SecurityOperationEnum getOperation()
	{
		return Operation;
	}
	public final void setOperation(SecurityOperationEnum value)
	{
		Operation = value;
	}
	private Short CameraID = null;
	public final Short getCameraID()
	{
		return CameraID;
	}
	public final void setCameraID(Short value)
	{
		CameraID = value;
	}
	private Integer ViewGroupID = null;
	public final Integer getViewGroupID()
	{
		return ViewGroupID;
	}
	public final void setViewGroupID(Integer value)
	{
		ViewGroupID = value;
	}

	@Override
	public String toString()
	{
		return String.format("%1$s - %2$s - %3$s , CameraID : %4$s, ViewGroupID : %5$s", getOperation().getValue(), getOperation().toString(), getOperation().getValue(), Integer.toString(getCameraID()), Integer.toString(getViewGroupID()));
		//if (CameraID == null)
		//{
		//    return string.Format("{0} - {1} - {2}", (int)Operation, Operation.ToString(), Operation.GetEnumDescription());
		//}
		//else
		//{
		//    return string.Format("{0} - {1} - {2} , CameraID : {3}", (int)Operation, Operation.ToString(), Operation.GetEnumDescription(), CameraID);
		//}
	}
}