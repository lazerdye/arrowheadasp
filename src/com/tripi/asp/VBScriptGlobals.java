/**
 * ArrowHead ASP Server 
 * This is a source file for the ArrowHead ASP Server - an 100% Java
 * VBScript interpreter and ASP server.
 *
 * For more information, see http://www.tripi.com/arrowhead
 *
 * Copyright (C) 2002  Terence Haddock
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.tripi.asp;

import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.tripi.asp.parse.SimpleCharStream;
import com.tripi.asp.parse.VBScript;
import com.tripi.asp.parse.VBScriptTokenManagerInterface;

/**
 * The VBScriptGlobals class handles variables global to every VBScript
 * expression, such as global functions and variables.
 * <p>State of variable implementation:
 * <ul>
 * <li>Color Constants
 *  <ul>
 *  <li><i>vbBlack</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbBlue</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbRed</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbMagenta</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbGreen</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbCyan</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbYellow</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbWhite</i> - <b>TODO</b> Not implemented.
 *  </ul>
 * <li>Comparison Constants
 *  <ul>
 *  <li><i>vbBinaryCompare</i> - Fully implemented.
 *  <li><i>vbTextCompare</i> - Fully implemented.
 *  </ul>
 * <li>Date and Time Constants
 *  <ul>
 *  <li><i>vbSunday</i> - Fully implemented.
 *  <li><i>vbMonday</i> - Fully implemented.
 *  <li><i>vbTuesday</i> - Fully implemented.
 *  <li><i>vbWednesday</i> - Fully implemented.
 *  <li><i>vbThursday</i> - Fully implemented.
 *  <li><i>vbFriday</i> - Fully implemented.
 *  <li><i>vbSaturday</i> - Fully implemented.
 *  <li><i>vbUseSystem</i> - Fully implemented.
 *  <li><i>vbUseSystemDayOfWeek</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbFirstJan1</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbFirstFourDays</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbFirstFullWeek</i> - <b>TODO</b> Not implemented.
 *  </ul>
 * <li>Date Format Constants
 *  <ul>
 *  <li><i>vbGeneralDate</i> - Implemented.
 *  <li><i>vbLongDate</i> - Implemented.
 *  <li><i>vbLongTime</i> - Implemented.
 *  <li><i>vbShortDate</i> - Implemented.
 *  <li><i>vbShortTime</i> - Implemented.
 *  </ul>
 * <li>Error Constants
 *  <ul>
 *  <li><i>vbObjectError</i> - <b>TODO</b> Not implemented.
 *  </ul>
 * <li>Logical and Tri-State Constants
 *  <ul>
 *  <li><i>NULL</i> - Fully implemented
 *  <li><i>FALSE</i> - Fully implemented
 *  <li><i>TRUE</i> - Fully implemented
 *  <li><i>vbTrue</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbFalse</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbUseDefault</i> - <b>TODO</b> Not implemented.
 *  </ul>
 * <li>Special Character Constants
 *  <ul>
 *  <li><i>vbCr</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbNullChar</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbCrLf</i> - Fully implemented.
 *  <li><i>vbNullString</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbFormFeed</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbTab</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbLf</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbVericalTab</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbNewline</i> - Fully Implemented
 *  </ul>
 * <li>Variable Sub-Type Constants
 *  <ul>
 *  <li><i>vbArray</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbError</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbBoolean</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbInteger</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbByte</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbLong</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbCurrency</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbNull</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbDataObject</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbObject</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbDate</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbSingle</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbDecimal</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbString</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbDouble</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbVariant</i> - <b>TODO</b> Not implemented.
 *  <li><i>vbEmpty</i> - <b>TODO</b> Not implemented.
 *  </ul>
 * </ul>
 * <p>State of function implementation:
 * <ul>
 * <li><i>abs</i> - Implemented for double and int. <b>TODO</b> Not implemented
 * for NULL value, nor undefined.
 * <li><i>array</i> - Fully implemented.
 * <li><i>asc</i> - Fully implemented (as Unicode).
 * <li><i>ascb</i> - Fully implemented
 * <li><i>ascw</i> - Fully implemented.
 * <li><i>atn</i> - Fully implemented.
 * <li><i>cbool</i> - Fully implemented.
 * <li><i>cbyte</i> - Fully implemented.
 * <li><i>ccur</i> - <b>TODO</b> Not implemented.
 * <li><i>cdate</i> - Works for string expression. <B>TODO</b> works for
 * numeric values?
 * <li><i>cdbl</i> - Fully implemented.
 * <li><i>chr</i> - Fully implemented.
 * <li><i>chrb</i> - <b>TODO</b> Not implemented.
 * <li><i>chrw</i> - Fully implemented.
 * <li><i>cint</i> - Implemented. <b>TODO</b> Does not handle out-of-range.
 * <li><i>clng</i> - <b>TODO</b> converts to a long, 32-bit integer.
 * <li><i>cos</i> - Fully implemented.
 * <li><i>createobject</i> - Implemented, <b>TODO</b> does not handle the
 * location variable. Also, there is no way to define custom names.
 * <li><i>csng</i> - Implemented, <b>TODO</b> does not handle out-of-range.
 * <li><i>cstr</i> - Implemented, <b>TODO</b> does not throw error
 *                   on NULL values.
 * <li><i>date</i> - Fully implemented.
 * <li><i>dateadd</i> - Fully implemented.
 * <li><i>datediff</i> - Implemented, <b>TODO</b> not for 'w', nor
 * 'ww' field, nor handles fourth and fifth fields.
 * <li><i>datepart</i> - Implemented, <b>TODO</b> weekday constants may not
 * be correct.
 * <li><i>dateserial</i> - Fully implemented.
 * <li><i>datevalue</i> - Fully implemented.
 * <li><i>day</i> - <b>TODO</b> Not implemented.
 * <li><i>erase</i> - Fully implemented.
 * <li><i>exp</i> - Fully implemented.
 * <li><i>filter</i> - <b>TODO</b> Not implemented.
 * <li><i>fix</i> - Fully implemented.
 * <li><i>formatcurrency</i> - <b>TODO</b> Not implemented.
 * <li><i>formatdatetime</i> - Implemented, <b>TODO</b> does not handle
 * vbGeneralDate properly because there is no distinction between date
 * and time in java.util.Date.
 * <li><i>formatnumber</i> - Implemented, <b>TODO</b> does not handle all
 * parameters
 * <li><i>formatpercent</i> - <b>TODO</b> Not implemented.
 * <li><i>getlocale</i> - <b>TODO</b> Not implemented.
 * <li><i>getobject</i> - <b>TODO</b> Not implemented.
 * <li><i>getref</i> - <b>TODO</b> Not implemented.
 * <li><i>hex</i> - Fully implemented.
 * <li><i>hour</i> - Fully implemented.
 * <li><i>instr</i> - Fully implemented.
 * <li><i>instrb</i> - <b>TODO</b> Not implemented.
 * <li><i>instrrev</i> - Fully implemented.
 * <li><i>int</i> - Fully implemented.
 * <li><i>isarray</i> - Fully implemented.
 * <li><i>isdate</i> - Fully implemented
 * <li><i>isempty</i> - Fully implemented
 * <li><i>isnull</i> - Fully implemented
 * <li><i>isnumeric</i> - Fully implemented
 * <li><i>isobject</i> - Fully implemented
 * <li><i>join</i> - Fully implemented.
 * <li><i>lbound</i> - Fully implemented.
 * <li><i>lcase</i> - Fully implemented.
 * <li><i>left</i> - Fully implemented.
 * <li><i>leftb</i> - <b>TODO</b> Not implemented.
 * <li><i>len</i> - Implemented <b>TODO</b> "number of bytes to store a 
 * varaible" not implemented.
 * <li><i>lenb</i> - <b>TODO</b> Not implemented.
 * <li><i>log</i> - Fully implemented.
 * <li><i>ltrim</i> - Fully implemented.
 * <li><i>mid</i> - Fully implemented.
 * <li><i>midb</i> - <b>TODO</b> Not implemented.
 * <li><i>minute</i> - Fully implemented.
 * <li><i>month</i> - Fully implemented.
 * <li><i>monthname</i> - Fully implemented.
 * <li><i>now</i> - Fully implemented.
 * <li><i>replace</i> - Fully implemented.
 * <li><i>rgb</i> - Fully implemented.
 * <li><i>randomize</i> - Fully implemented.
 * <li><i>right</i> - Fully implemented.
 * <li><i>rightb</i> - <b>TODO</b> Not implemented.
 * <li><i>rnd</i> - Fully implemented.
 * <li><i>round</i> - Fully implemented.
 * <li><i>rtrim</i> - Fully implemented.
 * <li><i>scriptengine</i> - Fully implemented.
 * <li><i>scriptenginebuildengine</i> - Fully implemented.
 * <li><i>scriptenginemajorversion</i> - Fully implemented.
 * <li><i>scriptengineminorversion</i> - Fully implemented.
 * <li><i>second</i> - Fully implemented.
 * <li><i>setlocale</i> - <b>TODO</b> Not implemented.
 * <li><i>sgn</i> - Fully implemented.
 * <li><i>sin</i> - Fully implemented.
 * <li><i>space</i> - Fully implemented.
 * <li><i>split</i> - Fully implemented.
 * <li><i>sqr</i> - Fully implemented.
 * <li><i>strcomp</i> - Fully implemented.
 * <li><i>string</i> - Fully implemented.
 * <li><i>strreverse</i> - Fully implemented.
 * <li><i>tan</i> - Fully implemented.
 * <li><i>time</i> - Fully implemented.
 * <li><i>timer</i> - <b>TODO</b> Not implemented.
 * <li><i>timeserial</i> - Fully implemented.
 * <li><i>timevalue</i> - <b>TODO</b> Not implemented.
 * <li><i>trim</i> - Fully implemented.
 * <li><i>typename</i> - <b>TODO</b> Not implemented.
 * <li><i>ucase</i> - Fully implemented.
 * <li><i>vartype</i> - <b>TODO</b> Not implemented.
 * <li><i>weekday</i> - Fully implemented.
 * <li><i>weekdayname</i> - Implemented, <b>TODO</b> but not for first day
 * of week parameter.
 * <li><i>year</i> - Fully implemented.
 * </ul>
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class VBScriptGlobals
{
    /** Debugging category */
    static final private Logger DBG =
        Logger.getLogger(VBScriptGlobals.class);

    /* Constants */
    /** "General" date */
    public final static int vbGeneralDate = 0;
    /** "Long" date format */
    public final static int vbLongDate = 1;
    /** "Short" date format */
    public final static int vbShortDate = 2;
    /** "Long" time format */
    public final static int vbLongTime = 3;
    /** "Short" time format */
    public final static int vbShortTime = 4;

    /* Comparison constants */
    /** Binary compare */
    public final static int vbBinaryCompare = 0;
    /** Text compare */
    public final static int vbTextCompare = 1;

    /** Private constructor, so class cannot be initialized */
    private VBScriptGlobals() {};

    /** Constructor, takes no arguments */
    static protected void initScope(Hashtable context) throws AspException
    {
        /* Global variables */
        /* Constants */
        context.put(new IdentNode("false"), new Boolean(false));
        context.put(new IdentNode("true"), new Boolean(true));
        context.put(new IdentNode("null"), Constants.nullNode);
        context.put(new IdentNode("nothing"), Constants.nothingNode);
        context.put(new IdentNode("empty"), Constants.undefinedValueNode);
        context.put(new IdentNode("vbcrlf"), "\r\n");
        context.put(new IdentNode("vbnewline"), "\n");
        context.put(new IdentNode("vbcr"), "\r");
        context.put(new IdentNode("vblf"), "\n");
        context.put(new IdentNode("vbgeneraldate"), new Integer(vbGeneralDate));
        context.put(new IdentNode("vblongdate"), new Integer(vbLongDate));
        context.put(new IdentNode("vbshortdate"), new Integer(vbShortDate));
        context.put(new IdentNode("vblongtime"), new Integer(vbLongTime));
        context.put(new IdentNode("vbshorttime"), new Integer(vbShortTime));
        context.put(new IdentNode("vbbinarycompare"), new Integer(vbBinaryCompare));
        context.put(new IdentNode("vbtextcompare"), new Integer(vbTextCompare));
        context.put(new IdentNode("vbusesystem"), new Integer(0));
        context.put(new IdentNode("vbsunday"), new Integer(1));
        context.put(new IdentNode("vbmonday"), new Integer(2));
        context.put(new IdentNode("vbtuesday"), new Integer(3));
        context.put(new IdentNode("vbwednesday"), new Integer(4));
        context.put(new IdentNode("vbthursday"), new Integer(5));
        context.put(new IdentNode("vbfriday"), new Integer(6));
        context.put(new IdentNode("vbsaturday"), new Integer(7));
        /* Functions */
        context.put(new IdentNode("abs"), new AbsFunction());
        context.put(new IdentNode("array"), new ArrayFunction());
        context.put(new IdentNode("asc"), new AscFunction());
        context.put(new IdentNode("ascb"), new AscBFunction());
        context.put(new IdentNode("ascw"), new AscFunction());
        context.put(new IdentNode("atn"), new AtnFunction());
        context.put(new IdentNode("cbool"), new CBoolFunction());
        context.put(new IdentNode("cbyte"), new CByteFunction());
        context.put(new IdentNode("ccur"), new CDblFunction());
        context.put(new IdentNode("cdate"), new CDateFunction());
        context.put(new IdentNode("cdbl"), new CDblFunction());
        context.put(new IdentNode("chr"), new ChrFunction());
        context.put(new IdentNode("chrw"), new ChrFunction());
        context.put(new IdentNode("cint"), new CIntFunction());
        context.put(new IdentNode("clng"), new CLngFunction());
        context.put(new IdentNode("cos"), new CosFunction());
        context.put(new IdentNode("createobject"), new CreateObjectFunction());
        context.put(new IdentNode("csng"), new CDblFunction());
        context.put(new IdentNode("cstr"), new CStrFunction());
        context.put(new IdentNode("date"), new DateFunction());
        context.put(new IdentNode("dateadd"), new DateAddFunction());
        context.put(new IdentNode("datediff"), new DateDiffFunction());
        context.put(new IdentNode("datepart"), new DatePartFunction());
        context.put(new IdentNode("dateserial"), new DateSerialFunction());
        context.put(new IdentNode("datevalue"), new DateValueFunction());
        context.put(new IdentNode("day"), new DayFunction());
        context.put(new IdentNode("erase"), new EraseFunction());
        context.put(new IdentNode("eval"), new EvalFunction());
        context.put(new IdentNode("exp"), new ExpFunction());
        context.put(new IdentNode("fix"), new FixFunction());
        context.put(new IdentNode("formatdatetime"), new FormatDateFunction());
        context.put(new IdentNode("formatnumber"), new FormatNumberFunction());
        context.put(new IdentNode("formatcurrency"), new FormatCurrencyFunction());
        context.put(new IdentNode("hex"), new HexFunction());
        context.put(new IdentNode("hour"), new HourFunction());
        context.put(new IdentNode("instr"), new InStrFunction());
        context.put(new IdentNode("instrrev"), new InStrRevFunction());
        context.put(new IdentNode("int"), new IntFunction());
        context.put(new IdentNode("isarray"), new IsArrayFunction());
        context.put(new IdentNode("isdate"), new IsDateFunction());
        context.put(new IdentNode("isempty"), new IsEmptyFunction());
        context.put(new IdentNode("isnull"), new IsNullFunction());
        context.put(new IdentNode("isnumeric"), new IsNumericFunction());
        context.put(new IdentNode("isobject"), new IsObjectFunction());
        context.put(new IdentNode("join"), new JoinFunction());
        context.put(new IdentNode("lbound"), new LBoundFunction());
        context.put(new IdentNode("lcase"), new LCaseFunction());
        context.put(new IdentNode("left"), new LeftFunction());
        context.put(new IdentNode("len"), new LenFunction());
        context.put(new IdentNode("log"), new LogFunction());
        context.put(new IdentNode("ltrim"), new LTrimFunction());
        context.put(new IdentNode("mid"), new MidFunction());
        context.put(new IdentNode("minute"), new MinuteFunction());
        context.put(new IdentNode("month"), new MonthFunction());
        context.put(new IdentNode("monthname"), new MonthNameFunction());
        context.put(new IdentNode("now"), new NowFunction());
        context.put(new IdentNode("oct"), new OctFunction());
        context.put(new IdentNode("randomize"), new RandomizeFunction());
        context.put(new IdentNode("replace"), new ReplaceFunction());
        context.put(new IdentNode("rgb"), new RGBFunction());
        context.put(new IdentNode("right"), new RightFunction());
        context.put(new IdentNode("rnd"), new RndFunction());
        context.put(new IdentNode("round"), new RoundFunction());
        context.put(new IdentNode("rtrim"), new RTrimFunction());
        context.put(new IdentNode("scriptengine"), new ScriptEngineFunction());
        context.put(new IdentNode("scriptenginebuildversion"),
            new ScriptEngineBuildVersionFunction());
        context.put(new IdentNode("scriptenginemajorversion"),
            new ScriptEngineMajorVersionFunction());
        context.put(new IdentNode("scriptengineminorversion"),
            new ScriptEngineMinorVersionFunction());
        context.put(new IdentNode("second"), new SecondFunction());
        context.put(new IdentNode("sgn"), new SgnFunction());
        context.put(new IdentNode("sin"), new SinFunction());
        context.put(new IdentNode("space"), new SpaceFunction());
        context.put(new IdentNode("split"), new SplitFunction());
        context.put(new IdentNode("sqr"), new SqrFunction());
        context.put(new IdentNode("strcomp"), new StrCompFunction());
        context.put(new IdentNode("string"), new StringFunction());
        context.put(new IdentNode("strreverse"), new StrReverseFunction());
        context.put(new IdentNode("tan"), new TanFunction());
        context.put(new IdentNode("time"), new TimeFunction());
        context.put(new IdentNode("timeserial"), new TimeSerialFunction());
        context.put(new IdentNode("trim"), new TrimFunction());
        context.put(new IdentNode("ubound"), new UBoundFunction());
        context.put(new IdentNode("ucase"), new UCaseFunction());
        context.put(new IdentNode("weekday"), new WeekdayFunction());
        context.put(new IdentNode("weekdayname"), new WeekdayNameFunction());
        context.put(new IdentNode("year"), new YearFunction());
        
        /* Error code */
        context.put(new IdentNode("err"),
            new JavaObjectNode(new AspNestedException()));
    }

    /** Absolute function */
    static class AbsFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context Context under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object value = values.get(0);
            if (Constants.nullNode.equals(value)) {
                return value;
            } else if (value instanceof Integer) {
                Integer iVal = Types.coerceToInteger(value);
                return new Integer(Math.abs(iVal.intValue()));
            } else {
                Double dVal = Types.coerceToDouble(value);
                return new Double(Math.abs(dVal.doubleValue()));
            }
        }
    }

    /** Array creation function */
    static class ArrayFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            ArrayNode array = new ArrayNode(values.size());
            for (int i = 0; i < values.size(); i++) {
                array._setValue(i, values.get(i));
            }
            return array;
        }
    }

    /** Ascii function */
    static class AscFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            String value = Types.coerceToString(values.get(0));
            return new Integer((int)value.charAt(0));
        }
    }

    /** Ascii Byte function */
    static class AscBFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            String value = Types.coerceToString(values.get(0));
            byte b = (byte)value.charAt(0);
            return new Integer(b);
        }
    }

    /** Arc-Tangent function */
    static class AtnFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object value = values.get(0);
            Double dVal = Types.coerceToDouble(value);
            return new Double(Math.atan(dVal.doubleValue()));
        }
    }

    /** Boolean conversion function */
    static class CBoolFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object value = values.get(0);
            return Types.coerceToBoolean(value);
        }
    }

    /** Byte (range 0-255) conversion function */
    static class CByteFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Integer iVal = Types.coerceToInteger(values.get(0));
            if (iVal.intValue() < 0 || iVal.intValue() > 255)
                throw new AspOverflowException(iVal.toString());
            return iVal;
        }
    }

    /** Date conversion function */
    static class CDateFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            return Types.coerceToDate(values.get(0));
        }
    }

    /** Double conversion function */
    static class CDblFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object value = values.get(0);
            return Types.coerceToDouble(value);
        }
    }

    /** Character conversion function */
    static class ChrFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Integer intVal = Types.coerceToInteger(values.get(0));
            return new String(""+((char)intVal.intValue()));
        }
    }

    /** 16-bit integer conversion function */
    static class CIntFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            double fPart = dVal - Math.floor(dVal);
            dVal = Math.round(dVal);
            if ((fPart == 0.5) && ((dVal % 2) != 0)) dVal--;
            return new Integer((int)dVal);
        }
    }

    /** 32-bit integer conversion function */
    static class CLngFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            double fPart = dVal - Math.floor(dVal);
            dVal = Math.round(dVal);
            if ((fPart == 0.5) && ((dVal % 2) != 0)) dVal--;
            return new Integer((int)dVal);
        }
    }

    /** Trigometric cosine function */
    static class CosFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            return new Double(Math.cos(dVal));
        }
    }

    /** Object creation function */
    static class CreateObjectFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }

            final IdentNode serverIdent = new IdentNode("server");
            JavaObjectNode serverObj =
                (JavaObjectNode)context.getValue(serverIdent);
            Server server = (Server)serverObj.getSubObject();
            String objectName = Types.coerceToString(values.get(0));
            try {
                return Types.coerceToNode(    
                    server.CreateObject(objectName));
            } catch (Exception ex) {
                throw new AspNestedException(ex);
            }
        }
    }

    /** String conversion function */
    static class CStrFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object value = values.get(0);
            /* XXX This should really be something like Null Pointer Exception */
            if (value == null || value instanceof NullNode)
                throw new AspCastException("NULL");
            return Types.coerceToString(values.get(0));
        }
    }

    /** Date current time function */
    static class DateFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Date date = new java.util.Date(System.currentTimeMillis());
            return new AspDate(date, false, true);
        }
    }

    /** Date addition function */
    static class DateAddFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=3) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            String field = Types.coerceToString(values.get(0));
            Object obj = values.get(1);
            int offset;
            if (obj == null || obj instanceof NullNode)
                offset = 0;
            else
                offset = (int)Math.round(Types.coerceToDouble(values.get(1)).
                    doubleValue());
            AspDate date = Types.coerceToDate(values.get(2));
    
            Calendar cal = date.toCalendar();
            int fieldID;
            if (field.equalsIgnoreCase("yyyy")) {
                fieldID = cal.YEAR;
            } else if (field.equalsIgnoreCase("q")) {
                offset *= 3;
                fieldID = cal.MONTH;
            } else if (field.equalsIgnoreCase("m")) {
                fieldID = cal.MONTH;
            } else if (field.equalsIgnoreCase("y")) {
                fieldID = cal.DAY_OF_YEAR;
            } else if (field.equalsIgnoreCase("d")) {
                fieldID = cal.DAY_OF_MONTH;
            } else if (field.equalsIgnoreCase("w")) {
                fieldID = cal.DAY_OF_MONTH;
            } else if (field.equalsIgnoreCase("ww")) {
                fieldID = cal.WEEK_OF_YEAR;
            } else if (field.equalsIgnoreCase("h")) {
                fieldID = cal.HOUR_OF_DAY;
            } else if (field.equalsIgnoreCase("n")) {
                fieldID = cal.MINUTE;
            } else if (field.equalsIgnoreCase("s")) {
                fieldID = cal.SECOND;
            } else {
                throw new AspException("Unknown date field identifier: " + field);
            }
            cal.add(fieldID, offset);
            return new AspDate(cal, date.hasTime(), true);
        }
    }

    /** Date difference function */
    static class DateDiffFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=3) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            String field = Types.coerceToString(values.get(0));
            AspDate aDate = Types.coerceToDate(values.get(1));
            Calendar aCal = aDate.toCalendar();

            AspDate bDate = Types.coerceToDate(values.get(2));
            Calendar bCal = bDate.toCalendar();

            int diff;
    
            if (field.equalsIgnoreCase("yyyy")) {
                int aYear = aCal.get(aCal.YEAR);
                int bYear = bCal.get(bCal.YEAR);
                diff = bYear - aYear;
            } else if (field.equalsIgnoreCase("q")) {
                int aYear = aCal.get(aCal.YEAR);
                int aMonth = aCal.get(aCal.MONTH);
                int bYear = bCal.get(bCal.YEAR);
                int bMonth = bCal.get(bCal.MONTH);
                diff = (bMonth - aMonth) + ((bYear - aYear)*12);
                diff /= 3;
            } else if (field.equalsIgnoreCase("m")) {
                int aYear = aCal.get(aCal.YEAR);
                int aMonth = aCal.get(aCal.MONTH);
                int bYear = bCal.get(bCal.YEAR);
                int bMonth = bCal.get(bCal.MONTH);
                diff = (bMonth - aMonth) + ((bYear - aYear)*12);
            } else if (field.equalsIgnoreCase("y") ||
                    field.equalsIgnoreCase("d")) {
                long aTime = aDate.toDate().getTime()/1000;
                long bTime = bDate.toDate().getTime()/1000;
                int aDays = (int)(aTime / 86400);
                int bDays = (int)(bTime / 86400);

                if (DBG.isDebugEnabled())
                {
                    DBG.debug("aTime: " + aTime);
                    DBG.debug("aDays: " + aDays);
                    DBG.debug("bTime: " + bTime);
                    DBG.debug("bDays: " + bDays);
                }
    
                return new Integer((int)(bDays - aDays));
            } else if (field.equalsIgnoreCase("w")) {
                throw new AspNotImplementedException("'w' field in datediff");
            } else if (field.equalsIgnoreCase("ww")) {
                throw new AspNotImplementedException("'ww' field in datediff");
            } else if (field.equalsIgnoreCase("h")) {
                long aTime = aDate.toDate().getTime() / 1000;
                long bTime = bDate.toDate().getTime() / 1000;

                return new Integer((int)(bTime - aTime)/3600);
            } else if (field.equalsIgnoreCase("n")) {
                long aTime = aDate.toDate().getTime() / 1000;
                long bTime = bDate.toDate().getTime() / 1000;
    
                return new Integer((int)(bTime - aTime)/60);
            } else if (field.equalsIgnoreCase("s")) {
                long aTime = aDate.toDate().getTime() / 1000;
                long bTime = bDate.toDate().getTime() / 1000;
    
                return new Integer((int)(bTime - aTime));
            } else {
                throw new AspException("Unknown date field identifier: " + field);
            }
            return new Integer(diff);
        }
    }

    /** Date part function */
    static class DatePartFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=2) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            String field = Types.coerceToString(values.get(0));
            AspDate aDate = Types.coerceToDate(values.get(1));
            Calendar aCal = aDate.toCalendar();

            int val;

            if (field.equalsIgnoreCase("yyyy")) {
                val = aCal.get(aCal.YEAR);
            } else if (field.equalsIgnoreCase("q")) {
                val = (aCal.get(aCal.MONTH) / 4)+1;
            } else if (field.equalsIgnoreCase("m")) {
                val = (aCal.get(aCal.MONTH))+1;
            } else if (field.equalsIgnoreCase("y")) {
                val = aCal.get(aCal.DAY_OF_YEAR);
            } else if (field.equalsIgnoreCase("d")) {
                val = aCal.get(aCal.DAY_OF_MONTH);
            } else if (field.equalsIgnoreCase("w")) {
                val = aCal.get(aCal.DAY_OF_WEEK);
            } else if (field.equalsIgnoreCase("ww")) {
                val = aCal.get(aCal.WEEK_OF_YEAR);
            } else if (field.equalsIgnoreCase("h")) {
                val = aCal.get(aCal.HOUR_OF_DAY);
            } else if (field.equalsIgnoreCase("n")) {
                val = aCal.get(aCal.MINUTE);
            } else if (field.equalsIgnoreCase("s")) {
                val = aCal.get(aCal.SECOND);
            } else {
                throw new AspException("Unknown date field identifier: " + field);
            }
            return new Integer(val);
        }
    }

    /** Date serial function */
    static class DateSerialFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=3) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            int year = Types.coerceToInteger(values.get(0)).intValue();
            if (year < 100) year += 1900;
            int month = Types.coerceToInteger(values.get(1)).intValue()-1;
            int day = Types.coerceToInteger(values.get(2)).intValue();
            return new AspDate(month,day,year);
        }
    }

    /** Date value function */
    static class DateValueFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            AspDate date = Types.coerceToDate(values.get(0));
            return new AspDate(date.getMonth(), date.getDay(), date.getYear());
        }
    }

    /** Day function */
    static class DayFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object objDate = values.get(0);
            if (Constants.nullNode.equals(objDate)) return objDate;
            AspDate aDate = Types.coerceToDate(objDate);
            Calendar aCal = aDate.toCalendar();

            int val = aCal.get(aCal.DAY_OF_MONTH);
            return new Integer(val);
        }
    }

    /** Erase function */
    static class EraseFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object objArr = values.get(0);
            if (Constants.nullNode.equals(objArr)) return objArr;
            if (!(objArr instanceof ArrayNode)) {
                throw new AspInvalidArgumentsException(objArr.getClass().toString());
            }
            ArrayNode arr = (ArrayNode)objArr;
            arr.internErase();
            return null;
        }
    }

    /** Eval function */
    static class EvalFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
 
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            
            try {
                String sVal = Types.coerceToString(values.get(0));
                Reader r    = new StringReader(sVal);
                SimpleCharStream scs = new SimpleCharStream(r);
                
                VBScriptTokenManagerInterface tokManager = new VBScriptTokenManagerInterface(scs);
                VBScript script = new VBScript(tokManager);
                
                Node node = script.ExprNode();
                node.prepare(context);
                Object result = node.execute(context);
                return result;
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getClass());
                ex.printStackTrace();
            }

            return null;
        }
    }
    
    /** Math exponent function */
    static class ExpFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            return new Double(Math.exp(dVal));
        }
    }

    /** Number fix function */
    static class FixFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            return new Integer((int)dVal);
        }
    }

    /** Format date function */
    static class FormatDateFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()>2 || values.size()<1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            AspDate aspDate = Types.coerceToDate(values.get(0));
            Date date = aspDate.toDate();
            int type;
            if (values.size() > 1)
                type = Types.coerceToInteger(values.get(1)).intValue();
            else
                type = vbGeneralDate;
            if (type == vbGeneralDate) {
                return AspDate.longDateTimeFormat.format(date);
            } else if (type == vbLongDate) {
                return AspDate.longDateFormat.format(date);
            } else if (type == vbShortDate) {
                return AspDate.shortDateFormat.format(date);
            } else if (type == vbShortTime) {
                return AspDate.shortTimeFormat.format(date);
            } else if (type == vbLongTime) {
                return AspDate.longTimeFormat.format(date);
            }
            return date.toString();
        }
    }

    /** Format number function */
    static class FormatNumberFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Double num = Types.coerceToDouble(values.get(0));
            Integer prec = Types.coerceToInteger(values.get(1));
            NumberFormat form = new DecimalFormat("0");
            form.setMaximumFractionDigits(prec.intValue());
            return form.format(num);
        }
    }

    /** Format currency function */
    static class FormatCurrencyFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<1) 
                throw new AspException("Invalid number of arguments: " + values.size());
            Double num = Types.coerceToDouble(values.get(0));
            Integer prec = new Integer(2);
            Integer leading = new Integer(-2);
            Integer negParens = new Integer(-2);
            Integer groupDigit = new Integer(-2);
            if (values.size() >= 2) 
                prec = Types.coerceToInteger(values.get(1));
            if (values.size() >= 3) 
                leading = Types.coerceToInteger(values.get(2));
            if (values.size() >= 4) 
                negParens = Types.coerceToInteger(values.get(3));
            if (values.size() == 5) 
                groupDigit = Types.coerceToInteger(values.get(4));
		    String decimals = ".";
		    for (int i = 0; i < prec.intValue(); i++) 
		    	decimals += "0";
		    
            DecimalFormat form = new DecimalFormat("¤0" + decimals);
            
            if(leading.intValue() == -1) {
            	form.setMinimumIntegerDigits(1);
            } else if(leading.intValue() == -2) {
            	form.setMinimumIntegerDigits(DecimalFormat.getInstance().getMinimumIntegerDigits());
            } else if(leading.intValue() == 0) {
                form.setMinimumIntegerDigits(0);
            }
            
            if (negParens.intValue() == -1) {
                form.setNegativePrefix("(" + form.getPositivePrefix());
                form.setNegativeSuffix(")");
            } else if(negParens.intValue() == -2) {
            	String negPref = 	((DecimalFormat) DecimalFormat.getInstance()).getNegativePrefix() + 
			               			((DecimalFormat) DecimalFormat.getInstance()).getPositivePrefix();
            	String negSuf  = 	((DecimalFormat) DecimalFormat.getInstance()).getNegativeSuffix();
            	form.setNegativePrefix(negPref);
            	form.setNegativeSuffix(negSuf);
            } else if(negParens.intValue() == 0) {
            	form.setNegativePrefix("-" + form.getPositivePrefix());
            	form.setNegativeSuffix("");
            }
            
        	form.setGroupingSize(3);
            if(groupDigit.intValue() == -1) {
            	form.setGroupingUsed(true);
            } else if (groupDigit.intValue() == -2) {
                 form.setGroupingUsed(DecimalFormat.getCurrencyInstance().isGroupingUsed());
            } else if (groupDigit.intValue() == 0 ){
            	form.setGroupingUsed(false);
            }

            form.setMaximumFractionDigits(prec.intValue());
            form.setCurrency(Currency.getInstance(Locale.getDefault()));

            if (Locale.getDefault() == Locale.US) {
                return "$" + form.format(num);
            } else {
                return form.format(num);
            }
        }
    }


    /** Hex format function */
    static class HexFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object dObj = values.get(0);
            if (Constants.nullNode.equals(dObj)) {
                return dObj;
            }
            int val = Types.coerceToInteger(dObj).intValue();
            return Integer.toHexString(val).toUpperCase();
        }
    }

    /** Hour function */
    static class HourFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object objDate = values.get(0);
            if (Constants.nullNode.equals(objDate)) return objDate;
            AspDate aDate = Types.coerceToDate(objDate);
            Calendar aCal = aDate.toCalendar();

            if (DBG.isDebugEnabled()) {
                DBG.debug("Calendar: " + aCal);
            }
            int val = aCal.get(aCal.HOUR_OF_DAY);
            return new Integer(val);
        }
    }

    /** String search function */
    static class InStrFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<2 || values.size() > 4) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            /* Possibilities:
                string, tofind
                start, string, tofind
                start, string, tofind, compare
            */
            int start = 1;
            Object stringObj;
            String string;
            Object tofindObj;
            String tofind;
            int compare = vbBinaryCompare;
            if (values.size() >= 3) {
                start = Types.coerceToInteger(values.get(0)).intValue();
                stringObj = values.get(1);
                tofindObj = values.get(2);
                if (values.size() == 4) {
                    compare = Types.coerceToInteger(values.get(3)).intValue();
                }
            } else {
                stringObj = values.get(0);
                tofindObj = values.get(1);
            }
            if (Constants.nullNode.equals(stringObj))
                return Constants.nullNode;
            if (Constants.nullNode.equals(tofindObj))
                return Constants.nullNode;
            string = Types.coerceToString(stringObj);
            tofind = Types.coerceToString(tofindObj);
            if (tofind.length()==0) {
                return new Integer(1);
            }
            int indexOf;
            if (compare == vbTextCompare)
                indexOf = string.toLowerCase().indexOf(tofind.toLowerCase(),
                        start - 1);
            else 
                indexOf = string.indexOf(tofind, start - 1);
            return new Integer(indexOf + 1);
        }
    }

    /** Reverse string search function */
    static class InStrRevFunction extends AbstractFunctionNode
    {
        /**
         * Executes the InStrRev function. It differs from the Java 
         * lastIndexOf in the definition of the 'start' (third) paramater.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<2 || values.size() > 4) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            /* Possibilities:
                string, tofind
                start, string, tofind
                start, string, tofind, compare
            */
            int start = -1;
            Object stringObj;
            String string;
            Object tofindObj;
            String tofind;
            int compare = vbBinaryCompare;
            stringObj = values.get(0);
            tofindObj = values.get(1);
            if (Constants.nullNode.equals(stringObj))
                return Constants.nullNode;
            if (Constants.nullNode.equals(tofindObj))
                return Constants.nullNode;
            string = Types.coerceToString(stringObj);
            tofind = Types.coerceToString(tofindObj);
            if (values.size() >= 3) {
                start = Types.coerceToInteger(values.get(2)).intValue();
                if (values.size() == 4) {
                    compare = Types.coerceToInteger(values.get(3)).intValue();
                }
            }
            if (tofind.length()==0) {
                return new Integer(string.length());
            }
            if (start == -1) {
                start = string.length();
            } else if (start < tofind.length()) {
                return new Integer(0);
            } else if (start > string.length()) {
                return new Integer(0);
            } else {
                start -= (tofind.length() - 1);
            }
            int indexOf;
            if (DBG.isDebugEnabled()) {
                DBG.debug("String: [" + string + "]");
                DBG.debug("ToFind: [" + tofind + "]");
                DBG.debug("Start: [" + start + "]");
                DBG.debug("compare: [" + compare + "]");
            }
            if (compare == vbTextCompare)
                indexOf = string.toLowerCase().lastIndexOf(tofind.toLowerCase(),
                        start - 1);
            else 
                indexOf = string.lastIndexOf(tofind, start - 1);
            if (DBG.isDebugEnabled()) {
                DBG.debug("Return: [" + indexOf + "]");
            }
            return new Integer(indexOf + 1);
        }
    }

    /** Convert to integer function */
    static class IntFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object aVal = Types.dereference(values.get(0));
            if (Constants.nullNode.equals(aVal))
                return Constants.nullNode;
            double dVal = Types.coerceToDouble(aVal).doubleValue();
            int iVal = (int)dVal;
            if (iVal < 0) {
                if (iVal > dVal) iVal--;
            }
            return new Integer(iVal);
        }
    }

    /** Boolean is array function */
    static class IsArrayFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object obj = values.get(0);
            return new Boolean(obj instanceof ArrayNode);
        }
    }

    /** Boolean is date function */
    static class IsDateFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object obj = values.get(0);
            if (obj instanceof String)
            try {
                AspDate date = Types.coerceToDate(obj);
                return new Boolean(true);
            } catch (AspCastException ex)
            {
                return new Boolean(false);
            }
            return new Boolean(obj instanceof AspDate);
        }
    }

    /** Lower bound function */
    static class LBoundFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            int dimension;
            if (values.size()>2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            if (values.size() == 1) {
                dimension = 1;
            } else {
                dimension = Types.coerceToInteger(
                    values.get(1)).intValue();
            }
            Object obj = values.get(0);
            while (obj instanceof SimpleReference &&
                !(obj instanceof ArrayNode) &&
                !(obj instanceof MapNode)) {
                if (DBG.isDebugEnabled())
                    DBG.debug("Dereferencing " + obj);
                obj = ((SimpleReference)obj).getValue();
            }
            if (obj instanceof ArrayNode) {
                return new Integer(((ArrayNode)obj).getLBOUND(dimension));
            }
            if (obj instanceof MapNode) {
                return new Integer(((MapNode)obj).getLBOUND(dimension));
            }
            throw new AspException("LBOUND called on non-array object: " + obj);
        }
    }

    /** Left string function */
    static class LeftFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return Constants.nullNode;
            String string = Types.coerceToString(obj);
            int index = Types.coerceToInteger(values.get(1)).intValue();
            if (index >= string.length()) {
                return string;
            } else {
                return string.substring(0, index);
            }
        }
    }

    /** Mid string function */
    static class MidFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<2 || values.size()>3) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return Constants.nullNode;
            String string = Types.coerceToString(obj);
            int index = Types.coerceToInteger(values.get(1)).intValue() - 1;
            if (values.size()>2) {
                int length = Types.coerceToInteger(
                    values.get(2)).intValue();
                if (length < 0) length = 0;
                if (index + length > string.length()) {
                    length = string.length() - index;
                }
                return string.substring(index, index + length);
            } else {
                if (index < 0) return string;
                if (index >= string.length()) return "";
                return string.substring(index);
            }
        }
    }

    /** Empty test function */
    static class IsEmptyFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (obj instanceof UndefinedValueNode) {
                return new Boolean(true);
            } else {
                return new Boolean(false);
            }
        }
    }

    /** Null test function */
    static class IsNullFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj)) {
                return new Boolean(true);
            } else {
                return new Boolean(false);
            }
        }
    }

    /** Numeric test function */
    static class IsNumericFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            try {
                Double dVal = Types.coerceToDouble(values.get(0));
            } catch (AspCastException ex) {
                return new Boolean(false);
            }
            return new Boolean(true);
        }
    }

    /** Object test function */
    static class IsObjectFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (obj instanceof ObjectNode) {
                return new Boolean(true);
            } else {
                return new Boolean(false);
            }
        }
    }

    /** Array Join function */
    static class JoinFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<1 || values.size()>2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            ArrayNode array = (ArrayNode)values.get(0);
            String delim = " ";
            if (values.size() > 1) {
                delim = Types.coerceToString(values.get(1));
            }
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i <= array.getUBOUND(1); i++)
            {
                VarListNode vln = new VarListNode();
                vln.append(new Integer(i));
                String elem = Types.coerceToString(array.getIndex(vln, context));
                buf.append(elem);
                if (i < array.getUBOUND(1)) {
                    buf.append(delim);
                }
            }
            return buf.toString();
        }
    }

    /** Lower case function */
    static class LCaseFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return obj;
            String string = Types.coerceToString(obj);
            return string.toLowerCase();
        }
    }

    /** Length function */
    static class LenFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return obj;
            String string = Types.coerceToString(obj);
            return new Integer(string.length());
        }
    }

    /** Logarithmic function */
    static class LogFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            return new Double(Math.log(dVal));
        }
    }

    /** String left trim function */
    static class LTrimFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return obj;
            String string = Types.coerceToString(obj);
            int i = 0;
            while (i < string.length()) {
                if (!Character.isWhitespace(string.charAt(i)))
                    break;
                i++;
            }
            return string.substring(i);
        }
    }

    /** Minute function */
    static class MinuteFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object objDate = values.get(0);
            if (Constants.nullNode.equals(objDate)) return objDate;
            AspDate aDate = Types.coerceToDate(objDate);
            Calendar aCal = aDate.toCalendar();

            int val = aCal.get(aCal.MINUTE);
            return new Integer(val);
        }
    }

    /** Month function */
    static class MonthFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object objDate = values.get(0);
            if (Constants.nullNode.equals(objDate)) return objDate;
            AspDate aDate = Types.coerceToDate(objDate);
            Calendar aCal = aDate.toCalendar();

            int val = aCal.get(aCal.MONTH) + 1;
            return new Integer(val);
        }
    }

    /** Month name function */
    static class MonthNameFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<1 || values.size()>2) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            int month = Types.coerceToInteger(values.get(0)).intValue();
            if (month < 1 || month > 12) {
                throw new AspInvalidArgumentsException("Invalid month: "
                            + month);
            }
            boolean abbreviate = false;
            if (values.size() > 1) {
                abbreviate =
                    Types.coerceToBoolean(values.get(1)).booleanValue();
            }
            DateFormatSymbols symbols = new DateFormatSymbols();
            String months[];
            if (abbreviate) months = symbols.getShortMonths();
                else months = symbols.getMonths();
            return months[month-1];
        }
    }

    /** Date current time function */
    static class NowFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            return new AspDate(new java.util.Date(System.currentTimeMillis()));
        }
    }

    /** Octal format function */
    static class OctFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object dObj = values.get(0);
            if (Constants.nullNode.equals(dObj)) {
                return dObj;
            }
            int val = Types.coerceToInteger(dObj).intValue();
            return Integer.toOctalString(val);
        }
    }

    /** Random number initialization function */
    static class RandomizeFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            /* XXX */
            return null;
        }
    }

    /** String replace function */
    static class ReplaceFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<3 || values.size()>6) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            String string = Types.coerceToString(values.get(0));
            String toReplace = Types.coerceToString(values.get(1));
            String replacement = Types.coerceToString(values.get(2));
            int start = 1;
            int count = -1;
            int compare = vbBinaryCompare;
            if (values.size() > 3)
                start = Types.coerceToInteger(values.get(3)).intValue();
            if (values.size() > 4)
                count = Types.coerceToInteger(values.get(4)).intValue();
            if (values.size() > 5)
                compare = Types.coerceToInteger(values.get(5)).intValue();

            if (toReplace.length() == 0) {
                return string;
            }

            StringBuffer buf = new StringBuffer();
            int lastIndex = start - 1;
            int index = start - 1;
            if (compare == vbBinaryCompare)
                index = string.indexOf(toReplace, index);
            else
                index = string.toLowerCase().indexOf(toReplace.toLowerCase(),
                    index);
            while (index != -1 && count != 0)
            {
                buf.append(string.substring(lastIndex, index));
                buf.append(replacement);
                lastIndex = index + toReplace.length();
                if (compare == vbBinaryCompare)
                    index = string.indexOf(toReplace, lastIndex);
                else
                    index = string.toLowerCase().indexOf(
                        toReplace.toLowerCase(), lastIndex);
                count--;
            }
            if (lastIndex < string.length()) 
                buf.append(string.substring(lastIndex));

            return buf.toString();
        }
    }

    /** RGB function */
    static class RGBFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=3) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            int red = Types.coerceToInteger(values.get(0)).intValue();
            if (red < 0) red = 0;
            if (red > 255) red = 255;
            int green = Types.coerceToInteger(values.get(1)).intValue();
            if (green < 0) green = 0;
            if (green > 255) green = 255;
            int blue = Types.coerceToInteger(values.get(2)).intValue();
            if (blue < 0) blue = 0;
            if (blue > 255) blue = 255;
            return new Integer((blue * 65536) + (green * 256) + red);
        }
    }

    /** String right function */
    static class RightFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return obj;
            String string = Types.coerceToString(obj);
            int ind = Types.coerceToInteger(values.get(1)).intValue();

            if (string.length() == 0) {
                return string;
            }
            if (ind >= string.length()) {
                return string;
            }
            return string.substring(string.length() - ind, string.length());
        }
    }

    /** Random number function */
    static class RndFunction extends AbstractFunctionNode
    {
        /**
         * Random number generator.
         */
        Random rndGenerator = new Random();

        /**
         * Last random number generated
         */
        Double lastNumber = null;

        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()>1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            int rndParam = 1;
            if (values.size()>0)
                rndParam = Types.coerceToInteger(values.get(0)).intValue();
            if (rndParam < 0) rndGenerator.setSeed(rndParam);
            if (rndParam != 0)
                lastNumber = new Double(rndGenerator.nextDouble());
            return lastNumber;
        }
    }

    /** Round function */
    static class RoundFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()>2 || values.size() < 1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double doubValue =
                Types.coerceToDouble(values.get(0)).doubleValue();
            int numPlaces = 0;
            if (values.size() > 1)
                numPlaces = Types.coerceToInteger(values.get(1)).intValue();
            double places;
            if (numPlaces > 0)
                places = Math.pow(10, numPlaces);
            else
                places = 1;
            doubValue *= places;
            double fPart = doubValue - Math.floor(doubValue);
            doubValue = Math.round(doubValue);
            if ((fPart == 0.5) && ((doubValue % 2) != 0)) doubValue--;
            doubValue /= places;
            return new Double(doubValue);
        }
    }

    /** String left trim function */
    static class RTrimFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return obj;
            String string = Types.coerceToString(obj);
            int i = string.length() - 1;
            while (i >= 0) {
                if (!Character.isWhitespace(string.charAt(i)))
                    break;
                i--;
            }
            return string.substring(0, i + 1);
        }
    }

    /** Script Engine function */
    static class ScriptEngineFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            return "VBScript";
        }
    }

    /** Script Engine Build Version function */
    static class ScriptEngineBuildVersionFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            return "0";
        }
    }

    /** Script Engine Major Version function */
    static class ScriptEngineMajorVersionFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            return new Integer(5);
        }
    }

    /** Script Engine Minor Version function */
    static class ScriptEngineMinorVersionFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            return new Integer(0);
        }
    }

    /** Second function */
    static class SecondFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object objDate = values.get(0);
            if (Constants.nullNode.equals(objDate)) return objDate;
            AspDate aDate = Types.coerceToDate(objDate);
            Calendar aCal = aDate.toCalendar();

            int val = aCal.get(aCal.SECOND);
            return new Integer(val);
        }
    }

    /** Sgn function */
    static class SgnFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            int val = Types.coerceToInteger(values.get(0)).intValue();
            if (val > 0) return new Integer(1);
            if (val < 0) return new Integer(-1);
            return new Integer(0);
        }
    }

    /** Trigometric sine function */
    static class SinFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            return new Double(Math.sin(dVal));
        }
    }

    /** Space function */
    static class SpaceFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            int iVal = Types.coerceToInteger(values.get(0)).intValue();
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < iVal; i++)
                buf.append(' ');
            return buf.toString();
        }
    }

    /** Split function */
    static class SplitFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<1 || values.size()>4) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            String expr = Types.coerceToString(values.get(0));
            if (expr.length() == 0) return new ArrayNode(0);
            String delim = " ";
            if (values.size() > 1) delim = Types.coerceToString(values.get(1));
            int count = -1;
            if (values.size() > 2)
                count = Types.coerceToInteger(values.get(2)).intValue();
            int compare = vbBinaryCompare;
            if (values.size() > 3)
                compare = Types.coerceToInteger(values.get(3)).intValue();

            Vector res = new Vector();
            int lastPos = 0;
            int index;
            if (compare == vbTextCompare)
                index = expr.toLowerCase().indexOf(delim.toLowerCase());
            else
                index = expr.indexOf(delim);
            /* Note that if count == -1, there is no limit */
            if (count != 0)
            {
                count--;
                while (index != -1 && count != 0) {
                    res.add(expr.substring(lastPos, index));
                    index += delim.length();
                    lastPos = index;
                    if (compare == vbTextCompare)
                        index = expr.toLowerCase().indexOf(delim.toLowerCase(),
                            index);
                    else
                        index = expr.indexOf(delim, index);
                    count--;
                }
                res.add(expr.substring(lastPos));
            }
            ArrayNode array = new ArrayNode(res.size());
            for (int i = 0; i < res.size(); i++)
                array._setValue(i, res.get(i));
            return array;
        }
    }

    /** Square root function */
    static class SqrFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            return new Double(Math.sqrt(dVal));
        }
    }

    /** String compare function */
    static class StrCompFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<2 || values.size() > 3) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object aObj = values.get(0);
            if (Constants.nullNode.equals(aObj)) return Constants.nullNode;
            String aStr = Types.coerceToString(aObj);
            Object bObj = values.get(1);
            if (Constants.nullNode.equals(bObj)) return Constants.nullNode;
            String bStr = Types.coerceToString(bObj);
            int compare = vbBinaryCompare;
            if (values.size() > 2)
                compare = Types.coerceToInteger(values.get(2)).intValue();
            int res;
            if (compare == vbTextCompare)
                res = aStr.compareToIgnoreCase(bStr);
            else
                res = aStr.compareTo(bStr);
            if (res > 0) return new Integer(1);
            if (res < 0) return new Integer(-1);
            return new Integer(0);
        }
    }

    /** Character repeat function */
    static class StringFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            int number = Types.coerceToInteger(values.get(0)).intValue();
            Object chObj = Types.dereference(values.get(1));
            char ch;
            if (chObj instanceof Integer)
            {
                ch = (char)Types.coerceToInteger(values.get(1)).intValue();
            } else {
                String strObj = Types.coerceToString(chObj);
                ch = strObj.charAt(0);
            }
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < number; i++)
                buf.append(ch);
            return buf.toString();
        }
    }

    /** String reverse function */
    static class StrReverseFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            String strVal = Types.coerceToString(values.get(0));
            StringBuffer buf = new StringBuffer();
            for (int i = strVal.length() - 1; i >= 0; i--)
            {
                buf.append(strVal.charAt(i));
            }
            return buf.toString();
        }
    }

    /** Trigometric tangent function */
    static class TanFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            double dVal = Types.coerceToDouble(values.get(0)).doubleValue();
            return new Double(Math.tan(dVal));
        }
    }

    /** Date current time function */
    static class TimeFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=0) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Date date = new java.util.Date(System.currentTimeMillis());
            return new AspDate(date, true, false);
        }
    }

    /** Time serial function */
    static class TimeSerialFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=3) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            int hour = Types.coerceToInteger(values.get(0)).intValue();
            int minute = Types.coerceToInteger(values.get(1)).intValue();
            int second = Types.coerceToInteger(values.get(2)).intValue();

            return new AspDate(-1,-1,-1,hour,minute,second);
        }
    }

    /** String trim function */
    static class TrimFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj))
                return Constants.nullNode;
            String string = Types.coerceToString(obj);
            return string.trim();
        }
    }

    /** Upper bound function */
    static class UBoundFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            int dimension;
            if (values.size()>2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            if (values.size() == 1) {
                dimension = 1;
            } else {
                dimension = Types.coerceToInteger(
                    values.get(1)).intValue();
            }
            Object obj = values.get(0);
            while (obj instanceof SimpleReference &&
                !(obj instanceof ArrayNode) &&
                !(obj instanceof MapNode)) {
                if (DBG.isDebugEnabled())
                    DBG.debug("Dereferencing " + obj);
                obj = ((SimpleReference)obj).getValue();
            }
            if (obj instanceof ArrayNode) {
                return new Integer(((ArrayNode)obj).getUBOUND(dimension));
            }
            if (obj instanceof MapNode) {
                return new Integer(((MapNode)obj).getUBOUND(dimension));
            }
            throw new AspException("UBOUND called on non-array object: " + obj);
        }
    }

    /** Upper case function */
    static class UCaseFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj)) return obj;
            String string = Types.coerceToString(values.get(0));
            return string.toUpperCase();
        }
    }

    /** Weekday function */
    static class WeekdayFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<1 || values.size()>2) {
                throw new AspException("Invalid number of arguments: " +
                    values.size());
            }
            Object obj = values.get(0);
            if (Constants.nullNode.equals(obj)) return obj;
            int ofVal = 0;
            if (values.size()>=2) {
                ofVal = Types.coerceToInteger(values.get(1))
                        .intValue() - 1;
            }
            AspDate date = Types.coerceToDate(obj);
            Calendar cal = date.toCalendar();
            int weekday = (cal.get(cal.DAY_OF_WEEK) - ofVal) % 7;
            if (weekday <= 0) weekday += 7;
            return new Integer(weekday);
        }
    }

    /** Weekday name function */
    static class WeekdayNameFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()<1 || values.size()>2) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            int day = Types.coerceToInteger(values.get(0)).intValue();
            if (day < 1 || day > 7) {
                throw new AspInvalidArgumentsException("Invalid weekday: "
                            + day);
            }
            boolean abbreviate = false;
            if (values.size() > 1) {
                abbreviate =
                    Types.coerceToBoolean(values.get(1)).booleanValue();
            }
            DateFormatSymbols symbols = new DateFormatSymbols();
            String days[];
            if (abbreviate) days = symbols.getShortWeekdays();
                else days = symbols.getWeekdays();
            return days[day];
        }
    }

    /** Year function */
    static class YearFunction extends AbstractFunctionNode
    {
        /**
         * Executes this function.
         * @param values Parameters for this expression.
         * @param context AspContext under which to evaluate this expression.
         * @return Return value of this expression.
         * @throws AspException if an error occurs.
         * @see AbstractFunction#execute(Vector,AspContext)
         */
        public Object execute(Vector values, AspContext context)
            throws AspException
        {
            if (values.size()!=1) {
                throw new AspException("Invalid number of " +
                    "arguments: " + values.size());
            }
            Object objDate = values.get(0);
            if (Constants.nullNode.equals(objDate)) return Constants.nullNode;
            AspDate aDate = Types.coerceToDate(objDate);
            Calendar aCal = aDate.toCalendar();

            int val = aCal.get(aCal.YEAR);
            return new Integer(val);
        }
    }
}
