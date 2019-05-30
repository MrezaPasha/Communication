package main.java.Rasad.Core.Services.NotificationService.Services;


import Rasad.Core.*;
import main.java.Rasad.Core.Services.PluginService.TPluginEventDefinition;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.Assembly, Inherited = false, AllowMultiple = true)] public sealed class TEventBehaviorTriggerAttribute : Attribute
public final class TEventBehaviorTriggerAttribute
{
    public TEventBehaviorTriggerAttribute(java.lang.Class triggerType, java.lang.Class editorType, String title, String description, String imageUrl)
    {
        this.setTriggerType(triggerType);
        this.setEditorType(editorType);
        this.setTitle(title);
        this.setImageUrl(imageUrl);
        this.setDescription(description);
    }

    public TEventBehaviorTriggerAttribute(TPluginEventDefinition item)
    {
		/*this.setTriggerType(TTypeHelper.GetTypeSafe(item.getITriggerTypeName()));
		this.setEditorType(TTypeHelper.GetTypeSafe(item.getITriggerEditorTypeName()));
		this.setTitle(item.getTitle());
		this.setImageUrl(item.getImageUrl());
		this.setDescription(item.getDescription());*/
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

    private java.lang.Class TriggerType;
    public java.lang.Class getTriggerType()
    {
        return TriggerType;
    }
    public void setTriggerType(java.lang.Class value)
    {
        TriggerType = value;
    }
    private java.lang.Class EditorType;
    public java.lang.Class getEditorType()
    {
        return EditorType;
    }
    public void setEditorType(java.lang.Class value)
    {
        EditorType = value;
    }

    /*public FrameworkElement CreateEditor()
    {
        return (FrameworkElement)getEditorType().newInstance();
    }
    public ITrigger CreateTrigger()
    {
        return (ITrigger)getTriggerType().newInstance();
    }*/
    @Override
    public String toString()
    {
        return String.format("%1$s - %2$s => %3$s", getTitle(), getDescription(), getTriggerType().getSimpleName());
    }
}