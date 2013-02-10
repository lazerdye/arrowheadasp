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

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.lang.reflect.*;
import org.apache.log4j.Category;

/**
 * JavaObjectNode class handles the interaction between Java objects
 * and ASP objects.
 * @author Terence Haddock
 * @version 0.9
 */
public class JavaObjectNode extends DefaultNode implements ObjectNode, MapNode
{
    /** Debugging category */
    private static final transient Category DBG = Category.getInstance(JavaObjectNode.class);

    /** Java object this ASP node is referencing */
    Object obj;

    /**
     * Constructor
     * @param obj Java object this ASP object is referencing
     */
    public JavaObjectNode(Object obj)
    {
        this.obj = obj;
    }

    /**
     * getField obtains a field of a Java object. Returns a JavaFieldNode
     * reference to this field.
     * @param ident Identifier of field to obtain    
     * @see ObjectNode#getField(IdentNode)
     */
    public Object getField(IdentNode ident)
    {
        if (DBG.isDebugEnabled())
            DBG.debug("getField: " + ident);
        String name = ident.ident;
        return new JavaFieldNode(obj, ident);
    }

    /**
     * Obtains the Java sub-object contained within this ASP node.
     * @return Java sub-object
     */
    public Object getSubObject()
    {
        return obj;
    }

    /**
     * Sets the value of this Java object, if the Java object implements
     * the SimpleReference object.
     * @param value Value to set
     * @throws AspException if an error occurs.
     */
    public void setValue(Object value) throws AspException
    {
        if (obj instanceof SimpleReference) {
            ((SimpleReference)obj).setValue(value);
        } else {
            throw new AspReadOnlyException();
        }
    }

    /**
     * Obtains an array index, or calls a function, of a Java object.
     * @param varlist Variable list of parameters.
     * @param context AspContext under which this Java object was called.
     * @return Value of the array or return value of the function.
     * @throws AspException if an exception occurs.
     * @see MapNode#getIndex(VarListNode,AspContext)
     */
    public Object getIndex(VarListNode varlist, AspContext context) throws AspException
    {
        /* See if this is a simple type of reference */
        if (varlist.size() == 0 && obj instanceof SimpleReference)
        {
            if (DBG.isDebugEnabled())
                DBG.debug("Trying simple reference");
            Object subValue = ((SimpleReference)obj).getValue();
            if (DBG.isDebugEnabled())
            {
                DBG.debug("Sub-value: " + subValue);
                if (subValue != null)
                    DBG.debug("Sub-value class: " +
                        subValue.getClass());
            }
            /* Return it as a Node object */
            return Types.coerceToNode(subValue);
        }
        /* Only single dimensional lists supported */
        if (varlist.size() != 1) {
            throw new AspInvalidArgumentsException(""+varlist.size());
        }
        /* Handle MapNode objects */
        if (obj instanceof MapNode) {
            return ((MapNode)obj).getIndex(varlist, context);
        }
        /* Handle Java arrays */
        if (obj.getClass().isArray()) {
            Vector vec = (Vector)varlist.execute(context);
            Integer i = Types.coerceToInteger(vec.get(0));
            Object sObj = Array.get(obj, i.intValue());
            return Types.coerceToNode(sObj);
        }
        /* Map and SimpleMap objects are all that is left */
        if (!(obj instanceof Map)&&!(obj instanceof SimpleMap)) {
            throw new AspException("Invalid argument: " + obj.getClass());
        }
        Vector vec = (Vector)varlist.execute(context);
        Object objVal = vec.get(0);
        if (!(objVal instanceof Integer) && !(objVal instanceof String))
            objVal = Types.coerceToString(objVal);
        if (DBG.isDebugEnabled())
            DBG.debug("getIndex(" + objVal + ")");
        if (obj instanceof Map) {
            return new JavaAccessorNode((Map)obj, objVal);
        } else {
            return new JavaAccessorNode((SimpleMap)obj, objVal);
        }
    }

    /**
     * Returns the upper bound of the array held within this JavaObjectNode.
     * Supports only single-dimensional Java arrays of objects.
     * @param dimension Which dimension to obtain
     * @return upper bound
     * @see MapNode#getUBOUND
     * @throws AspSubscriptOutOfRangeException if dimension does not equal 1
     */
    public int getUBOUND(int dimension) throws AspException
    {
        if (dimension != 1) {
            throw new AspSubscriptOutOfRangeException("UBOUND");
        }
        if (obj instanceof Object[]) {
            return ((Object[])obj).length - 1;
        }
        throw new AspException("LBOUND called on non-array");
    }

    /**
     * Returns the lower bound of the array contained within the Java object.
     * @param dimension Which dimension to obtain.
     * @return lower bound, usually 0
     * @see MapNode#getLBOUND
     * @throws AspSubscriptOutOfRangeExceptoin if dimension does not equal 1
     */
    public int getLBOUND(int dimension) throws AspException
    {
        if (dimension != 1) {
            throw new AspSubscriptOutOfRangeException("UBOUND");
        }
        if (obj instanceof Object[]) {
            return 0;
        }
        throw new AspException("LBOUND called on non-array");
    }

    /**
     * Converts this JavaObjectNode to a string, for debugging.
     * @return String representation of this Java object
     */
    public String toString()
    {
        if (obj == null)
        {
            return "{JavaObjectNode=null}";
        } else {
            return "{JavaObjectNode=" + obj.toString() + "(" + obj.getClass() + ")}";
        }
    }

    /**
     * JavaAccessorNode class handles the obtaining of object
     * within Java objects by strings.
     */
    static class JavaAccessorNode extends DefaultNode implements SimpleReference
    {
        /** simpleMapObj is used if the sub-object is an instance of the
          * SimpleMap interface.
          */
        SimpleMap simpleMapObj;

        /**
         * mapObj isused if the sub-object is an instance of the Map interface.
          */
        Map mapObj;

        /**
         * The key being obtained from the map.
         */
        Object key;

        /**
          * Constructor, Map form.
         * @param obj Map object
         * @param str String being referenced
         */
        public JavaAccessorNode(Map obj, Object key)
        {
            this.mapObj = obj;
            this.key = key;
        }

        /**
         * Constructor, SimpleMap form.
         * @param obj SimpleMap object.
         * @param str String being referenced.
         */
        public JavaAccessorNode(SimpleMap obj, Object key)
        {
            this.simpleMapObj = obj;
            this.key = key;
        }

        /**
         * getValue is a SimpleReference method which obtains the value
         * of the current reference.
          * @return value of the current reference
          */
        public Object getValue() throws AspException
        {
            Object ret;
            if (mapObj != null) {
                ret = Types.coerceToNode(mapObj.get(key));
                if (DBG.isDebugEnabled())
                    if (ret != null)
                        DBG.debug("Getting value of " + mapObj +  " = "
                            + ret + "(" + ret.getClass() + ")");
                    else
                        DBG.debug("Getting value of " + simpleMapObj +  " = null");
            } else {
                ret = Types.coerceToNode(simpleMapObj.get(key));
                if (DBG.isDebugEnabled())
                    if (ret != null)
                        DBG.debug("Getting value of " + simpleMapObj +  " = " +
                            ret + "(" + ret.getClass() + ")");
                    else
                        DBG.debug("Getting value of " + simpleMapObj +  " = null");
            }
            return ret;
        }

        /**
         * setValue sets the value of the current reference.
         * @param val New value
         */
        public void setValue(Object val) throws AspException
        {
            val = Types.dereference(val);
            if (mapObj != null) {
                mapObj.put(key, val);
            } else {
                simpleMapObj.put(key, val);
            }
        }
    }

    /**
     * The JavaFieldNode class handles referencing fields within Java objects.
     */
    static class JavaFieldNode extends DefaultNode implements FunctionNode, SimpleReference
    {
        /** Global cache of data about classes */
        static private Map classData = new java.util.WeakHashMap();

        /** The object being referenced by this class */
        Object obj;

        /** The identifier of the field being referenced */
        IdentNode ident;

        /** Vector of methods which match the identifier being referenced */
        Vector matchingMethods;

        /** Vector of fields which match the identifier being referenced */
        Vector matchingFields;
    
        /**
          * Constructor.
         * @param obj Object being referenced
         * @param field IdentNode identifier of field being referenced
          */
        JavaFieldNode(Object obj, IdentNode field)
        {
            this.obj = obj;
            this.ident = field;
            matchingMethods = getMatchingMethods(obj.getClass(), field);
            matchingFields = getMatchingFields(obj.getClass(), field);
        }

        /**
         * Executes this field given a parameter list.
         * @param vars Parameters to execute.
         * @param context Current context
         * @see FunctionNode#execute(VarListNode, AspContext)
         */
        public Object execute(VarListNode vars, AspContext context)
            throws AspException
        {
            if (DBG.isDebugEnabled())
                DBG.debug("Execute " + ident + 
                    " on " + obj.getClass() + " with " +
                    vars.size() + " parameters");

            Vector values = executeAndDereference(vars, context);
            Method met = findMethod(values);

            if (met == null) {
                if (DBG.isDebugEnabled())
                    DBG.debug("Associated method " +
                    "not found, trying field access " +
                    "on " + obj);
                /* Get the sub-object */
                Object subObj = getValue();
                /* Call the sub-object's function */
                if (subObj instanceof MapNode) {
                    return ((MapNode)subObj).getIndex(vars, context);
                }
                throw new AspException("Unknown function: " + ident);
            }
            Object res = executeMethod(met, values);

            Class paramTypes[] = met.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++)
            {
                if (paramTypes[i] == ByRefValue.class) {
                    Object finalValue = values.get(i);
                    Object calledValue = vars.get(i);
                    if (calledValue instanceof IdentNode) {
                        context.setValue((IdentNode)calledValue, finalValue);
                    } else if (calledValue instanceof SimpleReference) {
                        ((SimpleReference)calledValue).setValue(finalValue);
                    } else {
                        /* Cannot set value */
                    }
                }
            }
            
            return res;
        }

        /**
         * Sets the value of a field.
         * @param setObj new value of field.
         * @throws AspException if an error occurs
         * @see SimpleReference#setValue(Object)
         */
        public void setValue(Object setObj) throws AspException
        {
            if (matchingFields.size() == 0 && matchingMethods.size() == 0)
            {
                throw new AspException("Unknown field name: " + ident);
            }
            if (matchingFields.size()<1)
            {
                VarListNode vl = new VarListNode();
                vl.append(setObj);
                Vector vec = executeAndDereference(vl, null);
                Method met = findMethod(vec);
                if (met != null) {
                    executeMethod(met, vec);
                    return;
                }
                throw new AspException("Unknown field name: " + ident);
            }
            while (setObj instanceof JavaObjectNode) {
                if (DBG.isDebugEnabled())
                    DBG.debug("Dereferencing: " + setObj);
                setObj = ((JavaObjectNode)setObj).getSubObject();
            }
            setObj = Types.dereference(setObj);
            if (matchingFields.size()>1)
            {
                throw new AspException("Too many fields match: " + ident);
            }
            Field field = (Field)matchingFields.get(0);
            try {
                if (DBG.isDebugEnabled())
                    DBG.debug("Setting field " + field +
                        " = " + setObj);
                field.set(obj, setObj);
            } catch (IllegalAccessException ex)
            {
                throw new AspException("Illegal Access Exception: " + ex.toString());
            }
        }

        /**
         * Obtains the value of a field.
         * @return the value of the current field.
         * @throws AspException if an error occurs
         * @see SimpleReference#getValue
          */
        public Object getValue() throws AspException
        {
            if (DBG.isDebugEnabled())
                DBG.debug("Getvalue of field " + ident +
                    " of object: " + obj);
            if (matchingFields.size()<1)
            {
                Vector vec = new Vector();
                Method met = findMethod(vec);
                if (met != null) {
                    return executeMethod(met, vec);
                }
                throw new AspException("Unknown field name: " + ident);
            }
            if (matchingFields.size()>1)
            {
                throw new AspException("Too many fields match: " + ident);
            }
            Field field = (Field)matchingFields.get(0);
            if (DBG.isDebugEnabled())
                DBG.debug("One field matches: " + field);
            try {
                return Types.coerceToNode(field.get(obj));
            } catch (IllegalAccessException ex)
            {
                throw new AspException("Illegal Access Exception: " + ex.toString());
            }
        }

        /**
         * Internal function which executes and dereferences
         * the list of parameters in a VarListNode.
         * @param params Parameters to execute and dereference
          * @param context AspContext under which to execute the variables.
         * @return The list of parameters executed and dereferenced.
         * @throws AspException if an error occurs.
         */
        static private Vector executeAndDereference(VarListNode params,
            AspContext context) throws AspException
        {
            Vector vec = (Vector)params.execute(context);
            Vector deref = new Vector();
            for (int i = 0; i < vec.size(); i++)
            {
                Object value = vec.get(i);
                if (value instanceof JavaObjectNode) {
                    value = ((JavaObjectNode)value).
                        getSubObject();
                }
                deref.add(Types.dereference(value));
            }
            return deref;
        }

        /**
         * Internal method which finds a method which matches the
         * parameter list given.
         * Uses the current field, method name.
         * @param values Parameters for the method call.
         * @return Method which matches the given parameters.
         */
        private Method findMethod(Vector values)
        {
            int curScore = 0;
            Method curMethod = null;
            if (DBG.isDebugEnabled()) DBG.debug("Number of methods: " +
                matchingMethods.size());
            for (int i = 0; i < matchingMethods.size(); i++)
            {
                Method method = (Method)matchingMethods.get(i);
                Class params[] = method.getParameterTypes();
                int score = parametersAreCompatible(params, values);
                if (score > curScore)
                {
                    curScore = score;
                    curMethod = method;
                }
                if (DBG.isDebugEnabled())
                {
                    DBG.debug("Score: " + score);
                    DBG.debug("curScore: " + curScore);
                    DBG.debug("curMethod: " + curMethod);
                }
            }
            return curMethod;
        }

        /**
         * Internal method which executes a method with the given parameter
         * list.
         * @param met Method to execute
         * @param parameter Parameters for the method.
         * @return return value of method call
         * @throws AspException if an error ocurrs.
         */
        private Object executeMethod(Method met, Vector params)
            throws AspException
        {
            Class paramTypes[] = met.getParameterTypes();

            Object varObjs[] = new Object[params.size()];
            for (int i = 0; i < varObjs.length;i++)
            {
                if (paramTypes[i] == Integer.class ||
                    paramTypes[i] == int.class) {
                    varObjs[i] = Types.
                        coerceToInteger(params.get(i));
                } else if (paramTypes[i] == Long.class ||
                     	paramTypes[i] == long.class) {
                    varObjs[i] = Types.
                    	coerceToLong(params.get(i));
                } else if (paramTypes[i] == Boolean.class ||
                    paramTypes[i] == boolean.class) {
                    varObjs[i] = Types.
                        coerceToBoolean(params.get(i));
                } else if (paramTypes[i] == String.class) {
                    varObjs[i] = Types.
                        coerceToString(params.get(i));
                } else if (paramTypes[i] == java.util.Date.class) {
                    Object parm = params.get(i);
                    if (parm instanceof UndefinedValueNode ||
                        parm instanceof NullNode) {
                        varObjs[i] = null;
                    } else {
                        varObjs[i] = Types.coerceToDate(parm).toDate();
                    }
                } else if (paramTypes[i] == ByRefValue.class) {
                    ByRefValue newNode = new ByRefValue(params.get(i));
                    varObjs[i] = newNode;
                } else {
                    Object parm = params.get(i);
                    if (parm instanceof UndefinedValueNode ||
                        parm instanceof NullNode) {
                        varObjs[i] = null;
                    } else if (parm instanceof PackedCharArrayNode) {
                        varObjs[i] = ((PackedCharArrayNode)params.get(i)).
                                                internGetValues();
                    } else if (parm instanceof PackedByteArrayNode) {
                        varObjs[i] = ((PackedByteArrayNode)params.get(i)).
                                                internGetValues();
                    } else if (parm instanceof ArrayNode) {
                        varObjs[i] = ((ArrayNode)params.get(i)).toJavaArray();
                    } else {
                        varObjs[i] = params.get(i);
                    }
                }
            }
            try {
                Object retVal = met.invoke(obj, varObjs);
                if (DBG.isDebugEnabled())
                    DBG.debug("Return value = " + retVal);
                for (int i = 0; i < params.size(); i++) {
                    if (paramTypes[i] == ByRefValue.class) {
                        params.set(i,((ByRefValue)varObjs[i]).getValue());
                    }
                }
                return Types.coerceToNode(retVal);
            } catch (IllegalAccessException ex)
            {
                DBG.error("Error while calling Java object", ex);
                throw new AspNestedException(ex);
            } catch (InvocationTargetException ex)
            {
                Throwable targetEx = ex.getTargetException();
                if (targetEx != null)
                {
                    if (targetEx instanceof AspException) {
                        throw (AspException)targetEx;
                    }
                }
                DBG.error("Error while calling Java object", ex);
                throw new AspNestedException(ex);
            }
        }

        /**
         * Internal method which tests how compatible a list of classes
         * are to a parameters list.
         * Scores are as follows:
         * 2 = Exact match
         * 1 = Coersion possible
         * 0 = Coersion not possible.
         * @param types Parameter types to test
         * @param vars Parameter values
         * @return score
         */
        static private int parametersAreCompatible(Class types[],
            Vector vars)
        {
            int score = 0;
            if (types.length != vars.size()) return 0;
            /* Special case, no parameters */
            if (types.length == 0) return 2;
            for (int i = 0; i < types.length; i++)
            {
                Object value = vars.get(i);
                Class valClass = null;

                if (DBG.isDebugEnabled())
                {
                    DBG.debug("Value: " + value);
                    DBG.debug("Value(class): " + value.getClass());
                    DBG.debug("Class: " + types[i]);
                }

                if (value instanceof ArrayNode) {
                    if (DBG.isDebugEnabled())
                        DBG.debug("Converted object to List");
                    value = ((ArrayNode)value).toJavaArray();
                } else 
                if (value instanceof PackedCharArrayNode) {
                    if (DBG.isDebugEnabled())
                        DBG.debug("Converted object to char[]");
                    value = ((PackedCharArrayNode)value).internGetValues();
                } else
                if (value instanceof PackedByteArrayNode) {
                    if (DBG.isDebugEnabled())
                        DBG.debug("Converted object to byte[]");
                    value = ((PackedByteArrayNode)value).internGetValues();
                }
                if (value != null) valClass = value.getClass();

                if (DBG.isDebugEnabled()) DBG.debug("valClass: " + valClass);
    
                if ((value == null || valClass == NullNode.class
                    || valClass == UndefinedValueNode.class)
                    &&
                    Object.class.isAssignableFrom(types[i]))
                {
                    score += 2;
                } else
                if (types[i] == valClass)
                {
                    score += 2;
                } else
                if (types[i] == ByRefValue.class) {
                    score += 1;
                } else
                if ((types[i] == java.util.Date.class) &&
                    value instanceof AspDate) {
                    score += 2;
                } else
                if ((types[i] == AspDate.class) &&
                        value instanceof java.util.Date) {
                    score += 2;
                } else
                if ((types[i] == Boolean.class) ||
                    (types[i] == boolean.class)) {
                    /* XXX The score=2 case may be handled above */
                    if ((valClass == Boolean.class) ||
                        (valClass == boolean.class)) score += 2;
                    else
                        score += 1;
                } else
                if (types[i] == String.class) {
                    /* The exact case is handled above */
                    score += 1;
                } else
                if ((types[i] == int.class) ||
                    (types[i] == Integer.class) ||
                    (types[i] == long.class) ||
                    (types[i] == Long.class) ||
                    (types[i] == double.class) ||
                    (types[i] == Double.class) ||
                    (types[i] == float.class) ||
                    (types[i] == Float.class))
                {
                    score += 1;
                } else
                if (types[i].isInstance(value))
                {
                    score += 2;
                }
                if (DBG.isDebugEnabled())
                    DBG.debug("Cumulative score: " + score);
            }
            return score;
        }
            
        /**
         * Internal method which obtains cached class information,
         * class methods and fields.
         * @param cls Class to obtain data of
         * @return hashtable containin class data
         */
        static private Map getClassData(Class cls)
        {
            Map ht;
            synchronized(classData)
            {
                ht = (Map)classData.get(cls);
                if (ht == null) {
                    ht = new HashMap();
                    ht.put("allMethods", getPublicMethods(cls));
                    ht.put("allFields", getPublicFields(cls));
                    ht.put("methods", new HashMap());
                    ht.put("fields", new HashMap());
                    classData.put(cls, ht);
                }
            }
            return ht;
        }

        /**
         * Gets the publically accessible fields of this class.
         * @param cls class to get fields of
         * @return list of publically accessible fields
         */
        private static List getPublicFields(Class cls) {
            if (DBG.isDebugEnabled())
            {
                DBG.debug("Getting public fields for: " + cls);
                DBG.debug("Modifiers: " + cls.getModifiers());
            }
            List retList = new java.util.ArrayList();
            /* Is this class accessible? */
            if ((cls.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC)
            {
                if (DBG.isDebugEnabled()) DBG.debug("Class is public");
                Field fields[] = cls.getFields();
                for (int i = 0; i < fields.length; i++)
                {
                    Field field = fields[i];
                    if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC)
                    {
                        if (DBG.isDebugEnabled()) DBG.debug("Adding field: " + field);
                        retList.add(field);
                    }
                }
            } else if (!cls.isInterface()) {
                /* Try fields on the integerface(s) */
                Class interfaces[] = cls.getInterfaces();
                for (int i = 0; i < interfaces.length; i++)
                {
                    retList.addAll(getPublicFields(interfaces[i]));
                }
                Class superClass = cls.getSuperclass();
                if (superClass != null) retList.addAll(getPublicFields(cls.getSuperclass()));
            }
            return retList;
        }

        /**
         * Gets the publically accessible methods of this class.
         * @param cls class to get methods of
         * @return list of publically accessible methods
         */
        private static List getPublicMethods(Class cls) {
            List retList = new java.util.ArrayList();
            /* Is this class accessible? */
            if ((cls.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC)
            {
                Method methods[] = cls.getMethods();
                for (int i = 0; i < methods.length; i++)
                {
                    Method method = methods[i];
                    if ((method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC)
                    {
                        retList.add(method);
                    }
                }
            } else
            if (!cls.isInterface())
            {
                /* Add all of the interfaces */
                Class interfaces[] = cls.getInterfaces();
                for (int i = 0; i < interfaces.length; i++)
                {
                    retList.addAll(getPublicMethods(interfaces[i]));
                }
                /* Try the superclass if it exists */
                Class superClass = cls.getSuperclass();
                if (superClass != null)
                {
                    retList.addAll(getPublicMethods(cls.getSuperclass()));
                }
            }
            return retList;
        }

        /**
         * Internal function which obtains the list of methods
         * of a class which matches a given name (case insensitive).
         * @param cls Class to obtain methods of
         * @param ident Class name (case insensitive)
         * @return vector of matching classes.
         */
        static synchronized Vector getMatchingMethods(Class cls, IdentNode ident)
        {
            if (DBG.isDebugEnabled()) DBG.debug("Class: " + cls);
            Map classData = getClassData(cls);
            Map cachedMethods = (Map)classData.get("methods");
            synchronized(cachedMethods)
            {
                if (!cachedMethods.containsKey(ident))
                {
                    Vector matchingMethods = new Vector();
    
                    if (DBG.isDebugEnabled())
                    {
                        DBG.debug("Getting methods for: " + cls);
                        DBG.debug("Modifiers: " + cls.getModifiers());
                        Class interfaces[] = cls.getInterfaces();
                        for (int i = 0; i < interfaces.length; i++)
                        {
                            DBG.debug("Interface[" + i + "]= " + interfaces[i]);
                        }
                        Class sup = cls.getSuperclass();
                        while (sup != Object.class)
                        {
                            DBG.debug("Extends: " + sup.getSuperclass());
                            DBG.debug("Ext.Mod: " + sup.getModifiers());
                            interfaces = sup.getInterfaces();
                            for (int i = 0; i < interfaces.length; i++)
                            {
                                DBG.debug("Interface[" + i + "]= " + interfaces[i]);
                            }
                            sup = sup.getSuperclass();
                        }
                    }
                    List methods = (List)classData.get("allMethods");
                    for (int i = 0; i < methods.size(); i++)
                    {
                        Method method = (Method)methods.get(i);
                        if (method.getName().equalsIgnoreCase(ident.ident))
                        {
                            matchingMethods.add(method);
                        }
                    }
                    cachedMethods.put(ident, matchingMethods);
                    return matchingMethods;
                } else {
                    return (Vector)cachedMethods.get(ident);
                }
            }
        }

        /**
         * Internal function which finds the list of fields which match
         * a given name (case insensitive).
         * @param cls Class to find fields of
         * @param ident String name of field to find
         * @return list of matching fields
         */
        static synchronized Vector getMatchingFields(Class cls, IdentNode ident)
        {
            Map classData = getClassData(cls);
            Map cachedFields =
                (Map)classData.get("fields");
            synchronized(cachedFields)
            {
                if (!cachedFields.containsKey(ident))
                {
                    Vector matchingFields = new Vector();
    
                    List fields = (List)classData.get("allFields");
                    for (int i = 0; i < fields.size(); i++)
                    {
                        Field field = (Field)fields.get(i);
                        if (field.getName().equalsIgnoreCase(ident.ident))
                        {
                            matchingFields.add(field);
                        }
                    }
                    cachedFields.put(ident, matchingFields);
                    return matchingFields;
                } else {
                    return (Vector)cachedFields.get(ident);
                }
            }
        }
    }
}
