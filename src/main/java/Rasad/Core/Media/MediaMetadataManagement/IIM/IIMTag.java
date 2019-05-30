package Rasad.Core.Media.MediaMetadataManagement.IIM;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
//  IIMTag.cs
//
//  Author:
//       Eberhard Beilharz <eb1@sil.org>
//
//  Copyright (c) 2012 Eberhard Beilharz
//
//  This library is free software; you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//  Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA



public class IIMTag extends Xmp.XmpTag
{
	private ArrayList<String> m_Keywords;

	public IIMTag()
	{
	}

	@Override
	public Rasad.Core.Media.MediaMetadataManagement.TagTypes getTagTypes()
	{
		return Rasad.Core.Media.MediaMetadataManagement.TagTypes.IPTCIIM;
	}

	@Override
	public void Clear()
	{
		setTitle(null);
		m_Keywords = null;
		setCreator(null);
		setCopyright(null);
		setComment(null);
	}

	private String Title;
	@Override
	public String getTitle()
	{
		return Title;
	}
	@Override
	public void setTitle(String value)
	{
		Title = value;
	}
	private String Creator;
	@Override
	public String getCreator()
	{
		return Creator;
	}
	@Override
	public void setCreator(String value)
	{
		Creator = value;
	}
	private String Copyright;
	@Override
	public String getCopyright()
	{
		return Copyright;
	}
	@Override
	public void setCopyright(String value)
	{
		Copyright = value;
	}
	private String Comment;
	@Override
	public String getComment()
	{
		return Comment;
	}
	@Override
	public void setComment(String value)
	{
		Comment = value;
	}

	@Override
	public String[] getKeywords()
	{
		if (m_Keywords == null)
		{
			return null;
		}
		return m_Keywords.toArray(new String[0]);
	}

	public final void AddKeyword(String keyword)
	{
		if (m_Keywords == null)
		{
			m_Keywords = new ArrayList<String> ();
		}
		m_Keywords.add(keyword);
	}
}