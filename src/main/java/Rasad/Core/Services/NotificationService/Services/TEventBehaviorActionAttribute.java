package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.Assembly, Inherited = false, AllowMultiple = true)] public sealed class TEventBehaviorActionAttribute : Attribute
public final class TEventBehaviorActionAttribute
{
    public TEventBehaviorActionAttribute(java.lang.Class actionType, java.lang.Class actionEditorType, String title, String description, String imageUrl)
    {
        this.setActionType(actionType);
        this.setActionEditorType(actionEditorType);
        this.setTitle(title);
        this.setImageUrl(imageUrl);
        this.setDescription(description);
    }

    private String Title;
    public String getTitle()
    {
        return Title;
    }
    public void setTitle(String value)
    {
        Title = value;
    }
    private String Description;
    public String getDescription()
    {
        return Description;
    }
    public void setDescription(String value)
    {
        Description = value;
    }
    private String ImageUrl;
    public String getImageUrl()
    {
        return ImageUrl;
    }
    public void setImageUrl(String value)
    {
        ImageUrl = value;
    }
    private java.lang.Class ActionType;
    public java.lang.Class getActionType()
    {
        return ActionType;
    }
    public void setActionType(java.lang.Class value)
    {
        ActionType = value;
    }
    private java.lang.Class ActionEditorType;
    public java.lang.Class getActionEditorType()
    {
        return ActionEditorType;
    }
    public void setActionEditorType(java.lang.Class value)
    {
        ActionEditorType = value;
    }

    @Override
    public String toString()
    {
        return String.format("%1$s - %2$s => ", getTitle(), getDescription(), getActionType().getSimpleName());
    }

	/*public IEventAction CreateAction()
	{
		return (IEventAction)getActionType().newInstance();
	}*/
	/*public FrameworkElement CreateEditor()
	{
		return (FrameworkElement)getActionEditorType().newInstance();
	}*/
}