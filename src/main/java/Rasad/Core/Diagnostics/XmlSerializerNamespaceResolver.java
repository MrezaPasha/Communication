package Rasad.Core.Diagnostics;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class XmlSerializerNamespaceResolver implements IXmlNamespaceResolver
{
	private HashMap<String, String> m_namespaces = new HashMap<String, String>();

	public XmlSerializerNamespaceResolver(XmlSerializerNamespaces serializerNamespaces)
	{
		serializerNamespaces.ToArray().ForEach(qns ->
		{
				if (!qns.IsEmpty)
				{
					m_namespaces.put(qns.Name, qns.Namespace);
				}
		});
	}

	public final Map<String, String> GetNamespacesInScope(XmlNamespaceScope scope)
	{
		return m_namespaces;
	}

	public final String LookupNamespace(String prefix)
	{
		String ns;
		ns = m_namespaces.get(prefix);
		return ns;
	}

	public final String LookupPrefix(String namespaceName)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		return m_namespaces.Where(x = namespaceName.equals(> x.Value)).Select(x -> x.Key).FirstOrDefault();
	}
}