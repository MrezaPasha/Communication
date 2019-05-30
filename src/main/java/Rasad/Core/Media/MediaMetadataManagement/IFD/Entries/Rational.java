package Rasad.Core.Media.MediaMetadataManagement.IFD.Entries;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// Rational.cs: A structure to represent rational values by a numerator and
// a denominator.
//
// Author:
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
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
	Representation of an unsigned rational value
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct Rational : IFormattable
public final class Rational implements IFormattable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		The numerator of the rational value
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint numerator;
	private int numerator;

	/** 
		The denominator of the rational value
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint denominator;
	private int denominator;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructor

	/** 
		Creates a new Rational value
	 
	 @param numerator
		A <see cref="System.UInt32"/> with the numerator of the
		rational value
	 
	 @param denominator
		A <see cref="System.UInt32"/> with the denominator of the
		rational value. It must be not 0.
	 
	*/
	public Rational()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Rational(uint numerator, uint denominator)
	public Rational(int numerator, int denominator)
	{
		if (denominator == 0)
		{
			throw new IllegalArgumentException("denominator");
		}
		this.numerator = numerator;
		this.denominator = denominator;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Returns a rational value with reduced nominator and denominator
	 
	 @return 
	 A <see cref="Rational"/>
	 
	*/
	public Rational Reduce()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint gcd = Denominator;
		int gcd = getDenominator();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint b = Numerator;
		int b = getNumerator();

		while (b != 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint tmp = gcd % b;
			int tmp = gcd % b;
			gcd = b;
			b = tmp;
		}

		return new Rational(getNumerator() / gcd, getDenominator() / gcd);
	}

	/** 
		Formatprovider to allow formatting of a value. <see cref="IFormattable"/>
	 
	 @param format
		A <see cref="System.String"/>. <see cref="IFormattable"/>
	 
	 @param provider
		A <see cref="IFormatProvider"/>. <see cref="IFormattable"/>
	 
	 @return 
		A <see cref="System.String"/> formated according to the given parameter
	 
	*/
	public String toString(String format, IFormatProvider provider)
	{

		Rational reduced = Reduce().clone();

		return String.format("%1$s/%2$s", reduced.getNumerator(), reduced.getDenominator());
	}

	/** 
		Converts the value to a <see cref="System.String"/>.
	 
	 @return 
		A <see cref="System.String"/> with the current value.
	 
	*/
	@Override
	public String toString()
	{
		return String.format("%1$s", this.clone());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** <value>
		The numerator of the rational value
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getNumerator()
	public int getNumerator()
	{
		return numerator;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setNumerator(uint value)
	public void setNumerator(int value)
	{
		numerator = value;
	}

	/** <value>
		The denominator of the rational value
	 </value>
	 
		Cannot be 0.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getDenominator()
	public int getDenominator()
	{
		return denominator;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setDenominator(uint value)
	public void setDenominator(int value)
	{
		if (value == 0)
		{
			throw new IllegalArgumentException("denominator");
		}

		denominator = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Cast the <see cref="Rational"/> value to a <see cref="System.Double"/>.
	 
	 @param rat
		A <see cref="Rational"/> with the value to cast.
	 
	 @return 
		A <see cref="System.Double"/> with the double.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator double(Rational rat)
	{
		return (double) rat.getNumerator() / (double) rat.getDenominator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion


	public Rational clone()
	{
		Rational varCopy = new Rational();

		varCopy.numerator = this.numerator;
		varCopy.denominator = this.denominator;

		return varCopy;
	}
}