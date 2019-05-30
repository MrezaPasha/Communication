package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.TViewModelBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract] public class TTriggerDataContext : TViewModelBase
public class TTriggerDataContext
{
    public TTriggerDataContext()
    {
        //this.setTriggers(new ObservableCollection<ITrigger>());
        this.setParameters(new TTriggerParameterCollection());
    }

	/*//public final int getEventBehaviorID()
	{
		return GetValue<Integer>("EventBehaviorID");
	}
	public final void setEventBehaviorID(int value)
	{
		SetValue(value, "EventBehaviorID");
	}*/

    //[IgnoreDataMember]
    //private ITrigger _Trigger;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [IgnoreDataMember] public ObservableCollection<ITrigger> Triggers {get;private set;}
    //private ObservableCollection<ITrigger> Triggers;
    //public final ObservableCollection<ITrigger> getTriggers()
	/*{
		return Triggers;
	}*/
    //private void setTriggers(ObservableCollection<ITrigger> value)
	/*{
		Triggers = value;
	}*/

    //C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [IgnoreDataMember] public bool IsServiceMode {get;set;}
    private boolean IsServiceMode;
    public final boolean getIsServiceMode()
    {
        return IsServiceMode;
    }
    public final void setIsServiceMode(boolean value)
    {
        IsServiceMode = value;
    }

    //C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public TTriggerParameterCollection Parameters {get;private set;}
    private TTriggerParameterCollection Parameters;
    public final TTriggerParameterCollection getParameters()
    {
        return Parameters;
    }
    private void setParameters(TTriggerParameterCollection value)
    {
        Parameters = value;
    }

}