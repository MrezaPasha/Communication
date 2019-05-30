package main.java.Rasad.Core.Services;

import Rasad.Core.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.All, AllowMultiple = true)] public sealed class TRoleChildNodeAttributes : Attribute
public final class TRoleChildNodeAttributes
{
	public SecurityOperationEnum SOEAttribute = SecurityOperationEnum.values()[0];

	public TRoleChildNodeAttributes(SecurityOperationEnum operation)
	{
		this.SOEAttribute = operation;
	}
}