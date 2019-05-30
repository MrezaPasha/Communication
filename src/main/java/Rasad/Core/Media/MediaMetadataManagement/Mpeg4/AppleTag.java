package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// AppleTag.cs: Provides support for processing Apple "ilst" tags.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
// 
// This library is free software; you can redistribute it and/or modify
// it  under the terms of the GNU Lesser General Public License version
// 2.1 as published by the Free Software Foundation.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
// USA
//



/** 
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> to provide support
	for processing Apple "ilst" tags.
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class AppleTag extends Rasad.Core.Media.MediaMetadataManagement.Tag implements java.lang.Iterable<Box>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the ISO meta box in which that tag will be
		stored.
	*/
	private IsoMetaBox meta_box;

	/** 
		Contains the ILST box which holds all the values.
	*/
	private AppleItemListBox ilst_box;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="AppleTag" /> for a specified ISO user data box.
	 
	 @param box
		A <see cref="IsoUserDataBox" /> from which the tag is to
		be read.
	 
	*/
	public AppleTag(IsoUserDataBox box)
	{
		if (box == null)
		{
			throw new NullPointerException("box");
		}

		Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar = box.GetChild(BoxType.Meta);
		meta_box = tempVar instanceof IsoMetaBox ? (IsoMetaBox)tempVar : null;
		if (meta_box == null)
		{
			meta_box = new IsoMetaBox("mdir", null);
			box.AddChild(meta_box);
		}

		Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar2 = meta_box.GetChild(BoxType.Ilst);
		ilst_box = tempVar2 instanceof AppleItemListBox ? (AppleItemListBox)tempVar2 : null;

		if (ilst_box == null)
		{
			ilst_box = new AppleItemListBox();
			meta_box.AddChild(ilst_box);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets and sets whether or not the album described by the
		current instance is a compilation.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		album described by the current instance is a compilation.
	 </value>
	 
		This property is implemented using the "cpil" data box.
	 
	*/
	public final boolean getIsCompilation()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Cpil))
		{
			return box.getData().ToUInt() != 0;
		}

		return false;
	}
	public final void setIsCompilation(boolean value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetData(BoxType.Cpil, new ByteVector((byte)(value ? 1 : 0)), (uint) AppleDataBox.FlagType.ForTempo);
		SetData(BoxType.Cpil, new ByteVector((byte)(value ? 1 : 0)), (int) AppleDataBox.FlagType.ForTempo.getValue());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets all data boxes that match any of the provided types.
	 
	 @param types
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating a list
		of box types to match.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating the
		matching boxes.
	 
	*/
	public final java.lang.Iterable<AppleDataBox> DataBoxes(java.lang.Iterable<ByteVector> types)
	{
		// Check each box to see if the match any of the
		// provided types. If a match is found, loop through the
		// children and add any data box.
		for (Box box : ilst_box.getChildren())
		{
			for (ByteVector v : types)
			{
				if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpInequality(FixId(v), box.getBoxType()))
				{
					continue;
				}
				for (Box data_box : box.getChildren())
				{
					AppleDataBox adb = data_box instanceof AppleDataBox ? (AppleDataBox)data_box : null;
					if (adb != null)
					{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
						yield return adb;
					}
				}
			}
		}
	}

	/** 
		Gets all data boxes that match any of the provided types.
	 
	 @param types
		A <see cref="ByteVector[]" /> containing list of box
		types to match.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating the
		matching boxes.
	 
	*/
	public final java.lang.Iterable<AppleDataBox> DataBoxes(ByteVector... types)
	{
		return DataBoxes(types instanceof java.lang.Iterable<ByteVector> ? (java.lang.Iterable<ByteVector>)types : null);
	}

	/** 
		Gets all custom data boxes that match the specified mean
		and name pair.
	 
	 @param mean
		A <see cref="string" /> object containing the "mean" to
		match.
	 
	 @param name
		A <see cref="string" /> object containing the name to
		match.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating the
		matching boxes.
	 
	*/
	public final java.lang.Iterable<AppleDataBox> DataBoxes(String mean, String name)
	{
		// These children will have a box type of "----"
		for (Box box : ilst_box.getChildren())
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(box.getBoxType(), BoxType.DASH))
			{
				continue;
			}

			// Get the mean and name boxes, make sure
			// they're legit, and make sure that they match
			// what we want. Then loop through and add all
			// the data box children to our output.
			AppleAdditionalInfoBox mean_box = (AppleAdditionalInfoBox) box.GetChild(BoxType.Mean);
			AppleAdditionalInfoBox name_box = (AppleAdditionalInfoBox) box.GetChild(BoxType.Name);

			if (mean_box == null || name_box == null || !mean_box.getText().equals(mean) || !name_box.getText().equals(name))
			{
				continue;
			}

			for (Box data_box : box.getChildren())
			{
				AppleDataBox adb = data_box instanceof AppleDataBox ? (AppleDataBox)data_box : null;

				if (adb != null)
				{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
					yield return adb;
				}
			}
		}
	}

	/** 
		Gets all text values contained in a specified box type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the box
		type to match.
	 
	 @return 
		A <see cref="string[]" /> containing text from all
		matching boxes.
	 
	*/
	public final String[] GetText(ByteVector type)
	{
		ArrayList<String> result = new ArrayList<String> ();
		for (AppleDataBox box : DataBoxes(type))
		{
			if (box.getText() == null)
			{
				continue;
			}

			for (String text : box.getText().split("[;]", -1))
			{
				result.add(text.trim());
			}
		}

		return result.toArray(new String[0]);
	}

	/** 
		Sets the data for a specified box type to a collection of
		boxes.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the type to
		add to the new instance.
	 
	 @param boxes
		A <see cref="AppleDataBox[]" /> containing boxes to add
		for the specified type.
	 
	*/
	public final void SetData(ByteVector type, AppleDataBox[] boxes)
	{
		// Fix the type.
		type = FixId(type);

		boolean added = false;

		for (Box box : ilst_box.getChildren())
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(type, box.getBoxType()))
			{

				// Clear the box's children.
				box.ClearChildren();

				// If we've already added new childen,
				// continue.
				if (added)
				{
					continue;
				}

				added = true;

				// Add the children.
				for (AppleDataBox b : boxes)
				{
					box.AddChild(b);
				}
			}
		}

		if (added)
		{
			return;
		}

		Box box2 = new AppleAnnotationBox(type);
		ilst_box.AddChild(box2);

		for (AppleDataBox b : boxes)
		{
			box2.AddChild(b);
		}
	}

	/** 
		Sets the data for a specified box type using values from
		a <see cref="ByteVectorCollection" /> object.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the type to
		add to the new instance.
	 
	 @param data
		A <see cref="ByteVectorCollection" /> object containing
		data to add for the specified type.
	 
	 @param flags
		A <see cref="uint" /> value containing flags to use for
		the added boxes.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetData(ByteVector type, ByteVectorCollection data, uint flags)
	public final void SetData(ByteVector type, ByteVectorCollection data, int flags)
	{
		if (data == null || data.size() == 0)
		{
			ClearData(type);
			return;
		}

		AppleDataBox [] boxes = new AppleDataBox [data.size()];
		for (int i = 0; i < data.size(); i++)
		{
			boxes [i] = new AppleDataBox(data.get(i), flags);
		}

		SetData(type, boxes);
	}

	/** 
		Sets the data for a specified box type using a single
		<see cref="ByteVector" /> object.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the type to
		add to the new instance.
	 
	 @param data
		A <see cref="ByteVector" /> object containing data to add
		for the specified type.
	 
	 @param flags
		A <see cref="uint" /> value containing flags to use for
		the added box.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetData(ByteVector type, ByteVector data, uint flags)
	public final void SetData(ByteVector type, ByteVector data, int flags)
	{
		if (data == null || data.size() == 0)
		{
			ClearData(type);
		}
		else
		{
			SetData(type, new ByteVectorCollection(data), flags);
		}
	}

	/** 
		Sets the text for a specified box type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the type to
		add to the new instance.
	 
	 @param text
		A <see cref="string[]" /> containing text to store.
	 
	*/
	public final void SetText(ByteVector type, String[] text)
	{
		// Remove empty data and return.
		if (text == null)
		{
			ilst_box.RemoveChild(FixId(type));
			return;
		}

		SetText(type, tangible.StringHelper.join("; ", text));
	}

	/** 
		Sets the text for a specified box type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the type to
		add to the new instance.
	 
	 @param text
		A <see cref="string" /> object containing text to store.
	 
	*/
	public final void SetText(ByteVector type, String text)
	{
		// Remove empty data and return.
		if (tangible.StringHelper.isNullOrEmpty(text))
		{
			ilst_box.RemoveChild(FixId(type));
			return;
		}

		ByteVectorCollection l = new ByteVectorCollection();
		l.add(ByteVector.FromString(text, StringType.UTF8));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetData(type, l, (uint) AppleDataBox.FlagType.ContainsText);
		SetData(type, l, (int) AppleDataBox.FlagType.ContainsText.getValue());
	}

	/** 
		Clears all data for a specified box type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the type of
		box to remove from the current instance.
	 
	*/
	public final void ClearData(ByteVector type)
	{
		ilst_box.RemoveChild(FixId(type));
	}

	/** 
		Detaches the internal "ilst" box from its parent element.
	*/
	public final void DetachIlst()
	{
		meta_box.RemoveChild(ilst_box);
	}

	/** 
	 Gets the text string from a specific data box in a Dash (----) atom
	 
	 @param meanstring String specifying text from mean box
	 @param namestring String specifying text from name box
	 @return Text string from data box
	*/
	public final String GetDashBox(String meanstring, String namestring)
	{
		AppleDataBox data_box = GetDashAtoms(meanstring, namestring);
		if (data_box != null)
		{
			return data_box.getText();
		}
		else
		{
			return null;
		}
	}

	/** 
	 Sets a specific strings in Dash (----) atom.  This method updates
	 and existing atom, or creates a new one.  If an empty datastring is
	 specified, the Dash box and its children are removed.
	 
	 @param meanstring String specifying text for mean box
	 @param namestring String specifying text for name box
	 @param datastring String specifying text for data box
	*/
	public final void SetDashBox(String meanstring, String namestring, String datastring)
	{
		AppleDataBox data_box = GetDashAtoms(meanstring, namestring);

		// If we did find a data_box and we have an empty datastring we should
		// remove the entire dash box.
		if (data_box != null && tangible.StringHelper.isNullOrEmpty(datastring))
		{
			AppleAnnotationBox dash_box = GetParentDashBox(meanstring, namestring);
			dash_box.ClearChildren();
			ilst_box.RemoveChild(dash_box);
			return;
		}

		if (data_box != null)
		{
			data_box.setText(datastring);
		}
		else
		{
			//Create the new boxes, should use 1 for text as a flag
			AppleAdditionalInfoBox amean_box = new AppleAdditionalInfoBox(BoxType.Mean);
			AppleAdditionalInfoBox aname_box = new AppleAdditionalInfoBox(BoxType.Name);
			AppleDataBox adata_box = new AppleDataBox(BoxType.Data, 1);
			amean_box.setText(meanstring);
			aname_box.setText(namestring);
			adata_box.setText(datastring);
			AppleAnnotationBox whole_box = new AppleAnnotationBox(BoxType.DASH);
			whole_box.AddChild(amean_box);
			whole_box.AddChild(aname_box);
			whole_box.AddChild(adata_box);
			ilst_box.AddChild(whole_box);
		}
	}

	/** 
	 Gets the AppleDataBox that corresponds to the specified mean and name values.
	 
	 @param meanstring String specifying text for mean box
	 @param namestring String specifying text for name box
	 @return Existing AppleDataBox or null if one does not exist
	*/
	private AppleDataBox GetDashAtoms(String meanstring, String namestring)
	{
		for (Box box : ilst_box.getChildren())
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(box.getBoxType(), BoxType.DASH))
			{
				continue;
			}

			// Get the mean and name boxes, make sure
			// they're legit, check the Text fields for
			// a match.  If we have a match return
			// the AppleDatabox containing the data

			AppleAdditionalInfoBox mean_box = (AppleAdditionalInfoBox) box.GetChild(BoxType.Mean);
			AppleAdditionalInfoBox name_box = (AppleAdditionalInfoBox) box.GetChild(BoxType.Name);

			if (mean_box == null || name_box == null || !mean_box.getText().equals(meanstring) || !name_box.getText().equals(namestring))
			{
				continue;
			}
			else
			{
				return (AppleDataBox)box.GetChild(BoxType.Data);
			}
		}
		// If we haven't returned the found box yet, there isn't one, return null
		return null;
	}

	/** 
	 Returns the Parent Dash box object for a given mean/name combination
	 
	 @param meanstring String specifying text for mean box
	 @param namestring String specifying text for name box
	 @return AppleAnnotationBox object that is the parent for the mean/name combination
	*/
	private AppleAnnotationBox GetParentDashBox(String meanstring, String namestring)
	{
		for (Box box : ilst_box.getChildren())
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpInequality(box.getBoxType(), BoxType.DASH))
			{
				continue;
			}

			// Get the mean and name boxes, make sure
			// they're legit, check the Text fields for
			// a match.  If we have a match return
			// the AppleAnnotationBox that is the Parent

			AppleAdditionalInfoBox mean_box = (AppleAdditionalInfoBox) box.GetChild(BoxType.Mean);
			AppleAdditionalInfoBox name_box = (AppleAdditionalInfoBox) box.GetChild(BoxType.Name);

			if (mean_box == null || name_box == null || !mean_box.getText().equals(meanstring) || !name_box.getText().equals(namestring))
			{
				continue;
			}
			else
			{
				return (AppleAnnotationBox)box;
			}
		}
		// If we haven't returned the found box yet, there isn't one, return null
		return null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal Methods

	/** 
		Converts the provided ID into a readonly ID and fixes a
		3 byte ID.
	 
	 @param id
		A <see cref="ByteVector" /> object containing an ID to
		fix.
	 
	 @return 
		A fixed <see cref="ReadOnlyByteVector" /> or <see
		langword="null" /> if the ID could not be fixed.
	 
	*/
	public static ReadOnlyByteVector FixId(ByteVector id)
	{
		if (id.size() == 4)
		{
			ReadOnlyByteVector roid = id instanceof ReadOnlyByteVector ? (ReadOnlyByteVector)id : null;
			if (roid != null)
			{
				return roid;
			}

			return new ReadOnlyByteVector(id);
		}

		if (id.size() == 3)
		{
			return new ReadOnlyByteVector(0xa9, id.get(0), id.get(1), id.get(2));
		}

		return null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IEnumerable<Box>

	/** 
		Gets an enumerator for enumerating through the tag's data
		boxes.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the tag's data boxes.
	 
	*/
	public final Iterator<Box> iterator()
	{
		return ilst_box.getChildren().iterator();
	}

	public final Iterator GetEnumerator()
	{
		return ilst_box.getChildren().iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Rasad.Core.Media.MediaMetadataManagement.Tag

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.Apple" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.Apple;
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "@nam" data box.
	 
	*/
	@Override
	public String getTitle()
	{
		String [] text = GetText(BoxType.Nam);
		return text.length == 0 ? null : text [0];
	}
	@Override
	public void setTitle(String value)
	{
		SetText(BoxType.Nam, value);
	}

	/** 
		Gets and sets the performers or artists who performed in
		the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the performers or
		artists who performed in the media described by the
		current instance or an empty array if no value is
		present.
	 </value>
	 
		This property is implemented using the "@ART" data box.
	 
	*/
	@Override
	public String[] getPerformers()
	{
		return GetText(BoxType.Art);
	}
	@Override
	public void setPerformers(String[] value)
	{
		SetText(BoxType.Art, value);
	}

	/** 
		Gets and sets the band or artist who is credited in the
		creation of the entire album or collection containing the
		media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the band or artist
		who is credited in the creation of the entire album or
		collection containing the media described by the current
		instance or an empty array if no value is present.
	 </value>
	 
		This property is implemented using the "aART" data box.
	 
	*/
	@Override
	public String[] getAlbumArtists()
	{
		return GetText(BoxType.Aart);
	}
	@Override
	public void setAlbumArtists(String[] value)
	{
		SetText(BoxType.Aart, value);
	}

	/** 
		Gets and sets the composers of the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the composers of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "@wrt" data box.
	 
	*/
	@Override
	public String[] getComposers()
	{
		return GetText(BoxType.Wrt);
	}
	@Override
	public void setComposers(String[] value)
	{
		SetText(BoxType.Wrt, value);
	}

	/** 
		Gets and sets the album of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the album of
		the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "@alb" data box.
	 
	*/
	@Override
	public String getAlbum()
	{
		String [] text = GetText(BoxType.Alb);
		return text.length == 0 ? null : text [0];
	}
	@Override
	public void setAlbum(String value)
	{
		SetText(BoxType.Alb, value);
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "@cmt" data box.
	 
	*/
	@Override
	public String getComment()
	{
		String [] text = GetText(BoxType.Cmt);
		return text.length == 0 ? null : text [0];
	}
	@Override
	public void setComment(String value)
	{
		SetText(BoxType.Cmt, value);
	}

	/** 
		Gets and sets the genres of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the genres of the
		media represented by the current instance or an empty
		array if no value is present.
	 </value>
	 
		This property is implemented using the "@gen" and "gnre"
		data boxes.
	 
	*/
	@Override
	public String[] getGenres()
	{
		String [] text = GetText(BoxType.Gen);
		if (text.length > 0)
		{
			return text;
		}

		for (AppleDataBox box : DataBoxes(BoxType.Gnre))
		{
			if (box.getFlags() !=  AppleDataBox.FlagType.ContainsData.getValue())
			{
				continue;
			}

				// iTunes stores genre's in the GNRE box
				// as (ID3# + 1).

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort index = box.Data.ToUShort(true);
			short index = box.getData().ToUShort(true);
			if (index == 0)
			{
				continue;
			}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: string str = Rasad.Core.Media.MediaMetadataManagement.Genres.IndexToAudio((byte)(index - 1));
			String str = Rasad.Core.Media.MediaMetadataManagement.Genres.IndexToAudio((byte)(index - 1));

			if (str == null)
			{
				continue;
			}

			text = new String [] {str};
			break;
		}

		return text;
	}
	@Override
	public void setGenres(String[] value)
	{
		ClearData(BoxType.Gnre);
		SetText(BoxType.Gen, value);
	}

	/** 
		Gets and sets the year that the media represented by the
		current instance was recorded.
	 
	 <value>
		A <see cref="uint" /> containing the year that the media
		represented by the current instance was created or zero
		if no value is present.
	 </value>
	 
		This property is implemented using the "@day" data box.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getYear()
	@Override
	public int getYear()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint value;
		int value;
		for (AppleDataBox box : DataBoxes(BoxType.Day))
		{
			tangible.OutObject<Integer> tempOut_value = new tangible.OutObject<Integer>();
			tangible.OutObject<Integer> tempOut_value2 = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (box.Text != null && (uint.TryParse(box.Text, out value) || uint.TryParse(box.Text.Length > 4 ? box.Text.Substring(0, 4) : box.Text, out value)))
			if (box.getText() != null && (tangible.TryParseHelper.tryParseInt(box.getText(), tempOut_value) || tangible.TryParseHelper.tryParseInt(box.getText().length() > 4 ? box.getText().substring(0, 4) : box.getText(), tempOut_value2)))
			{
			value = tempOut_value2.argValue;
			value = tempOut_value.argValue;
				return value;
			}
		else
		{
			value = tempOut_value2.argValue;
			value = tempOut_value.argValue;
		}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setYear(uint value)
	@Override
	public void setYear(int value)
	{
		if (value == 0)
		{
			ClearData(BoxType.Day);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetText(BoxType.Day, value.ToString(CultureInfo.InvariantCulture));
			SetText(BoxType.Day, (new Integer(value)).toString(CultureInfo.InvariantCulture));
		}
	}

	/** 
		Gets and sets the position of the media represented by
		the current instance in its containing album.
	 
	 <value>
		A <see cref="uint" /> containing the position of the
		media represented by the current instance in its
		containing album or zero if not specified.
	 </value>
	 
		This property is implemented using the "trkn" data box.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrack()
	@Override
	public int getTrack()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Trkn))
		{
			if (box.getFlags() ==  AppleDataBox.FlagType.ContainsData.getValue() && box.getData().size() >= 4)
			{
				return box.getData().Mid(2, 2).ToUShort();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrack(uint value)
	@Override
	public void setTrack(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint count = TrackCount;
		int count = getTrackCount();
		if (value == 0 && count == 0)
		{
			ClearData(BoxType.Trkn);
			return;
		}

		ByteVector v = ByteVector.FromUShort((short)0);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) value));
		v.add(ByteVector.FromUShort((short) value));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) count));
		v.add(ByteVector.FromUShort((short) count));
		v.add(ByteVector.FromUShort((short)0));

		SetData(BoxType.Trkn, v, AppleDataBox.FlagType.ContainsData.getValue());
	}

	/** 
		Gets and sets the number of tracks in the album
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of tracks in
		the album containing the media represented by the current
		instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "trkn" data box.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getTrackCount()
	@Override
	public int getTrackCount()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Trkn))
		{
			if (box.getFlags() ==  AppleDataBox.FlagType.ContainsData.getValue() && box.getData().size() >= 6)
			{
				return box.getData().Mid(4, 2).ToUShort();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setTrackCount(uint value)
	@Override
	public void setTrackCount(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint track = Track;
		int track = getTrack();
		if (value == 0 && track == 0)
		{
			ClearData(BoxType.Trkn);
			return;
		}

		ByteVector v = ByteVector.FromUShort((short)0);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) track));
		v.add(ByteVector.FromUShort((short) track));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) value));
		v.add(ByteVector.FromUShort((short) value));
		v.add(ByteVector.FromUShort((short)0));
		SetData(BoxType.Trkn, v, AppleDataBox.FlagType.ContainsData.getValue());
	}

	/** 
		Gets and sets the number of the disc containing the media
		represented by the current instance in the boxed set.
	 
	 <value>
		A <see cref="uint" /> containing the number of the disc
		containing the media represented by the current instance
		in the boxed set.
	 </value>
	 
		This property is implemented using the "disk" data box.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDisc()
	@Override
	public int getDisc()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Disk))
		{
			if (box.getFlags() ==  AppleDataBox.FlagType.ContainsData.getValue() && box.getData().size() >= 4)
			{
				return box.getData().Mid(2, 2).ToUShort();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDisc(uint value)
	@Override
	public void setDisc(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint count = DiscCount;
		int count = getDiscCount();
		if (value == 0 && count == 0)
		{
			ClearData(BoxType.Disk);
			return;
		}

		ByteVector v = ByteVector.FromUShort((short)0);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) value));
		v.add(ByteVector.FromUShort((short) value));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) count));
		v.add(ByteVector.FromUShort((short) count));
		v.add(ByteVector.FromUShort((short)0));

		SetData(BoxType.Disk, v, AppleDataBox.FlagType.ContainsData.getValue());
	}

	/** 
		Gets and sets the number of discs in the boxed set
		containing the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of discs in
		the boxed set containing the media represented by the
		current instance or zero if not specified.
	 </value>
	 
		This property is implemented using the "disk" data box.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getDiscCount()
	@Override
	public int getDiscCount()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Disk))
		{
			if (box.getFlags() ==  AppleDataBox.FlagType.ContainsData.getValue() && box.getData().size() >= 6)
			{
				return box.getData().Mid(4, 2).ToUShort();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setDiscCount(uint value)
	@Override
	public void setDiscCount(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint disc = Disc;
		int disc = getDisc();
		if (value == 0 && disc == 0)
		{
			ClearData(BoxType.Disk);
			return;
		}

		ByteVector v = ByteVector.FromUShort((short)0);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) disc));
		v.add(ByteVector.FromUShort((short) disc));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: v.Add(ByteVector.FromUShort((ushort) value));
		v.add(ByteVector.FromUShort((short) value));
		v.add(ByteVector.FromUShort((short)0));
		SetData(BoxType.Disk, v, AppleDataBox.FlagType.ContainsData.getValue());
	}

	/** 
		Gets and sets the lyrics or script of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the lyrics or
		script of the media represented by the current instance
		or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "@lyr" data box.
	 
	*/
	@Override
	public String getLyrics()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Lyr))
		{
			return box.getText();
		}
		return null;
	}
	@Override
	public void setLyrics(String value)
	{
		SetText(BoxType.Lyr, value);
	}

	/** 
		Gets and sets the grouping on the album which the media
		in the current instance belongs to.
	 
	 <value>
		A <see cref="string" /> object containing the grouping on
		the album which the media in the current instance belongs
		to or <see langword="null" /> if no value is present.
	 </value>
	 
		This property is implemented using the "@grp" data box.
	 
	*/
	@Override
	public String getGrouping()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Grp))
		{
			return box.getText();
		}

		return null;
	}
	@Override
	public void setGrouping(String value)
	{
		SetText(BoxType.Grp, value);
	}

	/** 
		Gets and sets the number of beats per minute in the audio
		of the media represented by the current instance.
	 
	 <value>
		A <see cref="uint" /> containing the number of beats per
		minute in the audio of the media represented by the
		current instance, or zero if not specified.
	 </value>
	 
		This property is implemented using the "tmpo" data box.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override uint getBeatsPerMinute()
	@Override
	public int getBeatsPerMinute()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Tmpo))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (box.Flags == (uint) AppleDataBox.FlagType.ForTempo)
			if (box.getFlags() == (int) AppleDataBox.FlagType.ForTempo.getValue())
			{
				return box.getData().ToUInt();
			}
		}

		return 0;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setBeatsPerMinute(uint value)
	@Override
	public void setBeatsPerMinute(int value)
	{
		if (value == 0)
		{
			ClearData(BoxType.Tmpo);
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetData(BoxType.Tmpo, ByteVector.FromUShort((ushort)value), (uint) AppleDataBox.FlagType.ForTempo);
		SetData(BoxType.Tmpo, ByteVector.FromUShort((short)value), (int) AppleDataBox.FlagType.ForTempo.getValue());
	}

	/** 
		Gets and sets the conductor or director of the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the conductor
		or director of the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "cond" data box.
	 
	*/
	@Override
	public String getConductor()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Cond))
		{
			return box.getText();
		}

		return null;
	}
	@Override
	public void setConductor(String value)
	{
		SetText(BoxType.Cond, value);
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	 
		This property is implemented using the "cprt" data box.
	 
	*/
	@Override
	public String getCopyright()
	{
		for (AppleDataBox box : DataBoxes(BoxType.Cprt))
		{
			return box.getText();
		}

		return null;
	}
	@Override
	public void setCopyright(String value)
	{
		SetText(BoxType.Cprt, value);
	}

	/** 
		Gets and sets the sort names for the band or artist who
		is credited in the creation of the entire album or
		collection containing the media described by the
		current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names
		for the band or artist who is credited in the creation
		of the entire album or collection containing the media
		described by the current instance or an empty array if
		no value is present.
	 </value>
	 
		This property is implemented using the "soaa"
		Box type.
		http://musicbrainz.org/doc/PicardTagMapping
		http://code.google.com/p/mp4v2/wiki/iTunesMetadata
	 
	*/
	@Override
	public String[] getAlbumArtistsSort()
	{
		return GetText(BoxType.Soaa);
	}
	@Override
	public void setAlbumArtistsSort(String[] value)
	{
		SetText(BoxType.Soaa, value);
	}

	/** 
		Gets and sets the sort names of the performers or artists
		who performed in the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names for
		the performers or artists who performed in the media
		described by the current instance, or an empty array if
		no value is present. 
	 </value>
	 
		This property is implemented using the "soar" box type.
		http://musicbrainz.org/doc/PicardTagMapping
		http://code.google.com/p/mp4v2/wiki/iTunesMetadata
	 
	*/
	@Override
	public String[] getPerformersSort()
	{
		return GetText(BoxType.Soar);
	}
	@Override
	public void setPerformersSort(String[] value)
	{
		SetText(BoxType.Soar, value);
	}

	/** 
		Gets and sets the sort names of the Composer credited
		in the media described by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the sort names for
		the Composers in the media described by the current instance,
		or an empty array if no value is present. 
	 </value>
	 
		This property is implemented using the "soar" box type.
		http://musicbrainz.org/doc/PicardTagMapping
		http://code.google.com/p/mp4v2/wiki/iTunesMetadata
	 
	*/
	@Override
	public String[] getComposersSort()
	{
		return GetText(BoxType.Soco);
	}
	@Override
	public void setComposersSort(String[] value)
	{
		SetText(BoxType.Soco, value);
	}

	/** 
		Gets and sets the sort names of the Album Title of
		the media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names for
		the Album Title in the media described by the current
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "soal" box type.
		http://musicbrainz.org/doc/PicardTagMapping
		http://code.google.com/p/mp4v2/wiki/iTunesMetadata
	 
	*/
	@Override
	public String getAlbumSort()
	{
		String [] text = GetText(BoxType.Soal);
		return text.length == 0 ? null : text [0];
	}
	@Override
	public void setAlbumSort(String value)
	{
		SetText(BoxType.Soal, value);
	}

	/** 
		Gets and sets the sort names of the Track Title in the
		media described by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the sort names for
		the Track Title in the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "sonm" box type.
		http://musicbrainz.org/doc/PicardTagMapping
		http://code.google.com/p/mp4v2/wiki/iTunesMetadata
	 
	*/
	@Override
	public String getTitleSort()
	{
		String [] text = GetText(BoxType.Sonm);
		return text.length == 0 ? null : text [0];
	}
	@Override
	public void setTitleSort(String value)
	{
		SetText(BoxType.Sonm, value);
	}

	/** 
		Gets and sets the MusicBrainz ArtistID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ArtistID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzArtistId()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Artist Id");
	}
	@Override
	public void setMusicBrainzArtistId(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Artist Id", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseId()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Album Id");
	}
	@Override
	public void setMusicBrainzReleaseId(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Album Id", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseArtistID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseArtistID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseArtistId()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Album Artist Id");
	}
	@Override
	public void setMusicBrainzReleaseArtistId(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Album Artist Id", value);
	}

	/** 
		Gets and sets the MusicBrainz TrackID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		TrackID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzTrackId()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Track Id");
	}
	@Override
	public void setMusicBrainzTrackId(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Track Id", value);
	}

	/** 
		Gets and sets the MusicBrainz DiscID
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		DiscID for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzDiscId()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Disc Id");
	}
	@Override
	public void setMusicBrainzDiscId(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Disc Id", value);
	}

	/** 
		Gets and sets the MusicIP PUID
	 
	 <value>
		A <see cref="string" /> containing the MusicIP Puid
		for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicIpId()
	{
		return GetDashBox("com.apple.iTunes", "MusicIP PUID");
	}
	@Override
	public void setMusicIpId(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicIP PUID", value);
	}

	/** 
		Gets and sets the AmazonID
	 
	 <value>
		A <see cref="string" /> containing the AmazonID
		for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getAmazonId()
	{
		return GetDashBox("com.apple.iTunes", "ASIN");
	}
	@Override
	public void setAmazonId(String value)
	{
		SetDashBox("com.apple.iTunes", "ASIN", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseStatus
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseStatus for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseStatus()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Album Status");
	}
	@Override
	public void setMusicBrainzReleaseStatus(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Album Status", value);
	}

	/** 
		Gets and sets the MusicBrainz ReleaseType
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseType for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseType()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Album Type");
	}
	@Override
	public void setMusicBrainzReleaseType(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Album Type", value);
	}

	/** 
		Gets and sets the MusicBrainz Release Country
	 
	 <value>
		A <see cref="string" /> containing the MusicBrainz
		ReleaseCountry for the media described by the current 
		instance, or null if no value is present. 
	 </value>
	 
		This property is implemented using the "dash"/"----" box type.
		http://musicbrainz.org/doc/PicardTagMapping
	 
	*/
	@Override
	public String getMusicBrainzReleaseCountry()
	{
		return GetDashBox("com.apple.iTunes", "MusicBrainz Album Release Country");
	}
	@Override
	public void setMusicBrainzReleaseCountry(String value)
	{
		SetDashBox("com.apple.iTunes", "MusicBrainz Album Release Country", value);
	}

	/** 
		Gets and sets a collection of pictures associated with
		the media represented by the current instance.
	 
	 <value>
		A <see cref="IPicture[]" /> containing a collection of
		pictures associated with the media represented by the
		current instance or an empty array if none are present.
	 </value>
	 
		This property is implemented using the "covr" data box.
	 
	*/
	@Override
	public IPicture[] getPictures()
	{
		ArrayList<Picture> l = new ArrayList<Picture> ();

		for (AppleDataBox box : DataBoxes(BoxType.Covr))
		{
			Picture p = new Picture(box.getData());
			p.setType(PictureType.FrontCover);
			l.add(p);
		}

		return (Picture []) l.toArray(new Picture[0]);
	}
	@Override
	public void setPictures(IPicture[] value)
	{
		if (value == null || value.length == 0)
		{
			ClearData(BoxType.Covr);
			return;
		}

		AppleDataBox [] boxes = new AppleDataBox [value.length];
		for (int i = 0; i < value.length; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint type = (uint) AppleDataBox.FlagType.ContainsData;
			int type = (int) AppleDataBox.FlagType.ContainsData.getValue();

			if (value [i].getMimeType().equals("image/jpeg"))
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (uint) AppleDataBox.FlagType.ContainsJpegData;
				type = (int) AppleDataBox.FlagType.ContainsJpegData.getValue();
			}
			else if (value [i].getMimeType().equals("image/png"))
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (uint) AppleDataBox.FlagType.ContainsPngData;
				type = (int) AppleDataBox.FlagType.ContainsPngData.getValue();
			}

			boxes [i] = new AppleDataBox(value [i].getData(), type);
		}

		SetData(BoxType.Covr, boxes);
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if the current instance does not
		any values. Otherwise <see langword="false" />.
	 </value>
	*/
	@Override
	public boolean getIsEmpty()
	{
		return !ilst_box.getHasChildren();
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		ilst_box.ClearChildren();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}