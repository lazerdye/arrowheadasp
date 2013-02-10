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

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Category;

/**
 * ArithmenticNode handles arithmetic operations.
 * Status of implementation:
 * <ul>
 * <li><b>+</b> Addition - Fully implemented.
 * <li><b>and</b> - Fully implemented.
 * <li><b>&</b> Concat - Fully implemented.
 * <li><b>/</b> Division - Fully implemented.
 * <li><b>eqv</b> - Fully implemented.
 * <li><b>^</b> Exponent - Fully implemented.
 * <li><b>imp</b> Logical implication - Fully implemented.
 * <li><b>\</b> Integer division - Fully implemented.
 * <li><b>is</b> object comparison - <B>TODO</b> Not implemented.
 * <li><b>mod</b> modulus - Fully implemented.
 * <li><b>*</b> multiply - Fully implemented.
 * <li><b>-</b> negation - Fully implemented.
 * <li><b>not</b> logical negation - Fully implemented.
 * <li><b>or</b> logical or - Fully implemented.
 * 
 * </ul>
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ArithmeticNode implements Node
{
    /** Debugging class */
    private static final transient Category DBG = Category.getInstance(ArithmeticNode.class.getName());

    /** The plus operator. */
    public static final int PLUS = 1;

    /** The minus operator. */
    public static final int MINUS = 2;

    /** The multiplicative operator. */
    public static final int MULT = 3;

    /** The division operator. */
    public static final int DIV = 4;

    /** The integer division operator. */
    public static final int INTDIV = 5;

    /** The string concatenation operator. */
    public static final int CONCAT = 6;

    /** The equal operator. */
    public static final int EQ = 7;

    /** The greater than operator. */
    public static final int GT = 8;

    /** Less than operator */
    public static final int LT = 9;

    /** Not equal operator */
    public static final int NE = 10;

    /** Logical not operator */
    public static final int NOT = 11;

    /** Logical or operator */
    public static final int OR = 12;

    /** Logical and operator */
    public static final int AND = 13;

    /** Exponent (power) operator */
    public static final int EXP = 14;

    /** Logical equivalence operator */
    public static final int EQV = 15;

    /** Logical implication operator */
    public static final int IMP = 16;

    /** Modulus operator */
    public static final int MOD = 17;

    /** Logical exclusive or operator */
    public static final int XOR = 18;

    /** Greater than or equal operator */
    public static final int GE = 19;

    /** Less than or equal operator */
    public static final int LE = 20;

    /** Object equivilence */
    public static final int IS = 21;

    /** Left side of expression */
    Object left;

    /** Right side of expression */
    Object right;

    /** Type of expression */
    int    type;

    /**
     * Constructor for creating a new arithmetic node.
     * @param left Left side of expression.
     * @param right Right side of expression
     * @param type type of expression
     */
    public ArithmeticNode(Object left, Object right, int type)
    {
        this.left = left;
        this.right = right;
        this.type = type;
    }

    /**
     * Gets this operation type.
     */
    public int getType()
    {
        return type;
    }

    /**
     * Gets the left hand side.
     * @return left hand side
     */
    public Object getLeft()
    {
        return left;
    }

    /**
     * Gets the right hand side.
     * @return right hand side.
     */
    public Object getRight()
    {
        return right;
    }

    /**
     * Dumps this node to standard out.
     * @throws AspException if an error occurs.
     * @see Node#dump()
     */
    public void dump() throws AspException
    {
        System.out.print("(");
        if (left instanceof Node) {
            ((Node)left).dump();
        } else {
            System.out.print(left);
        }
        switch(type)
        {
            case PLUS:System.out.print(" + ");break;
            case MINUS:System.out.print(" - ");break;
            case MULT:System.out.print(" * ");break;
            case DIV:System.out.print(" / ");break;
            case INTDIV:System.out.print(" \\ ");break;
            case CONCAT:System.out.print(" & ");break;
            case EQ:System.out.print(" = ");break;
            case GT:System.out.print(" > ");break;
            case LT:System.out.print(" < ");break;
            case NE:System.out.print(" <> ");break;
            case NOT:System.out.print(" NOT ");break;
            case OR:System.out.print(" OR ");break;
            case AND:System.out.print(" AND ");break;
            case EXP:System.out.print(" ^ ");break;
            case EQV:System.out.print(" EQV ");break;
            case IMP:System.out.print(" IMP ");break;
            case MOD:System.out.print(" MOD ");break;
            case XOR:System.out.print(" XOR ");break;
            case GE:System.out.print(" >= ");break;
            case LE:System.out.print(" <= ");break;
        }
        if (right instanceof Node) {
            ((Node)right).dump();
        } else {
            System.out.print(right);
        }
        System.out.print(")");
    }

    /**
     * Prepares this node for execution.
     * 
     * @param context Global context
     * @throws AspException if an error occurs.
     * @see Node#prepare(AspContext)
     */
    public void prepare(AspContext context) throws AspException
    {
        /* Nothing to do, the arguments to expressions should
         * in general not need prepared.
         */
    }

    /**
     * Dumps this node to standard out.
     * @param context AspContext of expression
     * @return evaluated value of this expression
     * @throws AspException if an error occurs.
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        Object avalObj=null, bvalObj=null;
        if (left != null) {
            avalObj = ((Node)left).execute(context);
            avalObj = Types.dereference(avalObj);
        }
        if (right != null) {
            bvalObj = ((Node)right).execute(context);
            bvalObj = Types.dereference(bvalObj);
        }
        switch(type)
        {
            case PLUS: {
                if (Constants.nullNode.equals(avalObj) ||
                        Constants.nullNode.equals(bvalObj)) {
                    return Constants.nullNode;
                } else if (Types.isDate(avalObj) && Types.isDate(bvalObj))
                {
                    AspDate aVal = Types.coerceToDate(avalObj);
                    AspDate bVal = Types.coerceToDate(bvalObj);
    
                    return doDateAdd(aVal, bVal);
                } else if (Types.isDate(avalObj)) {
                    final IdentNode dateAddIdent = new IdentNode("dateadd");

                    AspDate aval = Types.coerceToDate(avalObj);
                    Integer bval = Types.coerceToInteger(bvalObj);

                    AbstractFunctionNode func = (AbstractFunctionNode)
                        context.getValue(dateAddIdent);
                    Vector values = new Vector();
                    values.add("y");
                    values.add(bval);
                    values.add(aval);
                    return func.execute(values, context);
                } else if (Types.isDate(bvalObj)) {
                    final IdentNode dateAddIdent = new IdentNode("dateadd");

                    Integer aval = Types.coerceToInteger(avalObj);
                    AspDate bval = Types.coerceToDate(bvalObj);

                    AbstractFunctionNode func = (AbstractFunctionNode)
                        context.getValue(dateAddIdent);
                    Vector values = new Vector();
                    values.add("y");
                    values.add(aval);
                    values.add(bval);
                    return func.execute(values, context);
                } else if (avalObj instanceof String &&
                        bvalObj instanceof String) {
                    String aval = Types.coerceToString(avalObj);
                    String bval = Types.coerceToString(bvalObj);
                    return aval + bval;
                } else if (avalObj instanceof Double ||
                        bvalObj instanceof Double) {
                    Double aval = Types.coerceToDouble(avalObj);
                    Double bval = Types.coerceToDouble(bvalObj);
                    return new Double(aval.doubleValue() + bval.doubleValue());
                } else {
                    Integer aval = Types.coerceToInteger(avalObj);
                    Integer bval = Types.coerceToInteger(bvalObj);
                    return new Integer(aval.intValue() + bval.intValue());
                }
            }
            case MINUS: {
                if (avalObj instanceof NullNode || bvalObj instanceof NullNode)
                {
                    return Constants.nullNode;
                } else if (Types.isDate(avalObj) && Types.isDate(bvalObj)) {
                    final IdentNode dateDiffIdent = new IdentNode("datediff");

                    AspDate aval = Types.coerceToDate(avalObj);
                    AspDate bval = Types.coerceToDate(bvalObj);

                    AbstractFunctionNode func = (AbstractFunctionNode)
                        context.getValue(dateDiffIdent);
                    Vector values = new Vector();
                    values.add("y");
                    values.add(bval);
                    values.add(aval);
                    return func.execute(values, context);
                } else if (Types.isDate(avalObj)) {
                    final IdentNode dateAddIdent = new IdentNode("dateadd");

                    AspDate aval = Types.coerceToDate(avalObj);
                    Integer bval = new Integer(-Types.coerceToInteger(bvalObj)
                            .intValue());

                    AbstractFunctionNode func = (AbstractFunctionNode)
                        context.getValue(dateAddIdent);
                    Vector values = new Vector();
                    values.add("y");
                    values.add(bval);
                    values.add(aval);
                    return func.execute(values, context);
                } else if (avalObj instanceof Double || bvalObj instanceof Double) {
                    final Double zero = new Double(0);
                    Double aval;
                    if (avalObj == null) {
                        aval = zero;
                    } else {
                        aval = Types.coerceToDouble(avalObj);
                    }
                    Double bval = Types.coerceToDouble(bvalObj);
                    return new Double(aval.doubleValue() - bval.doubleValue());
                } else {
                    final Integer zero = new Integer(0);
                    Integer aval;
                    if (avalObj == null) {
                        aval = zero;
                    } else {
                        aval = Types.coerceToInteger(avalObj);
                    }
                    Integer bval = Types.coerceToInteger(bvalObj);
                    return new Integer(aval.intValue() - bval.intValue());
                }
            }
            case MULT: {
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj))
                {
                    return Constants.nullNode;
                } else if (avalObj instanceof Double ||
                        bvalObj instanceof Double) {
                    Double aval = Types.coerceToDouble(avalObj);
                    Double bval = Types.coerceToDouble(bvalObj);
                    return new Double(aval.doubleValue() * bval.doubleValue());
                } else {
                    Integer aval = Types.coerceToInteger(avalObj);
                    Integer bval = Types.coerceToInteger(bvalObj);
                    return new Integer(aval.intValue() * bval.intValue());
                }
            }
            case DIV: {
                if (Constants.nullNode.equals(avalObj) ||
                        Constants.nullNode.equals(bvalObj))
                    return Constants.nullNode;
                Double aval = Types.coerceToDouble(avalObj);
                Double bval = Types.coerceToDouble(bvalObj);
                return new Double(aval.doubleValue() / bval.doubleValue());
                }
            case INTDIV: {
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                    return Constants.nullNode;
                Integer aval = Types.coerceToInteger(avalObj);
                Integer bval = Types.coerceToInteger(bvalObj);
                return new Integer(aval.intValue() / bval.intValue());
                }
            case IS: {
                if (avalObj instanceof NothingNode)
                {
                    if (bvalObj instanceof NothingNode)
                        return new Boolean(true);
                    if (!(bvalObj instanceof JavaObjectNode))
                    {
                        throw new AspException("Object expected");
                    }
                    return new Boolean(false);
                }
                if (bvalObj instanceof NothingNode)
                {
                    if (!(avalObj instanceof JavaObjectNode))
                    {
                        throw new AspException("Object expected");
                    }
                    return new Boolean(false);
                }
                if (!(avalObj instanceof JavaObjectNode) ||
                    !(bvalObj instanceof JavaObjectNode))
                {
                    throw new AspException("Object expected");
                }
                Object avalSubObj = ((JavaObjectNode)avalObj).getSubObject();
                Object bvalSubObj = ((JavaObjectNode)bvalObj).getSubObject();
                return new Boolean(avalSubObj == bvalSubObj);
                }
            case MOD: {
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) {
                    return Constants.nullNode;
                } else if (avalObj instanceof Integer && 
                        bvalObj instanceof Integer) {
                    Integer aval = Types.coerceToInteger(avalObj);
                    Integer bval = Types.coerceToInteger(bvalObj);
                    return new Integer(aval.intValue() % bval.intValue());
                } else {
                    double aval = Types.coerceToDouble(avalObj).doubleValue();
                    double bval = Types.coerceToDouble(bvalObj).doubleValue();
                    return new Integer((int)(Math.round(aval) %
                        Math.round(bval)));
                }
            }
            case CONCAT: {
                if (Constants.nullNode.equals(avalObj) &&
                    Constants.nullNode.equals(bvalObj))
                    return Constants.nullNode;
                String aval = Types.coerceToString(avalObj);
                String bval = Types.coerceToString(bvalObj);
                return aval + bval;
                }
            case EQ:
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                        return Constants.nullNode;
                return new Boolean(compare(avalObj, bvalObj) == 0);
            case GT:
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                        return Constants.nullNode;
                return new Boolean(compare(avalObj, bvalObj) > 0);
            case GE:
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                        return Constants.nullNode;
                return new Boolean(compare(avalObj, bvalObj) >= 0);
            case LT:
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                        return Constants.nullNode;
                return new Boolean(compare(avalObj, bvalObj) < 0);
            case LE:
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                        return Constants.nullNode;
                return new Boolean(compare(avalObj, bvalObj) <= 0);
            case NE:
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                        return Constants.nullNode;
                return new Boolean(compare(avalObj, bvalObj) != 0);
            case NOT: {
                if (Constants.nullNode.equals(avalObj)) 
                    return Constants.nullNode;
                if (Types.isNumber(avalObj))
                {
                    int bval = Types.coerceToInteger(avalObj).intValue();
                    return new Integer((-bval)-1);
                } else {
                    Boolean bval = Types.coerceToBoolean(avalObj);
                    return new Boolean(!bval.booleanValue());
                }
                }
            case OR: {
                if (Constants.nullNode.equals(avalObj) &&
                    Constants.nullNode.equals(bvalObj))
                    return Constants.nullNode;
                if (Types.isNumber(avalObj) || Types.isNumber(bvalObj))
                    return doNumericOr(avalObj, bvalObj);
                else
                    return doLogicalOr(avalObj, bvalObj);
            }
            case AND: {
                if (Constants.nullNode.equals(avalObj) &&
                    Constants.nullNode.equals(bvalObj))
                    return Constants.nullNode;
                if (Types.isNumber(avalObj) || Types.isNumber(bvalObj))
                    return doNumericAnd(avalObj, bvalObj);
                else
                    return doLogicalAnd(avalObj, bvalObj);
            }
            case XOR: {
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj))
                        return Constants.nullNode;
                if (Types.isNumber(avalObj) || Types.isNumber(bvalObj))
                    return doNumericXor(avalObj, bvalObj);
                else
                    return doLogicalXor(avalObj, bvalObj);
            }
            case EQV: {
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                    return Constants.nullNode;
                Boolean aval = Types.coerceToBoolean(avalObj);
                Boolean bval = Types.coerceToBoolean(bvalObj);
                return new Boolean(aval.booleanValue()==bval.booleanValue());
            }
            case IMP: {
                boolean aval, bval;

                if (Constants.nullNode.equals(avalObj)) {
                    if (Constants.nullNode.equals(bvalObj))
                        return Constants.nullNode;
                    bval = Types.coerceToBoolean(bvalObj).booleanValue();
                    if (bval) return new Boolean(true);
                    return Constants.nullNode;
                } else if (Constants.nullNode.equals(bvalObj)) {
                    aval = Types.coerceToBoolean(avalObj).booleanValue();
                    if (!aval) return new Boolean(true);
                    return Constants.nullNode;
                }
    
                aval = Types.coerceToBoolean(avalObj).booleanValue();
                bval = Types.coerceToBoolean(bvalObj).booleanValue();
                if (!aval || aval && bval) {
                    return new Boolean(true);
                } else {
                    return new Boolean(false);
                }
            }
            case EXP: {
                if (Constants.nullNode.equals(avalObj) ||
                    Constants.nullNode.equals(bvalObj)) 
                    return Constants.nullNode;
                Double aval = Types.coerceToDouble(avalObj);
                Double bval = Types.coerceToDouble(bvalObj);
                return new Double(Math.pow(aval.doubleValue(), bval.doubleValue()));
            }
        }
        throw new AspException("Unknown operator type: " + type);
    }

    /**
     * Internal function to perform a logical AND operation.
     * @param avalObj left hand side, expected to be of type boolean or NULL
     * @param bvalObj right hand side, expected to be of type boolean or NULL
     * @return result of logical and
     */
    private Object doLogicalAnd(Object avalObj, Object bvalObj)
        throws AspException
    {
        boolean aval;
        boolean bval;
        if (Constants.nullNode.equals(bvalObj))
        {
            bvalObj = avalObj;
            avalObj = Constants.nullNode;
        }
        if (Constants.nullNode.equals(avalObj))
        {
            bval = Types.coerceToBoolean(bvalObj).booleanValue();
            if (!bval) return new Boolean(false);
            return Constants.nullNode;
        }
        aval = Types.coerceToBoolean(avalObj).booleanValue();
        bval = Types.coerceToBoolean(bvalObj).booleanValue();
        return new Boolean(aval&&bval);
    }

    /**
     * Internal function to perform a numeric AND operation.
     * @param avalObj left hand side, expected to be of type boolean or NULL
     * @param bvalObj right hand side, expected to be of type boolean or NULL
     * @return result of logical and
     */
    private Object doNumericAnd(Object avalObj, Object bvalObj)
        throws AspException
    {
        int aval;
        int bval;
        if (Constants.nullNode.equals(bvalObj))
        {
            bvalObj = avalObj;
            avalObj = Constants.nullNode;
        }
        if (Constants.nullNode.equals(avalObj))
        {
            bval = Types.coerceToInteger(bvalObj).intValue();
            if (bval == 0) return new Integer(0);
            return Constants.nullNode;
        }
        aval = Types.coerceToInteger(avalObj).intValue();
        bval = Types.coerceToInteger(bvalObj).intValue();
        return new Integer(aval&bval);
    }

    /**
     * Internal function to perform a logical OR operation.
     * @param avalObj left hand side, expected to be of type boolean or NULL
     * @param bvalObj right hand side, expected to be of type boolean or NULL
     * @return result of logical and
     */
    private Object doLogicalOr(Object avalObj, Object bvalObj)
        throws AspException
    {
        boolean aval;
        boolean bval;
        if (Constants.nullNode.equals(bvalObj))
        {
            bvalObj = avalObj;
            avalObj = Constants.nullNode;
        }
        if (Constants.nullNode.equals(avalObj))
        {
            bval = Types.coerceToBoolean(bvalObj).booleanValue();
            if (bval) return new Boolean(true);
            return Constants.nullNode;
        }
        aval = Types.coerceToBoolean(avalObj).booleanValue();
        bval = Types.coerceToBoolean(bvalObj).booleanValue();
        return new Boolean(aval||bval);
    }

    /**
     * Internal function to perform a numeric OR operation.
     * @param avalObj left hand side, expected to be of type boolean or NULL
     * @param bvalObj right hand side, expected to be of type boolean or NULL
     * @return result of logical and
     */
    private Object doNumericOr(Object avalObj, Object bvalObj)
        throws AspException
    {
        int aval;
        int bval;
        if (Constants.nullNode.equals(bvalObj))
        {
            bvalObj = avalObj;
            avalObj = Constants.nullNode;
        }
        if (Constants.nullNode.equals(avalObj))
        {
            bval = Types.coerceToInteger(bvalObj).intValue();
            if (bval != 0) return bvalObj;
            return Constants.nullNode;
        }
        aval = Types.coerceToInteger(avalObj).intValue();
        bval = Types.coerceToInteger(bvalObj).intValue();
        return new Integer(aval|bval);
    }

    /**
     * Internal function to perform a logical XOR operation.
     * @param avalObj left hand side, expected to be of type boolean or NULL
     * @param bvalObj right hand side, expected to be of type boolean or NULL
     * @return result of logical and
     */
    private Object doLogicalXor(Object avalObj, Object bvalObj)
        throws AspException
    {
        boolean aval;
        boolean bval;
        aval = Types.coerceToBoolean(avalObj).booleanValue();
        bval = Types.coerceToBoolean(bvalObj).booleanValue();
        return new Boolean(aval^bval);
    }

    /**
     * Internal function to perform a numeric XOR operation.
     * @param avalObj left hand side, expected to be of type boolean or NULL
     * @param bvalObj right hand side, expected to be of type boolean or NULL
     * @return result of logical and
     */
    private Object doNumericXor(Object avalObj, Object bvalObj)
        throws AspException
    {
        int aval;
        int bval;
        aval = Types.coerceToInteger(avalObj).intValue();
        bval = Types.coerceToInteger(bvalObj).intValue();
        return new Integer(aval^bval);
    }

    /**
     * Internal procedure used to compare any two expressions.
     * @param aObj object A
     * @param bObj object B
     * @return comparison of this expression, positive if a &gt; b,
     *   negative if a &lt; b, zero if a = b.
     * @throws AspException if an error occurs.
     * @see Node#execute(AspContext)
     */
    protected int compare(Object aObj, Object bObj) throws AspException
    {
        if (DBG.isDebugEnabled()) {
            DBG.debug("Compare aObj: " + aObj);
            if (aObj != null) DBG.debug("aObj type: " + aObj.getClass());
            DBG.debug("Compare bObj: " + bObj);
            if (bObj != null) DBG.debug("bObj type: " + bObj.getClass());
        }
        if (aObj instanceof UndefinedValueNode)
        {
            if (bObj instanceof UndefinedValueNode) return 0;
            if (bObj instanceof Integer)
            {
                int bInt = Types.coerceToInteger(bObj).intValue();
                return -bInt;
            } else {
                String bStr = Types.coerceToString(bObj);
                if (bStr.equals("0")) return 0; 
                return -bStr.compareTo("");
            }
        }
        if (bObj instanceof UndefinedValueNode)
        {
            if (aObj instanceof Integer)
            {
                int aInt = Types.coerceToInteger(aObj).intValue();
                return aInt;
            } else {
                String aStr = Types.coerceToString(aObj);
                if (aStr.equals("0")) return 0; 
                return aStr.compareTo("");
            }
        }
        if (Types.isDate(aObj) && Types.isDate(bObj))
        {
            Date aDate = ((AspDate)aObj).toDate();
            Date bDate = ((AspDate)bObj).toDate();
            return aDate.compareTo(bDate);
        }
        if (aObj instanceof Integer && bObj instanceof Integer)
        {
            int aInt = Types.coerceToInteger(aObj).intValue();
            int bInt = Types.coerceToInteger(bObj).intValue();
            if (aInt > bInt) return 1;
            if (aInt < bInt) return -1;
            return 0;
        }
        if ((aObj instanceof Double || aObj instanceof Integer) &&
            (bObj instanceof Double || bObj instanceof Integer))
        {
            double aDoub = Types.coerceToDouble(aObj).doubleValue();
            double bDoub = Types.coerceToDouble(bObj).doubleValue();
            if (aDoub > bDoub) return 1;
            if (aDoub < bDoub) return -1;
            return 0;
        }
        String aStr = Types.coerceToString(aObj);
        String bStr = Types.coerceToString(bObj);
        if (DBG.isDebugEnabled()) {
            DBG.debug("\"" + aStr + "\"<=>\"" + bStr + "\"");
        }
        return aStr.compareTo(bStr);
    }

    /**
     * This function performs an add function with two dates.
     * @param aDate left hand value
     * @param bDate right hand value
     * @return new date value
     */
    private static AspDate doDateAdd(AspDate aDate, AspDate bDate)
        throws AspException
    {
        if (aDate.hasTime() && bDate.hasDate()) {
            AspDate tmp = aDate;
            aDate = bDate;
            bDate = tmp;
        }
        if (bDate.hasDate())
            throw new AspNotImplementedException("Date + Date");
        if (!bDate.hasTime())
            throw new AspNotImplementedException("Date + Date");
        Calendar cal = aDate.toCalendar();
        cal.add(Calendar.HOUR, bDate.getHour());
        cal.add(Calendar.MINUTE, bDate.getMinute());
        cal.add(Calendar.SECOND, bDate.getSecond());
        return new AspDate(cal, true, true);
    }
};

