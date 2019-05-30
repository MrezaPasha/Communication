package Rasad.Core.Diagnostics;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][XmlRoot("log-message")] public class LogMessage
public class LogMessage implements Serializable
{

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlElement("id")] public int id;
	public int id;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlElement("source")] public string source;
	public String source;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlElement("message")] public string message;
	public String message;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlElement("event-type")] public TraceEventType eventType;
	public TraceEventType eventType = TraceEventType.values()[0];

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlElement("date-time")] public DateTime dateTime;
	public LocalDateTime dateTime = LocalDateTime.MIN;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlIgnore] public TraceEventCache eventCache;
	public TraceEventCache eventCache;

	public final String EvalXPath(String xpath, XmlSerializerNamespaces namespaces)
	{
		XmlSerializerNamespaceResolver nsResolver = new XmlSerializerNamespaceResolver(namespaces);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var expr = XPathExpression.Compile(xpath, nsResolver);
		switch (xpath)
		{
			case "/log-message/id":
				return (new Integer(id)).toString(NumberFormatInfo.InvariantInfo);
			case "/log-message/source":
				return source;
			case "/log-message/event-type":
				return eventType.toString();
			case "/log-message/message":
				return message;
		}
		throw new RuntimeException("failed to evaluate xpath expression");
	}
}
//public class ObservableTraceListener : TraceListener
//{
//    //TODO: use weak reference
//    protected static Dictionary<string, Subject<LogMessage>> s_sinks = new Dictionary<string, Subject<LogMessage>>();
//    protected static Subject<LogMessage> s_unnamedSink = new Subject<LogMessage>();
//    protected Subject<LogMessage> m_subj = null;

//    public ObservableTraceListener()
//    {
//        m_subj = s_unnamedSink;
//    }

//    public ObservableTraceListener(string sinkName)
//    {
//        if (String.IsNullOrWhiteSpace(sinkName))
//        {
//            m_subj = s_unnamedSink;
//        }
//        else
//        {
//            lock (s_sinks)
//            {
//                if (!s_sinks.TryGetValue(sinkName, out m_subj))
//                {
//                    m_subj = new Subject<LogMessage>();
//                    s_sinks.Add(sinkName, m_subj);
//                }
//            }
//        }
//    }

//    public override void TraceEvent(TraceEventCache eventCache, string source, TraceEventType eventType, int id)
//    {
//        this.TraceEvent(eventCache, source, eventType, id, string.Empty);
//    }

//    public override void TraceEvent(TraceEventCache eventCache, string source, TraceEventType eventType, int id, string message)
//    {
//        if ((this.Filter == null) || this.Filter.ShouldTrace(eventCache, source, eventType, id, message, null, null, null))
//        {
//            var logMsg = new LogMessage()
//            {
//                eventCache = eventCache,
//                source = source,
//                eventType = eventType,
//                message = message,
//                id = id
//            };
//            m_subj.OnNext(logMsg);
//        }
//    }

//    public override void TraceEvent(TraceEventCache eventCache, string source, TraceEventType eventType, int id, string format, params object[] args)
//    {
//        if ((this.Filter == null) || this.Filter.ShouldTrace(eventCache, source, eventType, id, format, args, null, null))
//        {
//            var logMsg = new LogMessage()
//            {
//                eventCache = eventCache,
//                source = source,
//                eventType = eventType,
//                message = String.Format(CultureInfo.InvariantCulture, format, args),
//                id = id
//            };
//            m_subj.OnNext(logMsg);
//        }
//    }

//    public static IObservable<LogMessage> GetLogMessages()
//    {
//        return s_unnamedSink;
//    }

//    public static IObservable<LogMessage> GetLogMessages(string sinkName)
//    {
//        if (String.IsNullOrWhiteSpace(sinkName))
//        {
//            return GetLogMessages();
//        }
//        Subject<LogMessage> subj = null;
//        lock (s_sinks)
//        {
//            if (!s_sinks.TryGetValue(sinkName, out subj))
//            {
//                subj = new Subject<LogMessage>();
//                s_sinks.Add(sinkName, subj);
//            }
//        }
//        return subj;
//    }

//    public override void Write(string message)
//    {
//        var evtCache = new TraceEventCache();
//        TraceEvent(evtCache, null, TraceEventType.Information, 0, message);
//    }

//    public override void WriteLine(string message)
//    {
//        var evtCache = new TraceEventCache();
//        TraceEvent(evtCache, null, TraceEventType.Information, 0, message);
//    }
//}
